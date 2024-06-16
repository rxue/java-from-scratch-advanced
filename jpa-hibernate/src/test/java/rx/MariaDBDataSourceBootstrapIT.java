package rx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.transaction.*;
import org.junit.jupiter.api.Test;
import rx.jpa.TestEntity;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class MariaDBDataSourceBootstrapIT extends AbstractITConfigTemplate {
    @Test
    public void bootstrapHibernateJPAWithDataSource() {
        assertNotNull(emf);
    }
    @Test
    public void useEntityManagerAfterBootstrap() {
        try (EntityManager em = emf.createEntityManager()) {
            TestEntity newEntity = new TestEntity();
            em.persist(newEntity);
        }

        executeInJDBCConnection(s -> {
            ResultSet rs = s.executeQuery("select * from TestEntity");
            assertFalse(rs.next());
        });
    }

    @Test
    public void useEntityManagerAfterBootstrap_withUserTransaction_commit() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        tx.begin();
        try (EntityManager em = emf.createEntityManager()) {
            TestEntity newEntity = new TestEntity();
            em.persist(newEntity);
        }
        tx.commit();
        executeInJDBCConnection(s -> {
            ResultSet rs = s.executeQuery("select * from TestEntity");
            assertTrue(rs.next());
        });
    }

    @Test
    public void useEntityManagerAfterBootstrap_withUserTransaction_rollback() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        tx.begin();
        try (EntityManager em = emf.createEntityManager()) {
            TestEntity newEntity = new TestEntity();
            em.persist(newEntity);
        }
        tx.rollback();
        executeInJDBCConnection(s -> {
            ResultSet rs = s.executeQuery("select * from TestEntity");
            assertFalse(rs.next());
        });
    }
}

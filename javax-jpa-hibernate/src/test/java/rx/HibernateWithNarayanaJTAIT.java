package rx;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariJNDIFactory;
import org.junit.jupiter.api.Test;
import org.osjava.sj.SimpleJndiContextFactory;
import rx.jpa.TestEntity;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import javax.transaction.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateWithNarayanaJTAIT extends AbstractITConfigTemplate {
    @Test
    public void testUserTransactionCommit() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        tx.begin();
        EntityManager em = emf.createEntityManager();
        TestEntity e = new TestEntity();
        em.persist(e);
        em.close();
        tx.commit();

        EntityManager em2 = emf.createEntityManager();
        List<TestEntity> result = em2.createQuery("select e from TestEntity e")
                .getResultList();
        assertFalse(result.isEmpty());
        em2.close();
    }
    @Test
    public void testUserTransactionRollback() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        tx.begin();
        EntityManager em = emf.createEntityManager();
        TestEntity e = new TestEntity();
        em.persist(e);
        em.close();
        tx.rollback();

        EntityManager em2 = emf.createEntityManager();
        List<TestEntity> result = em2.createQuery("select e from TestEntity e")
                .getResultList();
        assertTrue(result.isEmpty());
        em2.close();
    }
}

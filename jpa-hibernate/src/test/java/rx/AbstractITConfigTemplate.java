package rx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;
import rx.util.Util;

import java.sql.Statement;
import java.util.Collections;

public abstract class AbstractITConfigTemplate {
    private static MariaDBContainer<?> db;
    protected static EntityManagerFactory emf;

    @BeforeAll
    public static void init() {
        System.setProperty("port", "3307");
        db = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.8"));
        db.setPortBindings(Collections.singletonList(Util.getPortNumber() + ":3306"));
        db.start();
        emf = Persistence.createEntityManagerFactory("mariadb-test");
    }
    @BeforeEach
    protected void clean() {
        executeInJDBCConnection(s -> s.execute("delete from TestEntity"));
    }

    protected static void executeInJDBCConnection(SQLStatementConsumer<Statement> consumer) {
        try (EntityManager em = emf.createEntityManager()) {
            Session session = em.unwrap(Session.class);
            session.doWork(connection -> {
                try (Statement stmt = connection.createStatement()) {
                    consumer.accept(stmt);
                }
            });
        }
    }

    @AfterAll
    public static void destroy() {
        emf.close();
        db.stop();
    }
}

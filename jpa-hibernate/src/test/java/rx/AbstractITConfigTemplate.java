package rx;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.function.Consumer;

public abstract class AbstractITConfigTemplate {
    private static MariaDBContainer<?> db;
    protected static EntityManagerFactory emf;

    @BeforeAll
    public static void init() {
        db = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.8"));
        db.setPortBindings(Collections.singletonList("3307:3306"));
        db.start();
        emf = Persistence.createEntityManagerFactory("mariadb-test");
    }
    @BeforeEach
    protected void clean() {
        executeInJDBCConnection(s -> s.execute("delete from TestEntity"));
    }

    protected static void executeInJDBCConnection(SQLStatementConsumer<Statement> consumer) {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3307/test", "root", "test");
             Statement stmt = conn.createStatement()) {
            consumer.accept(stmt);
        } catch (SQLException e) {
            System.out.println("JDBC execution failure");
        }
    }

    @AfterAll
    public static void destroy() {
        emf.close();
        db.stop();
    }
}

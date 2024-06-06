package rx;

import com.zaxxer.hikari.HikariJNDIFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mariadb.jdbc.MariaDbDataSource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import javax.naming.Context;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

public abstract class AbstractITConfigTemplate {
    private static MariaDBContainer<?> db;
    protected static EntityManagerFactory emf;

    @BeforeAll
    public static void initEnvironment() {
        db = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.8"));
        db.setPortBindings(Collections.singletonList("3307:3306"));
        db.start();
        System.setProperty("org.osjava.sj.root","src/test/resources/hikaricp");
        System.setProperty(Context.OBJECT_FACTORIES, HikariJNDIFactory.class.getName());
        emf = Persistence.createEntityManagerFactory("mariadb-test");

    }
    @AfterEach
    public void truncateTables() throws SQLException {
        String connectionUrl = "jdbc:mariadb://localhost:3307/test";
        String user = "root";
        String password = "test";
        try(Connection conn = DriverManager.getConnection(connectionUrl, user, password);
            Statement stmt = conn.createStatement()) {
            stmt.execute("truncate table TestEntity");
        }
    }

    @AfterAll
    public static void destroy() {
        emf.close();
        db.stop();
    }
}

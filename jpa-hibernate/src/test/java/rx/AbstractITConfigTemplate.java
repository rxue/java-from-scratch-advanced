package rx;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

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

    @AfterAll
    public static void destroy() {
        emf.close();
        db.stop();
    }
}

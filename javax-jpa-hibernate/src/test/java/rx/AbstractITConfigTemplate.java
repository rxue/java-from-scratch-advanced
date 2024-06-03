package rx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

public abstract class AbstractITConfigTemplate {
    private MariaDBContainer<?> db;

    @BeforeEach
    public void init() {
        db = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.8"));
        db.setPortBindings(Collections.singletonList("3307:3306"));
        db.start();
    }

    @AfterEach
    public void destroy() {
        db.stop();
    }
}

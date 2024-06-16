package rx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import org.osjava.sj.MariaDBDataSourceJNDIObjectFactory;
import rx.naming.spi.LocalJTAInitialContextFactory;

import javax.naming.Context;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MariaDBDataSourceBootstrapIT extends AbstractITConfigTemplate {
    /**
     * based on
     *  - https://mariadb.com/kb/en/about-mariadb-connector-j/
     */
    @Test
    public void bootstrapHibernateJPAWithDataSource() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb-test");//, Collections.singletonMap("hibernate.connection.datasource",ds));
        assertNotNull(emf);
    }
}

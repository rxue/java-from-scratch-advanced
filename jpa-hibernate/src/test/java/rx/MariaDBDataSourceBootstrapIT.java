package rx;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

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

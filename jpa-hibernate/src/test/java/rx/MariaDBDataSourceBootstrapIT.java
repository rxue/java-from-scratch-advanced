package rx;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MariaDBDataSourceBootstrapIT extends AbstractITConfigTemplate {
    @Test
    public void bootstrapHibernateJPAWithDataSource() {
        assertNotNull(emf);
    }
}

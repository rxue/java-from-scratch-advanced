package rx;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HikariCPBootstrapIT extends AbstractITConfigTemplate {
    /**
     * based on
     *  - https://mariadb.com/kb/en/about-mariadb-connector-j/
     *  and can learn from
     *  - https://github.com/brettwooldridge/HikariCP?tab=readme-ov-file#rocket-initialization
     */
    @Test
    public void testDataSource() throws SQLException {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://localhost:3307/test");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "test");
        ds.setAutoCommit(false);
        assertNotNull(ds.getConnection());

    }
}

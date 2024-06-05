package rx;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariJNDIFactory;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

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
    /**
     * reference:
     *
     * https://github.com/h-thurow/Simple-JNDI/blob/master/src/test/java/hikari/HikariTest.java
     *
     * @throws NamingException
     */
    @Test
    public void testBootstrapHikariCPWithInitialContext() throws NamingException {

        final Properties env = new Properties();
        env.put("org.osjava.sj.root", "src/test/java/hikari/roots/HikariCP");
        env.put("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
        env.put("org.osjava.sj.jndi.shared", "true");
        env.put("org.osjava.sj.delimiter", ".");
        env.put("jndi.syntax.separator", "/");
        env.put(Context.OBJECT_FACTORIES, HikariJNDIFactory.class.getName());
        InitialContext ctx = new InitialContext(env);
        DataSource ds = (DataSource) ctx.lookup("HikariDataSource");
        assertNotNull(ds);
        /*Properties properties = new Properties();
        properties.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.osjava.sj.SimpleJndiContextFactory");
        properties.put("org.osjava.sj.jndi.shared","true");
        properties.put("org.osjava.sj.space","java:/comp/env");
        InitialContext initialContext = new InitialContext(properties);
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://localhost:3307/test");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "test");
        ds.setAutoCommit(false);
        initialContext.bind("java:/comp/env/jboss/datasources/MariaDBDS", ds);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb-test");
        assertNotNull(emf);*/
    }
}

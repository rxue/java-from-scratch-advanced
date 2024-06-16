package rx.naming.spi;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.naming.*;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class LocalJTAInitialContextFactory implements InitialContextFactory {
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Context getInitialContext(Hashtable<?, ?> hashtable) throws NamingException {
        //final Properties env = new Properties();
        //env.put(Context.INITIAL_CONTEXT_FACTORY, this.getClass().getName());
        Context context = new MemoryContext();
        try {
            MariaDbDataSource ds = new MariaDbDataSource(properties.getProperty("dataSource.url"));
            ds.setUser("root");
            ds.setPassword("password");
            context.bind("mariaDBDataSource", ds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}

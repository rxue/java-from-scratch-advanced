package rx.naming.spi;

import org.mariadb.jdbc.MariaDbDataSource;
import rx.util.Util;

import javax.naming.*;
import javax.naming.spi.InitialContextFactory;
import java.sql.SQLException;
import java.util.*;

public class LocalJTAInitialContextFactory implements InitialContextFactory {
    @Override
    public Context getInitialContext(Hashtable<?, ?> hashtable) {
        Context context = new MemoryContext();
        try {
            MariaDbDataSource ds = new MariaDbDataSource("jdbc:mariadb://localhost:" + Util.getPortNumber() + "/test");
            context.bind("mariaDBDataSource", ds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}

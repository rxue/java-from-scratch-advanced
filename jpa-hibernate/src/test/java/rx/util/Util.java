package rx.util;

import jakarta.transaction.UserTransaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Util {
    private Util() {}
    private static final String PROPERTY_FILE_LOCATION = "src/test/resources/datasource.properties";
    public static UserTransaction userTransaction() {
        return com.arjuna.ats.jta.UserTransaction.userTransaction();
    }

    public static String getJdbcURL() {
        final String property = "dataSource.url";
        final String valueFromSystem = System.getProperty(property);
        if (valueFromSystem != null)
            return valueFromSystem;
        return getPropertyFromFile(property).orElseThrow(() -> new IllegalStateException("Property " + property + " is missing. This property has to be defined either as system property "));
    }
    private static Optional<String> getPropertyFromFile(String property) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(PROPERTY_FILE_LOCATION));
            final String value = props.getProperty(property);
            return Optional.ofNullable(value);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

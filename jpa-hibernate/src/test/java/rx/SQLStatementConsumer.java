package rx;

import java.sql.SQLException;

@FunctionalInterface
interface SQLStatementConsumer<Statement> {
    void accept(Statement statement) throws SQLException;
}

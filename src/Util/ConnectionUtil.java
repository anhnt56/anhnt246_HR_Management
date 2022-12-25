package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public class ConnectionUtil {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=JAVA_ASSIGN7", // {protocol}:{vendor}:{database information}
                "sa",
                "12345");
    }
}

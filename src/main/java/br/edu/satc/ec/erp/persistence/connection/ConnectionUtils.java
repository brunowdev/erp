package br.edu.satc.ec.erp.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public final class ConnectionUtils {

    private static Connection connection;

    private ConnectionUtils() {
    }

    public static Connection getConnection() {

        if (Objects.isNull(connection)) {
            try {
                String url = "jdbc:postgresql://localhost/ERP?user=postgres&password=oraclethebest";
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

}

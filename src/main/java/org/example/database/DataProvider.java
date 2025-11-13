package org.example.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

/**
 * Proveedor de {@link DataSource} para conexiones MySQL.
 * <p>
 * Usa un patrón Singleton: se crea una única instancia y se reutiliza.
 * </p>
 */
public class DataProvider {
    private static DataSource dataSource;

    private DataProvider(){}

    /**
     * Inicializa el {@link DataSource} si no existe y lo devuelve.
     *
     * @param url URL JDBC de la base de datos
     * @param user usuario de la base de datos
     * @param password contraseña del usuario
     * @return instancia única de {@link DataSource}
     */
    public static DataSource getDataSource(String url, String user, String password) {
        if (dataSource == null) {
            var ds  = new MysqlDataSource();
            ds.setUrl(url);
            ds.setUser(user);
            ds.setPassword(password);
            dataSource = ds;
        }
        return dataSource;
    }

    /**
     * Devuelve el {@link DataSource} actual.
     *
     * @return instancia única de {@link DataSource}, o {@code null} si no está inicializada
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}

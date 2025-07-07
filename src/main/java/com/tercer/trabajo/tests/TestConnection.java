package com.tercer.trabajo.tests;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestConnection {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (InputStream in = TestConnection.class
                                            .getClassLoader()
                                            .getResourceAsStream("application.properties")) {
            if(in == null){
                System.err.println("No encontré application.properties en el classpath");
                return;
            }
            props.load(in);
        } catch (Exception e) {
            System.err.println("Error cargando properties: " + e.getMessage());
            return;
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty("spring.datasource.url"));
        config.setUsername(props.getProperty("spring.datasource.username"));
        config.setPassword(props.getProperty("spring.datasource.password"));

        try (HikariDataSource ds = new HikariDataSource(config);
                Connection conn = ds.getConnection()) {
            if(conn.isValid(2)){
                System.out.println("Conexión exitosa a:" + conn.getMetaData().getURL());
            }else{
                System.err.println("Conexión establecida pero no válida. ");
            }
        } catch (Exception e) {
            System.err.println("No se pudo conectar: " + e.getMessage());
        }
    }

}

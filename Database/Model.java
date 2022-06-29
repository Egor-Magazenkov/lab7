package Lab_6.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Model {
    private String url = "jdbc:postgresql://localhost/test";
    private String user = "leo";
    private String password = "admin";

    public Model(){

    }
    public Model(String user, String password){
        this.user = user;
        this.password = password;
    }

    public Model(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Подключено к базе данных");
            } else {
                System.out.println("Не удалось подключиться к базе данных");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


}
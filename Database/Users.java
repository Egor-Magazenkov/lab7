package Lab_6.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class Users {
    public static String find_by_login(Connection connection, String login){
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("select login, password from users where login='%s'", login));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void add(Connection connection, String login, String password){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("insert into users (user_id, login, password) Values (nextval('generate_user_id'), '%s', '%s');", login, password));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
//            System.out.println(e);
            e.printStackTrace();
        }
    }
}

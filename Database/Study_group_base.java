package Lab_6.Database;

import Lab_6.Collection.StudyGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Study_group_base {
    private final String table = "groups";
    public void add(Connection connection, HashMap<String, String> args) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("insert into %s (id, %s) Values (nextval('generate_group_id'), %s);", this.table, args.keySet().stream().collect(Collectors.joining(",")), args.values().stream().collect(Collectors.joining(","))));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Object changeValue(Connection connection, int id, long value){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("update groups set averagemark=%d where id=%d", value, id));
            ResultSet rs = preparedStatement.executeQuery();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }


    public void clear(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("delete from groups;"));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void show(Connection connection) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from study_groups;");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println("id" + rs.getInt("id"));
                System.out.println("name" + rs.getString("name"));
                System.out.println("coordinates" + rs.getInt("coordinates"));
                System.out.println("creationDate" + rs.getString("creationdate"));
                System.out.println("StudentsCount" + rs.getLong("studentscount"));
                System.out.println("expelledStudents" + rs.getInt("expelledstudents"));
                System.out.println("averageMark" + rs.getLong("averagemark"));
                System.out.println("groupAdmin"+ rs.getInt("groupadmin"));

            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}

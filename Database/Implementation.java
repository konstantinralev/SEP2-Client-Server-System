
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.User;

public class Implementation implements Interface {

    private Connection connnection;

    @Override
    public boolean insert(User user) {
        try {
            String query = "INSERT INTO \"Users\".account2 values ('" + user.getUserName() + "','" + user.getPassword() + "','" + user.getName() + "','" + user.getLastName() + "')";
            Statement statement = this.connnection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User getUser(String username, String password) {
        try {
            String query = "SELECT username, password FROM \"Users\".account2 where "
					+ "username = '"
					+ username
					+ "' and password = '"
					+ password + "'";

            PreparedStatement preparedStatement = this.connnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;

            if (resultSet.next()) {
                user = new User();
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                
            }
            preparedStatement.close();
            resultSet.close();
            return user;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
		            "postgres", "123456");
            System.out.println("Connection established successfully with the database server.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

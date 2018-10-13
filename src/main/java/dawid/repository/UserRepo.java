package dawid.repository;

import dawid.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;

public class UserRepo {

    public static Connection connection = null;

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            UserRepo.connection = DriverManager.getConnection("jdbc:mysql://localhost/logowanie?user=root&password=");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        try {
            String sqlInsert = "INSERT INTO tuser (login, pass) VALUES (?, ?)";
            PreparedStatement preparedStatement = UserRepo.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, DigestUtils.md5Hex(user.getPass()));
            preparedStatement.executeUpdate(); //pozniej znaki zapytania sie uzupelniaja przez prepare statement

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean authenticate(User user) {
        try {

            String sqlSelect = "SELECT * FROM tuser WHERE login = '" + user.getLogin() + "';";
            System.out.println(sqlSelect);
            Statement s = UserRepo.connection.createStatement(); //paczka danych
            ResultSet resultSet = s.executeQuery(sqlSelect); //resultset - iterator

            while (resultSet.next()) {
                User userFromDB = new User();

                userFromDB.setId(resultSet.getInt("id"));
                userFromDB.setLogin(resultSet.getString("login"));
                userFromDB.setPass(resultSet.getString("pass"));

                if (user.getLogin().equals(userFromDB.getLogin()) && DigestUtils.md5Hex(user.getPass()).equals(userFromDB.getPass())) {
                    user = null;
                    userFromDB = null;
                    return true;
                } else {
                    user = null;
                    userFromDB = null;
                    return false;
                }
            }

            s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        user = null;
        return false;
    }

}

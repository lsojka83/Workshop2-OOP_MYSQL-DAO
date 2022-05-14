package pl.workshop2.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.workshop2.w2.DbUtil;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(email, username, password) VALUES (?,?,?);";
    private static final String UPDATE_USER = "UPDATE users SET email=?, username=?, password=? WHERE id = ?;";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";
    private static final String GET_ALL_USERS = "SELECT * FROM users;";


    public User create(User user) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            System.out.println("User created successfully...");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) ;
            {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                return user;
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public void update(User user) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Editing user with Id: " + user.getId());
            System.out.print("Do You want to edit email? (Y/N): ");
            String answer = "";
            while (!"Y".equals(answer) && !"N".equals(answer)) {
                answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    System.out.print("Enter email: ");
                    user.setEmail(scanner.nextLine());
                }
            }
            System.out.print("Do You want to edit username? (Y/N): ");
            answer = "";
            while (!"Y".equals(answer) && !"N".equals(answer)) {
                answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    System.out.print("Enter new username: ");
                    user.setUserName(scanner.nextLine());
                }
            }
            System.out.print("Do You want to edit password? (Y/N): ");
            answer = "";
            while (!"Y".equals(answer) && !"N".equals(answer)) {
                answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    System.out.print("Enter new password ");
                    user.setPassword(hashPassword(scanner.nextLine()));
                }
            }
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            if (read(userId) != null) {
                statement.setInt(1, userId);
                System.out.print(String.format("You are about to delete user with id: %d. Do You confirm? (Y/N) ", userId));
                Scanner scanner = new Scanner(System.in);
                String answer = "";
                while (!"Y".equals(answer) && !"N".equals(answer)) {
                    answer = scanner.nextLine();
                    if (answer.equals("Y")) {
                        statement.executeUpdate();
                        setIdToHighetMax();
                        System.out.println(String.format("User with id: %d deleted successfully...", userId));
                    } else {
                        System.out.println("Nothing deleted.");
                    }
                }
            } else {
                System.out.println("Such user does not exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            User[] users = new User[0];
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                users = addToArray(user, users);
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private User[] addToArray(User user, User[] users) {
        users = Arrays.copyOf(users, users.length + 1);
        users[users.length - 1] = user;

        return users;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setIdToHighetMax() {
        try (Connection connection = DbUtil.getConnection();) {

            String sql = "SELECT id FROM users;";
            PreparedStatement statement = connection.prepareStatement(sql);

            try {
                ResultSet resultSet = statement.executeQuery();
                int maxId = 0;
                while (resultSet.next()) {
                    maxId = resultSet.getInt(1);
                }
                System.out.println(maxId);
                String sql1 = "ALTER TABLE users AUTO_INCREMENT = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                statement1.setInt(1, maxId + 1);
                statement1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

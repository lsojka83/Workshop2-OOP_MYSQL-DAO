package pl.workshop2.w2;

import pl.workshop2.entity.User;
import pl.workshop2.entity.UserDao;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        UserDao userDao = new UserDao();

        CreateDb.createDatabase();
        CreateDb.createTable();

        //create user
        User user1 = new User();
        user1.setEmail("as@wp.pl");
        user1.setUserName("username1");
        user1.setPassword("user1password");
        userDao.create(user1);

        User user2 = new User();
        user2.setEmail("qwec@onet.pl");
        user2.setUserName("username2");
        user2.setPassword("user1password");
        userDao.create(user2);
//
        User user3 = new User();
        user3.setEmail("fref@g.com");
        user3.setUserName("user3");
        user3.setPassword("pass3");
        userDao.create(user3);

        User user4 = new User();
        user4.setEmail("fre234f@g.com");
        user4.setUserName("user4");
        user4.setPassword("pass4");
        userDao.create(user4);

//
//        //read user
//        System.out.println(userDao.read(10));
//        checkReadUser(userDao, 1);
//        checkReadUser(userDao, 10);
//
//
        //edit user
//        userDao.update(enterUserToBeEdited(userDao));
//
        //delete user
        userDao.delete(2);


        //findAll
        System.out.println(Arrays.toString(userDao.findAll()));
        for (User u : userDao.findAll()) {
            printUserData(u);
        }


    }

    public static User enterUserToBeEdited(UserDao userDao) {
        Scanner scanner = new Scanner(System.in);
        User user;

        while (true) {

            try {
                System.out.print("Enter user Id:");
                user = userDao.read(Integer.valueOf(scanner.nextLine()));
                user.getId();
                return user;
            } catch (Exception e) {
                System.out.println("No such user exist or bad were data entered.");
            }
        }

    }

    public static void checkReadUser(UserDao userDao, int userId) {
        System.out.println(userDao.read(userId));
        if (userDao.read(userId) != null) {
            System.out.println(String.format("Id: %s", userDao.read(userId).getId()));
            System.out.println(String.format("email: %s", userDao.read(userId).getEmail()));
            System.out.println(String.format("username: %s", userDao.read(userId).getUserName()));
            System.out.println(String.format("password: %s", userDao.read(userId).getPassword()));
        }
    }

    public static void printUserData(User user) {
        System.out.println(String.format("Id: %s", user.getId()));
        System.out.println(String.format("email: %s", user.getEmail()));
        System.out.println(String.format("username: %s", user.getUserName()));
        System.out.println(String.format("password: %s", user.getPassword()));
    }

}

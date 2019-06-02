package pl.coderslab.main;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.Solution;
import pl.coderslab.plain.User;

import java.sql.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        User user = new User("Jan Kowalski", "Jan.Kowalski@gmail.com", "tajne", 1);
//        User user1 = new User("Adam Kowalski", "adam.Kowalski@gmail.com", "tajne", 1);
//        User user2 = new User("Robert Kowalski", "robert.Kowalski@gmail.com", "tajne", 1);
//
//        UserDao userDao = new UserDao();
//
//        userDao.create(user);
//        userDao.create(user1);
//        userDao.create(user2);
//
//        int id = user.getId();
//
//        User loadUser = userDao.read(id);
//        System.out.println(loadUser);
//
//        User notExist = userDao.read(100);
//        System.out.println(notExist);
//
//        loadUser.setName("Adam Nowak");
//        loadUser.setEmail("adam.nowak@gmail.com");
//        userDao.update(loadUser);
//
//        User copyUser = userDao.read(id);
//        System.out.println(copyUser);
//
//        //userDao.delete(id);
//
//        User loadAgain = userDao.read(id);
//        System.out.println(loadAgain);
//
//        System.out.println("find all users");
//        User[] users = userDao.findAll();
//        for(User myUser : users) {
//            System.out.println(myUser);
//        }

//        userDao.delete(id);
//        userDao.delete(user1.getId());
//        userDao.delete(user2.getId());


        Solution solution = new Solution("2019-09-10", "2019-09-10", "świetne rozwiązanie", 1, 29);
        SolutionDao solutionDao = new SolutionDao();

        int id = solution.getId();

//        solutionDao.delete(id);

        System.out.println(solutionDao.findAllBySolutionId(id));

        Exercise exercise = new Exercise("rkłdaf", "adfadf");

        ExerciseDao exerciseDao = new ExerciseDao();
        exerciseDao.create(exercise);
        System.out.println(exerciseDao.read(2));
        exerciseDao.delete(2);




        }
}


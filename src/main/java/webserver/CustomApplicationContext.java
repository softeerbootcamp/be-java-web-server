package webserver;

import db.jdbc.*;
import db.tmpl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webserver.Service.BoardService;
import webserver.Service.UserService;
import webserver.controller.MainController;
import webserver.controller.UserController;
import webserver.utils.HttpSessionUtils;

@Configuration
public class CustomApplicationContext {

    public static void initialize(){
        boardService();
        mainController();
        userController();
        httpSessionUtils();
        userService();
    }

    @Bean
    public static BoardService boardService(){
        BoardService boardService = BoardService.getInstance();
        boardService.setBoardDataBase(boardDataBase());
        return boardService;
    }

    @Bean
    public static MainController mainController(){
        MainController mainController = MainController.getInstance();
        mainController.setBoardDataBase(boardDataBase());
        return mainController;
    }

    @Bean
    public static UserController userController(){
        UserController userController = UserController.getInstance();
        userController.setUserDatabase(userDataBase());
        return userController;
    }

    @Bean
    public static UserService userService(){
        UserService userService = UserService.getInstance();
        userService.setUserDatabase(userDataBase());
        return userService;
    }

    @Bean
    public static HttpSessionUtils httpSessionUtils(){
        HttpSessionUtils httpSessionUtils = HttpSessionUtils.getInstance();
        httpSessionUtils.setSessionDatabase(sessionDatabase());
        httpSessionUtils.setUserDatabase(userDataBase());
        return httpSessionUtils;
    }

    @Bean
    public static UserDatabase userDataBase(){
        return jdbcUserDatabase();
    }

    @Bean
    public static SessionDatabase sessionDatabase(){
        return jdbcSessionDatabase();
    }

    @Bean
    public static CommentDatabase commentDatabase(){
        return jdbcCommentDatabase();
    }

    @Bean
    public static BoardDataBase boardDataBase(){
        return jdbcBoardDatabase();
    }

    @Bean
    public static JdbcBoardDatabase jdbcBoardDatabase() {
        JdbcBoardDatabase jdbcBoardDatabase = JdbcBoardDatabase.getInstance();
        jdbcBoardDatabase.setConnectionManager(mysqlConnectionManager());
        return jdbcBoardDatabase;
    }

    @Bean
    public static JdbcCommentDatabase jdbcCommentDatabase() {
        JdbcCommentDatabase jdbcCommentDatabase = JdbcCommentDatabase.getInstance();
        jdbcCommentDatabase.setConnectionManager(mysqlConnectionManager());
        return jdbcCommentDatabase;
    }

    @Bean
    public static JdbcSessionDatabase jdbcSessionDatabase() {
        JdbcSessionDatabase jdbcSessionDatabase = JdbcSessionDatabase.getInstance();
        jdbcSessionDatabase.setConnectionManager(mysqlConnectionManager());
        return jdbcSessionDatabase;
    }

    @Bean
    public static JdbcUserDatabase jdbcUserDatabase() {
        JdbcUserDatabase jdbcUserDatabase = JdbcUserDatabase.getInstance();
        jdbcUserDatabase.setConnectionManager(mysqlConnectionManager());
        return jdbcUserDatabase;
    }

    @Bean
    public static ConnectionManager mysqlConnectionManager(){
        return MySQLConnectionManager.getInstance();
    }
}

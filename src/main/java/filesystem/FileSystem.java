package filesystem;

import db.Database;
import exception.FileSystemException;
import http.common.Session;
import http.request.HttpRequest;
import model.User;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystem {

    private FileSystem() {
    }

    public static FindResource getIndexPage(HttpRequest request) {
        String resourcePath = PathResolver.parse(request.getUrl());
        byte[] resource = readFile(resourcePath);
        resource = makeIndexPage(resource, Database.getSession(request.getCookie(Session.SESSION_FIELD_NAME).getValue()).getUser());
        return new FindResource(resourcePath, resource);
    }

    // todo: 리팩토링,,,,
    public static FindResource getUserListPage(HttpRequest request) {
        String resourcePath = PathResolver.parse(request.getUrl());
        byte[] resource = readFile(resourcePath);
        resource = makeUserListPage(resource, Database.findAll());
        return new FindResource(resourcePath, resource);

    }

    private static byte[] makeUserListPage(byte[] resource, Collection<User> users) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            String html = "";
            String line = bf.readLine();
            do {
                if (line.trim().equals("<%")) {
                    String element = getElementString(users);
                    html += element;
                    bf.readLine();
                } else {
                    html += line + System.lineSeparator();
                }
                line = bf.readLine();
            } while (line != null);
            return html.getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    private static String getElementString(Collection<User> users) {
        List<User> userList = users.stream().collect(Collectors.toList());
        String element = "";
        for (int i = 0; i < users.size(); i++) {
            element += "<tr>" +
                    "<th scope='row'>" + i + 1 + "</th>" +
                    "<td>" + userList.get(i).getUserId() + "</td>" +
                    "<td>" + userList.get(i).getName() + "</td>" +
                    "<td>" + userList.get(i).getEmail() + "</td>" +
                    "<td><a href='#' class='btn btn-success' role='button'>수정</a></td>" +
                    "</tr>";
        }
        return element;
    }


    private static byte[] makeIndexPage(byte[] resource, User user) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            String html = "";
            String line = bf.readLine();
            do {
                if (line.trim().equals("<%")) {
                    bf.readLine();
                    bf.readLine();
                    html += "<li>" + "<a href='#' role='button'>" + user.getUserId() + "</a>" + "</li>" + System.lineSeparator();
                    html += bf.readLine() + System.lineSeparator();
                    html += bf.readLine() + System.lineSeparator();
                    bf.readLine();
                } else {
                    html += line + System.lineSeparator();
                }
                line = bf.readLine();
            } while (line != null);
            return html.getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static FindResource findStaticResource(HttpRequest request) {
        String resourcePath = PathResolver.parse(request.getUrl());
        byte[] resource = readFile(resourcePath);
        resource = trimSpecialSymbol(resource);
        return new FindResource(resourcePath, resource);
    }

    private static byte[] trimSpecialSymbol(byte[] resource) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            String html = "";
            String line = bf.readLine();
            do {
                if (!line.trim().equals("<%") && !line.trim().equals("%>")) {
                    html += line + System.lineSeparator();
                }
                line = bf.readLine();
            } while (line != null);
            return html.getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static byte[] readFile(String resourcePath) {
        try {
            return Files.readAllBytes(Paths.get(resourcePath));
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

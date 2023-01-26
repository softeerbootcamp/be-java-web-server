package controller;

import dto.HttpRequest;
import dto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.HttpMethodType;
import utility.HttpStatusCode;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    public static final String DEFAULT_PATH = "./src/main/resources";
    public static final String TEMPLATES_PATH = "/templates";
    public static final String STATIC_PATH = "/static";

    private static final HttpResponse NOT_FOUND_RESPONSE = new HttpResponse("/", HttpStatusCode.NOT_FOUND, "text/plain");

    private static Map<Class<?>, Object> instance;
    private static List<Class<?>> controllers;

    static {
        instance = Map.ofEntries(
                entry(UserController.class, new UserController()),
                entry(ViewController.class, new ViewController()),
                entry(CommentController.class, new CommentController())
        );
        controllers = List.of(UserController.class, ViewController.class, CommentController.class);
    }

    public static HttpResponse fulfillRequest(HttpRequest request) throws IOException {
        HttpResponse response = handleDynamicResource(request);

        if(response.statusCode == HttpStatusCode.NOT_FOUND)
            return handleStaticResource(request);

        return response;
    }

    private static HttpResponse handleDynamicResource(HttpRequest request) {
        HttpResponse response = NOT_FOUND_RESPONSE;

        for(Class<?> con : controllers) {
            List<Method> methods = Arrays.stream(con.getMethods())
                    .filter(m -> m.isAnnotationPresent(ControllerMapping.class))
                    .collect(Collectors.toList());

            Method method = null;

            method = methods.stream().filter((m) -> ifMatchController(m, request)).findFirst().orElse(null);

            if(method == null)
                continue;

            try {
                Object controllerInstance = instance.get(con);
                response = (HttpResponse) method.invoke(controllerInstance, request);
            } catch (IllegalArgumentException | IllegalAccessException | NullPointerException |
                     InvocationTargetException e) {
                logger.error(e.getMessage());
            }
        }
        return response;
    }

    private static HttpResponse handleStaticResource(HttpRequest request) throws IOException {
        StringBuilder resourcePath = new StringBuilder(DEFAULT_PATH);
        String path = request.getPath();
        String contentType = Files.probeContentType(Path.of(path));

        if(path.contains("html") || path.contains(".ico")) {
            resourcePath.append(TEMPLATES_PATH);

        } else {
            resourcePath.append(STATIC_PATH);
        }
        resourcePath.append(path);

        logger.debug("resourcePath: " + resourcePath.toString());
        if(!new File(resourcePath.toString()).isFile()) {
            return NOT_FOUND_RESPONSE;
        }

        return new HttpResponse(resourcePath.toString(), HttpStatusCode.OK, contentType);
    }

    private static boolean ifMatchController(Method method, HttpRequest req) {
        ControllerMapping a = method.getAnnotation(ControllerMapping.class);
        return (a.method() == HttpMethodType.valueOf(req.getMethodType()) && a.path().equals(req.getPath()));
    }
}

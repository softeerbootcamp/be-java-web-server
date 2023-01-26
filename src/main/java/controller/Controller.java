package controller;

import request.Request;
import response.ResponseFactory;

import java.io.IOException;
import java.sql.SQLException;

public interface Controller {
    ResponseFactory controllerService(Request request) throws IOException, SQLException;
}

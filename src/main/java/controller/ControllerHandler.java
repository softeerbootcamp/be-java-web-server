package controller;

import request.HttpRequest;
import response.HttpResponse;
import util.Url;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ControllerHandler {

     void handle(Url url, DataOutputStream dataOutputStream, HttpRequest httpRequest) throws Exception;
}

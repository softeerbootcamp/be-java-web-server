package controller;

public class ControllerNotFoundException extends RuntimeException {

    ControllerNotFoundException() {

    }

    ControllerNotFoundException(String message) {
        super(message);
    }
}

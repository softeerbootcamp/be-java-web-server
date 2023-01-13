package http;

public class ResourceTypeNotFoundException extends RuntimeException{

    ResourceTypeNotFoundException() {

    }

    ResourceTypeNotFoundException(String message) {
        super(message);
    }
}

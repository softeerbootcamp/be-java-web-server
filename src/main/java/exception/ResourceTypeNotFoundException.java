package exception;

public class ResourceTypeNotFoundException extends RuntimeException{

    public ResourceTypeNotFoundException() {

    }

    public ResourceTypeNotFoundException(String message) {
        super(message);
    }
}

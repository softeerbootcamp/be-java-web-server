package customException.cannotLogIn;

public class CannotLogInException extends RuntimeException {
    CannotLogInException(){}

    public CannotLogInException(String msg){ super(msg);}
}

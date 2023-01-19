package customException.cannotLogIn;

public class PasswordMismatchException extends CannotLogInException{
    PasswordMismatchException(){}

    public PasswordMismatchException(String msg){super(msg);}
}

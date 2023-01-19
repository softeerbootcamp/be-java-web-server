package customException.cannotLogIn;


public class NotFoundUserException extends CannotLogInException{
    NotFoundUserException(){}

    public NotFoundUserException(String msg){super(msg);}
}

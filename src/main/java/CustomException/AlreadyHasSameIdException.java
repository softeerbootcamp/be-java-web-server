package CustomException;

public class AlreadyHasSameIdException extends RuntimeException{
    AlreadyHasSameIdException(){}

    public AlreadyHasSameIdException(String msg)
    {
        super(msg);
    }
}

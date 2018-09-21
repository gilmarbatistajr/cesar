package school.cesar;

public class EmailFailException extends RuntimeException {

    public EmailFailException(String msg){
        super(msg);
    }
}

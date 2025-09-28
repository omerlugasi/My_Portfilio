public class InvalidInputException extends IsraeliQueueException
{
    public InvalidInputException()
    {
        super();
    }
    public InvalidInputException(String message)
    {
        super(message);
    }
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

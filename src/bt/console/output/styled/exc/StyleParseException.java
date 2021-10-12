package bt.console.output.styled.exc;

public class StyleParseException extends RuntimeException
{
    public StyleParseException(String message)
    {
        super(message);
    }

    public StyleParseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
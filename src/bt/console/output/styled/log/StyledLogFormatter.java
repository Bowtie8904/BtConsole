package bt.console.output.styled.log;

import bt.console.output.styled.Style;
import bt.console.output.styled.StyledTextParser;
import bt.log.DefaultLogFormatter;
import bt.log.LoggerConfiguration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Lukas Hartwig
 * @since 16.12.2021
 */
public class StyledLogFormatter extends DefaultLogFormatter
{
    protected String[] timestampStyles;
    protected String[] logLevelStyles;
    protected String[] threadNameStyles;
    protected String[] callerStyles;
    protected String[] messageStyles;
    protected String[] errorStyles;

    /**
     * Creates a new instance with the given configuration.
     *
     * @param config
     */
    public StyledLogFormatter(LoggerConfiguration config)
    {
        super(config);
        this.timestampStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
        this.logLevelStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
        this.threadNameStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
        this.callerStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
        this.messageStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
        this.errorStyles = new String[]{ "red", "bold" };
    }

    /**
     * Creates a new instance with a default configuration.
     */
    public StyledLogFormatter()
    {
        this(new LoggerConfiguration());
    }

    @Override
    protected String getTimestampString(LogRecord record)
    {
        return Style.apply(super.getTimestampString(record), this.timestampStyles);
    }

    @Override
    protected String getLogLevelString(java.util.logging.Level logLevel)
    {
        return Style.apply(super.getLogLevelString(logLevel), this.logLevelStyles);
    }

    @Override
    protected String getThreadNameString()
    {
        return Style.apply(super.getThreadNameString(), this.threadNameStyles);
    }

    @Override
    protected String getCallerString()
    {
        return Style.apply(super.getCallerString(), this.callerStyles);
    }

    @Override
    protected String getThrowableText(Throwable t) throws IOException
    {
        return Style.apply(t, this.errorStyles);
    }

    @Override
    protected String getMessageText(LogRecord record)
    {
        String text = record.getMessage();
        String finalText = "";

        for (String line : text.split(System.lineSeparator()))
        {
            if (record.getLevel().equals(Level.SEVERE) || record.getLevel().equals(Level.WARNING))
            {
                finalText += Style.apply(line, this.errorStyles) + System.lineSeparator();
            }
            else
            {
                finalText += Style.apply(line, this.messageStyles) + System.lineSeparator();
            }
        }

        return finalText;
    }

    public StyledLogFormatter setTimestampStyles(String... timestampStyles)
    {
        this.timestampStyles = timestampStyles;
        return this;
    }

    public StyledLogFormatter setLogLevelStyles(String... logLevelStyles)
    {
        this.logLevelStyles = logLevelStyles;
        return this;
    }

    public StyledLogFormatter setThreadNameStyles(String... threadNameStyles)
    {
        this.threadNameStyles = threadNameStyles;
        return this;
    }

    public StyledLogFormatter setCallerStyles(String... callerStyles)
    {
        this.callerStyles = callerStyles;
        return this;
    }

    public StyledLogFormatter setMessageStyles(String... messageStyles)
    {
        this.messageStyles = messageStyles;
        return this;
    }

    public StyledLogFormatter setErrorStyles(String... errorStyles)
    {
        this.errorStyles = errorStyles;
        return this;
    }
}

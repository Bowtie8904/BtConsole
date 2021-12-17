package bt.console.output.styled.log;

import bt.console.output.styled.Style;
import bt.log.DefaultLogFormatter;
import bt.log.LoggerConfiguration;

import java.util.logging.LogRecord;

/**
 * @author Lukas Hartwig
 * @since 17.12.2021
 */
public class DestyledLogFormatter extends DefaultLogFormatter
{
    /**
     * Creates a new instance with the given configuration.
     *
     * @param config
     */
    public DestyledLogFormatter(LoggerConfiguration config)
    {
        super(config);
    }

    /**
     * Creates a new instance with a default configuration.
     */
    public DestyledLogFormatter()
    {
        super();
    }

    @Override
    protected String getMessageText(LogRecord record)
    {
        return Style.destyle(super.getMessageText(record));
    }
}
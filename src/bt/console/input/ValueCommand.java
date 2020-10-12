package bt.console.input;

import java.util.function.Consumer;

/**
 * Represents a command that can consume an additional value.
 *
 * @author &#8904
 */
public class ValueCommand extends Command<ValueCommand>
{
    protected Consumer<String> action;
    protected String value;

    public ValueCommand(String... aliases)
    {
        super(aliases);
    }

    /**
     * Sets the action that is executed if this command was found during a {@link CommandParser#parse(String[]) parse
     * operation}. The consumed String is the argument value.
     *
     * @param action
     * @return
     */
    public ValueCommand onExecute(Consumer<String> action)
    {
        this.action = action;
        return this;
    }

    protected void execute(String value)
    {
        this.value = value;
        this.executed = true;

        if (this.action != null)
        {
            this.action.accept(value);
        }
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @see bt.console.input.Command#reset()
     */
    @Override
    protected void reset()
    {
        this.executed = false;
        this.value = null;
    }
}
package bt.console.input;

import java.util.function.Consumer;

/**
 * Represents an argument that can consume an additional value.
 *
 * @author &#8904
 */
public class ValueArgument extends Argument<ValueArgument>
{
    protected Consumer<String> action;
    protected String value;

    public ValueArgument(String... aliases)
    {
        super(aliases);
    }

    /**
     * Sets the action that is executed if this argument was found during a {@link ArgumentParser#parse(String[]) parse
     * operation}. The consumed String is the argument value.
     *
     * @param action
     * @return
     */
    public ValueArgument onExecute(Consumer<String> action)
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
     * @see Argument#reset()
     */
    @Override
    protected void reset()
    {
        this.executed = false;
        this.value = null;
    }
}
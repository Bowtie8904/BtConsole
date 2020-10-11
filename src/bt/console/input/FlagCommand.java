package bt.console.input;

import bt.utils.Null;

/**
 * Represents a flag command which is either present or not.
 *
 * @author &#8904
 */
public class FlagCommand extends Command<FlagCommand>
{
    protected Runnable action;
    protected boolean flag;

    public FlagCommand(String... aliases)
    {
        super(aliases);
    }

    /**
     * Sets the action that is executed if this command was found during a {@link CommandParser#parse(String[]) parse
     * operation}.
     *
     * @param action
     * @return
     */
    public FlagCommand onExecute(Runnable action)
    {
        this.action = action;
        return this;
    }

    protected void execute()
    {
        this.flag = true;
        this.executed = true;
        Null.checkRun(this.action);
    }

    public boolean getFlag()
    {
        return this.flag;
    }

    /**
     * @see bt.console.input.Command#reset()
     */
    @Override
    protected void reset()
    {
        this.executed = false;
        this.flag = false;
    }
}
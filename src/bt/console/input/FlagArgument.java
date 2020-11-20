package bt.console.input;

/**
 * Represents a flag argument which is either present or not.
 *
 * @author &#8904
 */
public class FlagArgument extends Argument<FlagArgument>
{
    protected Runnable action;
    protected boolean flag;

    public FlagArgument(String... aliases)
    {
        super(aliases);
    }

    /**
     * Sets the action that is executed if this argument was found during a {@link ArgumentParser#parse(String[]) parse
     * operation}.
     *
     * @param action
     * @return
     */
    public FlagArgument onExecute(Runnable action)
    {
        this.action = action;
        return this;
    }

    protected void execute()
    {
        this.flag = true;
        this.executed = true;

        if (this.action != null)
        {
            this.action.run();
        }
    }

    public boolean getFlag()
    {
        return this.flag;
    }

    /**
     * @see Argument#reset()
     */
    @Override
    protected void reset()
    {
        this.executed = false;
        this.flag = false;
    }
}
package bt.console.input;

/**
 * @author &#8904
 *
 */
public abstract class Argument<T extends Argument>
{
    protected Runnable onMissing;
    protected String[] aliases;
    protected String usage;
    protected String description;
    protected boolean executed;
    protected String prefix;

    /**
     * Creates a new instance with the given aliases.
     *
     * @param aliases
     */
    public Argument(String... aliases)
    {
        if (aliases.length == 0)
        {
            throw new IllegalArgumentException("Need at least one alias.");
        }

        this.aliases = aliases;
        this.usage = "";
        this.description = "";
    }

    protected void init()
    {

    }

    /**
     * @param prefix
     *            the prefix to set
     */
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    /**
     * @param usage
     *            the usage to set
     */
    public T usage(String usage)
    {
        this.usage = usage;
        return (T)this;
    }

    /**
     * @param description
     *            the description to set
     */
    public T description(String description)
    {
        this.description = description;
        return (T)this;
    }

    /**
     * Sets an action that is executed if this argument was not found during a {@link ArgumentParser#parse(String[]) parse
     * operation}.
     *
     * @param action
     * @return This instance.
     */
    public T onMissing(Runnable action)
    {
        this.onMissing = action;
        return (T)this;
    }

    protected void missing()
    {
        if (this.onMissing != null)
        {
            this.onMissing.run();
        }
    }

    /**
     * @return the executed
     */
    public boolean isExecuted()
    {
        return this.executed;
    }

    /**
     * @return the aliases
     */
    public String[] getAliases()
    {
        return this.aliases;
    }

    /**
     * @return the usage
     */
    public String getUsage()
    {
        return this.usage;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    public void setExecuted(boolean executed)
    {
        this.executed = executed;
    }

    protected abstract void reset();
}
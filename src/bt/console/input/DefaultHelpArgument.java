package bt.console.input;

/**
 * @author &#8904
 *
 */
public class DefaultHelpArgument extends ValueArgument
{
    public DefaultHelpArgument(String... aliases)
    {
        super(aliases);
    }

    @Override
    protected void init()
    {
        this.usage = this.prefix + this.aliases[0] + " [<argument>]";
        this.description = "Offers information about available arguments.";
    }

    public String formatArgumentHelp(String prefix, Argument arg)
    {
        String descrString = "";
        String usageString = "";
        String aliasString = "[";

        for (var alias : arg.getAliases())
        {
            aliasString += prefix + alias + " ";
        }

        aliasString = aliasString.substring(0, aliasString.length() - 1);
        aliasString += "]";

        if (!arg.getUsage().isBlank())
        {
            usageString = "\n" + arg.getUsage();
        }

        if (!arg.getDescription().isBlank())
        {
            descrString = "\n" + arg.getDescription();
        }

        return aliasString + usageString + descrString;
    }
}
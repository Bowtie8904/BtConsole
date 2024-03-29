package bt.console.input.args;

import bt.console.input.Argument;
import bt.console.input.ValueArgument;

/**
 * @author &#8904
 *
 */
public class DefaultHelpArgument extends ValueArgument
{
    public DefaultHelpArgument(String alias, String... aliases)
    {
        super(alias, aliases);
    }

    public DefaultHelpArgument(String[] aliases)
    {
        this(null, aliases);
    }

    public DefaultHelpArgument()
    {
        this("h", "help");
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
            usageString = System.lineSeparator() + arg.getUsage();
        }

        if (!arg.getDescription().isBlank())
        {
            descrString = System.lineSeparator() + arg.getDescription();
        }

        return aliasString + usageString + descrString;
    }
}
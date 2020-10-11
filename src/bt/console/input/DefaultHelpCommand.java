package bt.console.input;

/**
 * @author &#8904
 *
 */
public class DefaultHelpCommand extends ValueCommand
{
    public DefaultHelpCommand(String... aliases)
    {
        super(aliases);
    }

    @Override
    protected void init()
    {
        this.usage = this.prefix + this.aliases[0] + " [<command>]";
        this.description = "Offers information about available commands.";
    }

    public String formatCommandHelp(String prefix, Command cmd)
    {
        String descrString = "";
        String usageString = "";
        String aliasString = "[";

        for (var alias : cmd.getAliases())
        {
            aliasString += prefix + alias + " ";
        }

        aliasString = aliasString.substring(0, aliasString.length() - 1);
        aliasString += "]";

        if (!cmd.getUsage().isBlank())
        {
            usageString = "\n" + cmd.getUsage();
        }

        if (!cmd.getDescription().isBlank())
        {
            descrString = "\n" + cmd.getDescription();
        }

        return aliasString + usageString + descrString;
    }
}
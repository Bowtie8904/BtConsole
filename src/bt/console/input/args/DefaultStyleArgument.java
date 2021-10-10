package bt.console.input.args;

import bt.console.input.FlagArgument;
import bt.console.output.styled.Style;

public class DefaultStyleArgument extends FlagArgument
{
    public DefaultStyleArgument(String firstAlias, String... aliases)
    {
        super(firstAlias, aliases);
        onExecute(() -> Style.setEnabled(false));
        onMissing(() -> Style.setEnabled(true));
    }

    public DefaultStyleArgument(String[] aliases)
    {
        this(null, aliases);
    }

    public DefaultStyleArgument()
    {
        this("disablestyle", "disablestyling", "nostyle");
    }

    @Override
    protected void init()
    {
        this.usage = this.prefix + this.aliases[0];
        this.description = "[Optional] Disables styling of logging output. Should be used if the application will not be used together with BtCommandLine.";
    }
}
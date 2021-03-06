package bt.console.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author &#8904
 *
 */
public class ArgumentParser
{
    protected String prefix;
    protected Map<String, FlagArgument> flagArguments;
    protected Map<String, ValueArgument> valueArguments;
    protected List<Argument> arguments;

    /**
     * Creates a new instance.
     *
     * @param prefix
     *            The prefix that is expected in front of every command.
     */
    public ArgumentParser(String prefix)
    {
        this.prefix = prefix.toLowerCase();
        this.flagArguments = new HashMap<>();
        this.valueArguments = new HashMap<>();
        this.arguments = new ArrayList<>();
    }

    /**
     * Creates a new instance without a command prefix.
     */
    public ArgumentParser()
    {
        this("");
    }

    /**
     * Registers a {@link DefaultHelpArgument} which will print the aliases, usage and description for every registered
     * command.
     *
     * @param aliases
     *            The aliases for the help command.
     */
    public DefaultHelpArgument registerDefaultHelpArgument(String... aliases)
    {
        var argument = new DefaultHelpArgument(aliases);

        argument.onExecute(arg ->
        {
            if (arg == null)
            {
                String helpString = "";

                for (var cmd : this.arguments)
                {
                    helpString += "\n" + argument.formatArgumentHelp(this.prefix, cmd) + "\n";
                }

                System.out.println(helpString);
            }
            else
            {
                Argument cmd = this.valueArguments.get(arg.toLowerCase());

                if (cmd == null)
                {
                    cmd = this.valueArguments.get(this.prefix + arg.toLowerCase());
                }

                if (cmd == null)
                {
                    cmd = this.flagArguments.get(arg.toLowerCase());
                }

                if (cmd == null)
                {
                    cmd = this.valueArguments.get(this.prefix + arg.toLowerCase());
                }

                if (cmd == null)
                {
                    System.out.println("Unknown argument: " + arg);
                }
                else
                {
                    System.out.println(argument.formatArgumentHelp(this.prefix, cmd));
                }
            }
        });

        register(argument);

        return argument;
    }

    /**
     * Gets the flag value for the given {@link FlagArgument} alias.
     *
     * @param argument
     *            One of the arguments aliases.
     * @return true if the flag was set, false otherwise.
     */
    public boolean getFlag(String argument)
    {
        var arg = this.flagArguments.get(this.prefix + argument.toLowerCase());
        return arg != null ? arg.getFlag() : false;
    }

    /**
     * Gets the value for the given {@link ValueArgument} alias.
     *
     * @param argument
     *            One of the arguments aliases.
     * @return The value for the command or null if the command was not set.
     */
    public String getValue(String argument)
    {
        var cmd = this.valueArguments.get(this.prefix + argument.toLowerCase());
        return cmd != null ? cmd.getValue() : null;
    }

    /**
     * Determines if a command was used.
     *
     * @param argument
     *            One of the arguments aliases.
     * @return True if the command was set, false otherwise.
     */
    public boolean wasExecuted(String argument)
    {
        Argument cmd = this.valueArguments.get(this.prefix + argument.toLowerCase());

        if (cmd == null)
        {
            cmd = this.flagArguments.get(this.prefix + argument.toLowerCase());
        }

        return cmd != null ? cmd.isExecuted() : false;
    }

    /**
     * Registeres a new {@link FlagArgument}, sets its prefix to the prefix of this instance and calls its
     * {@link Argument#init() init} method.
     *
     * @param argument
     */
    public void register(FlagArgument argument)
    {
        this.arguments.add(argument);
        argument.setPrefix(this.prefix);
        argument.init();

        for (String alias : argument.getAliases())
        {
            this.flagArguments.put(this.prefix + alias.toLowerCase(), argument);
        }
    }

    /**
     * Registeres a new {@link ValueArgument}, sets its prefix to the prefix of this instance and calls its
     * {@link Argument#init() init} method.
     *
     * @param argument
     */
    public void register(ValueArgument argument)
    {
        this.arguments.add(argument);
        argument.setPrefix(this.prefix);
        argument.init();

        for (String alias : argument.getAliases())
        {
            this.valueArguments.put(this.prefix + alias.toLowerCase(), argument);
        }
    }

    /**
     * Parses the given array of arguments.
     *
     * <p>
     * This will execute every argument that is contained in the argument list.
     * </p>
     *
     * @param args
     */
    public void parse(String[] args)
    {
        int i = 0;
        String arg = null;
        Argument cmd = null;

        while (i < args.length)
        {
            arg = getArg(args, i);
            cmd = this.valueArguments.get(arg.toLowerCase());

            if (cmd != null)
            {
                String value = getArg(args, ++ i);
                ((ValueArgument)cmd).execute(value);
            }
            else
            {
                cmd = this.flagArguments.get(arg.toLowerCase());

                if (cmd != null)
                {
                    ((FlagArgument)cmd).execute();
                }
            }

            i ++ ;
        }

        for (Argument argument : this.arguments)
        {
            if (!argument.isExecuted())
            {
                argument.missing();
            }
        }
    }

    protected String getArg(String[] args, int index)
    {
        if (args.length <= index)
        {
            return null;
        }

        return args[index];
    }

    /**
     * Resets all registered commands by calling their {@link Argument#reset()} methods. This usually means that their
     * values are reset and the arguments are now considered not set again.
     */
    public void reset()
    {
        for (var cmd : this.arguments)
        {
            cmd.reset();
        }
    }
}
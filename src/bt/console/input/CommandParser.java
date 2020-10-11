package bt.console.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bt.log.Logger;

/**
 * @author &#8904
 *
 */
public class CommandParser
{
    protected String prefix;
    protected Map<String, FlagCommand> flagCommands;
    protected Map<String, ValueCommand> valueCommands;
    protected List<Command> commands;

    /**
     * Creates a new instance.
     *
     * @param prefix
     *            The prefix that is expected in front of every command.
     */
    public CommandParser(String prefix)
    {
        this.prefix = prefix.toLowerCase();
        this.flagCommands = new HashMap<>();
        this.valueCommands = new HashMap<>();
        this.commands = new ArrayList<>();
    }

    /**
     * Creates a new instance without a command prefix.
     */
    public CommandParser()
    {
        this("");
    }

    /**
     * Registers a {@link DefaultHelpCommand} which will print the aliases, usage and description for every registered
     * command.
     *
     * @param aliases
     *            The aliases for the help command.
     */
    public void registerDefaultHelpCommand(String... aliases)
    {
        var command = new DefaultHelpCommand(aliases);

        command.onExecute(arg ->
        {
            if (arg == null)
            {
                String helpString = "";

                for (var cmd : this.commands)
                {
                    helpString += "\n" + command.formatCommandHelp(this.prefix, cmd) + "\n";
                }

                Logger.global().print(helpString);
            }
            else
            {
                Command cmd = this.valueCommands.get(arg.toLowerCase());

                if (cmd == null)
                {
                    cmd = this.valueCommands.get(this.prefix + arg.toLowerCase());
                }

                if (cmd == null)
                {
                    cmd = this.flagCommands.get(arg.toLowerCase());
                }

                if (cmd == null)
                {
                    cmd = this.valueCommands.get(this.prefix + arg.toLowerCase());
                }

                if (cmd == null)
                {
                    Logger.global().print("Unknown command: " + arg);
                }
                else
                {
                    Logger.global().print(command.formatCommandHelp(this.prefix, cmd));
                }
            }
        });

        register(command);
    }

    /**
     * Gets the flag value for the given {@link FlagCommand} alias.
     *
     * @param command
     *            One of the commands aliases.
     * @return true if the flag was set, false otherwise.
     */
    public boolean getFlag(String command)
    {
        var cmd = this.flagCommands.get(this.prefix + command.toLowerCase());
        return cmd != null ? cmd.getFlag() : false;
    }

    /**
     * Gets the value for the given {@link ValueCommand} alias.
     *
     * @param command
     *            One of the commands aliases.
     * @return The value for the command or null if the command was not set.
     */
    public String getValue(String command)
    {
        var cmd = this.valueCommands.get(this.prefix + command.toLowerCase());
        return cmd != null ? cmd.getValue() : null;
    }

    /**
     * Determines if a command was used.
     *
     * @param command
     *            One of the commands aliases.
     * @return True if the command was set, false otherwise.
     */
    public boolean wasExecuted(String command)
    {
        Command cmd = this.valueCommands.get(this.prefix + command.toLowerCase());

        if (cmd == null)
        {
            cmd = this.flagCommands.get(this.prefix + command.toLowerCase());
        }

        return cmd != null ? cmd.isExecuted() : false;
    }

    /**
     * Registeres a new {@link FlagCommand}, sets its prefix to the prefix of this instance and calls its
     * {@link Command#init() init} method.
     *
     * @param command
     */
    public void register(FlagCommand command)
    {
        this.commands.add(command);
        command.setPrefix(this.prefix);
        command.init();

        for (String alias : command.getAliases())
        {
            this.flagCommands.put(this.prefix + alias.toLowerCase(), command);
        }
    }

    /**
     * Registeres a new {@link ValueCommand}, sets its prefix to the prefix of this instance and calls its
     * {@link Command#init() init} method.
     *
     * @param command
     */
    public void register(ValueCommand command)
    {
        this.commands.add(command);
        command.setPrefix(this.prefix);
        command.init();

        for (String alias : command.getAliases())
        {
            this.valueCommands.put(this.prefix + alias.toLowerCase(), command);
        }
    }

    /**
     * Parses the given array of arguments.
     *
     * <p>
     * This will execute every command that is contained in the argument list.
     * </p>
     *
     * @param args
     */
    public void parse(String[] args)
    {
        int i = 0;
        String arg = null;
        Command cmd = null;

        while (i < args.length)
        {
            arg = getArg(args, i);
            cmd = this.valueCommands.get(arg.toLowerCase());

            if (cmd != null)
            {
                String value = getArg(args, ++ i);
                ((ValueCommand)cmd).execute(value);
            }
            else
            {
                cmd = this.flagCommands.get(arg.toLowerCase());

                if (cmd != null)
                {
                    ((FlagCommand)cmd).execute();
                }
            }

            i ++ ;
        }

        for (Command command : this.commands)
        {
            if (!command.isExecuted())
            {
                command.missing();
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
     * Resets all registered commands by calling their {@link Command#reset()} methods. This usually means that their
     * values are reset and the commands are now considered not set again.
     */
    public void reset()
    {
        for (var cmd : this.commands)
        {
            cmd.reset();
        }
    }
}
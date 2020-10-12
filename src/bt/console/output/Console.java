package bt.console.output;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * @author &#8904
 *
 */
public class Console extends Ansi
{
    static
    {
        Ansi.setDetector(Console::isGlobalEnabled);
        setGlobalEnabled(true);
    }

    private volatile static boolean enabledGlobal;

    /**
     * @return the enabled
     */
    public static boolean isGlobalEnabled()
    {
        return enabledGlobal;
    }

    public static void setGlobalEnabled(boolean enabled)
    {
        enabledGlobal = enabled;

        if (enabled)
        {
            AnsiConsole.systemInstall();
        }
        else
        {
            AnsiConsole.systemUninstall();
        }
    }

    public static Console get()
    {
        return new Console();
    }

    private StringBuilder builder;
    private boolean enabled = true;

    public Console()
    {
        this.builder = new StringBuilder();
    }

    public void enable()
    {
        this.enabled = true;
    }

    public void disable()
    {
        this.enabled = false;
    }

    @Override
    public Ansi newline()
    {
        super.newline();
        this.builder.append(System.getProperty("line.separator"));
        return this;
    }

    @Override
    public Console a(Attribute attribute)
    {
        super.a(attribute.value());
        this.builder.append(attribute.value());
        return this;
    }

    @Override
    public Console a(String value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(boolean value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(char value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(char[] value, int offset, int len)
    {
        super.a(value, offset, len);
        this.builder.append(value, offset, len);
        return this;
    }

    @Override
    public Console a(char[] value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(CharSequence value, int start, int end)
    {
        super.a(value, start, end);
        this.builder.append(value, start, end);
        return this;
    }

    @Override
    public Console a(CharSequence value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(double value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(float value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(int value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(long value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(Object value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console a(StringBuffer value)
    {
        super.a(value);
        this.builder.append(value);
        return this;
    }

    @Override
    public Console fg(Color color)
    {
        super.fg(color);
        return this;
    }

    @Override
    public Console fgBlack()
    {
        return this.fg(Color.BLACK);
    }

    @Override
    public Console fgBlue()
    {
        return this.fg(Color.BLUE);
    }

    @Override
    public Console fgCyan()
    {
        return this.fg(Color.CYAN);
    }

    @Override
    public Console fgDefault()
    {
        return this.fg(Color.DEFAULT);
    }

    @Override
    public Console fgGreen()
    {
        return this.fg(Color.GREEN);
    }

    @Override
    public Console fgMagenta()
    {
        return this.fg(Color.MAGENTA);
    }

    @Override
    public Console fgRed()
    {
        return this.fg(Color.RED);
    }

    @Override
    public Console fgYellow()
    {
        return this.fg(Color.YELLOW);
    }

    @Override
    public Console bg(Color color)
    {
        super.bg(color);
        return this;
    }

    @Override
    public Console bgCyan()
    {
        return this.fg(Color.CYAN);
    }

    @Override
    public Console bgDefault()
    {
        return this.bg(Color.DEFAULT);
    }

    @Override
    public Console bgGreen()
    {
        return this.bg(Color.GREEN);
    }

    @Override
    public Console bgMagenta()
    {
        return this.bg(Color.MAGENTA);
    }

    @Override
    public Console bgRed()
    {
        return this.bg(Color.RED);
    }

    @Override
    public Console bgYellow()
    {
        return this.bg(Color.YELLOW);
    }

    @Override
    public Console fgBright(Color color)
    {
        super.fgBright(color);
        return this;
    }

    @Override
    public Console fgBrightBlack()
    {
        return this.fgBright(Color.BLACK);
    }

    @Override
    public Console fgBrightBlue()
    {
        return this.fgBright(Color.BLUE);
    }

    @Override
    public Console fgBrightCyan()
    {
        return this.fgBright(Color.CYAN);
    }

    @Override
    public Console fgBrightDefault()
    {
        return this.fgBright(Color.DEFAULT);
    }

    @Override
    public Console fgBrightGreen()
    {
        return this.fgBright(Color.GREEN);
    }

    @Override
    public Console fgBrightMagenta()
    {
        return this.fgBright(Color.MAGENTA);
    }

    @Override
    public Console fgBrightRed()
    {
        return this.fgBright(Color.RED);
    }

    @Override
    public Console fgBrightYellow()
    {
        return this.fgBright(Color.YELLOW);
    }

    @Override
    public Console bgBright(Color color)
    {
        super.bgBright(color);
        return this;
    }

    @Override
    public Console bgBrightCyan()
    {
        return this.fgBright(Color.CYAN);
    }

    @Override
    public Console bgBrightDefault()
    {
        return this.bgBright(Color.DEFAULT);
    }

    @Override
    public Console bgBrightGreen()
    {
        return this.bgBright(Color.GREEN);
    }

    @Override
    public Console bgBrightMagenta()
    {
        return this.bg(Color.MAGENTA);
    }

    @Override
    public Console bgBrightRed()
    {
        return this.bgBright(Color.RED);
    }

    @Override
    public Console bgBrightYellow()
    {
        return this.bgBright(Color.YELLOW);
    }

    @Override
    public Console cursor(final int row, final int column)
    {
        super.cursor(row, column);
        return this;
    }

    @Override
    public Console cursorToColumn(final int x)
    {
        super.cursorToColumn(x);
        return this;
    }

    @Override
    public Console cursorUp(final int y)
    {
        super.cursorUp(y);
        return this;
    }

    @Override
    public Console cursorDown(final int y)
    {
        super.cursorDown(y);
        return this;
    }

    @Override
    public Console cursorRight(final int x)
    {
        super.cursorRight(x);
        return this;
    }

    @Override
    public Console cursorLeft(final int x)
    {
        super.cursorLeft(x);
        return this;
    }

    @Override
    public Console cursorDownLine()
    {
        super.cursorDownLine();
        return this;
    }

    @Override
    public Console cursorDownLine(final int n)
    {
        super.cursorDownLine(n);
        return this;
    }

    @Override
    public Console cursorUpLine()
    {
        super.cursorUpLine();
        return this;
    }

    @Override
    public Console cursorUpLine(final int n)
    {
        super.cursorUpLine(n);
        return this;
    }

    @Override
    public Console eraseScreen()
    {
        super.eraseScreen();
        return this;
    }

    @Override
    public Console eraseScreen(final Erase kind)
    {
        super.eraseScreen(kind);
        return this;
    }

    @Override
    public Console eraseLine()
    {
        super.eraseLine();
        return this;
    }

    @Override
    public Console eraseLine(final Erase kind)
    {
        super.eraseLine(kind);
        return this;
    }

    @Override
    public Console scrollUp(final int rows)
    {
        super.scrollUp(rows);
        return this;
    }

    @Override
    public Console scrollDown(final int rows)
    {
        super.scrollDown(rows);
        return this;
    }

    @Override
    public Console saveCursorPosition()
    {
        super.saveCursorPosition();
        return this;
    }

    @Override
    public Console restoreCursorPosition()
    {
        super.restoreCursorPosition();
        return this;
    }

    @Override
    public Console reset()
    {
        return a(Attribute.RESET);
    }

    @Override
    public Console bold()
    {
        return a(Attribute.INTENSITY_BOLD);
    }

    @Override
    public Console boldOff()
    {
        return a(Attribute.INTENSITY_BOLD_OFF);
    }

    @Override
    public String toString()
    {
        if (isEnabled() && this.enabled)
        {
            return super.toString();
        }
        else
        {
            return this.builder.toString();
        }
    }
}
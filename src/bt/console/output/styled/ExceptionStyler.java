package bt.console.output.styled;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionStyler extends PrintWriter
{
    private String[] styles;

    public ExceptionStyler(String... styles)
    {
        super(new StringWriter());

        if (styles.length > 0)
        {
            this.styles = styles;
        }
        else
        {
            this.styles = new String[]{"red"};
        }
    }

    @Override
    public void println(Object o)
    {
        String s = Style.apply(o.toString(), this.styles);
        super.println(s);
    }

    @Override
    public String toString()
    {
        return ((StringWriter)this.out).toString();
    }
}
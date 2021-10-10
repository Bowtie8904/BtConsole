package bt.console.output.styled;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Style
{
    private static boolean enabled = false;
    public static final String DEFAULT_TEXT_STYLE = "default_text";
    public static final String START_TAG = "<+bt";
    public static final String START_TAG_CLOSE = ">";
    public static final String END_TAG = "<-bt>";

    public static String apply(String text, String... styles)
    {
        String result = text;

        if (enabled)
        {
            result = START_TAG + " " + Arrays.stream(styles).collect(Collectors.joining(" ")) + START_TAG_CLOSE + text + END_TAG;
        }

        return result;
    }

    public static String apply(Throwable e, String... styles)
    {
        if (enabled)
        {
            PrintWriter writer = new ExceptionStyler(styles);
            e.printStackTrace(writer);
            return writer.toString();
        }
        else
        {
            StringWriter sWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(sWriter);
            e.printStackTrace(writer);
            return sWriter.toString();
        }
    }

    public static String destyle(String text)
    {
        return text.replaceAll("<\\+bt.*?>", "").replaceAll("<-bt>", "");
    }

    public static void setEnabled(boolean enabled)
    {
        Style.enabled = enabled;
    }
}
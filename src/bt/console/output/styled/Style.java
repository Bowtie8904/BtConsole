package bt.console.output.styled;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Style
{
    public static final String START_TAG = "<+bt";
    public static final String START_TAG_CLOSE = ">";
    public static final String END_TAG = "<-bt>";

    public static String apply(String text, String... styles)
    {
        return START_TAG + " " + Arrays.stream(styles).collect(Collectors.joining(" ")) + START_TAG_CLOSE + text + END_TAG;
    }

    public static String destyle(String text)
    {
        return text.replaceAll("<\\+bt.*?>", "").replaceAll("<-bt>", "");
    }
}
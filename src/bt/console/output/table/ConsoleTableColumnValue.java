package bt.console.output.table;


import java.util.ArrayList;
import java.util.List;

public class ConsoleTableColumnValue
{
    protected Object value;
    protected List<String> formattedLines;

    public ConsoleTableColumnValue(Object value)
    {
        this.value = value;
    }

    public List<String> getFormattedLines()
    {
        return this.formattedLines;
    }

    public void applyFormat(boolean multiline, ConsoleTableColumn column)
    {
        this.formattedLines = new ArrayList<>();
        String text = column.getValueRenderer().render(this.value);

        text = text == null ? "" : text;

        if (multiline)
        {
            for (String line : text.split("\n"))
            {
                line = line.trim();
                line = formatLine(line, column);
                this.formattedLines.add(line);
            }
        }
        else
        {
            String line = formatLine(text.replaceAll("\n", ""), column);
            this.formattedLines.add(line);
        }
    }

    protected String formatLine(String line, ConsoleTableColumn column)
    {
        if (column.getWidth() > 0 && line.length() > column.getWidth())
        {
            if (column.getWidth() <= 3)
            {
                line = "";
            }
            else if (column.getWidth() <= 5)
            {
                line = "...";
            }
            else
            {
                line = line.substring(0, column.getWidth() - 5) + "...";
            }
        }

        column.setMaxCalculatedWidth(line.length() + 2);

        return line;
    }
}
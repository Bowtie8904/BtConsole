package bt.console.output.table;

import java.util.ArrayList;
import java.util.List;

public class ConsoleTableRow
{
    protected List<ConsoleTableColumnValue> values;
    protected int numberOfLines;

    public ConsoleTableRow(Object... values)
    {
        this.values = createValues(values);
    }

    public int getNumberOfLines()
    {
        return this.numberOfLines;
    }

    protected List<ConsoleTableColumnValue> createValues(Object... values)
    {
        List<ConsoleTableColumnValue> valueList = new ArrayList<>();

        for (Object value : values)
        {
            valueList.add(new ConsoleTableColumnValue(value));
        }

        return valueList;
    }

    public void applyFormat(boolean multiline, List<ConsoleTableColumn> columns)
    {
        this.numberOfLines = 0;

        for (int i = 0; i < columns.size(); i++)
        {
            // check if there are values for this column and create default value if not present
            if (i >= this.values.size())
            {
                this.values.add(new ConsoleTableColumnValue(""));
            }

            this.values.get(i).applyFormat(multiline, columns.get(i));
        }

        for (var val : this.values)
        {
            this.numberOfLines = Math.max(this.numberOfLines, val.getFormattedLines().size());
        }
    }

    public Object getValue(int index)
    {
        return this.values.get(index);
    }

    public String getTextValue(int column, int line)
    {
        String textValue = null;
        var value = this.values.get(column);
        var lines = value.getFormattedLines();

        if (lines.size() > line)
        {
            textValue = lines.get(line);
        }

        return textValue;
    }
}
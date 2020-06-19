package bt.console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class holding information about an entire table consisting of individual rows.
 *
 * @author &#8904
 */
public class ConsoleTable
{
    private ConsoleFormatter formatter;
    private List<ConsoleRow> rows = new ArrayList<>();
    private ConsoleRow titleRow;
    private int rowLength = -1;

    /**
     * Creates a new instacne which will create a new formatter with the given format.
     *
     * @param format
     */
    public ConsoleTable(int... format)
    {
        this.formatter = new ConsoleFormatter(format);
    }

    /**
     * Creates a new instance that uses the given formatter.
     *
     * @param formatter
     */
    public ConsoleTable(ConsoleFormatter formatter)
    {
        this.formatter = formatter;
    }

    /**
     * Adds a new row to the bottom of the table and sets the given values for it. There needs to be a value for each
     * column of the formatter.
     *
     * @param data
     */
    public void addRow(Object... data)
    {
        ConsoleRow row = this.formatter.formatRow(data);
        this.rows.add(row);
        this.rowLength = row.length();
    }

    /**
     * Adds a new row to the bottom of the table and sets the given values for it. There needs to be a value for each
     * column of the formatter. The values will be centered within their individual cells.
     *
     * @param data
     */
    public void addCenteredRow(Object... data)
    {
        ConsoleRow row = this.formatter.formatRow(data, true);
        this.rows.add(row);
        this.rowLength = row.length();
    }

    /**
     * Sets the data of the title row. There needs to be a value for each column of the formatter.
     *
     * @param data
     */
    public void setTitle(Object... data)
    {
        ConsoleRow row = this.formatter.formatRow(data);
        this.titleRow = row;
        this.rowLength = row.length();
    }

    /**
     * Sets the data of the title row. There needs to be a value for each column of the formatter. The values will be
     * centered within their individual cells.
     *
     * @param data
     */
    public void setCenteredTitle(Object... data)
    {
        ConsoleRow row = this.formatter.formatRow(data, true);
        this.titleRow = row;
        this.rowLength = row.length();
    }

    /**
     * Removes the row at the given index. Pass -1 to remove the title row.
     *
     * @param index
     */
    public void removeRow(int index)
    {
        if (index == -1)
        {
            this.titleRow = null;
        }
        else
        {
            this.rows.remove(index);
        }
    }

    /**
     * Returns the row at the given index (zero index based, top down). The title row can be accessed by passing -1 as
     * index.
     *
     * @param index
     *            The index of the row. -1 for the title row.
     * @return
     */
    private ConsoleRow rowAtIndex(int index)
    {
        ConsoleRow row;

        if (index == -1)
        {
            row = this.titleRow;
        }
        else
        {
            row = this.rows.get(index);
        }

        return row;
    }

    /**
     * Returns the data array of the row at the given index (zero index based, top down). The title row can be accessed
     * by passing -1 as index.
     *
     * @param index
     *            The index of the row. -1 for the title row.
     * @return The data array of the selected row.
     */
    public Object[] getData(int index)
    {
        return rowAtIndex(index).getData();
    }

    /**
     * Sets the data array of the row at the given index (zero index based, top down). The title row can be accessed by
     * passing -1 as index.
     *
     * <p>
     * The passed data array must have the same length as the used format of this instance.
     * </p>
     *
     * @param index
     *            The index of the row. -1 for the title row.
     * @param data
     *            The new data array.
     */
    public void setData(int index, Object... data)
    {
        rowAtIndex(index).setData(data);
    }

    /**
     * Returns whether the row at the given index (zero index based, top down) is centered. The title row can be
     * accessed by passing -1 as index.
     *
     * @param index
     *            The index of the row. -1 for the title row.
     * @return true if the row is centered, false otherwise.
     */
    public boolean isCentered(int index)
    {
        return rowAtIndex(index).isCentered();
    }

    /**
     * Sets whether the values of the row at the given index (zero index based, top down) will be centered during
     * formatting. The title row can be accessed by passing -1 as index.
     *
     * @param index
     *            The index of the row. -1 for the title row.
     * @param centered
     *            true to center the row in the future.
     */
    public void setCentered(int index, boolean centered)
    {
        rowAtIndex(index).setCentered(centered);
    }

    /**
     * Applies the given formatter to every row in this table.
     *
     * @param formatter
     */
    public void setConsoleFormatter(ConsoleFormatter formatter)
    {
        this.formatter = formatter;

        if (this.titleRow != null)
        {
            this.titleRow.setFormatter(formatter);
        }

        for (ConsoleRow row : this.rows)
        {
            row.setFormatter(formatter);
        }
    }

    /**
     * Reformats every row in this table.
     */
    public void reformat()
    {
        if (this.titleRow != null)
        {
            this.titleRow.setupRow();
        }

        for (ConsoleRow row : this.rows)
        {
            row.setupRow();
        }
    }

    /**
     * Prints the entire table to the given printstream.
     *
     * @param out
     */
    public void print(PrintStream out)
    {
        String separator = "";
        String titleSeparator = "";

        for (int i = 0; i < this.rowLength; i ++ )
        {
            separator += this.formatter.getRowSeparator();
            titleSeparator += this.formatter.getTitleSeparator();
        }

        out.println(separator);

        if (this.titleRow != null)
        {
            out.println(this.titleRow);
            out.println(titleSeparator);
        }

        for (ConsoleRow row : this.rows)
        {
            out.println(row);
            out.println(separator);
        }
    }

    public String getFullRowSeparator()
    {
        String separator = "";

        for (int i = 0; i < this.rowLength; i ++ )
        {
            separator += this.formatter.getRowSeparator();
        }

        return separator;
    }

    /**
     * Returns a string representation of the entire table.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String output = "";

        String separator = "";
        String titleSeparator = "";

        for (int i = 0; i < this.rowLength; i ++ )
        {
            separator += this.formatter.getRowSeparator();
            titleSeparator += this.formatter.getTitleSeparator();
        }

        output += separator + System.lineSeparator();

        if (this.titleRow != null)
        {
            output += this.titleRow + System.lineSeparator();
            output += titleSeparator + System.lineSeparator();
        }

        for (ConsoleRow row : this.rows)
        {
            output += row + System.lineSeparator();
            output += separator + System.lineSeparator();
        }

        return output;
    }
}
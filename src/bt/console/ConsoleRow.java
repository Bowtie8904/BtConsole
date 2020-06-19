package bt.console;

/**
 * A class to hold formatted information about a single row within a {@link ConsoleTable}.
 *
 * @author &#8904
 */
public class ConsoleRow
{
    private ConsoleFormatter formatter;
    private String text;
    private Object[] data;
    private boolean centered;

    /**
     * Creates a new instance and initialises its fields.
     *
     * @param formatter
     *            The formatter that will apply its format to this rows data.
     * @param text
     *            The initial formatted text value for this row. May be empty or null.
     * @param data
     *            The values for its columns. There needs to be a value for each column of the formatter. Values may be
     *            null.
     * @param centered
     *            Defines if the values in this row will be centered or alligned on the left side within their
     *            individual cells.
     */
    public ConsoleRow(ConsoleFormatter formatter, String text, Object[] data, boolean centered)
    {
        this.formatter = formatter;
        this.text = text;
        this.data = data;
        this.centered = centered;
    }

    /**
     * Requests formatted text from the set formater.
     */
    public void setupRow()
    {
        ConsoleRow newRow = this.formatter.formatRow(this.data, this.centered);
        this.text = newRow.toString();
    }

    /**
     * Sets the formatter that should take care of the row formatting. {@link #setupRow()} will be called.
     *
     * @param formatter
     */
    public void setFormatter(ConsoleFormatter formatter)
    {
        this.formatter = formatter;
        setupRow();
    }

    /**
     * Gets the column values of this row.
     *
     * @return the data
     */
    public Object[] getData()
    {
        return this.data;
    }

    /**
     * Sets the values for the columns of this row.
     *
     * @param data
     *            the data to set
     */
    public void setData(Object... data)
    {
        this.data = data;
        setupRow();
    }

    /**
     * Indicates whether this row has cenetred or left alligned values.
     *
     * @return the centered
     */
    public boolean isCentered()
    {
        return this.centered;
    }

    /**
     * Sets whether this row has cenetred or left alligned values.
     *
     * @param centered
     *            the centered to set
     */
    public void setCentered(boolean centered)
    {
        this.centered = centered;
        setupRow();
    }

    /**
     * Gets the total length of this row.
     *
     * @return
     */
    public int length()
    {
        return this.text.length();
    }

    /**
     * Returns the formatted representation of this row.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.text == null ? "" : this.text;
    }
}
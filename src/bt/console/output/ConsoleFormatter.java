package bt.console.output;

/**
 * Class that is able to format table cells and their contained values for the {@link ConsoleTable}.
 *
 * @author &#8904
 */
public class ConsoleFormatter
{
    private int[] format;
    private String columnSeparator = "|";
    private char rowSeparator = '-';
    private char titleSeparator = '=';

    /**
     * Creates a new instance with the given format.
     *
     * @param format
     *            An array defining the character widths of the individual columns, left to right. A value of 5 means
     *            that the inside of the column will be 5 characters wide. This includes 2 characters for a space on
     *            each side of the contained value.
     */
    public ConsoleFormatter(int... format)
    {
        this.format = format;
    }

    /**
     * Creates a {@link ConsoleRow} with the given values by applying the set format. The values will be alligned on the
     * left side of the cells. Each value will be separated by the set {@link #setColumnSeparator(String)
     * columnSeparator}, the default value for this is the | character.
     *
     * @param data
     *            The array containing a value for each column. The array needs to have the same length as the format
     *            array. Null values will be replaced with the String "null".
     * @return
     */
    public ConsoleRow formatRow(Object[] data)
    {
        return formatRow(data, false);
    }

    /**
     * Creates a {@link ConsoleRow} with the given values by applying the set format. Each value will be separated by
     * the set {@link #setColumnSeparator(String) columnSeparator}, the default value for this is the | character.
     *
     * @param data
     *            The array containing a value for each column. The array needs to have the same length as the format
     *            array. Null values will be replaced with the String "null".
     * @param centered
     *            Defines if the values will be centered inside their cells or alligned on the left side. This applies
     *            to all values inside this row.
     * @return
     */
    public ConsoleRow formatRow(Object[] data, boolean centered)
    {
        if (data.length != this.format.length)
        {
            throw new IllegalArgumentException("Format and text array must be the same length.");
        }

        Object[] dataCopy = new Object[data.length];

        for (int i = 0; i < data.length; i ++ )
        {
            dataCopy[i] = data[i] == null ? "null" : data[i];
        }

        String row = this.columnSeparator;

        for (int i = 0; i < dataCopy.length; i ++ )
        {
            if (dataCopy[i].toString().length() > this.format[i] - 2)
            {
                if (this.format[i] == 0)
                {
                    throw new IllegalArgumentException("Format " + this.format[i] + " at position " + i + " is invalid. Formats must be above 0.");
                }

                if (this.format[i] <= 3)
                {
                    dataCopy[i] = "";
                }
                else if (this.format[i] <= 5)
                {
                    dataCopy[i] = "...";
                }
                else
                {
                    dataCopy[i] = dataCopy[i].toString().substring(0, this.format[i] - 5) + "...";
                }
            }

            int spaces = (int)((this.format[i] - dataCopy[i].toString().length()) / 2);

            if (centered)
            {
                for (int j = 0; j < spaces; j ++ )
                {
                    row += " ";
                }

                row += dataCopy[i];
                spaces = this.format[i] - dataCopy[i].toString().length() - spaces;

                for (int j = 0; j < spaces; j ++ )
                {
                    row += " ";
                }
            }
            else
            {
                row += " ";
                row += dataCopy[i];
                spaces = this.format[i] - dataCopy[i].toString().length() - 1;

                for (int j = 0; j < spaces; j ++ )
                {
                    row += " ";
                }
            }

            row += this.columnSeparator;
        }

        return new ConsoleRow(this,
                              row,
                              data,
                              centered);
    }

    /**
     * Gets the format that is used by this instance.
     *
     * @return the format
     */
    public int[] getFormat()
    {
        return this.format;
    }

    /**
     * Sets the format that is used by this instance.
     *
     * @param format
     *            An array defining the character widths of the individual columns, left to right. A value of 5 means
     *            that the inside of the column will be 5 characters wide. This includes 2 characters for a space on
     *            each side of the contained value.
     */
    public void setFormat(int... format)
    {
        this.format = format;
    }

    /**
     * Gets the character that is used to horizontally separate individual rows and create the top and bottom borders
     * for the table.
     *
     * @return the rowSeparator
     */
    public char getRowSeparator()
    {
        return this.rowSeparator;
    }

    /**
     * Sets the character that is used to horizontally separate individual rows and create the top and bottom borders
     * for the table.
     *
     * @param rowSeparator
     *            the rowSeparator to set
     */
    public void setRowSeparator(char rowSeparator)
    {
        this.rowSeparator = rowSeparator;
    }

    /**
     * Gets the character that is used to horizontally separate the title row from the other rows.
     *
     * @return
     */
    public char getTitleSeparator()
    {
        return this.titleSeparator;
    }

    /**
     * Sets the character that is used to horizontally separate the title row from the other rows.
     *
     * @param rowSeparator
     *            the rowSeparator to set
     */
    public void setTitleSeparator(char titleSeparator)
    {
        this.titleSeparator = titleSeparator;
    }

    /**
     * Gets the value that is used to separate individual cell values within a row.
     *
     * @return the columnSeparator
     */
    public String getColumnSeparator()
    {
        return this.columnSeparator;
    }

    /**
     * Sets the value that is used to vertically separate individual cell values within a row. The default value is the
     * | character.
     *
     * @param columnSeparator
     *            the columnSeparator to set
     */
    public void setColumnSeparator(String columnSeparator)
    {
        this.columnSeparator = columnSeparator;
    }
}
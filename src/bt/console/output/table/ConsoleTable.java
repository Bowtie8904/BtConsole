package bt.console.output.table;

import bt.console.output.styled.Style;
import bt.console.output.table.render.Alignment;
import bt.console.output.table.render.ConsoleTableValueRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsoleTable
{
    protected List<ConsoleTableColumn> columns;
    protected List<ConsoleTableRow> rows;
    protected String columnSeparator = "|";
    protected char rowSeparator = '-';
    protected char titleSeparator = '=';
    protected String[] defaultValueStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
    protected String[] defaultHeaderStyles = new String[]{ Style.DEFAULT_TEXT_STYLE, "bold" };
    protected String[] separatorStyles = new String[]{ Style.DEFAULT_TEXT_STYLE };
    protected boolean multiline;

    public static ConsoleTable of(Map map)
    {
        return of(map, null, null);
    }

    public static <K, V> ConsoleTable of(Map<K, V> map, ConsoleTableValueRenderer<K> keyRenderer, ConsoleTableValueRenderer<V> valueRenderer)
    {
        var table = new ConsoleTable();
        table.setMultiline(true);

        var col = table.addColumn("Key");

        if (keyRenderer != null)
        {
            col.setValueRenderer(keyRenderer);
        }

        col = table.addColumn("Value");

        if (valueRenderer != null)
        {
            col.setValueRenderer(valueRenderer);
        }

        for (K key : map.keySet())
        {
            table.addRow(key, map.get(key));
        }

        return table;
    }

    public static ConsoleTable of(Iterable iterable)
    {
        return of(iterable, null);
    }

    public static <T> ConsoleTable of(Iterable<T> iterable, ConsoleTableValueRenderer<T> valueRenderer)
    {
        var table = new ConsoleTable();
        table.setMultiline(true);

        var col = table.addColumn("Index");
        col.setValueAlignment(Alignment.RIGHT);

        col = table.addColumn("Value");

        if (valueRenderer != null)
        {
            col.setValueRenderer(valueRenderer);
        }

        int index = 0;

        for (T value : iterable)
        {
            table.addRow(index++, value);
        }

        return table;
    }

    public ConsoleTable(String... columnHeaders)
    {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();

        for (String header : columnHeaders)
        {
            addColumn(header);
        }
    }

    public ConsoleTableColumn addColumn(String header, int width)
    {
        var column = new ConsoleTableColumn(header, width);
        column.setDefaultHeaderStyles(this.defaultHeaderStyles);
        column.setHeaderStyles(this.defaultHeaderStyles);
        column.setDefaultValueStyles(this.defaultValueStyles);
        column.setHeaderAlignment(Alignment.CENTER);
        this.columns.add(column);

        return column;
    }

    public ConsoleTableColumn addColumn(String header)
    {
        return addColumn(header, -1);
    }

    public void addRow(Object... values)
    {
        this.rows.add(new ConsoleTableRow(values));
    }

    public void replaceRow(int index, Object... values)
    {
        this.rows.remove(index);
        this.rows.add(index, new ConsoleTableRow(values));
    }

    public void setDefaultValueStyles(String... defaultValueStyles)
    {
        this.defaultValueStyles = defaultValueStyles;
    }

    public void setDefaultHeaderStyles(String... defaultHeaderStyles)
    {
        this.defaultHeaderStyles = defaultHeaderStyles;
    }

    public void setSeparatorStyles(String... separatorStyles)
    {
        this.separatorStyles = separatorStyles;
    }

    public boolean isMultiline()
    {
        return multiline;
    }

    public void setMultiline(boolean multiline)
    {
        this.multiline = multiline;
    }

    public String getColumnSeparator()
    {
        return columnSeparator;
    }

    public void setColumnSeparator(String columnSeparator)
    {
        this.columnSeparator = columnSeparator;
    }

    public char getRowSeparator()
    {
        return rowSeparator;
    }

    public void setRowSeparator(char rowSeparator)
    {
        this.rowSeparator = rowSeparator;
    }

    public char getHeaderSeparator()
    {
        return titleSeparator;
    }

    public void setHeaderSeparator(char titleSeparator)
    {
        this.titleSeparator = titleSeparator;
    }

    @Override
    public String toString()
    {
        for (var row : this.rows)
        {
            row.applyFormat(this.multiline, this.columns);
        }

        String output = "";
        String rowSeparator = "";
        String headerSeparator = "";

        for (var col : this.columns)
        {
            for (int i = 0; i < col.getCalculatedWidth() + 1; i++)
            {
                rowSeparator += this.rowSeparator;
                headerSeparator += this.titleSeparator;
            }
        }

        rowSeparator += this.rowSeparator;
        headerSeparator += this.titleSeparator;
        rowSeparator = Style.apply(rowSeparator, this.separatorStyles);
        headerSeparator = Style.apply(headerSeparator, this.separatorStyles);

        output += rowSeparator + System.lineSeparator();
        output += Style.apply(this.columnSeparator, this.separatorStyles);

        for (var col : this.columns)
        {
            output += formatColumn(col.getHeader(), col.getHeaderAlignment(), col.getCalculatedWidth(), col.getHeaderStyles());
        }

        output += System.lineSeparator() + headerSeparator + System.lineSeparator();

        String textValue = null;

        for (var row : this.rows)
        {
            for (int i = 0; i < row.getNumberOfLines(); i++)
            {
                output += Style.apply(this.columnSeparator, this.separatorStyles);
                for (int j = 0; j < this.columns.size(); j++)
                {
                    var col = this.columns.get(j);
                    textValue = row.getTextValue(j, i);
                    output += formatColumn(textValue == null ? "" : textValue,
                                           col.getValueAlignment(),
                                           col.getCalculatedWidth(),
                                           col.getStyleRenderer().render(row.getValue(j)));
                }

                output += System.lineSeparator();
            }

            output += rowSeparator + System.lineSeparator();
        }

        return output;
    }

    protected String formatColumn(String value, Alignment alignment, int width, String[] styles)
    {
        String column = "";
        String destyledValue = Style.destyle(value);

        int spaces = (int)((width - destyledValue.length()) / 2);

        if (alignment == Alignment.CENTER)
        {
            for (int j = 0; j < spaces; j ++ )
            {
                column += " ";
            }

            column += Style.apply(value, styles);
            spaces = Math.max(width - destyledValue.length() - spaces, 1);

            for (int j = 0; j < spaces; j ++ )
            {
                column += " ";
            }
        }
        else if (alignment == Alignment.RIGHT)
        {
            spaces = width - destyledValue.length() - 1;

            for (int j = 0; j < spaces; j ++ )
            {
                column += " ";
            }

            column += Style.apply(value, styles);
            column += " ";
        }
        else
        {
            column += " ";
            column += Style.apply(value, styles);
            spaces = Math.max(width - destyledValue.length() - 1, 1);

            for (int j = 0; j < spaces; j ++ )
            {
                column += " ";
            }
        }

        column += Style.apply(this.columnSeparator, this.separatorStyles);

        return column;
    }
}
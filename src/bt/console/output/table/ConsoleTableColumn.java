package bt.console.output.table;

import bt.console.output.table.render.ConsoleTableStyleRenderer;
import bt.console.output.table.render.ConsoleTableValueRenderer;


public class ConsoleTableColumn
{
    protected ConsoleTableStyleRenderer styleRenderer;
    protected ConsoleTableValueRenderer valueRenderer;
    protected int width;
    protected int calculatedWidth;
    protected boolean headerCentered;
    protected boolean valuesCentered;
    protected String header;
    protected String[] headerStyles;
    protected String[] defaultValueStyles;
    protected String[] defaultHeaderStyles;

    public ConsoleTableColumn(String header, int width)
    {
        setHeader(header);
        setWidth(width);
        this.styleRenderer = createDefaultStyleRenderer();
        this.valueRenderer = createDefaultValueRenderer();
        this.headerStyles = getDefaultHeaderStyles();
    }

    public ConsoleTableColumn(String header)
    {
        this(header, -1);
    }

    protected ConsoleTableStyleRenderer createDefaultStyleRenderer()
    {
        return value -> this.defaultValueStyles;
    }

    protected ConsoleTableValueRenderer createDefaultValueRenderer()
    {
        return value -> value == null ? "< null >" : value.toString();
    }

    protected String[] getDefaultHeaderStyles()
    {
        return this.defaultHeaderStyles;
    }

    public ConsoleTableStyleRenderer getStyleRenderer()
    {
        return styleRenderer;
    }

    public void setStyleRenderer(ConsoleTableStyleRenderer styleRenderer)
    {
        if (styleRenderer != null)
        {
            this.styleRenderer = styleRenderer;
        }
        else
        {
            this.styleRenderer = createDefaultStyleRenderer();
        }
    }

    public ConsoleTableValueRenderer getValueRenderer()
    {
        return valueRenderer;
    }

    public void setValueRenderer(ConsoleTableValueRenderer valueRenderer)
    {
        if (valueRenderer != null)
        {
            this.valueRenderer = valueRenderer;
        }
        else
        {
            this.valueRenderer = createDefaultValueRenderer();
        }
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public boolean isHeaderCentered()
    {
        return headerCentered;
    }

    public void setHeaderCentered(boolean headerCentered)
    {
        this.headerCentered = headerCentered;
    }

    public boolean isValuesCentered()
    {
        return valuesCentered;
    }

    public void setValuesCentered(boolean valuesCentered)
    {
        this.valuesCentered = valuesCentered;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
        this.calculatedWidth = this.header.length() + 2;
    }

    public String[] getHeaderStyles()
    {
        return headerStyles;
    }

    public void setHeaderStyles(String... headerStyles)
    {
        if (headerStyles != null && headerStyles.length > 0)
        {
            this.headerStyles = headerStyles;
        }
        else
        {
            this.headerStyles = getDefaultHeaderStyles();
        }
    }

    protected void setDefaultValueStyles(String... defaultValueStyles)
    {
        this.defaultValueStyles = defaultValueStyles;
    }

    protected void setDefaultHeaderStyles(String... defaultHeaderStyles)
    {
        this.defaultHeaderStyles = defaultHeaderStyles;
    }

    protected void setMaxCalculatedWidth(int width)
    {
        if (this.width < 0)
        {
            this.calculatedWidth = Math.max(this.calculatedWidth, width);
        }
        else
        {
            this.calculatedWidth = Math.min(Math.max(this.calculatedWidth, width), this.width);
        }
    }

    protected int getCalculatedWidth()
    {
        return calculatedWidth;
    }

    protected void reset()
    {
        this.calculatedWidth = 0;
    }
}
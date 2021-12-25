package bt.console.output.styled;

import bt.console.output.styled.exc.StyleParseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StyledTextNode
{
    private StyledTextNode parent;
    private List<StyledTextNode> children;
    private List<String> styles;
    private String text = "";
    private boolean closed;
    private boolean isHyperlink;

    public StyledTextNode()
    {
        this.children = new ArrayList<>();
        this.styles = new ArrayList<>();
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setParent(StyledTextNode parent)
    {
        this.parent = parent;
    }

    public StyledTextNode getParent()
    {
        return parent;
    }

    public List<StyledTextNode> getChildren()
    {
        return children;
    }

    public void addChild(StyledTextNode node)
    {
        this.children.add(node);
        node.setParent(this);
    }

    public void addStyles(String[] styles)
    {
        for (String style : styles)
        {
            this.styles.add(style);
        }
    }

    public void addStyle(String style)
    {
        this.styles.add(style);

        if (style.equals(Style.HYPERLINK_STYLE))
        {
            this.isHyperlink = true;
        }
    }

    public boolean isClosed()
    {
        return closed;
    }

    public void close()
    {
        if (this.closed)
        {
            throw new StyleParseException("Node already closed.");
        }

        boolean closedChild = false;

        for (var node : this.children)
        {
            if (!node.isClosed())
            {
                node.close();
                closedChild = true;
                break;
            }
        }

        this.closed = !closedChild;
    }

    public boolean isCompletelyClosed()
    {
        boolean isClosed = this.closed;

        for (var childNode : this.children)
        {
            isClosed = isClosed && childNode.isCompletelyClosed();

            if (!isClosed)
            {
                break;
            }
        }

        return isClosed;
    }

    public Set<String> getStyles()
    {
        Set<String> fullList = new HashSet();
        boolean inheritStyles = true;

        for (String style : this.styles)
        {
            if (style.equals("-*"))
            {
                inheritStyles = false;
            }
        }

        if (inheritStyles)
        {
            if (this.parent != null && !this.isHyperlink)
            {
                fullList.addAll(this.parent.getStyles());
            }
        }

        fullList.addAll(this.styles);

        for (String style : this.styles)
        {
            if (style.startsWith("-"))
            {
                fullList.remove(style);
                fullList.remove(style.substring(1));
            }
        }

        return fullList;
    }

    public boolean isHyperlink()
    {
        return isHyperlink;
    }

    public void setHyperlink(boolean hyperlink)
    {
        isHyperlink = hyperlink;
    }

    @Override
    public String toString()
    {
        String s = System.lineSeparator() + this.styles.toString() + (this.text != null ? this.text : "") + System.lineSeparator();

        for (var child : this.children)
        {
            s += child.toString(4);
        }

        return s;
    }

    public String toString(int indentation)
    {
        String s = this.styles.toString() + (this.text != null ? this.text : "") + System.lineSeparator();

        for (int i = 0; i < indentation; i++)
        {
            s = " " + s;
        }

        for (var child : this.children)
        {
            s += child.toString(indentation + 4);
        }

        return s;
    }
}
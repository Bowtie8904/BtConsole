package bt.console.output.styled;

import java.util.ArrayList;
import java.util.List;

public class StyledTextNode
{
    private StyledTextNode parent;
    private List<StyledTextNode> children;
    private List<String> styles;
    private String text = "";

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
    }

    public List<String> getStyles()
    {
        List<String> fullList = new ArrayList();

        if (this.parent != null)
        {
            fullList.addAll(this.parent.getStyles());
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

    @Override
    public String toString()
    {
        String s = "\n" + this.styles.toString() + (this.text != null ? this.text : "") + "\n";

        for (var child : this.children)
        {
            s += child.toString(4);
        }

        return s;
    }

    public String toString(int indentation)
    {
        String s = this.styles.toString() + (this.text != null ? this.text : "")+ "\n";

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
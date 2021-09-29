package bt.console.output.styled;

public class StyledTextParser
{
    public StyledTextNode parseNode(String text)
    {
        StyledTextNode parent = new StyledTextNode();
        parseNode(text, parent);

        return parent;
    }

    protected int parseNode(String text, StyledTextNode parent)
    {
        StyledTextNode node = new StyledTextNode();
        parent.addChild(node);

        int textIndex = 0;
        int startTagIndex = 0;
        int startTagEndIndex = 0;
        int endTagIndex = 0;

        startTagIndex = text.indexOf(Style.START_TAG, textIndex);

        if (startTagIndex == -1)
        {
            endTagIndex = text.indexOf(Style.END_TAG);

            if (endTagIndex != -1)
            {
                node.setText(text.substring(0, endTagIndex));

                if (parent.getParent() != null)
                {
                    textIndex = parseNode(text.substring(endTagIndex + Style.END_TAG.length()), parent.getParent());
                }
                else
                {
                    throw new IllegalArgumentException("Invalid closing tag found: " + text);
                }
            }
            else
            {
                node.setText(text);
                textIndex = -1;
            }
        }
        else if (startTagIndex != 0)
        {
            node.setText(text.substring(0, startTagIndex));
            textIndex = parseNode(text.substring(startTagIndex), parent);
        }
        else
        {
            while (textIndex != -1)
            {
                startTagEndIndex = text.indexOf(Style.START_TAG_CLOSE, textIndex);
                String[] tags = text.substring(startTagIndex + Style.START_TAG.length(), startTagEndIndex).trim().split(" ");
                node.addStyles(tags);

                // check if there is more tags before the next closing tag
                startTagIndex = text.indexOf(Style.START_TAG, startTagEndIndex);
                endTagIndex = text.indexOf(Style.END_TAG, startTagEndIndex);

                if (startTagIndex != -1 && startTagIndex < endTagIndex)
                {
                    textIndex = parseNode(text.substring(startTagEndIndex + 1), node);
                }
                else if (endTagIndex != -1)
                {
                    textIndex = parseNode(text.substring(startTagEndIndex + 1, endTagIndex), node);
                    textIndex = parseNode(text.substring(endTagIndex + Style.END_TAG.length()), parent);
                }
                else
                {
                    textIndex = parseNode(text.substring(startTagEndIndex + 1), node);
                }
            }
        }

        return textIndex;
    }
}
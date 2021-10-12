package bt.console.output.styled;

import bt.console.output.styled.exc.StyleParseException;

public class StyledTextParser
{
    public StyledTextNode parseNode(String text)
    {
        StyledTextNode parent = new StyledTextNode();
        StyledTextNode currentNode = parent;
        currentNode.close();

        int textIndex = 0;

        int startTagIndex = 0;
        int endTagIndex = 0;
        int startTagEndIndex = 0;

        while (textIndex < text.length())
        {
            startTagIndex = text.indexOf(Style.START_TAG, textIndex);
            endTagIndex = text.indexOf(Style.END_TAG, textIndex);

            if (startTagIndex != -1 && startTagIndex <= Math.max(0, endTagIndex))
            {
                // parse starting tag

                // check if there is more text before the start tag
                if (textIndex < startTagIndex)
                {
                    // create new node for remaining text
                    StyledTextNode newNode = new StyledTextNode();
                    newNode.setText(text.substring(textIndex, startTagIndex));
                    newNode.close();
                    currentNode.addChild(newNode);
                }

                // create new node and set as child
                StyledTextNode newNode = new StyledTextNode();
                currentNode.addChild(newNode);

                // parse styles
                textIndex = text.indexOf(Style.START_TAG_CLOSE, textIndex);
                String[] tags = text.substring(startTagIndex + Style.START_TAG.length(), textIndex).trim().split(" ");
                newNode.addStyles(tags);

                // moving past starting tag end
                textIndex += 1;

                // make new node current node for further processing
                currentNode = newNode;
            }
            else if (endTagIndex != -1)
            {
                // parse closing tag

                // check if there is more text before the end tag
                if (textIndex < endTagIndex)
                {
                    currentNode.setText(text.substring(textIndex, endTagIndex));
                }

                // attempt to close current node
                try
                {
                    currentNode.close();
                }
                catch (StyleParseException e)
                {
                    throw new StyleParseException("Invalid closing tag found: " + text.substring(0, endTagIndex + Style.END_TAG.length()), e);
                }

                // move up to parrent after closing node
                currentNode = currentNode.getParent();
                textIndex = endTagIndex + Style.END_TAG.length();
            }
            else
            {
                // add remaining text as new node
                StyledTextNode newNode = new StyledTextNode();
                newNode.setText(text.substring(textIndex));
                newNode.close();
                currentNode.addChild(newNode);
                textIndex = text.length();
            }
        }

        if (!parent.isCompletelyClosed())
        {
            throw new StyleParseException("One or multiple closing tags missing: " + text);
        }

        return parent;
    }
}
package bt.console.output.table.render;

@FunctionalInterface
public interface ConsoleTableStyleRenderer
{
    public String[] render(Object value);
}
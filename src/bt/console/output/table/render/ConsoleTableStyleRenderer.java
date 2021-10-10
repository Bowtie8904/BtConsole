package bt.console.output.table.render;

@FunctionalInterface
public interface ConsoleTableStyleRenderer<T>
{
    public String[] render(T value);
}
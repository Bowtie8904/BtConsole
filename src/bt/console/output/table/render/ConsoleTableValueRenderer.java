package bt.console.output.table.render;

@FunctionalInterface
public interface ConsoleTableValueRenderer<T>
{
    public String render(T value);
}

package bt.console.output.table.render;

@FunctionalInterface
public interface ConsoleTableValueRenderer
{
    public String render(Object value);
}

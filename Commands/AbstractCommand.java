package Lab_6.Commands;
import Lab_6.Exceptions.CommandResultException;
import Lab_6.Utilities.*;

import java.sql.Connection;

/**
 * Abstract class for all commands
 */
public abstract class AbstractCommand {
    protected String name;
    protected String description;
    protected Control control;
    public AbstractCommand(Control control) {
        this.control = control;
    }
    public abstract void activate(String arg, Connection connection, int id) throws CommandResultException;

    public abstract String getName();

    public abstract String getDescription();
}

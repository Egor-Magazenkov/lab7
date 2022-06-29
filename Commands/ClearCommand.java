package Lab_6.Commands;

import Lab_6.Collection.StudyGroup;
import Lab_6.Database.Study_group_base;
import Lab_6.Exceptions.CommandResultException;
import Lab_6.Utilities.Control;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Scanner;


public class ClearCommand extends AbstractCommand{
    private String name = "clear";
    private String description = "очистить коллекцию";

    public ClearCommand(Control control) {
        super(control);
    }


    @Override
    public void activate(String arg, Connection connection, int id) throws CommandResultException {
        HashSet<StudyGroup> groups = this.control.getGroups();
        Study_group_base sg = new Study_group_base();
        sg.clear(connection);
        groups.clear();
        throw new CommandResultException(0, "Все элементы коллекции были удалены.");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

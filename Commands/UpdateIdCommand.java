package Lab_6.Commands;

import Lab_6.Database.Study_group_base;
import Lab_6.Exceptions.AbsenceResultException;
import Lab_6.Exceptions.CommandResultException;
import Lab_6.Exceptions.WrongFormatException;
import Lab_6.Utilities.*;
import Lab_6.Collection.*;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Scanner;

public class UpdateIdCommand extends AbstractCommand{
    private String name = "update_id";
    private String description = "обновить значение элемента коллекции, id которого равен заданному";

    public UpdateIdCommand(Control control) {
        super(control);
    }

    @Override
    public void activate(String s, Connection connection, int id) throws CommandResultException {
        String[] s_ = s.split(" ");
        int ID;
        long new_average_mark;
        try {
            ID = Integer.parseInt(s_[0]);
            new_average_mark = Long.parseLong(s.split(" ")[1]);
        }
        catch(NumberFormatException | IndexOutOfBoundsException e){
            throw new CommandResultException(1, "Ошибка ввода. Воспользуйтесь командой help.");
        }


        HashSet<StudyGroup> groups = this.control.getGroups();
        FindStudyGroupByVariable finder = new FindStudyGroupByVariable(groups);
        Study_group_base sg = new Study_group_base();
        try {
            StudyGroup group = finder.find_by_id(ID);
            if (group.getUser_id() == id) {
                sg.changeValue(connection, ID, new_average_mark);
                group.setAverageMark(new_average_mark);
                throw new CommandResultException(0, "Средняя оценка группы с Id=" + ID + " обновлено на " + new_average_mark);
            }
            else{
                throw new CommandResultException(1, "Средняя оценка группы с Id=" + ID + " не обновлена на " + new_average_mark + ". нет доступа");
            }
        }
        catch (WrongFormatException e) {
            throw new CommandResultException(1, e.getMessage());
        }
        catch (AbsenceResultException e){
            throw new CommandResultException(1, e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}

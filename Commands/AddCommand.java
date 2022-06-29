package Lab_6.Commands;

import Lab_6.Collection.StudyGroup;
import Lab_6.Database.Study_group_base;
import Lab_6.Exceptions.CommandResultException;
import Lab_6.Exceptions.SetResultException;
import Lab_6.Utilities.ChangeGroupFieldFromFile;
import Lab_6.Utilities.ChangeGroupFieldFromInput;
import Lab_6.Utilities.Control;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class AddCommand extends AbstractCommand{
    private String name = "add";
    private String description = "добавить новый элемент в коллекцию";

    public AddCommand(Control control) {
        super(control);
    }

    @Override
    public void activate(String arg, Connection connection, int id) throws CommandResultException {
        HashSet<StudyGroup> groups = this.control.getGroups();
        StudyGroup new_group = new StudyGroup();
        Study_group_base sg = new Study_group_base();
        if (control.isFromFile()){
            ChangeGroupFieldFromFile fieldSetter = new ChangeGroupFieldFromFile(new_group);
            boolean res = true;
            try {
                fieldSetter.change_name();
            } catch (SetResultException e) {
                res = false;
            }
            try {
                fieldSetter.change_coordinate();
            } catch (SetResultException e) {
                res = false;

            }
            try {
                fieldSetter.change_StudentsCount();
            } catch (SetResultException e) {
                res = false;

            }
            try {
                fieldSetter.change_ExpelledStudents();
            } catch (SetResultException e) {
                res = false;

            }
            try {
                fieldSetter.change_AverageMark();
            } catch (SetResultException e) {
                res = false;

            }
            try {
                fieldSetter.change_FormOfEducation();
            } catch (SetResultException e) {
                res = false;

            }
            try {
                fieldSetter.change_Admin();
            } catch (SetResultException e) {
                res = false;

            }
            if (res){
            sg.add(connection, new HashMap<String, String>(){{
                put("name", String.format("'%s'", new_group.getName()));
                put("coordinate_x", String.format("%.2f", new_group.getCoordinates().getX()));
                put("coordinate_y", String.format("%.2f", new_group.getCoordinates().getY()));
                put("creationdate", String.format("'%s'", new_group.getCreationDate().toString()));
                put("studentscount", String.format("%d", new_group.getStudentsCount()));
                put("expelledStudents", String.format("%d", new_group.getExpelledStudents()));
                put("averagemark", String.format("%d", new_group.getAverageMark()));
                put("formofeducation", String.format("%s", new_group.getFormOfEducation().toString()));
                put("admin_name", String.format("%s", new_group.getGroupAdmin().getName()));
                put("admin_eyecolor", String.format("%s", new_group.getGroupAdmin().getEyeColor()));
                put("admin_nationality", String.format("%s", new_group.getGroupAdmin().getNationality()));
                put("admin_birthday", String.format("%s", new_group.getGroupAdmin().getBirthday().toString()));
                put("admin_location_x", String.format("%.2f", new_group.getGroupAdmin().getLocation().getX()));
                put("admin_location_y", String.format("%.2f", new_group.getGroupAdmin().getLocation().getY()));
                put("admin_location_name", String.format("%s", new_group.getGroupAdmin().getLocation().getName()));
                put("user_id", String.format("%d", id));
            }});
            groups.add(new_group);
            }
            else {
                throw new CommandResultException(1, "Элемент не был добавлен из-за неправильного ввода.");
            }
        }else {
            ChangeGroupFieldFromInput fieldSetter = new ChangeGroupFieldFromInput(new_group, control.getOis(), control.getOos());
            fieldSetter.change_name();
            fieldSetter.change_coordinate();
            fieldSetter.change_StudentsCount();
            fieldSetter.change_ExpelledStudents();
            fieldSetter.change_AverageMark();
            fieldSetter.change_FormOfEducation();
            fieldSetter.change_Admin();
            new_group.setUser_id(id);
            sg.add(connection, new HashMap<String, String>(){{
                put("name", String.format("'%s'", new_group.getName()));
                put("coordinate_x", String.format("%.2f", new_group.getCoordinates().getX()));
                put("coordinate_y", String.format("%.2f", new_group.getCoordinates().getY()));
                put("creationdate", String.format("'%s'", new_group.getCreationDate().toString()));
                put("studentscount", String.format("%d", new_group.getStudentsCount()));
                put("expelledStudents", String.format("%d", new_group.getExpelledStudents()));
                put("averagemark", String.format("%d", new_group.getAverageMark()));
                put("formofeducation", String.format("'%s'", new_group.getFormOfEducation().toString()));
                put("admin_name", String.format("'%s'", new_group.getGroupAdmin().getName()));
                put("admin_eyecolor", String.format("'%s'", new_group.getGroupAdmin().getEyeColor()));
                put("admin_nationality", String.format("'%s'", new_group.getGroupAdmin().getNationality()));
                put("admin_birthday", String.format("'%s'", new_group.getGroupAdmin().getBirthday().toString()));
                put("admin_location_x", String.format("%.2f", new_group.getGroupAdmin().getLocation().getX()));
                put("admin_location_y", String.format("%.2f", new_group.getGroupAdmin().getLocation().getY()));
                put("admin_location_name", String.format("'%s'", new_group.getGroupAdmin().getLocation().getName()));
                put("user_id", String.format("%d", id));
            }});
            groups.add(new_group);
        }
        throw new CommandResultException(0, "Группа добавлена.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

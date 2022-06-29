package Lab_6.Parts.ClientSide;

import Lab_6.Collection.StudyGroup;
import Lab_6.Database.Model;
import Lab_6.Utilities.Control;
import Lab_6.Utilities.ParseXML;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                Socket socket = new Socket("localhost", 5000);

                System.out.println("Connected with Server...");
                System.out.println("Вы должны подключиться к базе данных используя свой логин и пароль. Или зарегестрироваться.");
                System.out.println("Введите sign in для входа или register для регистрации.");
                DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream oos = new ObjectOutputStream(dout);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                ObjectInputStream ois = new ObjectInputStream(dis);
                HashSet<StudyGroup> groups;
                String response = null;
                String command;
                String login = null;
                String password = null;
                while (true) {
                    try {
                        command = sc.nextLine();
                        switch (command) {
                            case "sign in":
                                oos.writeObject("sign in");

                                System.out.println("Введите логин");
                                login = sc.nextLine();
                                oos.writeObject(login);
                                System.out.println("Введите пароль");
                                password = sc.nextLine();
                                oos.writeObject(password);

                                response = ois.readObject().toString();
                                break;
                            case "register":
                                oos.writeObject("register");

                                System.out.println("Придумайте логин");
                                login = sc.nextLine();
                                oos.writeObject(login);
                                System.out.println("Придумайте пароль");
                                password = sc.nextLine();
                                oos.writeObject(password);
                                response = ois.readObject().toString();

                                break;
                            default:
                                response = null;
                            
                        }
                        System.out.println(response);
                        if (response.equals("Успешно")) {
                            Model m = new Model(login, password);
                            break;
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }

                while (true) {
//                    System.out.println("Введите команду");
                    command = sc.nextLine();
                    try {
                        oos.writeObject(command);
                    } catch (IOException e) {
                        System.out.println("error");
                    }

                    response = ois.readObject().toString();
                    System.out.println(response);
                    if (response.equalsIgnoreCase("Выход.\n Всего хорошего)")){
                        System.exit(1);
                    }

                }

            } catch (Exception e) {
                System.out.println("Проблемы с сервером. Стоит подождать или уточнить по телефону 02.");
                System.out.println("Ждать? (Yes/no)");
                String ans = sc.nextLine();
                if (ans.equals("") || ans.equalsIgnoreCase("yes")){
                    continue;
                }
                else {
                    break;
                }
            }
        }
    }
}
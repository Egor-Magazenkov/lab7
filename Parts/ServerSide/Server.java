package Lab_6.Parts.ServerSide;

import Lab_6.Collection.StudyGroup;
import Lab_6.Database.Model;
import Lab_6.Database.Users;
import Lab_6.Exceptions.ServerResponseException;
import Lab_6.Utilities.Control;
import sun.security.provider.MD5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Lab_6.Utilities.MD5.getMd5;

class Server {
    static Control control = new Control(new HashSet<>());

    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Model m = new Model();
            Connection connection = m.connect();
            ExecutorService executor = Executors.newFixedThreadPool(5);

            while (true) {
                Socket socket = serverSocket.accept();
                executor.submit(() -> {
                    try {
                        System.out.println("Waiting for Client...");
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        ObjectInputStream ois = new ObjectInputStream(dis);
                        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(dout);
                        System.out.println("Client Connected...");

                        String type;
                        String login, password, real_pass;
                        while (true) {
                            type = ois.readObject().toString();
                            if (type.equals("sign in")) {
                                login = ois.readObject().toString();
                                password = ois.readObject().toString();
                                real_pass = Users.find_by_login(connection, login);
                                System.out.println(password);
                                System.out.println(real_pass);
                                if (real_pass == null) {
                                    new Thread(()-> {
                                        try {
                                            oos.writeObject("Нет такого пользователя");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).start();
                                } else if (real_pass.equals(getMd5(password.trim()))) {
                                    new Thread(()-> {
                                        try {
                                            oos.writeObject("Успешно");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).start();
                                    break;
                                } else {
                                    new Thread(()-> {
                                        try {
                                            oos.writeObject("Неверный пароль");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).start();
                                }
                            } else if (type.equals("register")) {
                                login = ois.readObject().toString();
                                password = getMd5(ois.readObject().toString());
                                System.out.println(password);
                                Users.add(connection, login, password);
                                new Thread(()-> {
                                    try {
                                        oos.writeObject("Успешно");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).start();
                                break;
                            }
                            System.out.println("Попробуйте еще раз");
                        }
                        int id = 0;
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(String.format("select user_id from users where login='%s';", login));
                            ResultSet rs = preparedStatement.executeQuery();
                            while (rs.next()) {
                                id = rs.getInt("user_id");
                                System.out.println("nice");
                            }
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                        String command;
                        while (true) {
                            try {
                                command = ois.readObject().toString();
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(command);

                            try {
                                control.processing(command, oos, ois, connection, id);
                            } catch (ServerResponseException e) {
                                if (e.getStatus() != -1) {
                                    new Thread(()-> {
                                        try {
                                            oos.writeObject(e.getMessage());
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }).start();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (Exception e) {

        }
    }
}
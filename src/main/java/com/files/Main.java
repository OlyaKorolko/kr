package com.files;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 2) {
            try (Scanner scan = new Scanner(System.in);
                 BufferedReader usersReader = new BufferedReader(new FileReader(Processor.createFile(args[0])));
                 BufferedReader productsReader = new BufferedReader(new FileReader(Processor.createFile(args[1])))) {
                Processor processor = new Processor();
                processor.setUsers(processor.deserializeToListOfUsers(usersReader));
                processor.setProducts(processor.deserializeToListOfProducts(productsReader));

                if (processor.getUsers().size() != 0) {
                    showMenu();
                    int input;
                    do {
                        System.out.println("Enter the number of request: ");
                        input = scan.nextInt();
                        processRequest(scan, processor, input, args);
                    } while (input != 7);
                } else {
                    System.out.println("User file is empty.");
                }
            } catch (CustomException | IOException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("INVALID REQUEST INPUT");
            }
        }
    }

    public static void showMenu() {
        System.out.println("1 - регистрация пользователя (добавление в файл с пользователями новой записи. " +
                "перед добавлением проверить, что пользователя с таким логином еще не было. Роль проставляется как USER");
        System.out.println("2 - вход в систему (ввод логина и пароля");
        System.out.println("3 - просмотр всех записей о товарах");
        System.out.println("4 - поиск товара по номеру (только для админа");
        System.out.println("5 - вывод статистики по каждому товару: в какие категории включен, средняя частота по всем категориям");
        System.out.println("6 - топ-N товаров в поиске по определенной категории (N и категория вводится пользователем");
        System.out.println();
    }

    public static void processRequest(Scanner scan, Processor processor, int input, String[] args)
            throws CustomException, IOException {
        FileWriter out = new FileWriter(Processor.createFile(args[0]), true);
        switch (input) {
            case 1: {
                System.out.println("Enter your data:");
                scan.nextLine();
                String name = scan.nextLine();
                String login = scan.nextLine();
                String email = scan.nextLine();
                String password = scan.nextLine();
                User user = new User(name, login, email, password, "USER", true);
                int size = processor.getUsers().size();
                processor.getUsers().put(user.getUserName(), user);
                if (size == processor.getUsers().size()) {
                    throw new CustomException("This user already exists!");
                }
                String a = user.toString();
                out.write(a);
                break;
            }
            case 2: {
                System.out.println("Enter your data:");
                scan.nextLine();
                String login = scan.nextLine();
                String password = scan.nextLine();
                if (!processor.enterSystem(login, password).getEmail().equals("")) {
                    processor.setLogged(true);
                    processor.setUser(processor.enterSystem(login, password));
                    System.out.println("Logged successfully");
                } else {
                    System.out.println("Login/password dont exist.");
                }
                break;
            }
            case 3: {
                System.out.println("All products list: ");
                processor.printAllEntriesAboutProducts();
                break;
            }
            case 4: {
                if (processor.isLogged() && processor.getUser().getRole().equalsIgnoreCase("ADMIN")) {
                    System.out.println("Enter product number: ");
                    Product prod = processor.findProductByNumber(scan.nextInt());
                    if (prod.getFrequencyOfSearch() != 0) {
                        System.out.println(prod.toString());
                    }
                } else {
                    throw new CustomException("You are not an admin to do that.");
                }
                break;
            }
            case 5: {
                processor.showStatistics();
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                System.out.println("Work is finished.");
                break;
            }
        }

    }
}

package com.files;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws CustomException {
        if (args.length >= 2) {
            File file = Processor.createFile(args[0]);
            try (Scanner scan = new Scanner(System.in);
                 Scanner usersReader = new Scanner(file);
                 Scanner productsReader = new Scanner(Processor.createFile(args[1]));
                 FileWriter out = new FileWriter(file, true)) {
                Processor processor = new Processor();
                processor.setUsers(processor.deserializeToListOfUsers(usersReader));
                processor.setProducts(processor.deserializeToListOfProducts(productsReader));

                if (processor.getUsers().size() != 0 || processor.getProducts().size() != 0) {
                    showMenu();
                    int input;
                    do {
                        System.out.println("Enter the number of request: ");
                        input = scan.nextInt();
                        processRequest(scan, processor, input, out);
                    } while (input != 7);
                } else {
                    System.out.println("No source data available to read from.");
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

    public static void processRequest(Scanner scan, Processor processor, int input, FileWriter out)
            throws CustomException, IOException {
        switch (input) {
            case 1: {
                System.out.println("Enter your registration data (name, login, email, password):");
                scan.nextLine();
                String name = scan.nextLine();
                String login = scan.nextLine();
                String email = scan.nextLine();
                String password = scan.nextLine();
                User user = new User(name, login, email, password, "USER", true);
                if (processor.getUsers().containsKey(user.getUserName())) {
                    throw new CustomException("User already exists in the database!");
                }
                out.write("\n" + user.toString());
                break;
            }
            case 2: {
                System.out.println("Enter your login and password:");
                scan.nextLine();
                String login = scan.nextLine();
                String password = scan.nextLine();
                if (!processor.enterSystem(login, password).getEmail().equals("")) {
                    processor.setLogged(true);
                    processor.setUser(processor.enterSystem(login, password));
                    System.out.println("Logged successfully");
                } else {
                    System.out.println("Invalid user data.");
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
                    if (prod == null) {
                        System.out.println("No such product exists.");
                    } else {
                        System.out.println(prod.toString());
                    }
                } else {
                    System.out.println("You are not an admin to do that.");
                }
                break;
            }
            case 5: {
                processor.showStatistics();
                break;
            }
            case 6: {
                try {
                    System.out.println("Enter the number of products:");
                    int n = scan.nextInt();
                    System.out.println("Enter category: ");
                    scan.nextLine();
                    String category = scan.nextLine();
                    processor.topNProductsInCategory(n, category);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 7: {
                System.out.println("Work is finished.");
                break;
            }
        }

    }
}

package com.files;

import java.io.BufferedReader;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;

public class Processor {
    private User user;
    private boolean logged = false;

    private Map<String, User> users;
    private Map<Integer, List<Product>> products;

    public Processor() {
        users = new HashMap<>();
        products = new HashMap<>();
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<Integer, List<Product>> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, List<Product>> products) {
        this.products = products;
    }

    public static File createFile(String fileName) throws CustomException {
        try {
            return new File(System.getProperty("user.dir") + "\\src\\" + fileName);
        } catch (Exception e) {
            throw new CustomException("File creation failed.");
        }
    }

    public Map<String, User> deserializeToListOfUsers(BufferedReader in)
            throws CustomException {
        try {
            return in.lines().map(
                    line -> {
                        String[] x = setPattern().split(line);
                        return new User(x[0], x[1], x[2], x[3], x[4], false);
                    })
                    .filter(line -> line.getRole().equalsIgnoreCase("USER") || line.getRole().equalsIgnoreCase("ADMIN"))
                    .collect(Collectors.toMap(User::getUserName, Function.identity()));
        } catch (Exception ex) {
            throw new CustomException("Parsing file failed.");
        }
    }

    public Map<Integer, Product> deserializeToListOfProducts(BufferedReader in)
            throws CustomException {
        try {
            return in.lines().map(line -> {
                String[] x = setPattern().split(line);
                return new Product(Integer.parseInt(x[0]), x[1], LocalDate.parse(x[2]), x[3], parseDouble(x[4]));
            })
                    .collect(Collectors.toMap(Product::getNumber, Function.identity()));
        } catch (Exception ex) {
            throw new CustomException("Parsing file failed.");
        }
    }

    public User enterSystem(String login, String password) {
        for (User user : users.values()) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equalsIgnoreCase(password)) {
                user.setLogged(true);
                return user;
            }
        }
        return new User();
    }

    public void printAllEntriesAboutProducts() {
        for (List<Product> products : products.values()) {
            for (Product product: products) {
                System.out.println(product.toString());
            }
        }
        System.out.println();
    }

    /*public Product findProductByNumber(int number) {
        for (Product product : products.values()) {
            if (number == product.getNumber()) {
                return product;
            }
        }
        return new Product();
    }*/

    public void showStatistics() {
        products.forEach((key, value) ->
                value.forEach(product -> System.out.println(product.getName() + ": {" + product.getCategory() + ", " +
                        product.getFrequencyOfSearch() + "}\n")
                )
        );
    }

    private Pattern setPattern() {
        return Pattern.compile(" ");
    }
}

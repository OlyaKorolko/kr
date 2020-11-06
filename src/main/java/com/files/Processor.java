package com.files;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class Processor {
    private User user;
    private boolean logged = false;

    private Map<String, User> users;
    private Map<Integer, Product> products;
    private Map<String, List<Product>> categories;


    public Processor() {
        users = new HashMap<>();
        products = new HashMap<>();
        categories = new HashMap<>();
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

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    public Map<String, List<Product>> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, List<Product>> categories) {
        this.categories = categories;
    }

    public static File createFile(String fileName) throws CustomException {
        try {
            return new File(System.getProperty("user.dir") + "\\src\\" + fileName);
        } catch (Exception e) {
            throw new CustomException("File creation failed.");
        }
    }

    public Map<String, User> deserializeToListOfUsers(Scanner in)
            throws CustomException {
        try {
            while (in.hasNext()) {
                String[] info = in.nextLine().split(" ");
                if (!(info[4].equalsIgnoreCase("ADMIN") || info[4].equalsIgnoreCase("USER")) || users.containsKey(info[0])) {
                    throw new CustomException("Invalid input data");
                }
                users.put(info[0], new User(info[0], info[1], info[2], info[3], info[4], false));
            }
            return users;
        } catch (NumberFormatException ex) {
            throw new CustomException("Parsing file failed.");
        }
    }

    public Map<Integer, Product> deserializeToListOfProducts(Scanner in)
            throws CustomException {
        try {
            while (in.hasNext()) {
                String[] info = in.nextLine().split(" ");
                int id = Integer.parseInt(info[0]);
                double frequency = Double.parseDouble(info[4]);
                Product pr = new Product(id, info[1], LocalDate.parse(info[2]), info[3], frequency);
                if (products.containsKey(id)) {
                    products.get(id).getCategoryAndFrequencyOfSearch().put(info[3], frequency);
                } else {
                    products.put(id, pr);
                }
                if (!categories.containsKey(info[3])) {
                    categories.put(info[3], new ArrayList<>());
                }
                categories.get(info[3]).add(pr);

            }
            return products;
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
        for (Product product : products.values()) {
            System.out.println(product.toString());
        }
        System.out.println();
    }

    public Product findProductByNumber(int number) {
        return products.get(number);
    }

    public void showStatistics() {
        products.forEach((key, value) ->
                System.out.println(key + ": " + value.getCategoryAndFrequencyOfSearch() + "\n")
        );
    }

    public void topNProductsInCategory(int n, String category) {
        List<Product> lc = categories.get(category);
        if (lc != null && lc.size() >= n) {
            System.out.println(category + ": ");
            lc.sort(Comparator.comparing(o -> o.getCategoryAndFrequencyOfSearch().get(category)));
            for (int i = 0; i < n; i++) {
                System.out.println(lc.get(lc.size() - i - 1));
            }
        } else {
            System.out.println("Unable to find a valid output.");
        }
    }

}

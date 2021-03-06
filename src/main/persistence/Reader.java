package main.persistence;

import main.model.Account;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Reader {
    public static final String DELIMITER = ",";

    // construct an anniversary Reader
    public Reader() {
    }

    // EFFECTS: returns a list of anniversaries parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static Map<String,Account> read(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of anniversaries
    private static Map<String,Account> parseContent(List<String> fileContent) {

        Map<String,Account> accounts = new HashMap<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            accounts.put(parseName(lineComponents),parseAccounts(lineComponents));
        }

        return accounts;
    }

    // split the given String
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }



    private static Account parseAccounts(List<String> components) {

        String accountName = components.get(0);
        String password = components.get(1);
        boolean star = Boolean.parseBoolean(components.get(2));
        Account account = new Account(accountName,password);
        if (star) {
            account.markAsTrue();
        } else {
            account.markAsFalse();
        }

        return account;

    }
    private static String parseName(List<String> components) {

        String name = components.get(3);
        return name;

    }
}

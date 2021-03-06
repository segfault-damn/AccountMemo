package main.model;

import main.persistence.Reader;
import main.persistence.Saveable;

import java.io.PrintWriter;

public class Account implements Saveable {

    private String accountName;
    private String password;
    private Boolean star;

    public Account(String accountName, String password) {
        this.password = password;
        this.accountName = accountName;
        this.star = true;
    }


    public void markAsFalse() {
        star = false;
    }

    public void markAsTrue() {
        star = true;
    }



    public String getPassword() {
        return password;
    }

    public String getAccountName() {
        return accountName;
    }

    public Boolean getStar() {
        return star;
    }

    @Override
    public void save(PrintWriter printWriter,String name) {
        printWriter.print(accountName);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(password);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(star);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.println("");
    }
}

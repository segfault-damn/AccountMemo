package main.persistence;

import java.io.PrintWriter;

public interface Saveable {
    void save(PrintWriter printWriter, String name);
}

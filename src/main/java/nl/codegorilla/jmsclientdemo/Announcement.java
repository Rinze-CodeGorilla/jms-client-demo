package nl.codegorilla.jmsclientdemo;


import java.io.Serializable;

public record Announcement(String sender, String message) implements Serializable {
    @Override
    public String toString() {
        return "%s: %s".formatted(sender, message);
    }
}

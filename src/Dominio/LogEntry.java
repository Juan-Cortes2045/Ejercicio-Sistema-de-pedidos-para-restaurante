/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *
 * @author Maicol
 */
import java.time.LocalDateTime;
import Singleton.LogLevel;

public class LogEntry {

    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String message;
    private final String source;

    public LogEntry(LogLevel level, String message, String source) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.message = message;
        this.source = source;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + level + " - " + source + ": " + message;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maicol.restaurant.singleton;

/**
 *
 * @author Maicol
 */
import java.time.LocalDateTime;

public class LogEntry {

    private LocalDateTime timestamp;
    private LogLevel level;
    private String message;
    private String source;

    public LogEntry(LogLevel level, String message, String source) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.message = message;
        this.source = source;
    }

    public String toString() {
        return "[" + timestamp + "] " + level + " - " + source + ": " + message;
    }
}

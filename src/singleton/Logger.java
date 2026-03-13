/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maicol.restaurant.singleton;

/**
 *
 * @author Maicol
 */
import java.util.ArrayList;
import java.util.List;

public class Logger {

private static Logger instance;
private List<LogEntry> logs;
private LogLevel level;

private Logger() {
logs = new ArrayList<>();
level = LogLevel.INFO;
}

public static Logger getInstance() {
if (instance == null) {
instance = new Logger();
}
return instance;
}

public void log(String message, LogLevel level) {
LogEntry entry = new LogEntry(level, message, "System");
logs.add(entry);
System.out.println(entry);
}

public void info(String message) {
log(message, LogLevel.INFO);
}

public void warning(String message) {
log(message, LogLevel.WARNING);
}

public void error(String message) {
log(message, LogLevel.ERROR);
}

public List<LogEntry> getLogs() {
return logs;
}

public void showLogs() {
for (LogEntry log : logs) {
System.out.println(log);
}
}
}

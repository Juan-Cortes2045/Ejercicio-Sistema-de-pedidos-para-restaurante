/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;
import Dominio.LogEntry;
import java.util.ArrayList;
import java.util.List;

public class Logger {

private static Logger instance;
private final List<LogEntry> logs;
private final LogLevel level;

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

    // Added for compatibility with existing calls to Logger.warn()
    public void warn(String message) {
        warning(message);
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

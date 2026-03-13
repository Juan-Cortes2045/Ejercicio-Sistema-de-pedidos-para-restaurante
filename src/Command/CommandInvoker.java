/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author MERARI URBANO
 */
public class CommandInvoker {

    private final Deque<ICommand> history = new ArrayDeque<>();

    public void executeCommand(ICommand cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            ICommand last = history.pop();
            last.undo();
        }
    }

    public List<ICommand> getHistory() {
        return new ArrayList<>(history);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer;

/**
 *
 * @author JUAN CAMILO
 */
public class LoggerObserver implements IObserver {

    @Override
    public void update(String message) {
        System.out.println("[LoggerObserver] Log entry: " + message);
    }
}

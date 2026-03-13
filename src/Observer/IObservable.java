/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

/**
 *
 * @author JUAN CAMILO
 */
public interface IObservable {
    
    void addObserver(IObserver observer);

    void removeObserver(IObserver observer);

    void notifyObservers(String message);
    
}

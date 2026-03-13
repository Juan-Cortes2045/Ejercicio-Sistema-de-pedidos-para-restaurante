/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

/**
 *
 * @author Maicol
 */
import java.util.HashMap;
import java.util.Map;

public class CatalogManager {

    private static CatalogManager instance;
    private Map<String, Product> catalog;

    private CatalogManager() {
        catalog = new HashMap<>();
    }

    public static CatalogManager getInstance() {
        if (instance == null) {
        instance = new CatalogManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        catalog.put(product.getProductId(), product);
        Logger.getInstance().info("Product added to catalog: " + product.getName());
    }

        public Product getProduct(String id) {
            return catalog.get(id);
        }

        public void listCatalog() {
                for (Product product : catalog.values()) {
                System.out.println(product);
            }
        }
}

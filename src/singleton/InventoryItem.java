package Singleton;

import java.time.LocalDateTime;

import Dominio.Product;

public class InventoryItem {

    private Product product;
    private int quantity;
    private int minStock;
    private LocalDateTime lastUpdated;

    public InventoryItem(Product product, int quantity, int minStock) {
        this.product = product;
        this.quantity = quantity;
        this.minStock = minStock;
        this.lastUpdated = LocalDateTime.now();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinStock() {
        return minStock;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public boolean isLowStock() {
        return quantity <= minStock;
    }

    public void decrease(int qty) {
        if (qty <= 0) {
            return;
        }
        this.quantity -= qty;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
        this.lastUpdated = LocalDateTime.now();
    }

    public void increase(int qty) {
        if (qty <= 0) {
            return;
        }
        this.quantity += qty;
        this.lastUpdated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", minStock=" + minStock +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}


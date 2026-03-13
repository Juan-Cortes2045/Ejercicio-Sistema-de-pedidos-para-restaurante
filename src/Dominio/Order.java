package Dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Order {

    private final String orderId;
    private List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private double total;

    public Order() {
        this.orderId = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.total = 0.0;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<OrderItem> items) {
        this.items = new ArrayList<>(items);
        recalculateTotal();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getTotal() {
        return total;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            return;
        }
        items.add(item);
        recalculateTotal();
    }

    public void removeItem(String productId) {
        if (productId == null) {
            return;
        }
        Iterator<OrderItem> it = items.iterator();
        while (it.hasNext()) {
            OrderItem item = it.next();
            if (item.getProduct() != null && productId.equals(item.getProduct().getProductId())) {
                it.remove();
                break;
            }
        }
        recalculateTotal();
    }

    public OrderItem getItem(String productId) {
        if (productId == null) {
            return null;
        }
        for (OrderItem item : items) {
            if (item.getProduct() != null && productId.equals(item.getProduct().getProductId())) {
                return item;
            }
        }
        return null;
    }

    public void confirm() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.CONFIRMED;
        }
    }

    public void cancel() {
        if (status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED) {
            status = OrderStatus.CANCELLED;
        }
    }

    public double calculateTotal() {
        recalculateTotal();
        return total;
    }

    private void recalculateTotal() {
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.getSubtotal();
        }
        this.total = sum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}


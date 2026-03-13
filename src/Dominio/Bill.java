package Dominio;

import java.time.LocalDateTime;

import Singleton.InvoiceType;
import Singleton.Order;

public class Bill {

    private String invoiceId;
    private Order order;
    private InvoiceType type;
    private LocalDateTime issuedAt;
    private double total;

    public Bill(Order order) {
        this(order, InvoiceType.PHYSICAL);
    }

    public Bill(Order order, InvoiceType type) {
        this.order = order;
        this.type = type;
        this.total = order != null ? order.calculateTotal() : 0.0;
    }

    public void generate() {
        this.issuedAt = LocalDateTime.now();
        if (order != null) {
            this.total = order.calculateTotal();
        }
    }

    public void print() {
        System.out.println(toString());
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public InvoiceType getType() {
        return type;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "invoiceId='" + invoiceId + '\'' +
                ", orderId=" + (order != null ? order.getOrderId() : "null") +
                ", type=" + type +
                ", issuedAt=" + issuedAt +
                ", total=" + total +
                '}';
    }
}


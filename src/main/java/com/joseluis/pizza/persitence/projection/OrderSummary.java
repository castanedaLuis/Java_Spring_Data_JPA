package com.joseluis.pizza.persitence.projection;

import java.time.LocalDate;

public interface OrderSummary {

    Integer getIdOrder();
    String getCustomerName();
    LocalDate getOrderDate();
    Double getOrderTotal();
    String getPizzasName();

}

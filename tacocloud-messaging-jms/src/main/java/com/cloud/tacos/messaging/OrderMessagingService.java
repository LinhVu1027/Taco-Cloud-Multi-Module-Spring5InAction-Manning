package com.cloud.tacos.messaging;

import com.cloud.tacos.domain.Order;

public interface OrderMessagingService {

    void sendOrder(Order order);

}


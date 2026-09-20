package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.query.OrderTime;
import reactor.core.publisher.Flux;

public interface IGetOrderTimesByRestaurantServicePort {

    Flux<OrderTime> getOrderTimes(Long ownerRestaurant);
}

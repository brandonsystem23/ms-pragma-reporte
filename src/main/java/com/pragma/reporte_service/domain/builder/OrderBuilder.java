package com.pragma.reporte_service.domain.builder;

import com.pragma.reporte_service.domain.model.OrderStatus;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.model.query.EmployeeRanking;
import com.pragma.reporte_service.domain.model.query.OrderTime;
import com.pragma.reporte_service.domain.model.query.TraceabilityOrderHistory;
import com.pragma.reporte_service.domain.model.query.TraceabilityTimelineItem;

import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OrderBuilder {

    private static final ZoneId LIMA_ZONE = ZoneId.of("America/Lima");

    private OrderBuilder() {
    }

    public static Traceability buildTraceability(CreateTraceabilityCommand command, Long changedByUserId,
                                                 String changedByRole) {

        return Traceability.builder()
                .orderId(command.orderId())
                .customerId(command.customerId())
                .customerName(command.customerName())
                .restaurantId(command.restaurantId())
                .restaurantName(command.restaurantName())
                .employeeAssignedId(command.employeeAssignedId())
                .employeeAssignedName(command.employeeAssignedName())
                .ownerRestaurant(command.ownerRestaurant())
                .status(command.status())
                .description(command.description())
                .changedByUserId(changedByUserId)
                .changedByRole(changedByRole)
                .changedAt(command.changedAt())
                .build();
    }


    public static List<EmployeeRanking> buildRanking(
            List<Traceability> traceabilityList) {

        List<OrderTime> orderTimes = buildOrderTimes(traceabilityList);

        Map<Long, List<OrderTime>> groupedByEmployee = new LinkedHashMap<>();

        for (OrderTime orderTime : orderTimes) {
            if (orderTime.employeeAssignedId() == null) {
                continue;
            }

            groupedByEmployee
                    .computeIfAbsent(
                            orderTime.employeeAssignedId(),
                            key -> new ArrayList<>())
                    .add(orderTime);
        }

        return groupedByEmployee.entrySet().stream()
                .map(entry -> {
                    Long employeeId = entry.getKey();
                    List<OrderTime> employeeOrders = entry.getValue();

                    long averageDuration = Math.round(
                            employeeOrders.stream()
                                    .mapToLong(OrderTime::durationMinutes)
                                    .average()
                                    .orElse(0)
                    );

                    return EmployeeRanking.builder()
                            .employeeAssignedId(employeeId)
                            .employeeAssignedName(
                                    resolveEmployeeAssignedName(
                                            traceabilityList,
                                            employeeId))
                            .completedOrders((long) employeeOrders.size())
                            .averageDurationMinutes(averageDuration)
                            .build();
                })
                .sorted(
                        Comparator.comparingLong(
                                EmployeeRanking::averageDurationMinutes))
                .toList();
    }

    public static List<OrderTime> buildOrderTimes(
            List<Traceability> traceabilityList) {

        Map<Long, List<Traceability>> groupedByOrder =
                new LinkedHashMap<>();

        for (Traceability traceability : traceabilityList) {
            groupedByOrder
                    .computeIfAbsent(
                            traceability.getOrderId(),
                            key -> new ArrayList<>())
                    .add(traceability);
        }

        return groupedByOrder.values().stream()
                .map(OrderBuilder::buildOrderTime)
                .filter(Objects::nonNull)
                .toList();
    }

    public static OrderTime buildOrderTime(
            List<Traceability> orderTraceability) {

        boolean cancelled = orderTraceability.stream()
                .anyMatch(item ->
                        OrderStatus.CANCELLED.equals(item.getStatus()));

        if (cancelled) {
            return null;
        }

        Traceability started = orderTraceability.stream()
                .filter(item ->
                        OrderStatus.PENDING.equals(item.getStatus()))
                .findFirst()
                .orElse(null);

        Traceability finished = orderTraceability.stream()
                .filter(item ->
                        OrderStatus.DELIVERED.equals(item.getStatus()))
                .reduce((first, second) -> second)
                .orElse(null);

        if (started == null || finished == null) {
            return null;
        }

        long durationMinutes = Duration.between(
                        started.getChangedAt().atZone(LIMA_ZONE),
                        finished.getChangedAt().atZone(LIMA_ZONE))
                .toMinutes();

        return OrderTime.builder()
                .orderId(started.getOrderId())
                .customerId(started.getCustomerId())
                .customerName(started.getCustomerName())
                .employeeAssignedId(
                        resolveEmployeeAssignedId(orderTraceability))
                .employeeAssignedName(
                        resolveEmployeeAssignedName(orderTraceability))
                .startedAt(started.getChangedAt())
                .finishedAt(finished.getChangedAt())
                .durationMinutes(durationMinutes)
                .build();
    }

    public static Long resolveEmployeeAssignedId(
            List<Traceability> traceabilityList) {

        return traceabilityList.stream()
                .map(Traceability::getEmployeeAssignedId)
                .filter(Objects::nonNull)
                .reduce((first, second) -> second)
                .orElse(null);
    }

    public static String resolveEmployeeAssignedName(
            List<Traceability> traceabilityList) {

        return traceabilityList.stream()
                .map(Traceability::getEmployeeAssignedName)
                .filter(name ->
                        name != null && !name.isBlank())
                .reduce((first, second) -> second)
                .orElse(null);
    }

    public static String resolveEmployeeAssignedName(
            List<Traceability> traceabilityList,
            Long employeeId) {

        return traceabilityList.stream()
                .filter(item ->
                        employeeId.equals(item.getEmployeeAssignedId()))
                .map(Traceability::getEmployeeAssignedName)
                .filter(name ->
                        name != null && !name.isBlank())
                .reduce((first, second) -> second)
                .orElse(null);
    }

    public static List<TraceabilityOrderHistory> buildTraceabilityOrderHistory(
            List<Traceability> traceabilityList) {

        Map<Long, List<Traceability>> groupedByOrder =
                new LinkedHashMap<>();

        for (Traceability traceability : traceabilityList) {
            groupedByOrder
                    .computeIfAbsent(
                            traceability.getOrderId(),
                            key -> new ArrayList<>())
                    .add(traceability);
        }

        return groupedByOrder.values().stream()
                .map(OrderBuilder::buildOrderHistory)
                .toList();
    }

    private static TraceabilityOrderHistory buildOrderHistory(
            List<Traceability> traceabilityList) {

        Traceability first = traceabilityList.getFirst();

        return TraceabilityOrderHistory.builder()
                .orderId(first.getOrderId())
                .customerId(first.getCustomerId())
                .customerName(first.getCustomerName())
                .restaurantId(first.getRestaurantId())
                .restaurantName(first.getRestaurantName())
                .employeeAssignedId(
                        resolveEmployeeAssignedId(traceabilityList))
                .employeeAssignedName(
                        resolveEmployeeAssignedName(traceabilityList))
                .timeline(
                        traceabilityList.stream()
                                .map(item ->
                                        TraceabilityTimelineItem.builder()
                                                .status(item.getStatus())
                                                .description(item.getDescription())
                                                .changedByUserId(
                                                        item.getChangedByUserId())
                                                .changedByRole(
                                                        item.getChangedByRole())
                                                .changedAt(
                                                        item.getChangedAt())
                                                .build())
                                .toList())
                .build();
    }
}
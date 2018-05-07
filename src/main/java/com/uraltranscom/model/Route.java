package com.uraltranscom.model;

import com.uraltranscom.model.additional_model.DeliveryPeriod;
import com.uraltranscom.model.additional_model.VolumePeriod;
import com.uraltranscom.model.additional_model.WagonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Класс Маршрута
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 17.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 * 24.04.2018
 *   1. Версия 4.2
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class Route {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);

    private String keyOfStationDeparture; // Код станции отправления
    private String nameOfStationDeparture; // Станция отправления
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Станция назначения
    private String distanceOfWay; // Расстояние маршрута
    private String VIP = "0"; // Флаг приоритера 1 - Приоритетный, 0 - Неприоритетный
    private String customer; // Заказчик
    private int countOrders; // Количество заявок на маршрут
    private VolumePeriod volumePeriod; // Возможны объем вагона в заявке
    private String numberOrder; // Номер заявки
    private String cargo; // Груз
    private WagonType wagonType; // Тип вагона
    private Map<Integer, List<DeliveryPeriod>> deliveryPeriodMap; // Период подачи

    public Route(String keyOfStationDeparture, String nameOfStationDeparture, String keyOfStationDestination, String nameOfStationDestination, String distanceOfWay, String customer, int countOrders, VolumePeriod volumePeriod, String numberOrder, String cargo, WagonType wagonType, Map<Integer, List<DeliveryPeriod>> deliveryPeriodMap) {
        this.keyOfStationDeparture = keyOfStationDeparture;
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.distanceOfWay = distanceOfWay;
        this.customer = customer;
        this.countOrders = countOrders;
        this.volumePeriod = volumePeriod;
        this.numberOrder = numberOrder;
        this.cargo = cargo;
        this.wagonType = wagonType;
        this.deliveryPeriodMap = deliveryPeriodMap;
    }

    public String getKeyOfStationDeparture() {
        return keyOfStationDeparture;
    }

    public void setKeyOfStationDeparture(String keyOfStationDeparture) {
        this.keyOfStationDeparture = keyOfStationDeparture;
    }

    public String getNameOfStationDeparture() {
        return nameOfStationDeparture;
    }

    public void setNameOfStationDeparture(String nameOfStationDeparture) {
        this.nameOfStationDeparture = nameOfStationDeparture;
    }

    public String getKeyOfStationDestination() {
        return keyOfStationDestination;
    }

    public void setKeyOfStationDestination(String keyOfStationDestination) {
        this.keyOfStationDestination = keyOfStationDestination;
    }

    public String getNameOfStationDestination() {
        return nameOfStationDestination;
    }

    public void setNameOfStationDestination(String nameOfStationDestination) {
        this.nameOfStationDestination = nameOfStationDestination;
    }

    public String getDistanceOfWay() {
        return distanceOfWay;
    }

    public void setDistanceOfWay(String distanceOfWay) {
        this.distanceOfWay = distanceOfWay;
    }

    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    public VolumePeriod getVolumePeriod() {
        return volumePeriod;
    }

    public void setVolumePeriod(VolumePeriod volumePeriod) {
        this.volumePeriod = volumePeriod;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
    }

    public Map<Integer, List<DeliveryPeriod>> getDeliveryPeriodMap() {
        return deliveryPeriodMap;
    }

    public void setDeliveryPeriodMap(Map<Integer, List<DeliveryPeriod>> deliveryPeriodMap) {
        this.deliveryPeriodMap = deliveryPeriodMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return countOrders == route.countOrders &&
                Objects.equals(keyOfStationDeparture, route.keyOfStationDeparture) &&
                Objects.equals(nameOfStationDeparture, route.nameOfStationDeparture) &&
                Objects.equals(keyOfStationDestination, route.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, route.nameOfStationDestination) &&
                Objects.equals(distanceOfWay, route.distanceOfWay) &&
                Objects.equals(VIP, route.VIP) &&
                Objects.equals(customer, route.customer) &&
                Objects.equals(volumePeriod, route.volumePeriod) &&
                Objects.equals(numberOrder, route.numberOrder) &&
                Objects.equals(cargo, route.cargo) &&
                Objects.equals(wagonType, route.wagonType) &&
                Objects.equals(deliveryPeriodMap, route.deliveryPeriodMap);
    }

    @Override
    public int hashCode() {

        return Objects.hash(keyOfStationDeparture, nameOfStationDeparture, keyOfStationDestination, nameOfStationDestination, distanceOfWay, VIP, customer, countOrders, volumePeriod, numberOrder, cargo, wagonType, deliveryPeriodMap);
    }

    @Override
    public String toString() {
        return  numberOrder +
                ", " + keyOfStationDeparture +
                ", " + nameOfStationDeparture +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination +
                ", " + distanceOfWay +
                ", " + VIP +
                ", " + customer +
                ", " + countOrders +
                ", " + wagonType +
                ", " + cargo +
                ", " + volumePeriod +
                ", " + deliveryPeriodMap;
    }
}

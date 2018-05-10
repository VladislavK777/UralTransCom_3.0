package com.uraltranscom.model_ext;

import com.uraltranscom.model.additional_model.DeliveryPeriod;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 *
 * Класс для формирвоания итоговой информации для вагона
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 04.04.2018
 *
 * 24.04.2018
 *   1. Версия 4.1
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class WagonFinalInfo extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WagonFinalInfo.class);

    private String numberOfWagon; // Номер вагона
    private int countCircleDays; // Количество дней в пути
    private int distanceEmpty; // Расстояние порожнее
    private String nameOfStationDepartureOfWagon; // Станция, куда едет вагон порожний
    private String route; // Маршрут
    private String cargo; // Груз
    private int cargoType; // Класс груза
    private DeliveryPeriod deliveryPeriod; // Период подачи вагона
    private String deliveryPeriodToString; // Период подачи для веб-морды

    // Для выгрузки в файл
    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty, DeliveryPeriod deliveryPeriod) {
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.deliveryPeriod = deliveryPeriod;
    }

    // Для отображения в веб-морде
    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty, String nameOfStationDepartureOfWagon, String route, String cargo, int cargoType, DeliveryPeriod deliveryPeriod) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
        this.route = route;
        this.cargo = cargo;
        this.cargoType = cargoType;
        this.deliveryPeriodToString = simpleDateFormat.format(deliveryPeriod.getPeriodFrom()) + "-" + simpleDateFormat.format(deliveryPeriod.getPeriodTo());
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public int getCountCircleDays() {
        return countCircleDays;
    }

    public void setCountCircleDays(int countCircleDays) {
        this.countCircleDays = countCircleDays;
    }

    public int getDistanceEmpty() {
        return distanceEmpty;
    }

    public void setDistanceEmpty(int distanceEmpty) {
        this.distanceEmpty = distanceEmpty;
    }

    public String getNameOfStationDepartureOfWagon() {
        return nameOfStationDepartureOfWagon;
    }

    public void setNameOfStationDepartureOfWagon(String nameOfStationDepartureOfWagon) {
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public DeliveryPeriod getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(DeliveryPeriod deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public String getDeliveryPeriodToString() {
        return deliveryPeriodToString;
    }

    public void setDeliveryPeriodToString(String deliveryPeriodToString) {
        this.deliveryPeriodToString = deliveryPeriodToString;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }
}

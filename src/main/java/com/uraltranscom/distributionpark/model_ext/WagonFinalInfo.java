package com.uraltranscom.distributionpark.model_ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Класс для формирвоания итоговой информации для вагона
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 04.04.2018
 *
 * 24.04.2018
 *   1. Версия 4.1
 *
 */

public class WagonFinalInfo {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WagonFinalInfo.class);

    private String numberOfWagon; // Номер вагона
    private int countCircleDays; // Количество дней в пути
    private int distanceEmpty; // Расстояние порожнее
    private String nameOfStationDepartureOfWagon; // Станция, куда едет вагон порожний
    private String route; // Маршрут
    private String cargo; // Груз
    private int cargoType; // Класс груза

    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty) {
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
    }

    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty, String nameOfStationDepartureOfWagon, String route, String cargo, int cargoType) {
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
        this.route = route;
        this.cargo = cargo;
        this.cargoType = cargoType;
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

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }
}

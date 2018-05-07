package com.uraltranscom.model;

import com.uraltranscom.model.additional_model.NumberDaysToStationDestinationOfWagon;
import com.uraltranscom.model.additional_model.WagonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * Класс Вагон
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
 * 03.05.2018
 *   1. Версия 4.2
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class Wagon extends NumberDaysToStationDestinationOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Wagon.class);

    private String numberOfWagon; // Номер вагона
    private WagonType wagonType; // Тип вагона
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Название станции назначения
    private int volume; // Объем вагона
    private String cargo; // Груз
    private String keyItemCargo; // Код груза
    private List<Integer> listNotRoute = new ArrayList<>(); // Неподходящие маршруты для вагона

    public Wagon(int distanceToStationDestination, String numberOfWagon, String keyOfStationDestination, String nameOfStationDestination, int volume, String cargo, String keyItemCargo) {
        super(distanceToStationDestination);
        this.numberOfWagon = numberOfWagon;
        this.wagonType = new WagonType(TYPE_OF_WAGON_KR);
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.volume = volume;
        this.cargo = cargo;
        this.keyItemCargo = keyItemCargo;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getKeyItemCargo() {
        return keyItemCargo;
    }

    public void setKeyItemCargo(String keyItemCargo) {
        this.keyItemCargo = keyItemCargo;
    }

    public List<Integer> getListNotRoute() {
        return listNotRoute;
    }

    public void setListNotRoute(List<Integer> listNotRoute) {
        this.listNotRoute = listNotRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Wagon wagon = (Wagon) o;
        return volume == wagon.volume &&
                Objects.equals(numberOfWagon, wagon.numberOfWagon) &&
                Objects.equals(wagonType, wagon.wagonType) &&
                Objects.equals(keyOfStationDestination, wagon.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, wagon.nameOfStationDestination) &&
                Objects.equals(cargo, wagon.cargo) &&
                Objects.equals(keyItemCargo, wagon.keyItemCargo) &&
                Objects.equals(listNotRoute, wagon.listNotRoute);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), numberOfWagon, wagonType, keyOfStationDestination, nameOfStationDestination, volume, cargo, keyItemCargo, listNotRoute);
    }

    @Override
    public String toString() {
        return  numberOfWagon +
                ", " + wagonType.toString() +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination +
                ", " + volume +
                ", " + cargo +
                ", " + keyItemCargo +
                ", " + getDistanceToStationDestination() +
                ", " + getNumberDaysToStationDestination() +
                ", " + listNotRoute;
    }
}

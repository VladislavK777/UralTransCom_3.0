package com.uraltranscom.model.additional_model;

import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс числа оставшихся дней до станции назначения вагона + разгрузка (4 дня)
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 07.05.2018
 *
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class NumberDaysToStationDestinationOfWagon extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(NumberDaysToStationDestinationOfWagon.class);

    private int distanceToStationDestination; // Остаточное расстояние до станции назначения
    private int numberDaysToStationDestination; // Число оставшихся дней до станции назначения

    public NumberDaysToStationDestinationOfWagon(int distanceToStationDestination) {
        this.distanceToStationDestination = distanceToStationDestination;
        this.numberDaysToStationDestination = getNumberDaysToStationDest(distanceToStationDestination);
    }

    public int getDistanceToStationDestination() {
        return distanceToStationDestination;
    }

    public void setDistanceToStationDestination(int distanceToStationDestination) {
        this.distanceToStationDestination = distanceToStationDestination;
    }

    public int getNumberDaysToStationDestination() {
        return numberDaysToStationDestination;
    }

    public void setNumberDaysToStationDestination(int numberDaysToStationDestination) {
        this.numberDaysToStationDestination = numberDaysToStationDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberDaysToStationDestinationOfWagon that = (NumberDaysToStationDestinationOfWagon) o;
        return distanceToStationDestination == that.distanceToStationDestination &&
                numberDaysToStationDestination == that.numberDaysToStationDestination;
    }

    @Override
    public int hashCode() {

        return Objects.hash(distanceToStationDestination, numberDaysToStationDestination);
    }

    @Override
    public String toString() {
        return "distanceToStationDestination = " + distanceToStationDestination +
                ", numberDaysToStationDestination = " + numberDaysToStationDestination;
    }

    private int getNumberDaysToStationDest(int distance) {
        return Math.round(distance / KILOMETERS_IN_DAY + UNLOADING_OF_WAGON);
    }
}

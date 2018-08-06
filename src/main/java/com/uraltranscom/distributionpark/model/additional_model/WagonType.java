package com.uraltranscom.distributionpark.model.additional_model;

/**
 *
 * Класс типа вагона
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 19.04.2018
 *
 * 19.04.2018
 *   1. Версия 4.1
 *
 */

public class WagonType {
    private String wagonType;

    public WagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    @Override
    public String toString() {
        return wagonType;
    }
}

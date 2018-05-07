package com.uraltranscom.model.additional_model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

/**
 *
 * Класс периода подачи вагона
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 07.05.2018
 *
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class DeliveryPeriod {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(DeliveryPeriod.class);

    private Date periodFrom; // Период подачи от
    private Date periodTo; // Период подачи до
    private int countOrders; // Количество заявок в этот период

    public DeliveryPeriod(Date periodFrom, Date periodTo, int countOrders) {
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.countOrders = countOrders;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryPeriod that = (DeliveryPeriod) o;
        return countOrders == that.countOrders &&
                Objects.equals(periodFrom, that.periodFrom) &&
                Objects.equals(periodTo, that.periodTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(periodFrom, periodTo, countOrders);
    }

    @Override
    public String toString() {
        return periodFrom +
                ", " + periodTo +
                ", " + countOrders;
    }
}

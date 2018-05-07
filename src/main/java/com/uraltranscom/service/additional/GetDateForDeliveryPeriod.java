package com.uraltranscom.service.additional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * Класс получения измененной текущей даты для Периода поставки
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 07.05.2018
 *
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

public class GetDateForDeliveryPeriod {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDateForDeliveryPeriod.class);

    public static Date getDateForDeliveryPeriod(int countDays) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, countDays);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();
    }
}

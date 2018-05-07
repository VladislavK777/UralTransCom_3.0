package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

@Service
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase implements GetFullMonthCircleOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);

    private int numberDaysToEmpty; // Число дней для проезда порожняком

    private GetFullMonthCircleOfWagonImpl() {
    }

    @Override
    public int fullDays(String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {
        int fullMonthCircle = 0;

        numberDaysToEmpty = Math.round(distanceOfEmpty / KILOMETERS_IN_DAY + 1);

        fullMonthCircle += numberDaysToEmpty;
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += LOADING_OF_WAGON_KR;
        } else {
            fullMonthCircle += LOADING_OF_WAGON_PV;
        }
        fullMonthCircle += Math.round(Integer.parseInt(distanceOfRoute) / KILOMETERS_IN_DAY + 1);
        fullMonthCircle += UNLOADING_OF_WAGON;

        return fullMonthCircle;
    }

    public int getNumberDaysToEmpty() {
        return numberDaysToEmpty;
    }

    public void setNumberDaysToEmpty(int numberDaysToEmpty) {
        this.numberDaysToEmpty = numberDaysToEmpty;
    }
}

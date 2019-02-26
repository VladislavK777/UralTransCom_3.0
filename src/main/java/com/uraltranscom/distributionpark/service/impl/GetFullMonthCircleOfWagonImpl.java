package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 *
 */

@Component
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);

    private GetFullMonthCircleOfWagonImpl() {
    }

    public int fullDays(String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {
        int fullMonthCircle = 0;

        fullMonthCircle += Math.round(distanceOfEmpty / 300f + 1);
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += LOADING_OF_WAGON_KR;
        } else {
            fullMonthCircle += LOADING_OF_WAGON_PV;
        }
        fullMonthCircle += Math.round(Integer.parseInt(distanceOfRoute) / 300f + 1);
        fullMonthCircle += UNLOADING_OF_WAGON;

        return fullMonthCircle;
    }
}

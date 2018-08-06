package com.uraltranscom.distributionpark.service;

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model.Wagon;

import java.util.List;
import java.util.Map;

/**
 *
 * Интерфейс класса-обработчика основного алгоритма поиска
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

public interface ClassHandlerLookingFor {
    void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> listOfWagons);
}

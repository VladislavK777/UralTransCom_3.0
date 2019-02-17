package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model.Wagon;
import com.uraltranscom.distributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.distributionpark.service.ClassHandlerLookingFor;
import com.uraltranscom.distributionpark.service.additional.CompareMapValue;
import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * Класс-обработчик алгоритма расчета
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 *
 */

@Component
public class ClassHandlerLookingForImpl extends JavaHelperBase implements ClassHandlerLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerLookingForImpl.class);

    @Autowired
    private GetListOfDistanceImpl getListOfDistance;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl;
    @Autowired
    private BasicClassLookingForImpl basicClassLookingFor;

    private ClassHandlerLookingForImpl() {
    }

    private Map<List<Object>, Integer> mapDistance = new HashMap<>();

    @Override
    public void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> tempListOfWagons) {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");

        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>(tempListOfWagons);
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>(mapOfRoutes);

        // Запускаем цикл
        Boolean isOk = true;
        while (isOk) {
            isOk = false;

            // Заполняем мапу расстояниями
            getListOfDistance.fillRootMapWithDistances(copyListOfWagon, tempMapOfRoutes);
            //Запускаем метод поиска расстоняий
            lookingForMinDistance(copyListOfWagon, tempMapOfRoutes);

            int indexMap = mapDistance.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[indexMap];
            indexMap = 0;
            for (Map.Entry<List<Object>, Integer> entry : mapDistance.entrySet()) {
                compareMapValues[indexMap++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);

            if (compareMapValues.length != 0) {
                List<Object> listRouteMinDistance = compareMapValues[0].list;
                Wagon wagon = (Wagon) listRouteMinDistance.get(0);
                Route route = (Route) listRouteMinDistance.get(1);
                int minDistance = compareMapValues[0].distance;

                // Число дней пройденных вагоном
                int countCircleDays = getFullMonthCircleOfWagonImpl.fullDays(wagon.getNumberOfWagon(), minDistance, route.getDistanceOfWay());
                int getKeyNumber = 0;

                for (int i = 0; i < copyListOfWagon.size(); i++) {
                    if (copyListOfWagon.get(i).getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                        getKeyNumber = i;
                    }
                }

                copyListOfWagon.remove(getKeyNumber);

                // Уменьшаем количество рейсов у маршрута
                for (Map.Entry<Integer, Route> entry : tempMapOfRoutes.entrySet()) {
                    if (entry.getValue().equals(route)) {
                        tempMapOfRoutes.put(entry.getKey(), new Route(tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDeparture(),
                                tempMapOfRoutes.get(entry.getKey()).getNameOfStationDeparture(),
                                tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDestination(),
                                tempMapOfRoutes.get(entry.getKey()).getNameOfStationDestination(),
                                tempMapOfRoutes.get(entry.getKey()).getDistanceOfWay(),
                                tempMapOfRoutes.get(entry.getKey()).getCustomer(),
                                tempMapOfRoutes.get(entry.getKey()).getCountOrders() - 1,
                                tempMapOfRoutes.get(entry.getKey()).getVolumePeriod(),
                                tempMapOfRoutes.get(entry.getKey()).getNumberOrder(),
                                tempMapOfRoutes.get(entry.getKey()).getCargo(),
                                tempMapOfRoutes.get(entry.getKey()).getWagonType()));
                    }

                    // Удаляем маршрут, если по нему 0 рейсов
                    Iterator<Map.Entry<Integer, Route>> it = tempMapOfRoutes.entrySet().iterator();
                    while (it.hasNext()) {
                        if (tempMapOfRoutes.get(entry.getKey()).getCountOrders() == 0) {
                            it.remove();
                        }
                    }
                }

                basicClassLookingFor.getListOfDistributedRoutesAndWagons().add(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, minDistance, route.getNameOfStationDeparture(), route.getNameOfStationDeparture() + " - " + route.getNameOfStationDestination(), wagon.getCargo().trim(), getListOfDistance.getRootMapWithTypeOfCargo().get(wagon.getKeyItemCargo())));
                basicClassLookingFor.getTotalMapWithWagonNumberAndRoute().put(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, minDistance), route);

                isOk = true;

            }
        }

        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
    }

    private void lookingForMinDistance(List<Wagon> copyListOfWagon, Map<Integer, Route> tempMapOfRoutes) {
        //logger.info("Start method lookingForMinDistance");
        // Очищаем мапу расстояний
        mapDistance.clear();
        for (Wagon _wagons : copyListOfWagon) {

            // Получаем код станции назначения вагона
            String keyOfStationOfWagonDestination = _wagons.getKeyOfStationDestination().trim();
            String keyCargo = _wagons.getKeyItemCargo();

            // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
            // Цикл расчета расстояния и заполнения мапы
            for (Map.Entry<Integer, Route> _routes : tempMapOfRoutes.entrySet()) {
                List<Object> list = new ArrayList<>();
                // Станция отправления рейса
                String keyOfStationDeparture = _routes.getValue().getKeyOfStationDeparture();
                list.add(_wagons);
                list.add(_routes.getValue());
                if (_wagons.getVolume() >= _routes.getValue().getVolumePeriod().getVolumeFrom() &&
                        _wagons.getVolume() <= _routes.getValue().getVolumePeriod().getVolumeTo() &&
                        _routes.getValue().getCountOrders() > 0) {

                    String key = keyOfStationOfWagonDestination + "_" + keyOfStationDeparture + "_" + keyCargo;

                    // Ищем расстояние
                    if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                        if (Integer.parseInt((String)getListOfDistance.getRootMapWithDistances().get(key).get(2)) != -1) {
                            list.add(getListOfDistance.getRootMapWithDistances().get(key));
                            mapDistance.put(list, Integer.parseInt((String)getListOfDistance.getRootMapWithDistances().get(key).get(2)));
                        }
                    }
                }
            }
        }
    }

    public BasicClassLookingForImpl getBasicClassLookingFor() {
        return basicClassLookingFor;
    }

    public void setBasicClassLookingFor(BasicClassLookingForImpl basicClassLookingFor) {
        this.basicClassLookingFor = basicClassLookingFor;
    }
}

package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.model.additional_model.DeliveryPeriod;
import com.uraltranscom.model_ext.WagonFinalInfo;
import com.uraltranscom.service.ClassHandlerLookingFor;
import com.uraltranscom.service.additional.CompareMapValue;
import com.uraltranscom.service.additional.GetDateForDeliveryPeriod;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс-обработчик алгоритма расчета
 *
 * @author Vladislav Klochkov
 * @version 4.3
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 * 07.05.2018
 *   1. Версия 4.3
 *
 */

@Service
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

    @Override
    public void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> tempListOfWagons) {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");

        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>(tempListOfWagons);
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>(mapOfRoutes);

        // Список расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Запускаем цикл
        Boolean isOk = true;
        while (isOk) {
            isOk = false;
            // Очищаем массивы
            mapDistance.clear();

            for (Wagon _copyListOfWagon : copyListOfWagon) {
                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = _copyListOfWagon.getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                for (Map.Entry<Integer, Route> _tempMapOfRoutes : tempMapOfRoutes.entrySet()) {

                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = _tempMapOfRoutes.getValue().getKeyOfStationDeparture();
                    String keyItemCargo = _copyListOfWagon.getKeyItemCargo();
                    list.add(_copyListOfWagon);
                    list.add(_tempMapOfRoutes.getValue());
                    String key = keyOfStationOfWagonDestination + "_" + keyOfStationDeparture;

                    // Ищем в готовой мапе расстояние
                    if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                        if (getListOfDistance.getRootMapWithTypeOfCargo().containsKey(keyItemCargo)) {
                            if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 0) {
                                switch (getListOfDistance.getRootMapWithTypeOfCargo().get(keyItemCargo)) {
                                    case 3:
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 300) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                        break;
                                    default:
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 600) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                        break;
                                }
                            } else if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 1) {
                                if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 2500) {
                                    mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                }
                            } else {
                                if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 1800) {
                                    mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                }
                            }
                        }
                    }
                }
            }

            // Сортируем мапу по значению
            Map<List<Object>, Integer> mapDistanceSort = new LinkedHashMap<>();

            int index = mapDistance.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[index];
            index = 0;
            for (Map.Entry<List<Object>, Integer> entry : mapDistance.entrySet()) {
                compareMapValues[index++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);
            for (CompareMapValue cmv : compareMapValues) {
                mapDistanceSort.put(cmv.wagon, cmv.distance);
            }

            // Цикл формирования рейсов
            // Проверяем на пустоту мап, либо вагоны, либо рейсы
            outer:
            if (!mapDistanceSort.isEmpty() && !copyListOfWagon.isEmpty()) {
                for (Map.Entry<List<Object>, Integer> mapDistanceSortFirstElement : mapDistanceSort.entrySet()) {
                    List<Object> listRouteMinDistance = mapDistanceSortFirstElement.getKey();
                    Route route = (Route) listRouteMinDistance.get(1);
                    Wagon wagon = (Wagon) listRouteMinDistance.get(0);
                    String nameOfStationDepartureOfWagon = route.getNameOfStationDeparture();

                    final int[] o = {0};
                    Map<Integer, Route> tempMapOfRouteForDelete = new HashMap<>();
                    tempMapOfRoutes.forEach((k, v) -> {
                        tempMapOfRouteForDelete.put(o[0], v);
                        o[0]++;
                    });

                    Iterator<Map.Entry<Integer, Route>> it = tempMapOfRoutes.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Integer, Route> entry = it.next();
                        for (int j = 0; j < tempMapOfRouteForDelete.size(); j++) {
                            // Находим маршрут для вагона
                            if (tempMapOfRouteForDelete.get(j).equals(route) && entry.getValue().getCountOrders() > 0) {
                                if (tempMapOfRouteForDelete.get(j).equals(entry.getValue())) {
                                    if (wagon.getVolume() >= entry.getValue().getVolumePeriod().getVolumeFrom() && wagon.getVolume() <= entry.getValue().getVolumePeriod().getVolumeTo()) {
                                        if (!wagon.getListNotRoute().contains(entry.getKey())) {
                                            for (Map.Entry<Integer, List<DeliveryPeriod>> period : tempMapOfRouteForDelete.get(j).getDeliveryPeriodMap().entrySet()) {
                                                for (DeliveryPeriod deliveryPeriod : period.getValue()) {
                                                    int getKeyNumber = 0;

                                                    for (int i = 0; i < copyListOfWagon.size(); i++) {
                                                        if (copyListOfWagon.get(i).getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                                            getKeyNumber = i;
                                                        }
                                                    }

                                                    // Число дней пройденных вагоном
                                                    int countCircleDays = getFullMonthCircleOfWagonImpl.fullDays(copyListOfWagon.get(getKeyNumber).getWagonType().toString(), mapDistanceSortFirstElement.getValue(), route.getDistanceOfWay());

                                                    // Количество дней от станции назначения вагона до станции отправления(включая время до станции назначения, разгрузка, порожнее расстояние)
                                                    int countDaysToStationDeparture = getFullMonthCircleOfWagonImpl.getNumberDaysToEmpty() + wagon.getNumberDaysToStationDestination();

                                                    if (GetDateForDeliveryPeriod.getDateForDeliveryPeriod(countDaysToStationDeparture).getTime() < deliveryPeriod.getPeriodTo().getTime()) {

                                                        // Удалять рейс из периода подачи, если по нему все отработано
                                                        Map<Integer, List<DeliveryPeriod>> tempMap = new HashMap<>(tempMapOfRouteForDelete.get(j).getDeliveryPeriodMap());

                                                        deliveryPeriod.setCountOrders(deliveryPeriod.getCountOrders() - 1);

                                                        if (deliveryPeriod.getCountOrders() != 0) {
                                                            tempMap.put(period.getKey(), period.getValue());
                                                        } else {
                                                            tempMap.remove(period.getKey());
                                                        }

                                                        // Удаляем вагон
                                                        copyListOfWagon.remove(getKeyNumber);

                                                        // Уменьшаем количество рейсов у маршрута
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
                                                                tempMapOfRoutes.get(entry.getKey()).getWagonType(),
                                                                tempMap));

                                                        // Удаляем маршрут, если по нему 0 рейсов
                                                        if (tempMapOfRoutes.get(entry.getKey()).getCountOrders() == 0) {
                                                            it.remove();
                                                        }

                                                        basicClassLookingFor.getListOfDistributedRoutesAndWagons().add(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue(), nameOfStationDepartureOfWagon, tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination(), wagon.getCargo().trim(), deliveryPeriod));
                                                        basicClassLookingFor.getTotalMapWithWagonNumberAndRoute().put(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue(), deliveryPeriod), tempMapOfRouteForDelete.get(j));

                                                        isOk = true;
                                                        // Выходим из цикла, так как с ним больше ничего не сделать
                                                        break outer;
                                                    }
                                                }
                                            }

                                            // Помечаем вагону неподходящий рейс
                                            for (Wagon _listWagon : tempListOfWagons) {
                                                if (_listWagon.equals(wagon)) {
                                                    _listWagon.getListNotRoute().add(entry.getKey());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
    }
}

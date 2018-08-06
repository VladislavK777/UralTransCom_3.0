package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model.Wagon;
import com.uraltranscom.distributionpark.service.GetListOfDistance;
import com.uraltranscom.distributionpark.service.additional.FillMapsNotVipAndVip;
import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс получения списка первоначальных расстояний
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 14.03.2018
 *
 * 03.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 *
 */

@Service
public class GetListOfDistanceImpl extends JavaHelperBase implements GetListOfDistance {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfDistanceImpl.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;
    @Autowired
    private FillMapsNotVipAndVip fillMapsNotVipAndVip;
    @Autowired
    private GetTypeOfCargoImpl getTypeOfCargo;

    // Основная мапа
    private Map<String, List<Integer>> rootMapWithDistances = new HashMap<>();

    // Мапа с расстояниями больше максимального значения
    private Map<String, List<Integer>> rootMapWithDistanceMoreMaxDist = new HashMap<>();

    // Мапа хранит классы грузов
    private Map<String, Integer> rootMapWithTypeOfCargo = new HashMap<>();

    @Override
    public void fillMap(String routeId) {
        logger.info("Start process fill map with distances");

        Map<Integer, Route> mapOfRoutes = new HashMap<>(getListOfRoutesImpl.getMapOfRoutes());
        List<Wagon> listOfWagons = new ArrayList<>(getListOfWagonsImpl.getListOfWagons());

        if (!routeId.isEmpty()) {
            String[] routesId = routeId.split(",");
            for (Map.Entry<Integer, Route> _mapOfRoutes : mapOfRoutes.entrySet()) {
                for (String _routesId : routesId) {
                    if (_mapOfRoutes.getKey() == Integer.parseInt(_routesId)) {
                        mapOfRoutes.get(Integer.parseInt(_routesId)).setVIP("1");
                    }
                }
            }
        }

        Iterator<Map.Entry<Integer, Route>> iterator = mapOfRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            for (int i = 0; i < listOfWagons.size(); i++) {

                String stationCode1 = listOfWagons.get(i).getKeyOfStationDestination();
                String stationCode2 = entry.getValue().getKeyOfStationDeparture();
                String keyItemCargo = listOfWagons.get(i).getKeyItemCargo();

                String key = stationCode1 + "_" + stationCode2;

                // Заполняем мапу классами
                if (!rootMapWithTypeOfCargo.containsKey(keyItemCargo)) {
                    int type = getTypeOfCargo.getTypeOfCargo(keyItemCargo);
                    rootMapWithTypeOfCargo.put(keyItemCargo, type);
                }

                // Заполняем мапы расстояний
                if (!rootMapWithDistanceMoreMaxDist.containsKey(key)) {
                    if (!rootMapWithDistances.containsKey(key)) {
                        List<Integer> listDistance = getDistanceBetweenStations.getDistanceBetweenStations(stationCode1, stationCode2);
                        int distance = listDistance.get(0);
                        if (distance == -1) {
                            if (!checkExistKeyOfStationImpl.checkExistKey(stationCode2)) {
                                basicClassLookingForImpl.getListOfError().add("Проверьте код станции " + stationCode2 + " в файле заявок");
                                logger.error("Проверьте код станции " + stationCode2 + " в файле заявок");
                                iterator.remove();
                                break;
                            }
                            if (!checkExistKeyOfStationImpl.checkExistKey(stationCode1)) {
                                basicClassLookingForImpl.getListOfError().add("Проверьте код станции " + stationCode1 + " в файле дислокации вагонов");
                                logger.error("Проверьте код станции {}", stationCode1 + " в файле дислокации вагонов");
                                listOfWagons.remove(i);
                                break;
                            }
                            if (checkExistKeyOfStationImpl.checkExistKey(stationCode2) && checkExistKeyOfStationImpl.checkExistKey(stationCode1)) {
                                basicClassLookingForImpl.getListOfError().add("Не нашел расстояние между " + stationCode1 + " и " + stationCode2);
                                logger.error("Не нашел расстояние между " + stationCode1 + " и " + stationCode2);
                                break;
                            }
                        } else {
                            if (distance != -20000) {
                                rootMapWithDistances.put(key, listDistance);
                            } else {
                                rootMapWithDistanceMoreMaxDist.put(key, listDistance);
                            }
                        }
                    }
                }
            }
        }

        try {
            fillMapsNotVipAndVip.separateMaps(mapOfRoutes);
        } catch (NullPointerException e) {
            logger.error("Map must not empty");
        }

        // Заменяем список вагонов после получения расстояний
        getListOfWagonsImpl.replaceListOfWagon(listOfWagons);

        logger.info("Stop process fill map with distances");
    }

    public Map<String, List<Integer>> getRootMapWithDistances() {
        return rootMapWithDistances;
    }

    public void setRootMapWithDistances(Map<String, List<Integer>> rootMapWithDistances) {
        this.rootMapWithDistances = rootMapWithDistances;
    }

    public Map<String, List<Integer>> getRootMapWithDistanceMoreMaxDist() {
        return rootMapWithDistanceMoreMaxDist;
    }

    public void setRootMapWithDistanceMoreMaxDist(Map<String, List<Integer>> rootMapWithDistanceMoreMaxDist) {
        this.rootMapWithDistanceMoreMaxDist = rootMapWithDistanceMoreMaxDist;
    }

    public GetListOfRoutesImpl getGetListOfRoutesImpl() {
        return getListOfRoutesImpl;
    }

    public void setGetListOfRoutesImpl(GetListOfRoutesImpl getListOfRoutesImpl) {
        this.getListOfRoutesImpl = getListOfRoutesImpl;
    }

    public GetListOfWagonsImpl getGetListOfWagonsImpl() {
        return getListOfWagonsImpl;
    }

    public void setGetListOfWagonsImpl(GetListOfWagonsImpl getListOfWagonsImpl) {
        this.getListOfWagonsImpl = getListOfWagonsImpl;
    }

    public Map<String, Integer> getRootMapWithTypeOfCargo() {
        return rootMapWithTypeOfCargo;
    }

    public void setRootMapWithTypeOfCargo(Map<String, Integer> rootMapWithTypeOfCargo) {
        this.rootMapWithTypeOfCargo = rootMapWithTypeOfCargo;
    }
}

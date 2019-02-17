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

import java.io.*;
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
    private ClassHandlerLookingForImpl classHandlerLookingFor;

    // Основная мапа
    private Map<String, List<Object>> rootMapWithDistances;

    private Map<Integer, Route> mapOfRoutes;
    private List<Wagon> listOfWagons;

    // Мапа хранит классы грузов
    private Map<String, Integer> rootMapWithTypeOfCargo = new HashMap<>();

    @Override
    public void fillMap() {
        logger.info("Start process fill map with distances");
        rootMapWithDistances = deSerializeMap();
        mapOfRoutes = new HashMap<>(getListOfRoutesImpl.getMapOfRoutes());
        listOfWagons = new ArrayList<>(getListOfWagonsImpl.getListOfWagons());
        logger.info("Stop process fill map with distances");
    }

    public void fillRootMapWithDistances(List<Wagon> listWagon, Map<Integer, Route> mapRoutes) {
        //logger.info("Start method fillRootMapWithDistances");
        Iterator<Map.Entry<Integer, Route>> iterator = mapRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            for (int i = 0; i < listWagon.size(); i++) {

                String wagonKeyOfStationDestination = listWagon.get(i).getKeyOfStationDestination();
                String routeKeyOfStationDeparture = entry.getValue().getKeyOfStationDeparture();
                String keyCargo = listWagon.get(i).getKeyItemCargo();
                String key = wagonKeyOfStationDestination + "_" + routeKeyOfStationDeparture + "_" + keyCargo;

                if (!wagonKeyOfStationDestination.equals("")) {
                    if (keyCargo.equals("000000")) {
                        List<Object> listDistance = new ArrayList<>();
                        listDistance.add(0);
                        listDistance.add(0);
                        listDistance.add(0);
                        listDistance.add(null);
                        listDistance.add(null);
                        rootMapWithDistances.put(key, listDistance);
                    } else {
                        // Заполняем мапы расстояний
                        if (!rootMapWithDistances.containsKey(key)) {
                            List<Object> listDistance = getDistanceBetweenStations.getDistanceBetweenStations(wagonKeyOfStationDestination, routeKeyOfStationDeparture, keyCargo);
                            int distance = Integer.parseInt((String)listDistance.get(0));
                            if (distance == -20000) {
                                classHandlerLookingFor.getBasicClassLookingFor().getListOfError().add(String.format("Не нашел расстояние между %s и %s", wagonKeyOfStationDestination, routeKeyOfStationDeparture));
                                logger.error(String.format("Не нашел расстояние между %s и %s", wagonKeyOfStationDestination, routeKeyOfStationDeparture));
                                iterator.remove();
                                listWagon.remove(i);
                                break;
                            } else {
                                rootMapWithDistances.put(key, listDistance);
                            }
                        }
                    }
                }
            }
        }
    }

    void serializeMap(HashMap<String, List<Object>> map) {
        File file = new File(JavaHelperBase.PATH_SAVE_FILE_MAP);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("Файл не сериализации найден и был создан:", e.getMessage());
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream))
        {
            objectOutputStream.writeObject(map);
            logger.info("Карты успешно сохранены");
        } catch (Exception e) {
            logger.error("IO Exception", e.getMessage());
        }
    }

    private Map<String, List<Object>> deSerializeMap() {
        File file = new File(JavaHelperBase.PATH_SAVE_FILE_MAP);
        if (!file.exists()) {
            logger.info("Файл сериализации не найден");
            return new HashMap<>();
        }
        Map<String, List<Object>> map = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream (file);
             ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream))
        {
            map = (Map<String, List<Object>>) objectInputStream.readObject();
            logger.info("Карты успешно загружены");
        } catch (Exception e) {
            logger.error("IO Exception", e.getMessage());
        }
        return map;
    }

    public GetListOfRoutesImpl getGetListOfRoutesImpl() {
        return getListOfRoutesImpl;
    }

    public void setGetListOfRoutesImpl(GetListOfRoutesImpl getListOfRoutesImpl) {
        this.getListOfRoutesImpl = getListOfRoutesImpl;
    }

    public GetDistanceBetweenStationsImpl getGetDistanceBetweenStations() {
        return getDistanceBetweenStations;
    }

    public void setGetDistanceBetweenStations(GetDistanceBetweenStationsImpl getDistanceBetweenStations) {
        this.getDistanceBetweenStations = getDistanceBetweenStations;
    }

    public GetListOfWagonsImpl getGetListOfWagonsImpl() {
        return getListOfWagonsImpl;
    }

    public void setGetListOfWagonsImpl(GetListOfWagonsImpl getListOfWagonsImpl) {
        this.getListOfWagonsImpl = getListOfWagonsImpl;
    }

    public ClassHandlerLookingForImpl getClassHandlerLookingFor() {
        return classHandlerLookingFor;
    }

    public void setClassHandlerLookingFor(ClassHandlerLookingForImpl classHandlerLookingFor) {
        this.classHandlerLookingFor = classHandlerLookingFor;
    }

    public Map<String, List<Object>> getRootMapWithDistances() {
        return rootMapWithDistances;
    }

    public void setRootMapWithDistances(Map<String, List<Object>> rootMapWithDistances) {
        this.rootMapWithDistances = rootMapWithDistances;
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public List<Wagon> getListOfWagons() {
        return listOfWagons;
    }

    public void setListOfWagons(List<Wagon> listOfWagons) {
        this.listOfWagons = listOfWagons;
    }

    public Map<String, Integer> getRootMapWithTypeOfCargo() {
        return rootMapWithTypeOfCargo;
    }

    public void setRootMapWithTypeOfCargo(Map<String, Integer> rootMapWithTypeOfCargo) {
        this.rootMapWithTypeOfCargo = rootMapWithTypeOfCargo;
    }
}

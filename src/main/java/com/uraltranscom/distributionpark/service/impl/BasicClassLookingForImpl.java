package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model.Wagon;
import com.uraltranscom.distributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Основной класс
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 01.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 *
 */

@Component
public class BasicClassLookingForImpl extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassLookingForImpl.class);

    @Autowired
    private GetListOfDistanceImpl getListOfDistance;
    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;

    // Мапа для записи в файл Вагона + Станция назначения.
    private Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute = new HashMap<>();

    // Массив распределенных маршрутов и вагонов
    private List<WagonFinalInfo> listOfDistributedRoutesAndWagons = new ArrayList<>();

    // Массив ошибок
    private List<String> listOfError = new ArrayList<>();

    private BasicClassLookingForImpl() {
    }

    public void fillMapRouteIsOptimal() {
        // Очищаем массивы итоговые
        totalMapWithWagonNumberAndRoute.clear();
        listOfDistributedRoutesAndWagons.clear();
        listOfError.clear();

        // Запускаем метод заполненеия первоначальной мапы расстояний
        getListOfDistance.fillMap();

        // Заполняем мапы
        Map<Integer, Route> tempMapRoutes = getGetListOfDistance().getMapOfRoutes();
        List<Wagon> tempListOfWagons = getGetListOfDistance().getListOfWagons();

        classHandlerLookingFor.lookingForOptimalMapOfRoute(tempMapRoutes, tempListOfWagons);

        // очищаем мапы
        tempListOfWagons.clear();
        tempMapRoutes.clear();
    }

    public Map<WagonFinalInfo, Route> getTotalMapWithWagonNumberAndRoute() {
        return totalMapWithWagonNumberAndRoute;
    }

    public void setTotalMapWithWagonNumberAndRoute(Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute) {
        this.totalMapWithWagonNumberAndRoute = totalMapWithWagonNumberAndRoute;
    }

    public List<WagonFinalInfo> getListOfDistributedRoutesAndWagons() {
        return listOfDistributedRoutesAndWagons;
    }

    public void setListOfDistributedRoutesAndWagons(List<WagonFinalInfo> listOfDistributedRoutesAndWagons) {
        this.listOfDistributedRoutesAndWagons = listOfDistributedRoutesAndWagons;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public void setListOfError(List<String> listOfError) {
        this.listOfError = listOfError;
    }

    public GetListOfDistanceImpl getGetListOfDistance() {
        return getListOfDistance;
    }

    public void setGetListOfDistance(GetListOfDistanceImpl getListOfDistance) {
        this.getListOfDistance = getListOfDistance;
    }
}
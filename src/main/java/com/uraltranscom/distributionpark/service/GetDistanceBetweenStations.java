package com.uraltranscom.distributionpark.service;

/**
 *
 * Интерфейс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 25.10.2017
 *
 * 06.11.2017
 *   1. Добавлено внесение в мапу название ЖД, для более детального поиска номера станции
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 22.04.2018
 *   1. Версия 4.1
 *
 */

import java.sql.SQLException;
import java.util.List;

public interface GetDistanceBetweenStations {
    List<Integer> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination) throws SQLException;

}

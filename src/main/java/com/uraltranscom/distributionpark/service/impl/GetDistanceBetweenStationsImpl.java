package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.service.GetDistanceBetweenStations;
import com.uraltranscom.distributionpark.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 25.10.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 22.04.2018
 *   1. Версия 4.1
 * 03.05.2018
 *   1. Версия 4.2
 *
 */

@Service
public class GetDistanceBetweenStationsImpl extends ConnectionDB implements GetDistanceBetweenStations {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsImpl.class);

    private GetDistanceBetweenStationsImpl() {
    }

    @Override
    public List<Object> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination, String keyCargo) {

        List<Object> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, keyCargo);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get distance for: {}", keyOfStationDeparture + "_" + keyOfStationDestination + ": " + listResult.get(0));
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return listResult;
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, String keyCargo) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call  test_distance.get_root_distance2(?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setString(3, keyCargo);
        return callableStatement;
    }
}

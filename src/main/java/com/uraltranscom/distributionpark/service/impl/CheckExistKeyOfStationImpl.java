package com.uraltranscom.distributionpark.service.impl;

/**
 *
 * Класс проверки корректности кода станции
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 * 04.05.2018
 *   1. Версия 4.2
 *
 */

import com.uraltranscom.distributionpark.service.CheckExistKeyOfStation;
import com.uraltranscom.distributionpark.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class CheckExistKeyOfStationImpl extends ConnectionDB implements CheckExistKeyOfStation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(CheckExistKeyOfStationImpl.class);

    private CheckExistKeyOfStationImpl() {
    }

    public boolean checkExistKey(String keyOfStation) {

        Boolean isExist = false;

        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, keyOfStation);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                if (!resultSet.wasNull()) {
                    isExist = true;
                }
            }
            logger.debug("Проверяем станцию: {}", keyOfStation);
        } catch (SQLException ex) {
            logger.error("Ошибка запроса: {}", ex.getMessage());
        }
        return isExist;
    }

    private PreparedStatement createPreparedStatement(Connection connection, String keyOfStation) throws SQLException {
        String sql = "select distinct 1 from distances_new where (station_id1 = ? or station_id2 = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyOfStation);
        preparedStatement.setString(2, keyOfStation);
        return preparedStatement;
    }
}

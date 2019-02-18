package com.uraltranscom.distributionpark.service.impl;

import com.uraltranscom.distributionpark.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения класса груза
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 03.05.2018
 *
 * 03.05.2018
 *   1. Версия 4.2
 *
 */

@Component
public class GetTypeOfCargoImpl extends ConnectionDB {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetTypeOfCargoImpl.class);

    public GetTypeOfCargoImpl() {
    }

    public int getTypeOfCargo(String key) {

        int type = 0;

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, key);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                type = resultSet.getInt(1);
            }
            logger.debug("Get type of cargo: {}", key + ": " + type);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return type;
    }

    private CallableStatement createCallableStatement(Connection connection, String key) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call public.getclassofcargo(?) } ");
        callableStatement.setString(1, key);
        return callableStatement;
    }
}

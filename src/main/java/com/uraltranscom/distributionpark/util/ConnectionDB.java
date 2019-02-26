package com.uraltranscom.distributionpark.util;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 *
 * Класс получения DataSource
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 25.10.2017
 *
 * 13.11.2017
 *   1. Добавление хранения пароля в ZooKeeper
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

@Component
public class ConnectionDB {
    private static DataSource dataSource;

    public ConnectionDB() {
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        ConnectionDB.dataSource = dataSource;
    }
}

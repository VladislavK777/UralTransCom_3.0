package com.uraltranscom.distributionpark.util;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

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
    private static HikariDataSource dataSource;

    public ConnectionDB() {
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(HikariDataSource dataSource) {
        ConnectionDB.dataSource = dataSource;
    }
}

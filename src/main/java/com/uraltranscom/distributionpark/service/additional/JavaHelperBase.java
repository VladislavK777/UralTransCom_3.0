package com.uraltranscom.distributionpark.service.additional;

import com.uraltranscom.distributionpark.util.GetPathSaveFile;

/**
 *
 * Класс-помощник содержит основные константы
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 25.03.2018
 *
 * 25.03.2018
 *   1. Версия 4.0
 *
 */

public class JavaHelperBase {
    // Максимальное количетво дней в обороте вагона
    public static final int MAX_FULL_CIRCLE_DAYS = 31;

    // Параметры загрузки вагонов
    public static final int LOADING_OF_WAGON_KR = 7; // Крытый вагон
    public static final int LOADING_OF_WAGON_PV = 4; // Полувагон
    public static final int UNLOADING_OF_WAGON = 4; // Выгрузка вагонов

    // Типы вагонов
    public static final String TYPE_OF_WAGON_KR = "КР";
    public static final String TYPE_OF_WAGON_PV = "ПВ";

    // Константы для класса преобразования префикла "дней"
    public static final String PREFIX_ONE_DAY = "день";
    public static final String PREFIX_2_4_DAYS = "дня";
    public static final String PREFIX_5_10_DAYS = "дней";

    // Путь к файлу серилизации сохраненных карт расстояний
    public static final String PATH_SAVE_FILE_MAP = GetPathSaveFile.getPathTomcat();

    // Максимальное расстояние для пустого вагона
    public static final int MAX_DISTANCE_RUS_TO_RUS = 600; // Внутри России
    public static final int MAX_DISTANCE_CIS_TO_CIS = 2500; // Внутри СНГ
    public static final int MAX_DISTANCE_RUS_TO_CIS_TO_RUS = 1800; // Между Россией_СНГ_Россией

    // Код страны
    public static final int CODE_IS_RUSSIA = 11;

    // Коды проверок принадлежности стран
    public static final int RUS_RUS = 0; // Внутри России
    public static final int CIS_CIS = 1; // Внутри СНГ
    public static final int RUS_CIS_RUS = 2; // Между Россией_СНГ_Россией

}

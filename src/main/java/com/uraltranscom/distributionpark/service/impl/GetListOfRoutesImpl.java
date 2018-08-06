package com.uraltranscom.distributionpark.service.impl;

/**
 *
 * Класс получения списка маршрутов
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 25.10.2017
 *
 * 17.11.2017
 *   1. Изменен метод заполнения Map
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 * 24.04.2018
 *   1. Версия 4.2
 *
 */

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model.additional_model.VolumePeriod;
import com.uraltranscom.distributionpark.model.additional_model.WagonType;
import com.uraltranscom.distributionpark.service.GetList;
import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetListOfRoutesImpl extends JavaHelperBase implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfRoutesImpl.class);

    // Основаная мапа, куда записываем все маршруты
    private Map<Integer, Route> mapOfRoutes = new HashMap<>();

    // Переменные для работы с файлами
    private File file;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private GetListOfRoutesImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
        mapOfRoutes.clear();
        // Получаем файл формата xls
        try {
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем Map данными
            sheet = xssfWorkbook.getSheetAt(0);
            int i = 0;
            for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(1);

                String keyOfStationDeparture = null;
                String nameOfStationDeparture = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;
                String distanceOfWay = null;
                String customer = null;
                int countOrders = 0;
                int volumeFrom = 0;
                int volumeTo = 0;
                String numberOrder = null;
                String cargo = null;
                String wagonType = null;

                for (int c = 1; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals("Код ЕСР ст. отправления")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Ст. отправления")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Код ЕСР ст.назначения")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Ст. назначения")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Расстояние, км")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        String val = Double.toString(xssfRow.getCell(c).getNumericCellValue());
                        double valueDouble = xssfRow.getCell(c).getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distanceOfWay = val;
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Контрагент")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Кол-во ПС")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        countOrders = (int) xssfRow.getCell(c).getNumericCellValue();
                        if (countOrders < 0) {
                            countOrders = countOrders * (-1);
                        }
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Объем от")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeFrom = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Объем до")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeTo = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Тип парка")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        wagonType = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Номер заявки")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        numberOrder = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Груз")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        cargo = xssfRow.getCell(c).getStringCellValue();
                    }
                }
                if (wagonType.equals(TYPE_OF_WAGON_KR)) {
                    mapOfRoutes.put(i, new Route(keyOfStationDeparture, nameOfStationDeparture, keyOfStationDestination, nameOfStationDestination, distanceOfWay, customer, countOrders, new VolumePeriod(volumeFrom, volumeTo), numberOrder, cargo, new WagonType(wagonType)));
                    i++;
                }
            }
            logger.debug("Body route: {}", mapOfRoutes);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}

package com.uraltranscom.distributionpark.service.export;

/**
 *
 * Класс записи в файл Excel
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 09.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 25.04.2018
 *   1. Версия 4.2
 *
 */

import com.uraltranscom.distributionpark.model.Route;
import com.uraltranscom.distributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.distributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.distributionpark.service.additional.PrefixOfDays;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class WriteToFileExcel extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WriteToFileExcel.class);

    // Успешная выгрузка
    private static boolean isOk = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static XSSFWorkbook xssfWorkbook;

    private static File file;

    private WriteToFileExcel() {
    }

    public static void downloadFileExcel(HttpServletResponse response, List<String>... listOfFinal) {
        try {
            xssfWorkbook = new XSSFWorkbook();
            xssfWorkbook.createSheet("Распределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные вагоны");
            xssfWorkbook.createSheet("Ошибки");

            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, listOfFinal);

            xssfWorkbook.close();
            isOk = true;
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static void downloadFileExcel(HttpServletResponse response, Map<WagonFinalInfo, Route> map) {
        try {
            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, map);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static synchronized void writeToFileExcel(HttpServletResponse response, Map<WagonFinalInfo, Route> map) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))) {

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    XSSFRow row = sheet.getRow(0);
                    for (int c = 0; c < row.getLastCellNum(); c++) {
                        if (row.getCell(c).getStringCellValue().trim().equals("Номер вагона")) {
                            XSSFRow xssfRow = sheet.getRow(j);
                            String val = String.valueOf((int) xssfRow.getCell(c).getNumericCellValue());
                            for (Map.Entry<WagonFinalInfo, Route> mapForAdd : map.entrySet()) {
                                if (val.equals(mapForAdd.getKey().getNumberOfWagon())) {
                                    for (int q = 0; q < row.getLastCellNum(); q++) {
                                        if (row.getCell(q).getStringCellValue().trim().equals("Станция погрузки запланированная")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(mapForAdd.getValue().getNameOfStationDeparture());
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                        if (row.getCell(q).getStringCellValue().trim().equals("Клиент Следующее задание")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(mapForAdd.getValue().getCustomer());
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                        if (row.getCell(q).getStringCellValue().trim().equals("Примечание")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(buildText(mapForAdd.getKey().getDistanceEmpty(), mapForAdd.getKey().getCountCircleDays()));
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    private static String buildText(int dist, int countCircle) {
        if (countCircle < MAX_FULL_CIRCLE_DAYS ) {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle);
        } else {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle) + "(превышение!)";
        }
    }

    private static XSSFCellStyle cellStyle(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        return cellStyle;
    }

    // Метод записи в файл
    public static synchronized void writeToFileExcel(HttpServletResponse response, List<String>... listOfFinalArray) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                XSSFRow row;
                Cell cell;

                // Заполняем данными
                for (String list : listOfFinalArray[i]) {
                    int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                    row = sheet.createRow(rowCount + 1);
                    cell = row.createCell(0);
                    cell.setCellValue(list);
                }
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    public static boolean isIsOk() {
        return isOk;
    }

    public static void setIsOk(boolean isOk) {
        WriteToFileExcel.isOk = isOk;
    }

    public static void setFile(File file) {
        WriteToFileExcel.file = file;
    }
}

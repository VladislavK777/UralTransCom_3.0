package com.uraltranscom.distributionpark.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 09.04.2018
 *   1. Версия 4.2
 *
 */

import com.uraltranscom.distributionpark.service.export.WriteToFileExcel;
import com.uraltranscom.distributionpark.service.impl.BasicClassLookingForImpl;
import com.uraltranscom.distributionpark.service.impl.GetListOfRoutesImpl;
import com.uraltranscom.distributionpark.service.additional.MultipartFileToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BasicController {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;

    @Autowired
    private GetListOfRoutesImpl getListOfRoutes;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "routes") MultipartFile routeFile,
                             @RequestParam(value = "wagons") MultipartFile wagonFile, Model model) {
        basicClassLookingForImpl.getGetListOfDistance().getGetListOfRoutesImpl().setFile(MultipartFileToFile.multipartToFile(routeFile));
        basicClassLookingForImpl.getGetListOfDistance().getGetListOfWagonsImpl().setFile(MultipartFileToFile.multipartToFile(wagonFile));
        model.addAttribute("listRoute", getListOfRoutes.getMapOfRoutes());
        return "showroutes";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String reportList(Model model) {
        basicClassLookingForImpl.fillMapRouteIsOptimal();
        model.addAttribute("reportListOfDistributedRoutesAndWagons", basicClassLookingForImpl.getListOfDistributedRoutesAndWagons());
        model.addAttribute("reportListOfError", basicClassLookingForImpl.getListOfError());
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void getXLS(HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileExcel(response, basicClassLookingForImpl.getTotalMapWithWagonNumberAndRoute());
    }
}

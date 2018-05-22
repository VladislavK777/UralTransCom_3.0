package com.uraltranscom.service.additional;

import com.uraltranscom.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс разделения рейсов VIP - неVIP
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 *
 */

@Service
public class FillMapsNotVipAndVip {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(FillMapsNotVipAndVip.class);

    // Выходные мапы
    private Map<Integer, Route> mapVIP = new HashMap<>(); // Мапа VIP
    private Map<Integer, Route> mapNotVIP = new HashMap<>(); // Мапа неVIP

    private FillMapsNotVipAndVip() {
    }

    public void separateMaps(Map<Integer, Route> map) throws NullPointerException {
        if (map.isEmpty()) {
            throw new NullPointerException("Не был загружен файл заявок");
        }
        for (Map.Entry<Integer, Route> maps : map.entrySet()) {
            if (maps.getValue().getVIP().equals("1")) {
                mapVIP.put(maps.getKey(), maps.getValue());
            } else {
                mapNotVIP.put(maps.getKey(), maps.getValue());
            }
        }
    }

    public Map<Integer, Route> getMapVIP() {
        return mapVIP;
    }

    public void setMapVIP(Map<Integer, Route> mapVIP) {
        this.mapVIP = mapVIP;
    }

    public Map<Integer, Route> getMapNotVIP() {
        return mapNotVIP;
    }

    public void setMapNotVIP(Map<Integer, Route> mapNotVIP) {
        this.mapNotVIP = mapNotVIP;
    }
}

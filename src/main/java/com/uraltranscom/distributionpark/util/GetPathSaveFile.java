package com.uraltranscom.distributionpark.util;

/**
 *
 * Класс получения пути к файлу с сохраненными картами расстояний
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 06.09.2018
 *
 * 06.09.2018
 *   1. Версия 1.0
 *
 */

public final class GetPathSaveFile {
    private static final String PATH_TOMCAT = System.getenv("CATALINA_HOME");
    // Времянка
    private static final String PATH_HOME_MAC = System.getenv("HOME") + "/Desktop/apache-tomcat-8.5.29";
    private static final String FILE_NAME = "saveDistanceMap.ser";
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static String getPathTomcat() {
        if(isWindows()){
            return PATH_TOMCAT + "\\save\\" + FILE_NAME;
        } else if(isMac() || isUnix ()){
            return PATH_HOME_MAC + "/save/" + FILE_NAME;
        }
        return "Неизвестная OS";
    }
    private static boolean isWindows(){
        //windows
        return (OS_NAME.contains("win"));

    }

    private static boolean isMac(){
        //Mac
        return (OS_NAME.contains( "mac" ));

    }

    private static boolean isUnix (){
        //linux or unix
        return (OS_NAME.contains( "nix") || OS_NAME.contains( "nux"));

    }
}

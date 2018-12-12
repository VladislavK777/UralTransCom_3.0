<%--
  Created by IntelliJ IDEA.
  User: Vladislav.Klochkov
  Date: 15.01.2018
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UralTransCom|DistributionPark</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>

    <!-- Скрипт всплывающего окна -->
    <script type="text/javascript">
        $(document).ready(function () {
            $(popup_bg).click(function () {
                $(popup).fadeOut(800);
            });
        });
        function showPopup() {
            $(popup).fadeIn(800);
        }
    </script>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>

    <style>
        /* Настрйка вкладок*/
        /* Стили секций с содержанием */
        .tabs > section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
        }
        .tabs > section > p {
            margin: 0 0 5px;
            line-height: 1.5;
            color: #383838;
            /* прикрутим анимацию */
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }
        /* Прячем чекбоксы */
        .tabs > input {
            display: none;
            position: absolute;
        }
        /* Стили переключателей вкладок (табов) */
        .tabs > label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: center;
            color: #aaa;
            border: 0px solid #ddd;
            border-width: 1px 1px 1px 1px;
            background: #f1f1f1;
            border-radius: 3px 3px 0 0;
        }
        /* Шрифт-иконки от Font Awesome в формате Unicode */
        .tabs > label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
        }
        /* Изменения стиля переключателей вкладок при наведении */
        .tabs > label:hover {
            color: #888;
            cursor: pointer;
        }
        /* Стили для активной вкладки */
        .tabs > input:checked + label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
        }
        /* Активация секций с помощью псевдокласса :checked */
        #tab1:checked ~ #content-tab1,
        #tab2:checked ~ #content-tab2 {
            display: block;
        }
        /* Убираем текст с переключателей и оставляем иконки на малых экранах*/
        @media screen and (max-width: 1500px) {
            .tabs > label {
                font-size: 12px;
                width: 180px;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 12px;
            }
        }
        /* Изменяем внутренние отступы переключателей для малых экранов */
        @media screen and (max-width: 400px) {
            .tabs > label {
                padding: 15px;
            }
        }
    </style>
</head>

<body onload="cop()">

<div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
</div>

<div class="one">
    <h1>СЕРВИС РАСПРЕДЕЛЕНИЯ ВАГОНОВ</h1>
    <div class="train">
    		<img class="image" src="resources/train.jpg">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<br><br><br><br><br><br><br><br><br><br>
<div>
    <c:if test="${empty reportListOfDistributedRoutesAndWagons}">
        <input type="button" value="Создать процесс" onclick="showPopup()" class="bot1" style="visibility:visible">
        <br><br>
    </c:if>

    <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
        <form action="/distributionpark" method="get" style="visibility:visible">
            <input type="submit" value="Очистить форму" class="bot1">
        </form>
        <form action="export" method="post" style="visibility:visible">
            <input type="submit" value="Скачать отчет" class="bot1">
        </form>
    </c:if>

    <table class="table_report">
        <tr>
            <td class="td_report">
                <div id="popup"
                     style="position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;">
                    <div id="popup_bg"
                         style="background: rgba(0, 0, 0, 0.2); position: absolute; z-index: 1; height: 100%; width: 100%;">
                    </div>
                    <div class="form">
                        <form enctype="multipart/form-data" method="post" action="routes">
                            <p>Файл заявок</p>
                            <input type="file" name="routes" multiple accept="xlsx">
                            <p>Файл дислокации вагонов</p>
                            <input type="file" name="wagons" multiple accept="xlsx">
                            <p>
                                <input type="submit" value="Загрузить" class="bot1">
                            </p>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <div>
        <div class="tabs">
            <input id="tab1" type="radio" name="tabs" checked>
            <label for="tab1" title="Распределенные рейсы">Распределенные заявки</label>

            <input id="tab2" type="radio" name="tabs">
            <label for="tab2" title="Ошибки">Ошибки в кодах станций</label>

            <section id="content-tab1">
             <div>
                <table class="table_report">
                    <tr>
                        <th>Номер вагона</th>
                        <th>Станция распределения</th>
                        <th>Рейс</th>
                        <th>Порожнее расстояние</th>
                        <th>Оборот дней</th>
                        <th>Из под груза</th>
                        <th>Класс груза</th>
                    </tr>
                     <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
                         <c:forEach items="${reportListOfDistributedRoutesAndWagons}" var="reportList">
                            <tr>
                                <c:choose>
                                    <c:when test="${reportList.getCargo() == 'СБ.ПОВАГ.ОТП'}">
                                        <td style="background: #364274; color: #ffffff;">${reportList.getNumberOfWagon()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getNameOfStationDepartureOfWagon()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getRoute()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getDistanceEmpty()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getCountCircleDays()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getCargo()}</td>
                                        <td style="background: #364274; color: #ffffff;">${reportList.getCargoType()}</td>
                                    </c:when>
                                    <c:when test="${reportList.getCountCircleDays() > 30}">
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getNumberOfWagon()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getNameOfStationDepartureOfWagon()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getRoute()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getDistanceEmpty()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getCountCircleDays()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getCargo()}</td>
                                        <td style="background: #ff0000; color: #ffffff;">${reportList.getCargoType()}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getNumberOfWagon()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getNameOfStationDepartureOfWagon()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getRoute()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getDistanceEmpty()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getCountCircleDays()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getCargo()}</td>
                                        <td style="background: #ffffff; color: #364274;">${reportList.getCargoType()}</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                         </c:forEach>
                     </c:if>
                </table>
             </div>
            </section>
            <section id="content-tab2">
                <c:if test="${!empty reportListOfError}">
                    <table class="table_report">
                        <c:forEach items="${reportListOfError}" var="Error">
                            <tr>
                                <td style="background: #ffffff; color: #364274;">${Error}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </section>
        </div>
    </div>
    <br>
</div>

<br><br><br>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>
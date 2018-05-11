<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UralTransCom</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>
    <!-- Вызов лоадера -->
    <script>
        jQuery(function ($) {
            $('#startProcess').on('click', function (e) {
                $('.content').toggleClass('hide');
            });
        });
    </script>

    <!-- Блокировка экрана -->
    <script type="text/javascript">
        function lockScreen() {
            var lock = document.getElementById('lockPane');
            if (lock)
                lock.className = 'lockScreenOn';
                $('body').addClass('stop-scrolling');
                document.body.scrollTop = document.documentElement.scrollTop = 0;
        }
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
        body {
            font: 14px/1 "Open Sans", sans-serif;
        }
        .stop-scrolling {
            height: 100%;
            overflow: hidden;
        }
        /* Стили лоадера */
        .hide {
            display: none;
        }
        .loader {
            border: 16px solid #f3f3f3;
            border-top: 16px solid #364274;
            border-radius: 50%;
            width: 120px;
            height: 120px;
            animation: spin 2s linear infinite;
            position: relative;
            top: 40%;
            left: 45%;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        /* Блокировка экрана */
        .lockScreenOff {
            display: none;
            visibility: hidden;
        }
        .lockScreenOn {
            display: block;
            visibility: visible;
            position: absolute;
            z-index: 999;
            top: 0px;
            left: 0px;
            width: 100%;
            height: 100%;
            background-color: #ccc;
            text-align: center;
            filter: alpha(opacity=75);
            opacity: 0.75;
        }
    </style>
</head>

<body onload="cop()">

<div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
</div>

<div class="one">
    <h1>сервис распределения вагонов</h1>
</div>

<div>
    <img style="position: relative; left: 15%;" src="resources/logo.jpg">
</div>

<br><br><br><br><br>

<form action="/uraltranscom" method="get">
    <input type="submit" value="Очистить форму" class="bot1">
</form>

<form method="post" action="reports">
    <input type="submit" value="Запустить распределение" class="bot1" id="startProcess" onclick="lockScreen();">
    <div>
        <table class="table_report">
            <tr>
                <th>Номер заявки</th>
                <th>Станция отправления</th>
                <th>Станция назначения</th>
                <th>Контрагент</th>
                <th>Кол.вагонов</th>
                <th>Расстояние</th>
                <!-- <th>Приоритет</th> -->
            </tr>
            <br><br>
            <c:if test="${!empty listRoute}">
                <c:forEach items="${listRoute}" var="list">
                    <tr>
                        <td style="background: #ffffff; color: #364274;">${list.value.getNumberOrder()}</td>
                        <td style="background: #ffffff; color: #364274;">${list.value.getNameOfStationDeparture()}</td>
                        <td style="background: #ffffff; color: #364274;">${list.value.getNameOfStationDestination()}</td>
                        <td style="background: #ffffff; color: #364274;">${list.value.getCustomer()}</td>
                        <td style="background: #ffffff; color: #364274;">${list.value.getCountOrders()}</td>
                        <td style="background: #ffffff; color: #364274;">${list.value.getDistanceOfWay()}</td>
                        <!-- <td style="background: #ffffff; color: #364274;"><input type="checkbox" name="routeId" value="${list.key}" /></td> -->
                    </tr>
                </c:forEach>
            </c:if>
        </table>
    </div>
</form>

<br><br><br>

<div align="center" id="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>

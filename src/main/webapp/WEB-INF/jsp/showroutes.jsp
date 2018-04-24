<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UralTransCom</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
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
            /* Описываем анимацию свойства opacity */
            @-webkit-keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }
            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
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
            #tab2:checked ~ #content-tab2,
            #tab3:checked ~ #content-tab3,
            #tab4:checked ~ #content-tab4 {
                display: block;
            }
            /* Убираем текст с переключателей и оставляем иконки на малых экранах*/
            @media screen and (max-width: 680px) {
                .tabs > label {
                    font-size: 0;
                }
                .tabs > label:before {
                    margin: 0;
                    font-size: 18px;
                }
            }
            /* Изменяем внутренние отступы переключателей для малых экранов */
            @media screen and (max-width: 400px) {
                .tabs > label {
                    padding: 15px;
                }
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

<form method="post" action="reports">
    <div>
        <table class="table_report">
            <tr>
                <th>Станция отправления</th>
                <th>Станция назначения</th>
                <th>Заказчик</th>
                <th>Заявка, в/о</th>
                <th>Расстояние</th>
                <th>VIP</th>
            </tr>
            <br><br>
            <c:if test="${!empty listRoute}">
                <c:forEach items="${listRoute}" var="list">
                    <tr>
                        <td>${list.value.getNameOfStationDeparture()}</td>
                        <td>${list.value.getNameOfStationDestination()}</td>
                        <td>${list.value.getCustomer()}</td>
                        <td>${list.value.getCountOrders()}</td>
                        <td>${list.value.getDistanceOfWay()}</td>
                        <td><input type="checkbox" name="routeId" value="${list.key}" /></td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
        <p>
            <input type="submit" value="Start" class="bot2" id="startProcess" onclick="lockScreen();">
        </p>
    </div>
</form>

<br><br><br>

<div align="center" id="footer">
    Copyright &copy; ООО "Уральская транспортная компания" <span id="copy"></span>.
    Create by Vladislav Klochkov. All rights reserved
</div>

</body>
</html>

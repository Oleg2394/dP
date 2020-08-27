# Дипломный проект профессии «Тестировщик ПО»

[![Build status](https://ci.appveyor.com/api/projects/status/nqlwvg7ggw12wqqf?svg=true)](https://ci.appveyor.com/project/Oleg2394/diplomqa)

## Документация проекта

* [План автоматизации тестирования](https://github.com/Oleg2394/DiplomQa/blob/master/src/main/java/ru/netolody/docs/Plan.md)

* [Отчет о тестировании веб-сервиса](https://github.com/Oleg2394/DiplomQa/blob/master/src/main/java/ru/netolody/docs/Report.md)

## Настройка запуска авто-тестов

До запуска автотестов необходимо перейти в папку с установлеными тестами и выполнить следующие шаги:

1. Установить и запустить Docker (Docker Toolbox) и Docker-compose.
1. Запустить в Docker контейнеры СУБД MySQl и PostgerSQL.
1. Запустить в Docker контейнер Node.js
1. Откорректировать настройку тестируемого приложения для использования СУБД и Node.js.
1. Запустить тестируемое приложение

## Установка и запуск MySQL в качестве Docker-контейнера
1. В Docker перейти в папку ./mysql
1. Выполнить команду docker-compose up -d
1. Дождаться сообщения о готовности MySQL

## Установка и запуск PostgerSQL в качестве Docker-контейнера
1. В Docker перейти в папку ./postgresql
1. Выполнить команду docker-compose up -d
1. Дождаться сообщения о готовности PostgerSQL

## Установка и запуск Node.js в качестве Docker-контейнера
1. В Docker перейти в папку ./gate-simulator
1. Выполнить команду docker-compose up -d
1. Дождаться сообщения о готовности Node.js

## Настройка тестируемого приложения
Необходимо отредактировать файл application.properties:

1. Вписать IP адрес или URL по которому доступен Docker в параметрах: spring.credit-gate.url, spring.payment-gate.url, spring.datasource.url.
1. При прохождении тестов на MySQL в параметре spring.datasource.url необходимо написать jdbc:mysql: и порт 3306.
1. При прохождении тестов на PostgerSQL в параметре spring.datasource.url необходимо написать jdbc:postgresql: и порт 5432.

## Запуск тестируемого приложения MySQL
java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar 

## Запуск тестируемого приложения PostgerSQL
java -Dspring.datasource.url=jdbc:mysql://localhost:5432/app -jar aqa-shop.jar

## Выполнение авто-тестов
Выполнение авто-тестов запускается файлом gradlew.bat clean build (в Windows) или gradlew clean build (в Linux)

## Для получения отчета Allure 
1. Выполнить команду gradlew allureReport
1. Запустить автотесты gradlew clean build allureReport 
1. Для просмотра отчета Allure необходимо выполнить команду gradlew allureServe и дождаться открытия отчета в браузере.


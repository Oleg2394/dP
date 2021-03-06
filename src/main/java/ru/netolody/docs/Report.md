# Отчет о тестировании веб-сервиса
21.06.2020 - 26.06.2020 было проведено тестирование приложения "Путешествие дня"

В процессе тестирования было проведено автоматизированное тестирование позитивных и негативных проверок заполнения всех полей формы. 
Для этих целей был написан набор автотестов. В систему автоматизации тестирования интегрированы отчёты Allure.

Выполнено 19 автотестов, из них 6 (31.57%) выполнены успешно, 13 (68,43%) не успешно:

[Скриншот](https://ibb.co/7JJBVRx)

В результате тестирования выявлены следующие дефекты:
1. [Орфографическая ошибка в слове Марракеш](https://github.com/Oleg2394/DiplomQa/issues/1)
1. [Не относящаяся к туру информация](https://github.com/Oleg2394/DiplomQa/issues/2)
1. [Наложение всплывающего окна при вводе карты](https://github.com/Oleg2394/DiplomQa/issues/3)
1. [Не исчезают placeholder с ошибками после ввода валидных данных (Купить и Купить в кредит)](https://github.com/Oleg2394/DiplomQa/issues/4)
1. [Не верный заголовок страницы в браузере](https://github.com/Oleg2394/DiplomQa/issues/5)
1. [После нажатия не изменяется визуально кнопка (Купить, Купить в кредит) ](https://github.com/Oleg2394/DiplomQa/issues/6)
1. [Не отслеживается ошибка при вводе в (цифр, спецсимволов) в поле Владелец цифр (Купить, купить в кредит)](https://github.com/Oleg2394/DiplomQa/issues/7)
1. [Не отслеживается ошибка при вводе русского алфавита в поле Владелец](https://github.com/Oleg2394/DiplomQa/issues/8)
1. [Не отслеживается ошибка при вводе в поле CVC 000 (Купить, Купить в кредит)](https://github.com/Oleg2394/DiplomQa/issues/9)
1. [Не отслеживается ошибка при вводе 00 в поле месяц (Купить, Купить в кредит)](https://github.com/Oleg2394/DiplomQa/issues/10)
1. [При вводе карты (DECLINED) и проведением оплаты всплывает окно с одобрением (Купить, Купить в кредит)](https://github.com/Oleg2394/DiplomQa/issues/11)
1. [Сумма тура в таблице 'payment_entry" отличается от цены тура (карта APPROVED и карта DECLINED)](https://github.com/Oleg2394/DiplomQa/issues/12)
1. [Некорректное сообщение для пустого поля (Номер карты, год, месяц, CVC/CVV) bug](https://github.com/Oleg2394/DiplomQa/issues/13)
1. [В БД не создается credit_id в order_entity при попытке взять в кредит](https://github.com/Oleg2394/DiplomQa/issues/14)

[Скриншот](https://ibb.co/WgYcd3d)

## Основные моменты где были выявлены дефекты:

* Отображение и текст сообщений полей формы ввода;
* Отсутствие обработки действий по заблокированным картам;
* Неправильную связь в базе данных при покупке в кредит;

## Тестирование производилось в следующем окружении:

* ОС: Windows 10 x64 Pro
* Java: openjdk version "11.0.8"" 2020-07-14
* Docker: Docker Toolbox v19.03.12
* Node.js: Docker образ node:8.16.2-alpine
* MySQL: Docker образ mysql:8.0.19
* PostgeSQL: Docker образ postgres:12-alpine

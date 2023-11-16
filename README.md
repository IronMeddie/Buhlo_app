

Приложение для сбора переводов

Последняя версия https://disk.yandex.ru/d/w_rOs8bTRqS3Yw
Перед установкой нужно полностью удалить предыдущую версию. Были изменения в БД, но миграцию на новую версию не прописал., поэтому без переустановки будут краши.

В приложении не подключены никакие системы оплаты. Я просто взял диплинк из сберовского редиректа и пробую вызвать его или отправляю на ссылку от сбера для пополнения. Сейчас нет никакой возможности проверить совершен ли реально перевод и на бек отправляю информацию по нажатию на кнопку. Возможный вариант, но костыльный - добавить кнопку подтверждения перевода, которую будет видеть владелец номера и сумма будет засчитываться после подтверждения

Ближайшие планы:
- отрефакторить код из data, отправить весь хардкод в ресурсы или константы
- сделать выбор категории более подробным, добавить деление на светлое/темное красное/белое сухое/полусладкое и т.п.
- добавить картинки для категорий и реализовать их загрузку
- добавить возможность просто скопировать номер телефона вместо перехода по ссылке
- возможность изменить номер телефона для пополнения
- добавить экран профиля с выводом информации о пользователе, возможностью изменить данные, с выводом статистики пополнений, в том числе по категориям
- подключить пуши

Скорее фантастичные планы:
- изучить и попробовать перенести проект в KMM

История:

1.0.1-beta
16.11
- исправил баг с запоминанием выбранных категорий при обновлении экрана. Если натыкать много одновременно, при обновлении выбранные востанавливались некорректно

15.11
- добавил возможность выбирать множество категорий, а так же разом сбросить все выбранные. 
- добавлен вывод статистики текущего баланса в разбивке по категориям
- переключил rules в firestorre на режим if request.auth != null



1.0.0

- подключил DI HILT
- добавлена авторизация, экраны логина, подключен firebase auth
- настроена бд firestore вкачестве бека. Реализована загрузка из нее категорий для пополнения. Настроена отправка информции о транзакциях, и текущем балансе для хранении на беке. 
- настроена бд внутри приложения, все данные загружаемые из бека кладутся во внутреннюю бд, а уже оттуда подается на экраны.
- подключена AppMetrica
- создан основной экран с выводом категорий,с кнопкой для пополнения, с выводом баланса и истории транзакций.
- добавлен pull to refresh
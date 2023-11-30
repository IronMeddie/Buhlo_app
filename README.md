

Приложение для сбора переводов

Последняя версия https://disk.yandex.ru/d/w_rOs8bTRqS3Yw
Перед установкой нужно полностью удалить предыдущую версию. Были изменения во внутренней БД, но миграцию на новую версию не прописал(лень), поэтому без переустановки будут краши.

В приложении не подключены никакие системы оплаты. Я просто взял диплинк из сберовского редиректа и пробую вызвать его или отправляю на ссылку от сбера для пополнения. Сейчас нет никакой возможности проверить совершен ли реально перевод и на бек отправляю информацию по нажатию на кнопку. Возможный вариант, но костыльный - добавить кнопку подтверждения перевода, которую будет видеть владелец номера и сумма будет засчитываться после подтверждения

Ближайшие планы:

- сделать выбор категории более подробным, добавить деление на светлое/темное красное/белое сухое/полусладкое и т.п.
- добавить картинки для категорий и реализовать их загрузку
- реализовать удаление старых данных из внутренней бд, а так же делать запросы к беку только за последними из них и реализовать постраничную подгрузку(если firestore вообще такое позволит)
- добавить возможность просто скопировать номер телефона вместо перехода по ссылке
- возможность изменить номер телефона для пополнения
- добавить экран профиля с выводом информации о пользователе, возможностью изменить данные, с выводом статистики пополнений, в том числе по категориям
- подключить пуши
- добавить кнопку сброса баланса

Скорее фантастичные планы:
- изучить и попробовать перенести проект в KMMP

История:

1.0.1-beta

    30.11 
    - добавил ручку сброса баланса
    - в отправку транзакций добавил отправку ID пьянки

    18.11
    - добавил возможность сменить логин и аватар на странице 
    - исправил баг с кнокой "очистить все категоии" - при обновлении экрана они востанавливались

    16.11
    - исправил баг с запоминанием выбранных категорий при обновлении экрана. Если натыкать много одновременно, при обновлении выбранные востанавливались некорректно
    - рефакторинг кода из firestoreDB, хардкод унес в костанты

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


идея по реализации категорий - верхний уровень тип напитка, далее его более подробные разделы
верхний уровень: Вино, Пиво, Виски, крепкий алкоголь

подкатегории 
вино:
- белое
- красное
- шампанское

Пиво:
- светлое
- темное
- пшеничное, нефильтрованное

крепкий алкоголь:
- виски
- водка
- абсент
- ягер
- текила

можно добавить еще категории реализовать кнопку предложить категорию.

Мне нужно собирать и выводить в приложении информацию кто из пользователей сколько внес.

Кажется считать это из транзакций удобно, но как тогда отличить те, что были ранее и те, что актуальны. Нужно добавить какой-то флаг, который будет меняться при обнулении средств. Или иначе - У каждой пьянки должен быть свой идентификатор и все транзакции летят на него, а после обнуления формируется новый. Так получится еще и вести историю пьянок.
так можно будет отображать только транзакции по текущей, а остальные удалять с устройства.

Кажется только не очень удобно получать идентификатор текущей пьянки и вставлять его в каждое пополнение.

Cчитать это на устройстве не удобно, логичнее сразу получать готовые данные. 

Простым способом будет создать еще одну табличку, где будет counter по каждому юзеру для каждой категории и в целом. Тогда получается нужен примерно такой вид
{емейл пользователя :
                    {категория 1:
                                    пополнения за всю историю: 0
                                    пополнения актуальной пьянки - то есть счетчик сбрасывается при выводе денег:0}
                    {категория 2
                                    ...
}

                                    


из-за разрастания проекта к нему уже похоже пора делать документацию. Сам уже забыть где и как что устроено.
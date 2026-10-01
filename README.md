# BeautyBar — приложение для управления салоном красоты и записи на услуги
BeautyBar — Android-приложение для управления салоном красоты, каталогом услуг,
профилями мастеров и клиентскими записями.

Проект разработан для практики проектирования архитектуры Android-приложения,
создания адаптивных интерфейсов на Jetpack Compose и интеграции с удалённой
базой данных для авторизации и реализации бизнес-процессов.

## Возможности
- Управление салоном:
  администратор может добавлять, редактировать и удалять категории услуг,
  услуги и профили мастеров.
- Аутентификация пользователей и администраторов.
- Просмотр каталога услуг.
- Просмотр администратором списка записей.
- Редактирование администратором портфолио работ мастеров.
- Запись пользователя к мастеру на выбранную услугу.
- Редактирование пользователем личных данных.
- Просмотр пользователем своих записей.

## Скриншоты
<img width="320" alt="Screenshot_20261001_104525_BeautyBar" src="https://github.com/user-attachments/assets/6240e194-f40e-49a9-b8e5-4f8325ce3d98" />
<img width="320" alt="Screenshot_20261001_104518_BeautyBar" src="https://github.com/user-attachments/assets/6c390220-5ed9-46db-a98f-603cbe232b69" />
<img width="320" alt="Screenshot_20261001_112224_BeautyBar" src="https://github.com/user-attachments/assets/62f967f1-49f0-403a-be3d-1b1415b38bb8" />
<img width="320" alt="Screenshot_20261001_112232_BeautyBar" src="https://github.com/user-attachments/assets/19d0f868-7d5e-4e0e-b2dc-1185e965e4ae" />
<img width="320" alt="Screenshot_20261001_112220_BeautyBar" src="https://github.com/user-attachments/assets/1f9dd3a5-55e5-4df9-a3b3-0709f9f5b16d" />
<img width="320" alt="Screenshot_20261001_112242_BeautyBar" src="https://github.com/user-attachments/assets/882fe478-a95f-4229-a202-c8e26eeec4cd" />
<img width="320" alt="Screenshot_20261001_112313_BeautyBar" src="https://github.com/user-attachments/assets/0ecaacb8-2d96-4524-8a50-78939ebbff18" />
<img width="320" alt="Screenshot_20261001_112517_BeautyBar" src="https://github.com/user-attachments/assets/9b3de8d3-b857-4bcc-90ef-bb379888ec71" />
<img width="320" alt="Screenshot_20261001_112510_BeautyBar" src="https://github.com/user-attachments/assets/4df10878-1cf0-4711-935e-3caaf2d854e8" />
<img width="320" alt="Screenshot_20261001_112524_BeautyBar" src="https://github.com/user-attachments/assets/bceb940d-01e1-4bca-a159-3d2e96eb73f0" />
<img width="320" alt="Screenshot_20261001_112542_BeautyBar" src="https://github.com/user-attachments/assets/af5e28b5-6e27-4034-8321-21ed1eb80323" />
<img width="320" alt="Screenshot_20261001_114204_BeautyBar" src="https://github.com/user-attachments/assets/8a5bc7ea-54c7-4a90-aa8e-4676ab5b6de9" />
<img width="320" alt="Screenshot_20261001_173033_BeautyBar" src="https://github.com/user-attachments/assets/78a65616-eec1-4f30-bad5-0c9376bb0211" />
<img width="320" alt="Screenshot_20261001_123013_BeautyBar" src="https://github.com/user-attachments/assets/740ac6f0-2f43-4c6b-a143-176cdb962cae" />
<img width="320" alt="Screenshot_20261001_103118_BeautyBar" src="https://github.com/user-attachments/assets/1e9c4e13-ea67-452d-8bd9-fc6b6a4946ab" />
<img width="320" alt="Screenshot_20261001_103110_BeautyBar" src="https://github.com/user-attachments/assets/5e11a701-4b30-4f95-bbd6-99e1757096a1" />
<img width="320" alt="Screenshot_20261001_103122_BeautyBar" src="https://github.com/user-attachments/assets/ec82c9d6-af09-45bc-8551-6dcf275cc88a" />
<img width="320" alt="Screenshot_20261001_103139_BeautyBar" src="https://github.com/user-attachments/assets/cc3bdf96-7097-4779-9ad1-34fa2879c271" />
<img width="320" alt="Screenshot_20261001_104504_BeautyBar" src="https://github.com/user-attachments/assets/501bf226-4be2-4731-8c93-4ecd0457557f" />
<img width="320" alt="Screenshot_20261001_103153_BeautyBar" src="https://github.com/user-attachments/assets/804319ed-4cd5-4fcd-b36a-5428ab696861" />
<img width="320" alt="Screenshot_20261001_103147_BeautyBar" src="https://github.com/user-attachments/assets/6731d674-755a-41ba-9ea3-56e76a8a131f" />
<img width="320" alt="Screenshot_20261001_173009_BeautyBar" src="https://github.com/user-attachments/assets/0c32f055-3633-4b28-b06e-6e518d67bf7a" />

## Инструкция по сборке и запуску:
1) Клонируйте репозиторий:
   ```
   git clone https://github.com/Artemiy-Z/BeautyBar
   ```
2) Создайте и настройте базу данных:
   - Зарегистрируйтесь/авторизируйтесь на сайте supabase.com
   - Создайте пустой проект
   - Во вкладке "SQL Editor" вставьте содержимое /BeautyBar/supabase_migration.sql и запустите
   - Проверьте создание таблиц USER, ADMIN, MASTER, BOOKING, CATEGORY, SERVICE, WORK_SCHEME и связей между ними
   - Создайте bucket во вкладке "Storage" и назовите его "img" (без кавычек)
   - Во вкладке "SQL Editor" выполните следующую команду:
     ```
     INSERT into "ADMIN"(login, passhash) values('admin', '92668751')
     ```
     эта команда создаст в базе данных запись об администраторе с данными: логин=admin, пароль=admin
3) В файле /BeautyBar/local.properties измените следующие строки:
   ```
   SUPABASE_PUBLIC_KEY=PUBLIC_KEY
   SUPABASE_URL=SUPABASE_URL
   ```
   заменив PUBLIC_KEY и SUPABASE_URL на URL и PUBLISHABLE_KEY из базы данных Supabase (см. Dashboard/Connect/Server, а также Project Settings/API Keys/Publishable Key)
   > ВАЖНО!
   > Используйте только Publishable Key. Никогда не используйте
   > Secret Key, service_role key или другие административные ключи.
5) В папке /BeautyBar/ запустите сборщик Gradle для получения .apk файла:
   ```
   ./gradlew assembleDebug
   ```
   ИЛИ (если вы на Windows)
   ```
   .\gradlew.bat assembleDebug
   ```
Установка:
1) в папке /BeautyBar/app/build/outputs/apk/debug:
   - находим файл app-debug.apk
   - переносим на телефон
   - открываем (при необходимости разрешаем в настройках установку из непроверенных источников)
2) После установки запускаем из меню приложений

## Стек
- Kotlin
- Android SDK
- Jetpack Compose - декларативный пользовательский интерфейс
- Kotlin Coroutines
- Supabase Kotlin Client - работа с PostgreSQL и хранение данных
- PostgreSQL
- Cicerone - навигация между экранами.
- Coil Compose - асинхронная загрузка изображений
- Gradle
- Git

## Архитектура
Приложение использует слоистую архитектуру с разделением представления, бизнес-логики и работы с данными

Основные слои:
- `database` — модели данных и взаимодействие с Supabase/PostgreSQL.
- `navigation` — описание экранов и навигация между ними.
- `navigation/presenter` — логика экранов, загрузка данных и выполнение операций.
- `ui/fragment` — Fragment-контейнеры, используемые для интеграции Cicerone
  с экранами на Jetpack Compose.
- `ui/common` — переиспользуемые Compose-компоненты.
- `ui/theme` — тема, цвета, типографика и стили приложения.

Каждый экран получает данные через отдельный объект логики экрана,
а UI отвечает за отображение состояния и передачу пользовательских действий.

> В текущей версии часть общего состояния приложения хранится на уровне
> application-компонента, а Cicerone открывает Fragment-экраны,
> внутри которых размещается Compose UI.
> В дальнейшем планируется перенести логику экранов в ViewModel,
> перейти на Navigation Compose и представить экраны непосредственно
> как composable destinations.

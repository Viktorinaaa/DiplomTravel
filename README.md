## Дипломный проект по профессии «Тестировщик»
### Автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

#### Запуск проекта
1. Открыть IntelliJ IDEA.
2. Склонировать проект для запуска на локальном ПК.
3. Установить и запустить Docker.
4. Запустить контейнеры.
5. Запустить тестируемый сервис.

#### Prerequisites
- IntelliJ IDEA
- Браузер Chrome
- Git
- Docker


#### Настройка и запуск автотестов для MYSQL
1. Настроить контейнер MYSQL в docker-compose.
2. Настроить контейнер NODE в Dockerfile.
3. Запустить в терминале контейнеры командой:

   `docker-compose up --build`

4. Запустить в терминале приложение с указанием пути к базе данных mysql:

   `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`

5. Запустить в терминале автотесты с указанием пути к базе данных mysql:

   `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`

#### Настройка и запуск автотестов для POSTGRESQL
1. Настроить контейнер POSTGRESQL в docker-compose.
2. Настроить контейнер NODE в Dockerfile.
3. Запустить в терминале контейнеры командой:

   `docker-compose up --build`

4. Запустить в терминале приложение с указанием пути к базе данных postgresql:

   `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`

5. Запустить в терминале автотесты с указанием пути к базе данных postgresql:

   `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`

#### Отчетность
В проект интегрирована система отчетов Allure. Запуск отчета в терминале:

   `./gradlew allureserve`

#### Документация

[План автоматизации тестирования сервиса](https://github.com/Viktorinaaa/Diplom/blob/aae8992c771f9ccf7d490a764526cde13125e122/docs/Plan.md)

[Отчётные документы по итогам тестирования](https://github.com/Viktorinaaa/Diplom/blob/master/docs/Report.md)

[Отчётные документы по итогам автоматизации](https://github.com/Viktorinaaa/Diplom/blob/master/docs/Summary.md)



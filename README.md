# Pool Rest Api Test App

REST Api для организации работы бассейна

---



# Сборка и запуск
### Docker
```shell
docker-compose up
```

---

# API

## Client

Управление клиентской базой

---

### Получить список клиентов

`GET /api/v0/pool/client/all`<br>

JSON Response Body

| Параметр | Тип    | Описание                                       |
|----------|--------|------------------------------------------------|
| id       | number | id клиента                                     |
| name     | string | Имя клиента                                    |

**Response**

```json
[
  {
    "id": 1,
    "name": "Vladislav Khabirov"
  },
  {
    "id": 2,
    "name": "Michael Krotov"
  },
  {
    "id": 3,
    "name": "Ivan Ivanov"
  }
]
```

### Получить данные клиента

`GET /api/v0/pool/client/get/{id}`<br>

PathVariable

| Параметр | Тип    | Обязательно | Описание   |
|----------|--------|:-----------:|------------|
| id       | number |     Да      | id клиента |

JSON Response Body

| Параметр | Тип    | Описание                                       |
|----------|--------|------------------------------------------------|
| id       | number | id клиента                                     |
| name     | string | Имя клиента                                    |
| phone    | string | Телефон клиента в формате 11 чисел с/без знака |
| email    | string | Email клиента в формате `*@*.*`                |

**Response**

```json
{
  "id": 1,
  "name": "Vladislav Khabirov",
  "phone": "+55555555555",
  "email": "didager@ya.ru"
}
```

### Добавить клиента

`POST /api/v0/pool/client/add`<br>

JSON Request Body

| Параметр | Тип    | Обязательно | Описание                                       |
|----------|--------|:-----------:|------------------------------------------------|
| id       | number |     Да      | id клиента                                     |
| name     | string |     Да      | Имя клиента                                    |
| phone    | string |     Да      | Телефон клиента в формате 11 чисел с/без знака |
| email    | string |     Да      | Email клиента в формате `*@*.*`                |

**Response**

```
200 OK
```

### Обновить данные клиента

`POST /api/v0/pool/client/update`<br>

JSON Request Body

| Параметр | Тип    | Обязательно | Описание                                             |
|----------|--------|:-----------:|------------------------------------------------------|
| id       | number |     Да      | id клиента                                           |
| name     | string |     Да      | Новое имя клиента                                    |
| phone    | string |     Да      | Новый телефон клиента в формате 11 чисел с/без знака |
| email    | string |     Да      | Новый email клиента в формате `*@*.*`                |

**Response**

```
200 OK
```

## TimeTable

Управление записями на бассейн

---

### Получить занятые записи на определенную дату

`GET /api/v0/pool/timetable/all/{date}`<br>

PathVariable

| Параметр | Тип    |  Обязательно  | Описание                            |
|----------|--------|:-------------:|-------------------------------------|
| date     | string |      Да       | Дата записей в формате `yyyy-MM-dd` |


JSON Response Body

| Параметр | Тип    | Описание                                       |
|----------|--------|------------------------------------------------|
| time     | string | Время записи                                   |
| count    | number | Количество занятых записей                     |

**Response**

```json
[
  {
    "time": "12:00",
    "count": 3
  },
  {
    "time": "13:00",
    "count": 3
  },
  {
    "time": "14:00",
    "count": 3
  }
]
```

### Получить доступные записи на определенную дату

`GET /api/v0/pool/timetable/available/{date}`<br>

PathVariable

| Параметр | Тип    |  Обязательно  |   Описание                          |
|----------|--------|:-------------:|-------------------------------------|
| date     | string |      Да       | Дата записей в формате `yyyy-MM-dd` |


JSON Response Body

| Параметр | Тип    | Описание                     |
|----------|--------|------------------------------|
| time     | string | Время записи                 |
| count    | number | Количество свободных записей |

**Response**

```json
[
  {
    "time": "12:00",
    "count": 7
  },
  {
    "time": "13:00",
    "count": 7
  },
  {
    "time": "14:00",
    "count": 7
  },
  {
    "time": "15:00",
    "count": 10
  },
  {
    "time": "16:00",
    "count": 10
  },
  {
    "time": "17:00",
    "count": 10
  }
]
```

### Добавить запись клиента на определенное время

`POST /api/v0/pool/timetable/reserve`<br>

JSON Request Body

| Параметр  | Тип     |  Обязательно  | Описание                                         |
|-----------|---------|:-------------:|--------------------------------------------------|
| clientId  | number  |      Да       | id клиента                                       |
| dateTime  | string  |      Да       | Дата и время записи в формате `yyyy-MM-dd HH:mm` |
| duration  | number  |      Нет      | Длительность записи                              |

JSON Response Body

| Параметр | Тип    | Описание            |
|----------|--------|---------------------|
| orderId  | number | id созданной записи |

**Response**

```json
{
    "orderId": 5    
}
```


### Отменить запись клиента

`POST /api/v0/pool/timetable/cancel`<br>

JSON Request Body

| Параметр | Тип    |  Обязательно  | Описание   |
|----------|--------|:-------------:|------------|
| clientId | number |      Да       | id клиента |
| orderId  | number |      Да       | id записи  |

**Response**

```
200 OK
```

## Holiday

Управление праздничными днями

---

### Получить список праздников

`GET /api/v0/pool/holiday/all`<br>

JSON Response Body

| Параметр | Тип    | Описание                              |
|----------|--------|:--------------------------------------|
| id       | number | id праздника                          |
| date     | string | Дата праздника в формате `yyyy-MM-dd` |

**Response**

```json
[
  {
    "id": 1,
    "date": "2022-06-11"
  },
  {
    "id": 2,
    "date": "2022-06-15"
  }
]
```

### Добавить праздник

`POST /api/v0/pool/holiday/add`<br>

JSON Request Body

| Параметр | Тип    | Обязательно | Описание                              |
|----------|--------|:-----------:|---------------------------------------|
| date     | string |     Да      | Дата праздника в формате `yyyy-MM-dd` |

JSON Response Body

| Параметр | Тип    | Описание                                       |
|----------|--------|------------------------------------------------|
| id       | number | id клиента                                     |
| name     | string | Имя клиента                                    |
| phone    | string | Телефон клиента в формате 11 чисел с/без знака |
| email    | string | Email клиента в формате `*@*.*`                |

**Response**

```
200 OK
```

### Удалить праздник

`POST /api/v0/pool/holiday/remove`<br>

JSON Request Body

| Параметр | Тип    | Обязательно | Описание                              |
|----------|--------|:-----------:|---------------------------------------|
| date     | string |     Да      | Дата праздника в формате `yyyy-MM-dd` |

**Response**

```
200 OK
```
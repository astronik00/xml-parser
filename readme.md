# Условие задачи
Написать xml-парсер для файла заданной структуры. 
Накладываемые ограничения:
- дубли по идентификатору должны быть проигнорированы

# Что было сделано?
1. Чтобы исключить дубликаты по id, был использован HashMap
2. В качестве XML парсера был выбран StAX из-за его скорости
3. Чтобы обеспечить скорость вставки данных в БД, данные разбиваются на батчи, которые 
заливаются одним коммитом

Была создана модель Product со следующими полями:

|  имя  |   тип   | обязателен |
|:-----:|:-------:|:----------:|
|  id   | Integer |     да     |
| name  | String  |     да     |
| type  | String  |     да     |
| price | Double  |     да     |

# Перед запуском
Для запуска программы необходимы два файла, расположенные в
папке [resources](src/main/resources). 

Файл [properties.json](src/main/resources/properties.json) является конфигурационном файлом в приложении, 
в котором расположены настройки параметров подключения к СУБД,
а также размер батча загрузки данных:

```json
{
  "url": "your_connection_string",
  "user": "your_user_name",
  "password": "your_user_password",
  "batch_size": "integer_value"
}
```

Файл [products.xml](src/main/resources/products.xml) является целевым файлом, 
который будет выбран в качестве объекта для парсинга. 
Должен иметь валидный XML формат.



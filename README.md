# Fractal Flame

Генератор фракталов «фрактальное пламя» (fractal flame) на Java. Программа строит изображение по аффинным преобразованиям и нелинейным вариациям и сохраняет его в PNG. Параметры можно передать аргументами командной строки или JSON-файлом.

Домашнее задание 5 по Java.

## Возможности

- Аффинные преобразования, заданные коэффициентами `a, b, c, d, e, f`.
- Нелинейные вариации с весами: `linear`, `sinusoidal`, `swirl`, `horseshoe`, `heart`.
- Симметрия заданного порядка (`--symmetry-level`).
- Многопоточный расчёт (`--threads`).
- Конфигурация из JSON с проверкой значений.
- Вывод в PNG.
- Бенчмарк времени генерации (`BenchmarkRunner`, результаты в `benchmark_results.csv`).

## Аргументы командной строки

| Аргумент | Краткая форма | Описание |
|----------|---------------|----------|
| `--width` | `-w` | Ширина изображения |
| `--height` | `-h` | Высота изображения |
| `--iteration-count` | `-i` | Количество итераций |
| `--output-path` | `-o` | Путь к выходному PNG |
| `--threads` | `-t` | Количество потоков |
| `--symmetry-level` | `-s` | Уровень симметрии |
| `--seed` | — | Зерно генератора случайных чисел |
| `--functions` | `-f` | Вариации в формате `имя:вес,имя:вес` |
| `--affine-params` | `-ap` | Аффинные преобразования: коэффициенты `a,b,c,d,e,f`, преобразования разделяются `/` |
| `--config` | — | Путь к JSON-конфигурации |

## Примеры

```bash
mvn clean package

# Базовое изображение
java -jar target/fractal-flame-1.0-SNAPSHOT.jar -w 1024 -h 768 -i 5000000 -o pic1_basic.png \
  -f swirl:1.0,horseshoe:0.8 \
  -ap "0.5,0,0,0,0.5,0/0.5,0,0.5,0,0.5,0/0.5,0,0,0,0.5,0.5/0.5,0,0.5,0,0.5,0.5"

# Через JSON-конфигурацию
java -jar target/fractal-flame-1.0-SNAPSHOT.jar --config config_pic5.json
```

## Пример JSON-конфигурации

Файл `config_pic5.json` в корне репозитория:

```json
{
  "width": 1920,
  "height": 1080,
  "iterationCount": 15000000,
  "outputPath": "pic5_from_json.png",
  "threads": 6,
  "seed": 987654,
  "symmetryLevel": 5,
  "functions": [
    { "name": "swirl", "weight": 1.0 },
    { "name": "horseshoe", "weight": 1.0 },
    { "name": "sinusoidal", "weight": 0.5 },
    { "name": "linear", "weight": 0.2 },
    { "name": "heart", "weight": 0.8 }
  ],
  "affineParams": [
    { "a": 0.7, "b": 0.1, "c": -0.1, "d": 0.1, "e": 0.7, "f": 0.0 },
    { "a": 0.5, "b": -0.5, "c": 0.0, "d": 0.5, "e": 0.5, "f": 0.0 },
    { "a": 0.2, "b": 0.8, "c": 0.1, "d": -0.8, "e": 0.2, "f": 0.1 }
  ]
}
```

## Результаты

В репозитории лежат готовые изображения: `pic1_basic.png`, `pic2_sym6.png`, `pic3_heart.png`, `pic4_horseshoe.png`, `pic5_from_json.png`.

## Структура

```
src/main/java/org/example/
├── Main.java                 точка входа
├── FlameApplication.java     запуск генерации
├── FractalFlameGenerator.java расчёт точек
├── AffineParams.java         аффинные коэффициенты
├── Point.java
├── ConfigLoader.java         аргументы командной строки и JSON
├── ConfigValidator.java      проверка конфигурации
├── ParamParser.java          разбор строковых параметров
├── ImageRenderer.java        запись PNG
├── BenchmarkRunner.java      замеры времени
└── variations/               вариации и фабрика VariationFactory
src/test/                     тесты
```

## Сборка и тесты

```bash
mvn clean package
```

Тесты лежат в `src/test/java/org/example`.

## Требования

- JDK 17 или новее.
- Maven.

## Автор

Мария Комарова — домашнее задание 5 по Java.

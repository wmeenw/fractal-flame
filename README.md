# Fractal flame

Домашнее задание 5 курса по Java. Генератор фракталов «фрактальное пламя»: строит изображение по аффинным преобразованиям и нелинейным вариациям и сохраняет его в PNG.

## Что сделано в качестве ДЗ

- Генерация точек по аффинным преобразованиям (`FractalFlameGenerator`, `AffineParams`).
- Набор вариаций: линейная, синусоидальная, swirl, heart, horseshoe и другие (`variations/`), создание через `VariationFactory`.
- Загрузка конфигурации из JSON с проверкой (`ConfigLoader`, `ConfigValidator`, `ParamParser`).
- Рендер изображения в PNG (`ImageRenderer`).
- Замеры производительности (`BenchmarkRunner`, `benchmark_results.csv`).
- Юнит- и интеграционные тесты (`src/test`).

Примеры результатов: `pic1_basic.png` … `pic5_from_json.png`, конфигурация `config_pic5.json`.

## Сборка и запуск

```bash
mvn clean package
```

Исходный код: ветка `homework5` репозитория [wmeenw/JAVAhomework](https://github.com/wmeenw/JAVAhomework).

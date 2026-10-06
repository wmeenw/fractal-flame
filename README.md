# Домашнее задание 5 — фрактальное пламя

Генератор фракталов «фрактальное пламя» (fractal flame). Программа строит изображение по аффинным преобразованиям и нелинейным вариациям и сохраняет его в PNG.

- `FractalFlameGenerator`, `Point`, `AffineParams` — расчёт точек.
- `variations/` — вариации: `Linear`, `Sinusoidal`, `Swirl`, `Heart`, `Horseshoe` и другие.
- `ConfigLoader`, `ConfigValidator`, `ParamParser` — загрузка и проверка конфигурации из JSON.
- `ImageRenderer` — вывод изображения.
- `BenchmarkRunner` — замеры времени (`benchmark_results.csv`).
- Примеры результатов: `pic1_basic.png` … `pic5_from_json.png`, конфигурация `config_pic5.json`.

## Сборка и запуск

```bash
mvn clean package
```

Тесты лежат в `src/test/java/org/example`.

Исходный код взят из ветки `homework5` репозитория [wmeenw/JAVAhomework](https://github.com/wmeenw/JAVAhomework).

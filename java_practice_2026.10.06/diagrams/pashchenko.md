# Фамилия Имя

Скопируйте файл в `diagrams/Фамилия.md`. Шпаргалка по синтаксису: `mermaid.md`.

## 1. Диаграмма классов: как есть

```mermaid
classDiagram
    class Notifier {
        +send(channelType, message) boolean
    }
```

## 2. Последовательность: отправка SMS с повтором

```mermaid
sequenceDiagram
    participant M as Main
    participant N as Notifier
    M->>N: send("sms", message)
```

## 3. Диаграмма классов: после рефакторинга

Только та часть, которую вы меняли.

```mermaid
classDiagram
    class Channel
```

## 4. Где паттерн окупился, а где был бы лишним

(трек «опытный»)

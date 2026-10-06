# Фамилия Имя

Скопируйте файл в `diagrams/Фамилия.md`. Шпаргалка по синтаксису: `mermaid.md`.

## 1. Диаграмма классов: как есть

```mermaid
classDiagram
    class Notifier {
        +send(channelType, message) boolean
        +notifyGroup(notifier, channelType, group, text) void 
    }
    class Message {
        +text() String
    }
    class AppConfig {
        +getInstance() String
    }
    class LegacySmsGateway {
        +transmit(msisdn, payload)() int
    }
    class Main {
        +main(args) void
    }
    Main ..> Notifier : использует
    Main ..> Message : использует
    Main ..> AppConfig : использует
    Notifier ..> LegacySmsGateway : использует
    Notifier ..> AppConfig : использует
```

## 2. Последовательность: отправка SMS с повтором

```mermaid
sequenceDiagram
    participant M as Main
    participant N as Notifier
    participant G as LegacySmsGateway

    M->>N: send("sms", message)
    loop пока не доставлено, не больше retries+1 раз
        N->>G: transmit(digits, payload)
        alt доставлено
            G-->>N: 200
        else провайдер недоступен
            G-->>N: 503
        end
    end
    N-->>M: true
```

`->>` вызов, `-->>` ответ, `loop` повтор, `alt` / `else` развилка.

## 3. Диаграмма классов: после рефакторинга

Только та часть, которую вы меняли.

```mermaid
classDiagram
    class Channel {
        <<interface>>
        +send(message) boolean
    }
    class EmailChannel {
        -config
        +send(message) boolean
    }
    class TelegramChannel {
        -config
        +send(message) boolean
    }
    class SmsChannel {
        -config
        +send(message) boolean
    }
    class LogDecorateChannel {
        -config
        -channel
        +send(message) boolean
    }
    class RetriesDecorateChannel {
        -config
        -channel
        +send(message) boolean
    }
    class Notifier {
        +send(channelType, message) boolean
    }
    class Message {
        +text()
        +build()
    }

    Channel <|.. EmailChannel : реализует
    Channel <|.. TelegramChannel : реализует
    Channel <|.. SmsChannel : реализует
    Channel <|.. LogDecorateChannel : реализует
    Channel <|.. RetriesDecorateChannel : реализует
    Notifier --> Channel : использует
```

## 4. Где паттерн окупился, а где был бы лишним

(трек «опытный»)

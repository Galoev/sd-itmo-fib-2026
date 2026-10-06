# Фамилия Имя

Скопируйте файл в `diagrams/Фамилия.md`. Шпаргалка по синтаксису: `mermaid.md`.

## 1. Диаграмма классов: как есть

```mermaid
classDiagram
    class Message {
        +to String 
        +subject String 
        +body String 
        +cc String 
        +priority String 
        +silent boolean
        +retries Integer
        +template String 
        +signature boolean
        +footer String 
        +text() String
    }

    class AppConfig {
        +senderEmail String
        +maxRetries unt
        +logEnabled boolean
        +sentCount int
        +getInstance() AppConfig
    }

    class Notifier {
        +send(String ChannelType, Message message) boolean
        +notifyGroup(Notifier notifier, String channelType, Object group, String text) void
    }

    class Main {
        +main(String[] args) void
    }

    Notifier ..> AppConfig : использует
    Notifier ..> Message : использует
    Main ..> Notifier : использует
    Main ..> AppConfig : использует
    Main ..> Message : использует
```

## 2. Последовательность: отправка SMS с повтором

```mermaid
sequenceDiagram
    participant M as Main
    participant N as Notifier
    participant G as LegacySmsGateway
    participant A as AppConfig
    participant O as System.out

    M->>N: send("sms", message)
    loop пока не доставлено, не больше retries+1 раз

        N->>O: println("Log...")
        N->>G: transmit(digits, payload)
        alt доставлено
            G-->>N: 200
            N-->>M: true
        else провайдер недоступен
            G-->>N: 503
            N-->>M: false
        end
    end
    N-->>M: true
```

## 3. Диаграмма классов: после рефакторинга

Только та часть, которую вы меняли.

```mermaid
classDiagram
    class Channel
```

## 4. Где паттерн окупился, а где был бы лишним

(трек «опытный»)
az
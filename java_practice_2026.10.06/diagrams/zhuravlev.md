# Фамилия Имя

Скопируйте файл в `diagrams/Фамилия.md`. Шпаргалка по синтаксису: `mermaid.md`.

## 1. Диаграмма классов: как есть

```mermaid
classDiagram
    class Notifier {
        +send(String, Message message) boolean
        +notifyGroup(Notifier notifier, String channelType, Object group, String text) void
    }

    class Message {
        +Message(String to, String subject, String body)
        +Message(String to, String subject, String body, String cc, String priority)
        +Message(String to, String subject, String body, String cc, String priority, boolean silent, Integer retries, String template, boolean signature, String footer)
        +text() String
    }

    class AppConfig {
        +AppConfig getInstance()
    }

    class LegacySmsGateway {
        +transmit(String msisdn, byte[] payload) int
    }

    class Main {
        +main(String[] args) void
    }

    Notifier ..> AppConfig : uses
    Notifier ..> LegacySmsGateway : creates & uses
    Notifier ..> Message : uses
    Main ..> AppConfig : uses
    Main ..> Notifier : creates & uses
    Main ..> Message : creates
```

## 2. Последовательность: отправка SMS с повтором

```mermaid
sequenceDiagram
    actor M as Main
    participant N as Notifier
    participant C as AppConfig
    participant G as LegacySmsGateway
    
    M->>N: send("sms")
    activate N

    N->>C: getInstance()
    activate C
    C-->>N: config
    deactivate C

    N->>G: new LegacySmsGateway()

    N->>G: transmit()
    activate G
    G-->>N: httpCode (200)
    deactivate G

    N->>G: transmit()
    activate G
    G-->>N: httpCode (503)
    deactivate G

    N->>C: sentCount += 1
    N->>M: true
    
    deactivate N
```


## 3. Диаграмма классов: после рефакторинга

Только та часть, которую вы меняли.

```mermaid
classDiagram
    class Channel {
        <<interface>>
        +send(Message message) boolean
        }

    class EmailChannel {
        +send(Message message) boolean
    }

    class SmsChannel {
        +send(Message message) boolean
    }
```

## 4. Где паттерн окупился, а где был бы лишним

(трек «опытный»)

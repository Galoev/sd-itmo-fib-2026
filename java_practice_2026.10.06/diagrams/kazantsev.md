# Шпаргалка по Mermaid

Диаграмма пишется в блоке кода с языком `mermaid`. Предпросмотр в VS Code: расширение `bierner.markdown-mermaid`, затем `Cmd/Ctrl+Shift+V`.

## Диаграмма классов

```mermaid
classDiagram
    class Main
    class Notifier
    class AppConfig
    class Message
    class LegacySmsGateway
    class System.out

    Main --> Notifier
    Main --> Message
    Notifier --> AppConfig
    Notifier --> Message
    Notifier --> LegacySmsGateway
    Notifier --> System.out

```

| Связь | Как пишется | Что значит |
|---|---|---|
| Наследование | `Base <\|-- Child` | Child это Base |
| Реализация интерфейса | `Interface <\|.. Impl` | Impl выполняет контракт Interface |
| Композиция | `Owner *-- Part` | Part живет внутри Owner и без него не существует |
| Агрегация | `Group o-- Member` | Group содержит Member, но Member живет и сам |
| Ассоциация | `A --> B` | A хранит ссылку на B |
| Зависимость | `A ..> B` | A пользуется B (параметр, создание) |

Видимость: `+` публичное, `-` приватное. Подпись связи после двоеточия: `A --> B : создает`.

## Диаграмма последовательности

```mermaid
sequenceDiagram
    autonumber
    actor Main
    participant Notifier
    participant AppConfig
    participant LegacySmsGateway
    participant System.out

    Main->>Notifier: send(...) x2 + notifyGroup(...) + send(...)
    Notifier->>AppConfig: getInstance()
    Notifier->>System.out: email, sms, telegram
    Notifier->>LegacySmsGateway: transmit(503, затем 200)
    Notifier->>AppConfig: sentCount++
    Notifier-->>Main: true

```

`->>` вызов, `-->>` ответ, `loop` повтор, `alt` / `else` развилка.

## Компонентная диаграмма

В Mermaid нет отдельного типа, используем `flowchart`:

```mermaid
flowchart TB
    subgraph App["Интернет-магазин (уведомления)"]
        Main["Main<br/>сценарий заказа"]
        Notifier["Notifier<br/>отправка по каналам"]
        Message["Message<br/>текст + шаблон"]
        AppConfig["AppConfig<br/>singleton-настройки"]
    end

    subgraph External["Внешнее (чужой код)"]
        Gateway["LegacySmsGateway<br/>SMS-провайдер"]
    end

    Out["System.out<br/>консоль"]

    Main --> Notifier
    Main --> Message
    Notifier --> Message
    Notifier --> AppConfig
    Notifier --> Gateway
    Notifier --> Out
    Gateway --> Out
```

Стрелка значит «использует». `LR` слева направо, `TD` сверху вниз.

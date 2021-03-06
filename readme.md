# Практические задания к лекции "Введение в тестирование"

Задания для самостоятельного выполнения находятся в директории `/workshop`

### О приложении

Простое приложение построенное на API https://todoist.com/. 

- Загружает списки проектов и задач. 
- Отображает задачи для выбранного проекта.

![Project navigation](workshop/images/readme-sample.png)

### Переключение аккаунта

По умолчанию, приложение использует общий демо аккаунт. Для переключения аккаунта, замените значение константы `API_KEY`. 
```kotlin
class ApiKeyInterceptor: Interceptor {
    companion object {
        private const val API_KEY = "<ваш токен>"
    }
    ...
}
```

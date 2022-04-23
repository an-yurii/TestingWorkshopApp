# Workshop 1

В рамках воркшопа вы создадите пару тестов для `TaskMapper`

## Добавить тест класс

- Найти класс `TaskMapper`

    Вы можете воспользоваться возможностями меню **Navigate**

    ![Project navigation](images/navigation-tip.png)

- Установить курсор на название класса

    ![Project navigation](images/situate-on-class-name.png)
  
- В меню **Navigate** выбрать пункт **Test** (или нажать на желтую ламочку рядом с названием класса)

    ![Project navigation](images/navigate-test.png)

- Выбрать **Create new test**

    ![Project navigation](images/create-new-test.png)

- Выбрать в появившемся окне библиотеку `JUnit4`

    ![Project navigation](images/choose-junit4.png)

- Выбрать директорию `/test/` (директория для модульных тестов)

    ![Project navigation](images/choose-test-directory.png)

- Добавить пустое тело класса `TaskMapperTest`
    ```kotlin
    class TaskMapperTest {
    
    }
    ```

## Тест маппинга полей

- Добавить пустой метод в класс `TaskMapperTest`
    ```kotlin
    @Test
    fun `responseToTask by default returns Task item`() {

    }
    ```

- Создать инстанс `TaskResponse` внутри добавленного метода
    ```kotlin
    @Test
    fun `responseToTask by default returns Task item`() {
        val taskResponse = TaskResponse(id = 1L, projectId = 2L, content = "Test item", order = 5, priority = 2)
    }
    ```

- Добавить вызов маппера
    ```kotlin
    @Test
    fun `responseToTask by default returns Task item`() {
        val taskResponse = ...

        val task = TaskMapper.responseToTask(taskResponse)
    }
    ```

- Воспользоваться методом `assertEquals([expected], [actual])` для проверки параметров объекта `task`
    ```kotlin
    @Test
    fun `responseToTask by default returns Task item`() {
        ...
        val task = ...
  
        assertEquals(1L, task.id)
        assertEquals(2L, task.projectId)
        assertEquals("Test item", task.title)
        assertEquals(5, task.order)
        assertEquals(2, task.priority)
    }
    ```

- Запустить тест

    ![Project navigation](images/launch-test.png)

- Убедиться, что тест выполнен успешно

    ![Project navigation](images/launch-result.png)

## Тест выбора цвета

- Создать фабричный метод `createTaskResponse(...)`
    ```kotlin
    fun createTaskResponse(
        id: Long = 0L,
        projectId: Long = 0L,
        content: String = "",
        order: Int = 0,
        priority: Int = 0
    ): TaskResponse {
        return TaskResponse(
            id = id,
            projectId = projectId,
            content = content,
            order = order,
            priority = priority
        )
    }
    ```

- Заменить в тесте вызов конструктора на вызов фабричного метода
    ```kotlin
    @Test
    fun `responseToTask by default returns Task item`() {
        val taskResponse = createTaskResponse(
            id = 1L, projectId = 2L, content = "Test item", order = 5, priority = 2
        )
        ...
    ```

- Добавить еще один пустой метод
    ```kotlin
    @Test
    fun `responseToTask for priority 4 returns color orange`() {

    }
    ```

- Создать инстанс `TaskResponse` внутри добавленного метода

    При вызове фабричного метода достаточно указать только необходимый параметр.

    ```kotlin
    @Test
    fun `responseToTask for priority 4 returns color orange`() {
        val taskResponse = createTaskResponse(priority = 4)
    }
    ```

- Добавить вызов маппера
    ```kotlin
    @Test
    fun `responseToTask for priority 4 returns color orange`() {
        val taskResponse = ...

        val task = TaskMapper.responseToTask(taskResponse)
    }
    ```

- Воспользоваться методом `assertEquals([expected], [actual])` для проверки параметра `task.colorRes`
    ```kotlin
    @Test
    fun `responseToTask for priority 4 returns color orange`() {
        ...
        val task = ...

        assertEquals(R.color.orange, task.colorRes)
    }
    ```

- Запустить тесты для класса `TaskMapperTest`

    Оба теста должны выполниться успешно
    - `responseToTask by default returns Task item`
    - `responseToTask for priority 4 returns color orange`

## Протестировать тест

- Найти класс `Palette` и изменить возвращаемое значение в методе `getColorByPriority`
    ```kotlin
    fun getColorByPriority(priority: Int): Int {
        return when (priority) {
            4 -> R.color.orange
            ...
        }
    }
    ```

- Запустить тесты для класса `TaskMapperTest`

   Тест `responseToTask for priority 4 returns color orange` должен завершиться с ошибкой

- Не забудте вернуть код в рабочее состояние)

# Summary

В результате у вас должно получиться что-то похожее на:

```kotlin
package ru.yurii.testingworkshopapp.data

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.yurii.testingworkshopapp.R
import ru.yurii.testingworkshopapp.data.api.TaskResponse

class TaskMapperTest {

    @Test
    fun `responseToTask by default returns Task item`() {
        val taskResponse = createTaskResponse(
            id = 1L, projectId = 2L, content = "Test item", order = 5, priority = 2
        )

        val task = TaskMapper.responseToTask(taskResponse)

        assertEquals(1L, task.id)
        assertEquals(2L, task.projectId)
        assertEquals("Test item", task.title)
        assertEquals(5, task.order)
        assertEquals(2, task.priority)
    }

    @Test
    fun `responseToTask for priority 4 returns color orange`() {
        val taskResponse = createTaskResponse(priority = 4)

        val task = TaskMapper.responseToTask(taskResponse)

        assertEquals(R.color.orange, task.colorRes)
    }

    fun createTaskResponse(
        id: Long = 0L,
        projectId: Long = 0L,
        content: String = "",
        order: Int = 0,
        priority: Int = 0
    ): TaskResponse {
        return TaskResponse(
            id = id,
            projectId = projectId,
            content = content,
            order = order,
            priority = priority
        )
    }
}
```

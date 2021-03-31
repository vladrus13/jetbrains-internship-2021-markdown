# Создатель заголовков

## Условие задачи

Вам нужно написать генератор оглавления для markdown файлов.

Это должна быть command-line тула на Java, которая принимает на вход путь к markdown файлу, добавляет к нему в начало оглавление и выводит результат в standard output.

Нельзя пользоваться библиотеками для генерации и парсинга markdown.

В качестве результата отправьте ссылку на репозиторий на github или bitbucket.

Input:

```
# My Project
## Idea
content
## Implementation
### Step 1
content
### Step 2
content
```
Output:

```
1. [My Project](#my-project)
    1. [Idea](#idea)
    2. [Implementation](#implementation)
        1. [Step 1](#step-1)
        2. [Step 2](#step-2)

# My Project
## Idea
content
## Implementation
### Step 1
content
### Step 2
content
```

## Решение задачи

Решение выполнено на языке Java c использованием системы сборки maven.

В [папке](src/main/resources) есть несколько примеров md файлов, на которых можно запустить программу (осторожно, некоторые из них занимают больше 1000 строк, и на вывод может попасть очень много строк, что вызовет зависание у компьютера).

Первым и единственным аргументом программы должен быть путь до файла (относительный или абсолютный).

Пример запуска: 

```
mvn package exec:java -Dexec.args=src/main/resources/input.md
```
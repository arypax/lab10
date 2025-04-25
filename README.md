# Email Service

Сервис для управления email рассылками и записью студентов на курсы.

## Требования

- Java 17
- Maven
- Docker и Docker Compose
- PostgreSQL

## Установка и запуск

1. Клонируйте репозиторий:
```bash
git clone <repository-url>
cd email-service
```

2. Создайте файл `.env` в корневой директории проекта:
```env
SMTP_USERNAME=your-email@gmail.com
SMTP_PASSWORD=your-smtp-password
```

3. Соберите проект:
```bash
mvn clean package
```

4. Запустите с помощью Docker Compose:
```bash
docker-compose up -d
```

## Использование

После запуска приложение будет доступно по адресу: http://localhost:8080

Swagger UI доступен по адресу: http://localhost:8080/swagger-ui.html

## API Endpoints

- POST /api/enrollments - Запись студента на курс
- POST /api/email/send - Отправка одиночного email
- POST /api/email/mass - Массовая рассылка

## Безопасность

- Все чувствительные данные хранятся в переменных окружения
- Файл .env не включен в репозиторий
- Используется HTTPS для SMTP 
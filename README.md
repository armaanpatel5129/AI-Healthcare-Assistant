# AI-Driven Healthcare Assistant

Full-stack Java project with a Spring Boot REST API, MySQL storage, and a
vanilla HTML/CSS/JavaScript frontend.

## Requirements

- Java 17 or newer
- Maven 3.9+
- MySQL 8+
- VS Code Live Server extension (or another local static web server)

## Run locally (Windows)

### 1. Set up MySQL

Install and start MySQL Server 8 or newer. In MySQL Workbench, connect to your
local server, open `database/schema.sql`, and execute the whole script (lightning
bolt). It creates the `healthcare_db` database and all required tables. You can
also run the script from PowerShell if the MySQL client is on `PATH`:

```powershell
mysql -u root -p < database/schema.sql
```

### 2. Start the Spring Boot backend

Install Java 17+ and Maven 3.9+. Open a VS Code terminal at the repository root
and run:

```powershell
cd backend
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "your-local-mysql-password"
$env:JWT_SECRET = "replace-with-a-random-secret-at-least-32-characters"
mvn spring-boot:run
```

Replace the username and password with your local MySQL credentials. Keep this
terminal open while using the app. The API starts at
`http://localhost:8080/api`; Spring connects to
`jdbc:mysql://localhost:3306/healthcare_db` by default.

### 3. Start the frontend

In VS Code, install the **Live Server** extension if needed. Right-click
`frontend/index.html` and select **Open with Live Server**. Keep the default
port 5500. Register an account, sign in, and use the dashboard. The frontend
needs to be served over HTTP; do not open the HTML file directly with `file://`.

If Live Server uses a different port, add its full origin to
`FRONTEND_ORIGINS` before starting the backend, for example:

```powershell
$env:FRONTEND_ORIGINS = "http://localhost:5500,http://127.0.0.1:5500"
```

The backend uses `spring.jpa.hibernate.ddl-auto=validate`: it checks the SQL
schema but does not silently create or modify database tables. Configure
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, and optionally
`FRONTEND_ORIGINS` as environment variables when deploying. The built-in JWT
secret is only for local development and must be replaced before deployment.

## API overview

All endpoints except registration and login require
`Authorization: Bearer <token>`.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Create account and sign in |
| `POST` | `/api/auth/login` | Sign in |
| `GET` | `/api/auth/me` | Get signed-in account |
| `GET`, `PUT` | `/api/profile` | Read or update health profile |
| `GET` | `/api/dashboard` | Get dashboard totals |
| `POST`, `GET`, `DELETE` | `/api/analyses` | Analyze symptoms, read history, or clear it |

API errors use a JSON response with a `message` field. This application offers
general educational guidance only; it does not diagnose or replace a healthcare
professional.

## Test

Run the backend unit and API integration tests with `cd backend; mvn test`.

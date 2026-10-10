# HealthAI Frontend

Frontend for **AI-Driven Healthcare Assistant**.

## Tech
- HTML5
- CSS3
- Vanilla JavaScript
- localStorage for frontend demo data

## Run
Open `index.html` in a browser, or use VS Code Live Server.

## Pages
- `index.html` — Login
- `register.html` — Registration
- `dashboard.html` — Dashboard
- `profile.html` — Health profile
- `symptoms.html` — Symptom input
- `analysis.html` — Analysis result
- `history.html` — Saved history

## Backend integration
The current frontend uses localStorage so Plan C can be developed independently.

When the Spring Boot backend is ready, replace the demo/localStorage logic with `fetch()` calls such as:

```js
fetch("http://localhost:8080/api/auth/login", {
  method: "POST",
  headers: {"Content-Type":"application/json"},
  body: JSON.stringify({email, password})
});
```

Coordinate the exact endpoint names and JSON fields with the Plan A teammate.

## Important
This project provides general educational health guidance and is not a medical diagnosis system.

# HealthAI Frontend

Frontend for **AI-Driven Healthcare Assistant**.

## Tech
- HTML5
- CSS3
- Vanilla JavaScript
- Spring Boot REST API for account and healthcare data
- localStorage only for the login token and current analysis display

## Run
Start MySQL and the backend using the instructions in the repository root README.
Then serve the repository's `frontend/` folder with VS Code Live Server on port
5500 and open `index.html`. Do not open the HTML file directly (`file://`), because
the API uses browser CORS protections.

## Pages
- `index.html` - Login
- `register.html` - Registration
- `dashboard.html` - Dashboard
- `profile.html` - Health profile
- `symptoms.html` - Symptom input
- `analysis.html` - Analysis result
- `history.html` - Saved history

## Backend integration
The frontend is connected to `http://localhost:8080/api`. Update `js/api.js` if
the backend is hosted elsewhere. The backend allows local Live Server origins
on ports 5500 and 5501 by default.

## Important
This project provides general educational health guidance and is not a medical diagnosis system.

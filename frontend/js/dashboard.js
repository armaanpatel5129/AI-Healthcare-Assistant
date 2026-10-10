function getUser() {
  return JSON.parse(localStorage.getItem("healthUser") || '{"name":"Guest User","email":""}');
}

function requireAuth() {
  if (!localStorage.getItem("healthUser")) {
    location.href = "index.html";
  }
}

function logout() {
  localStorage.removeItem("healthUser");
  location.href = "index.html";
}

function nav(active) {
  return `
  <aside class="sidebar">
    <div class="brand"><div class="logo">✚</div><b>Health<span>AI</span></b></div>
    <nav class="nav">
      <a class="${active === "dashboard" ? "active" : ""}" href="dashboard.html"><span class="nav-icon">▦</span><span>Dashboard</span></a>
      <a class="${active === "profile" ? "active" : ""}" href="profile.html"><span class="nav-icon">☺</span><span>Health Profile</span></a>
      <a class="${active === "symptoms" ? "active" : ""}" href="symptoms.html"><span class="nav-icon">✚</span><span>Analyze Symptoms</span></a>
      <a class="${active === "history" ? "active" : ""}" href="history.html"><span class="nav-icon">◷</span><span>History</span></a>
    </nav>
    <button class="btn danger logout" onclick="logout()"><span>Logout</span></button>
  </aside>`;
}

function topbar(title) {
  const u = getUser();
  const initial = (u.name || "G").charAt(0).toUpperCase();
  return `
  <div class="topbar">
    <h1>${title}</h1>
    <div class="user-pill"><span class="avatar">${initial}</span>${u.name}</div>
  </div>`;
}

requireAuth();
const u = getUser();
const firstName = (u.name || "there").split(" ")[0];

document.getElementById("app").innerHTML = nav("dashboard") + `
<main class="main">
  ${topbar("Good day, " + firstName)}

  <div class="hero">
    <div>
      <h2>Take care of your health.</h2>
      <p>Describe your symptoms and get general health guidance and a simple risk category.</p>
    </div>
    <div class="hero-art">✚</div>
  </div>

  <div class="grid">
    <div class="card"><div class="stat-icon">🩺</div><div class="stat-number" id="analyses">0</div><div class="stat-label">Analyses done</div></div>
    <div class="card"><div class="stat-icon">📋</div><div class="stat-number" id="records">0</div><div class="stat-label">Health profile saved</div></div>
    <div class="card"><div class="stat-icon">👤</div><div class="stat-number">100%</div><div class="stat-label">Private to you</div></div>
    <div class="card"><div class="stat-icon">💚</div><div class="stat-number">Active</div><div class="stat-label">Account status</div></div>
  </div>

  <div class="section">
    <div class="section-title"><h3>Quick actions</h3></div>
    <div class="quick-grid">
      <a class="card quick" href="symptoms.html"><div class="qicon">🩺</div><div><h4>Analyze symptoms</h4><p>Get general guidance</p></div></a>
      <a class="card quick" href="profile.html"><div class="qicon">👤</div><div><h4>Update profile</h4><p>Keep your details current</p></div></a>
      <a class="card quick" href="history.html"><div class="qicon">📊</div><div><h4>View history</h4><p>See past analyses</p></div></a>
    </div>
  </div>

  <div class="section">
    <div class="card notice">⚠️ <b>Important:</b> HealthAI provides general educational guidance only. It is not a medical diagnosis. In an emergency, contact your local emergency services immediately.</div>
  </div>
</main>`;

const history = JSON.parse(localStorage.getItem("healthHistory") || "[]");
document.getElementById("analyses").textContent = history.length;
document.getElementById("records").textContent = localStorage.getItem("healthProfile") ? "1" : "0";
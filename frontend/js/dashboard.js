function getUser(){return JSON.parse(localStorage.getItem("healthUser")||'{"name":"Guest User","email":"guest@example.com"}')}
function requireAuth(){if(!localStorage.getItem("healthUser"))location.href="index.html"}
function logout(){localStorage.removeItem("healthUser");location.href="index.html"}
function nav(active){return `<aside class="sidebar"><div class="brand"><div class="logo">✚</div><b>Health<span>AI</span></b></div><nav class="nav">
<a class="${active==="dashboard"?"active":""}" href="dashboard.html"><span class="nav-icon">⌂</span><span>Dashboard</span></a>
<a class="${active==="profile"?"active":""}" href="profile.html"><span class="nav-icon">♙</span><span>Health Profile</span></a>
<a class="${active==="symptoms"?"active":""}" href="symptoms.html"><span class="nav-icon">✚</span><span>Symptom Analysis</span></a>
<a class="${active==="history"?"active":""}" href="history.html"><span class="nav-icon">◷</span><span>Health History</span></a>
</nav><button class="btn danger logout" onclick="logout()">↪ <span>Logout</span></button></aside>`}
function topbar(title){const u=getUser();return `<div class="topbar"><div><h1>${title}</h1><p class="muted">Your personal intelligent healthcare assistant</p></div><div class="user-pill"><span class="avatar">${(u.name||"G")[0].toUpperCase()}</span>${u.name||"Guest"}</div></div>`}
function saveHistory(item){const h=JSON.parse(localStorage.getItem("healthHistory")||"[]");h.unshift(item);localStorage.setItem("healthHistory",JSON.stringify(h))}

requireAuth();const u=getUser();
document.getElementById("app").innerHTML=nav("dashboard")+`<main class="main">${topbar("Good day, ${u.name.split(" ")[0]} 👋")}
<div class="hero"><div><h2>Take care of your health.</h2><p>Describe your symptoms and get general, personalized health guidance based on the information you provide.</p><a class="btn" style="background:#fff;color:#126c70;display:inline-block;margin-top:18px" href="symptoms.html">Start Analysis →</a></div><div class="hero-art">♥</div></div>
<div class="grid">
<div class="card"><div class="stat-icon">🩺</div><div class="stat-number" id="analyses">0</div><div class="stat-label">Analyses completed</div></div>
<div class="card"><div class="stat-icon">📋</div><div class="stat-number" id="records">0</div><div class="stat-label">Health records</div></div>
<div class="card"><div class="stat-icon">👤</div><div class="stat-number">100%</div><div class="stat-label">Profile progress</div></div>
<div class="card"><div class="stat-icon">💚</div><div class="stat-number">Active</div><div class="stat-label">Health journey</div></div></div>
<div class="section"><div class="section-title"><h3>Quick actions</h3></div><div class="quick-grid">
<a class="card quick" href="symptoms.html"><div class="qicon">🩺</div><div><h4>Analyze symptoms</h4><p>Check your current symptoms</p></div></a>
<a class="card quick" href="profile.html"><div class="qicon">👤</div><div><h4>Update profile</h4><p>Keep your health information updated</p></div></a>
<a class="card quick" href="history.html"><div class="qicon">📊</div><div><h4>View history</h4><p>Review previous analyses</p></div></a>
</div></div>
<div class="section"><div class="card notice">⚠️ <b>Important:</b> HealthAI provides general educational guidance and is not a substitute for professional medical diagnosis or emergency care. If symptoms are severe or life-threatening, contact emergency medical services.</div></div></main>`;
document.getElementById("analyses").textContent=JSON.parse(localStorage.getItem("healthHistory")||"[]").length;
document.getElementById("records").textContent=localStorage.getItem("healthProfile")?"1":"0";

function getUser(){return JSON.parse(localStorage.getItem("healthUser")||'{"name":"Guest User","email":"guest@example.com"}')}
function requireAuth(){if(!getAuthToken()){location.href="index.html";return false}return true}
function logout(){localStorage.removeItem("healthToken");localStorage.removeItem("healthUser");location.href="index.html"}
function nav(active){return `<aside class="sidebar"><div class="brand"><div class="logo">✚</div><b>Health<span>AI</span></b></div><nav class="nav">
<a class="${active==="dashboard"?"active":""}" href="dashboard.html"><span class="nav-icon">⌂</span><span>Dashboard</span></a>
<a class="${active==="profile"?"active":""}" href="profile.html"><span class="nav-icon">♙</span><span>Health Profile</span></a>
<a class="${active==="symptoms"?"active":""}" href="symptoms.html"><span class="nav-icon">✚</span><span>Symptom Analysis</span></a>
<a class="${active==="history"?"active":""}" href="history.html"><span class="nav-icon">◷</span><span>Health History</span></a>
</nav><button class="btn danger logout" onclick="logout()">↪ <span>Logout</span></button></aside>`}
function topbar(title){const u=getUser();return `<div class="topbar"><div><h1>${title}</h1><p class="muted">Your personal intelligent healthcare assistant</p></div><div class="user-pill"><span class="avatar">${escapeHTML((u.name||"G")[0].toUpperCase())}</span>${escapeHTML(u.name||"Guest")}</div></div>`}

requireAuth();
const result=JSON.parse(localStorage.getItem("currentAnalysis")||"null");
if(!result){
  location.href="symptoms.html";
}else{
  document.getElementById("app").innerHTML=nav("symptoms")+`<main class="main">${topbar("Analysis Result")}
  <div class="card"><div class="result-score"><div class="score-ring"><div class="score-inner"><b>!</b><small>risk</small></div></div><div><h2>General health guidance — ${escapeHTML(result.riskLevel)}</h2><p class="muted">Based on the symptoms you selected. This is not a medical diagnosis.</p><div style="margin-top:10px">${result.symptoms.map(symptom=>`<span class="tag">${escapeHTML(symptom)}</span>`).join("")}</div></div></div>
  <div class="result-box"><h4>💡 Guidance</h4><p>${escapeHTML(result.guidance)}</p></div><div class="result-box"><h4>📌 Assessment</h4><p><b>${escapeHTML(result.riskLevel)}</b></p><p class="muted" style="margin-top:5px">${escapeHTML(result.warning)}</p></div>
  <div class="actions"><a class="btn primary" href="symptoms.html">New Analysis</a><a class="btn secondary" href="history.html">View History</a><span class="muted">Saved to your health history.</span></div>
  <div class="section"><div class="notice">⚠️ If symptoms are severe, rapidly worsening, or you believe you may be experiencing an emergency, seek immediate professional medical care.</div></div></div></main>`;
}

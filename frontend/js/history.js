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
function formatDate(value){return value?new Date(value).toLocaleString():"—"}

async function renderHistory(){
  if(!requireAuth())return;
  try{
    const history=await apiRequest("/analyses");
    document.getElementById("app").innerHTML=nav("history")+`<main class="main">${topbar("Health History")}
    <div class="card"><div class="section-title"><div><h3>Your previous analyses</h3><p class="muted">Review your saved analyses.</p></div>${history.length?'<button class="btn danger" id="clearHistory">Clear History</button>':""}</div>
    ${history.length?`<div class="history-wrap"><table class="history-table"><thead><tr><th>Date</th><th>Symptoms</th><th>Result</th><th>Status</th></tr></thead><tbody>${history.map(item=>`<tr><td>${escapeHTML(formatDate(item.createdAt))}</td><td>${escapeHTML(item.symptoms.join(", "))}</td><td>${escapeHTML(item.riskLevel)}</td><td><span class="status">Saved</span></td></tr>`).join("")}</tbody></table></div>`:`<div class="empty">📋<h3 style="margin:10px 0">No history yet</h3><p>Complete a symptom analysis to see it here.</p><a class="btn primary" style="display:inline-block;margin-top:15px" href="symptoms.html">Start Analysis</a></div>`}</div></main>`;
    document.getElementById("clearHistory")?.addEventListener("click",clearHistory);
  }catch(error){alert(error.message);}
}

async function clearHistory(){
  if(!confirm("Clear all saved history?"))return;
  try{
    await apiRequest("/analyses",{method:"DELETE"});
    renderHistory();
  }catch(error){alert(error.message);}
}

renderHistory();

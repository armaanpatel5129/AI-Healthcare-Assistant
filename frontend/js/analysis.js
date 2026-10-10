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

requireAuth();const data=JSON.parse(localStorage.getItem("currentSymptoms")||"null");
if(!data){location.href="symptoms.html"}else{
let symptoms=data.symptoms;
let risk=symptoms.some(x=>["Shortness of breath","Chest discomfort"].includes(x))?"Needs prompt medical attention":"General guidance";
let advice=symptoms.includes("Fever")||symptoms.includes("Cough")?"Rest, stay hydrated, monitor your symptoms, and consider speaking with a healthcare professional if symptoms persist or worsen.":"Maintain hydration, adequate rest, balanced meals, and monitor how your symptoms change.";
document.getElementById("app").innerHTML=nav("symptoms")+`<main class="main">${topbar("Analysis Result")}
<div class="card"><div class="result-score"><div class="score-ring"><div class="score-inner"><b>72%</b><small>match</small></div></div><div><h2>General health guidance</h2><p class="muted">Based on the symptoms you selected. This is not a medical diagnosis.</p><div style="margin-top:10px">${symptoms.map(s=>`<span class="tag">${s}</span>`).join("")}</div></div></div>
<div class="result-box"><h4>💡 Guidance</h4><p>${advice}</p></div><div class="result-box"><h4>📌 Assessment</h4><p><b>${risk}</b></p><p class="muted" style="margin-top:5px">Your selected symptoms should be interpreted together with your age, medical history and other clinical information by a healthcare professional.</p></div>
<div class="actions"><a class="btn primary" href="symptoms.html">New Analysis</a><a class="btn secondary" href="history.html">View History</a><button class="btn secondary" onclick="saveResult()">Save Result</button></div>
<div class="section"><div class="notice">⚠️ If symptoms are severe, rapidly worsening, or you believe you may be experiencing an emergency, seek immediate professional medical care.</div></div></div></main>`;
}
function saveResult(){saveHistory({date:data.date,symptoms:data.symptoms.join(", "),result:"General health guidance"});alert("Analysis saved to history.");}

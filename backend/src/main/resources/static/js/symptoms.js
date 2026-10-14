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
const symptoms=["Fever","Headache","Cough","Sore throat","Runny nose","Fatigue","Nausea","Vomiting","Stomach pain","Diarrhea","Dizziness","Body pain","Shortness of breath","Chest discomfort","Back pain","Loss of appetite"];
document.getElementById("app").innerHTML=nav("symptoms")+`<main class="main">${topbar("Symptom Analysis")}
<div class="symptom-layout"><div class="card"><h3>What are you experiencing?</h3><p class="muted">Select all symptoms that apply.</p><div class="symptom-grid">${symptoms.map(s=>`<label class="symptom-option"><input type="checkbox" value="${s}"> ${s}</label>`).join("")}</div>
<div class="form-grid"><div><label class="form-label" for="duration">How long have you had them?</label><select class="form-control" id="duration"><option value="">Not sure</option><option value="1 day">1 day</option><option value="2 days">2 days</option><option value="3 days">3 days</option><option value="1 week">1 week</option><option value="1 month">1 month or more</option></select></div>
<div><label class="form-label" for="severity">How severe are they?</label><select class="form-control" id="severity"><option value="MILD">Mild</option><option value="MODERATE">Moderate</option><option value="SEVERE">Severe</option></select></div></div>
<label class="form-label" for="details">Additional details</label><textarea class="form-control" id="details" maxlength="2000" placeholder="Tell us anything else relevant..."></textarea><div class="actions"><button class="btn primary" id="analyzeButton" type="button">Analyze Symptoms →</button></div></div>
<div class="card"><h3>Before you continue</h3><div class="notice" style="margin-top:15px">This tool is for general educational guidance only. It does not diagnose diseases or replace a qualified healthcare professional.</div><div style="margin-top:20px"><h4>Emergency?</h4><p class="muted" style="margin-top:7px">For severe chest pain, severe breathing difficulty, loss of consciousness, or other emergency symptoms, seek urgent medical care.</p></div></div></div></main>`;

document.getElementById("analyzeButton").addEventListener("click",analyze);
async function analyze(){
  if(!requireAuth())return;
  const selected=[...document.querySelectorAll(".symptom-option input:checked")].map(input=>input.value);
  if(!selected.length){alert("Please select at least one symptom.");return}
  const button=document.getElementById("analyzeButton");
  button.disabled=true;
  try{
    const result=await apiRequest("/analyses",{method:"POST",body:{
      symptoms:selected,
      duration:document.getElementById("duration").value,
      severity:document.getElementById("severity").value,
      notes:document.getElementById("details").value
    }});
    localStorage.setItem("currentAnalysis",JSON.stringify(result));
    location.href="analysis.html";
  }catch(error){
    alert(error.message);
    button.disabled=false;
  }
}

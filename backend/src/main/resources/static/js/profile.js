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

async function renderProfile(){
  if(!requireAuth())return;
  try{
    const p=await apiRequest("/profile");
    document.getElementById("app").innerHTML=nav("profile")+`<main class="main">${topbar("Health Profile")}
    <div class="card form-card"><div class="section-title"><div><h3>Your health information</h3><p class="muted">This information can help personalize future guidance.</p></div></div>
    <form id="profileForm"><div class="form-grid">
    <div><label class="form-label" for="name">Full name</label><input class="form-control" id="name" required maxlength="100"></div>
    <div><label class="form-label" for="email">Email</label><input class="form-control" id="email" type="email" required maxlength="150"></div>
    <div><label class="form-label" for="age">Age</label><input class="form-control" id="age" type="number" min="1" max="120" placeholder="e.g. 21"></div>
    <div><label class="form-label" for="gender">Gender</label><select class="form-control" id="gender"><option value="">Select</option><option>Male</option><option>Female</option><option>Other</option></select></div>
    <div><label class="form-label" for="blood">Blood group</label><select class="form-control" id="blood"><option value="">Select</option><option>A+</option><option>A-</option><option>B+</option><option>B-</option><option>AB+</option><option>AB-</option><option>O+</option><option>O-</option></select></div>
    <div><label class="form-label" for="height">Height (cm)</label><input class="form-control" id="height" type="number" min="0.1" step="0.1"></div>
    <div><label class="form-label" for="weight">Weight (kg)</label><input class="form-control" id="weight" type="number" min="0.1" step="0.1"></div>
    <div><label class="form-label" for="allergies">Known allergies</label><input class="form-control" id="allergies" placeholder="e.g. None"></div>
    <div class="form-group full-row"><label class="form-label" for="conditions">Existing health conditions / medications</label><textarea class="form-control" id="conditions" placeholder="Enter relevant information"></textarea></div>
    </div><div class="actions"><button class="btn primary" type="submit">Save Profile</button><a class="btn secondary" href="dashboard.html">Cancel</a></div></form></div></main>`;

    document.getElementById("name").value=p.name||"";
    document.getElementById("email").value=p.email||"";
    document.getElementById("age").value=p.age??"";
    document.getElementById("gender").value=p.gender||"";
    document.getElementById("blood").value=p.blood||"";
    document.getElementById("height").value=p.height??"";
    document.getElementById("weight").value=p.weight??"";
    document.getElementById("allergies").value=p.allergies||"";
    document.getElementById("conditions").value=p.conditions||"";
    document.getElementById("profileForm").addEventListener("submit",saveProfile);
  }catch(error){alert(error.message);}
}

async function saveProfile(event){
  event.preventDefault();
  const numberOrNull=id=>document.getElementById(id).value===""?null:Number(document.getElementById(id).value);
  try{
    const p=await apiRequest("/profile",{method:"PUT",body:{
      name:document.getElementById("name").value,
      email:document.getElementById("email").value,
      age:numberOrNull("age"),
      gender:document.getElementById("gender").value,
      blood:document.getElementById("blood").value,
      height:numberOrNull("height"),
      weight:numberOrNull("weight"),
      allergies:document.getElementById("allergies").value,
      conditions:document.getElementById("conditions").value
    }});
    localStorage.setItem("healthUser",JSON.stringify({name:p.name,email:p.email}));
    alert("Profile saved successfully!");
    await renderProfile();
  }catch(error){alert(error.message);}
}

renderProfile();

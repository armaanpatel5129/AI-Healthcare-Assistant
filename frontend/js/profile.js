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

requireAuth();const u=getUser(),p=JSON.parse(localStorage.getItem("healthProfile")||"{}");
document.getElementById("app").innerHTML=nav("profile")+`<main class="main">${topbar("Health Profile")}
<div class="card form-card"><div class="section-title"><div><h3>Your health information</h3><p class="muted">This information can help personalize future guidance.</p></div></div>
<form id="profileForm"><div class="form-grid">
<div><label class="form-label">Full name</label><input class="form-control" id="name" value="${p.name||u.name||""}" required></div>
<div><label class="form-label">Email</label><input class="form-control" id="email" type="email" value="${p.email||u.email||""}" required></div>
<div><label class="form-label">Age</label><input class="form-control" id="age" type="number" min="1" max="120" value="${p.age||""}" placeholder="e.g. 21"></div>
<div><label class="form-label">Gender</label><select class="form-control" id="gender"><option value="">Select</option><option ${p.gender==="Male"?"selected":""}>Male</option><option ${p.gender==="Female"?"selected":""}>Female</option><option ${p.gender==="Other"?"selected":""}>Other</option></select></div>
<div><label class="form-label">Blood group</label><select class="form-control" id="blood"><option value="">Select</option><option>A+</option><option>A-</option><option>B+</option><option>B-</option><option>AB+</option><option>AB-</option><option>O+</option><option>O-</option></select></div>
<div><label class="form-label">Height (cm)</label><input class="form-control" id="height" type="number" value="${p.height||""}"></div>
<div><label class="form-label">Weight (kg)</label><input class="form-control" id="weight" type="number" value="${p.weight||""}"></div>
<div><label class="form-label">Known allergies</label><input class="form-control" id="allergies" value="${p.allergies||""}" placeholder="e.g. None"></div>
<div class="form-group full-row"><label class="form-label">Existing health conditions / medications</label><textarea class="form-control" id="conditions" placeholder="Enter relevant information">${p.conditions||""}</textarea></div>
</div><div class="actions"><button class="btn primary" type="submit">Save Profile</button><a class="btn secondary" href="dashboard.html">Cancel</a></div></form></div></main>`;
document.getElementById("blood").value=p.blood||"";
document.getElementById("profileForm").onsubmit=e=>{e.preventDefault();const x={name:name.value,email:email.value,age:age.value,gender:gender.value,blood:blood.value,height:height.value,weight:weight.value,allergies:allergies.value,conditions:conditions.value};localStorage.setItem("healthProfile",JSON.stringify(x));localStorage.setItem("healthUser",JSON.stringify({name:x.name,email:x.email}));alert("Profile saved successfully!");};

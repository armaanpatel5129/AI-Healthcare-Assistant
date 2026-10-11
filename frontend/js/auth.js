function togglePassword(id){const x=document.getElementById(id);x.type=x.type==="password"?"text":"password";}
document.getElementById("loginForm")?.addEventListener("submit",async e=>{
  e.preventDefault();
  try{
    const auth=await apiRequest("/auth/login",{method:"POST",body:{
      email:document.getElementById("loginEmail").value,
      password:document.getElementById("loginPassword").value
    }});
    storeAuth(auth);
    location.href="dashboard.html";
  }catch(error){alert(error.message);}
});
document.getElementById("registerForm")?.addEventListener("submit",async e=>{
  e.preventDefault();
  try{
    const auth=await apiRequest("/auth/register",{method:"POST",body:{
      name:document.getElementById("regName").value,
      email:document.getElementById("regEmail").value,
      password:document.getElementById("regPassword").value
    }});
    storeAuth(auth);
    location.href="dashboard.html";
  }catch(error){alert(error.message);}
});

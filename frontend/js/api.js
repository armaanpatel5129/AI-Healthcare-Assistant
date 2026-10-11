const API_BASE_URL = "http://localhost:8080/api";

function getAuthToken() {
  return localStorage.getItem("healthToken");
}

async function apiRequest(path, options = {}) {
  const headers = { Accept: "application/json", ...(options.headers || {}) };
  if (options.body !== undefined) {
    headers["Content-Type"] = "application/json";
  }
  const token = getAuthToken();
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
    body: options.body === undefined ? undefined : JSON.stringify(options.body)
  });
  const contentType = response.headers.get("content-type") || "";
  const payload = contentType.includes("application/json") ? await response.json() : null;

  if (!response.ok) {
    if (response.status === 401 && !path.startsWith("/auth/")) {
      localStorage.removeItem("healthToken");
      localStorage.removeItem("healthUser");
      location.href = "index.html";
    }
    throw new Error(payload?.message || `Request failed (${response.status}).`);
  }
  return payload;
}

function storeAuth(auth) {
  localStorage.setItem("healthToken", auth.token);
  localStorage.setItem("healthUser", JSON.stringify(auth.user));
}

function escapeHTML(value) {
  return String(value ?? "").replace(/[&<>"']/g, character => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#39;"
  })[character]);
}

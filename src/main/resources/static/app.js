const BASE_URL = "http://localhost:8080";
let token = "";

// REGISTER
function register() {
    
    const data = {
    
        name: document.getElementById("regName").value,
        password: document.getElementById("regPassword").value,
        college: document.getElementById("college").value,
        branch: document.getElementById("branch").value,
        age: parseInt(document.getElementById("age").value),
        marks: parseInt(document.getElementById("marks").value)
    };

    fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
        
    })
    .then(res => res.text())
    .then(data => showOutput(data))
    .catch(err => showOutput(err));

    alert(data.name+" regestred successfully");
}

function login() {
    const data = {
        name: document.getElementById("loginName").value,
        password: document.getElementById("loginPassword").value
    };

    fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
    .then(res => res.text())
    .then(data => {

        // ❌ check invalid response
        if (data.includes("invalid")) {
            showOutput("Login failed!");
            return;
        }

        token = data;
        localStorage.setItem("token", token);
        showOutput("Login Success ✅");
    })
    .catch(err => showOutput(err));
}

function viewAll() {

    const token = getToken();

    if (!token) {
        showOutput("Please login first ❌");
        return;
    }

    const rollno = document.getElementById("rollno").value;

    fetch(`${BASE_URL}/user/${rollno}/viewall`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(async res => {

        const text = await res.text();   // 🔥 ALWAYS read as text first

        try {
            const json = JSON.parse(text);   // try to parse
            showOutput(JSON.stringify(json, null, 2));
        } catch (e) {
            // not JSON → show raw message
            showOutput(text);
        }

    })
    .catch(err => showOutput(err.message));
}


// DELETE
function deleteStudent() {
    const id = document.getElementById("rollno").value;

    fetch(`${BASE_URL}/user/delete/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(res => res.text())
    .then(data => showOutput(data))
    .catch(err => showOutput(err));
}

// ADMIN - MAKE ADMIN
function makeAdmin() {
    const username = document.getElementById("adminUser").value;

    fetch(`${BASE_URL}/admin/makeadmin/${username}`, {
        method: "PATCH",
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(res => res.text())
    .then(data => showOutput(data))
    .catch(err => showOutput(err));
}

// ADMIN - REMOVE ADMIN
function removeAdmin() {
    const username = document.getElementById("adminUser").value;

    fetch(`${BASE_URL}/admin/removeadmin/${username}`, {
        method: "PATCH",
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(res => res.text())
    .then(data => showOutput(data))
    .catch(err => showOutput(err));
}

// HELPER
function getToken() {
    return localStorage.getItem("token");
}

function showOutput(msg) {
    document.getElementById("output").innerText = msg;
}

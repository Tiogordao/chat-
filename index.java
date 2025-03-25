<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Chat</title>
<script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
<style>
    body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; }
    .chat-container { width: 300px; text-align: center; }
    #messages { border: 1px solid #ccc; height: 200px; overflow-y: auto; padding: 10px; }
    .message { background: #f1f1f1; padding: 5px; margin: 5px 0; border-radius: 5px; }
</style>
</head>
<body>
<div class="chat-container">
    <div id="login">
        <input type="text" id="username" placeholder="Digite seu nome" />
        <button onclick="login()">Entrar</button>
    </div>
    <div id="chat" style="display: none;">
        <div id="messages"></div>
        <input type="text" id="message" placeholder="Digite uma mensagem..." />
        <button onclick="sendMessage()">Enviar</button>
    </div>
</div>

<script>
    const socket = io("http://localhost:3001");
    let username = "";

    function login() {
        username = document.getElementById("username").value.trim();
        if (username) {
            document.getElementById("login").style.display = "none";
            document.getElementById("chat").style.display = "block";
        }
    }

    function sendMessage() {
        const messageInput = document.getElementById("message");
        const message = messageInput.value.trim();
        if (message) {
            socket.emit("message", { username, text: message });
            messageInput.value = "";
        }
    }

    socket.on("message", (msg) => {
        const messagesDiv = document.getElementById("messages");
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        messageElement.innerHTML = `<strong>${msg.username}:</strong> ${msg.text}`;
        messagesDiv.appendChild(messageElement);
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    });
</script>
</body>
</html>

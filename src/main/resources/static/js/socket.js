let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

const debateRoomId = document.getElementById("debateroom-id").value;

stompClient.connect({}, function(frame) {

    stompClient.subscribe('/sub/chatting/rooms/' + debateRoomId, function (msg){
        const body = JSON.parse(msg.body);
        const username = body.username;
        const message = body.message;

        const cons = document.getElementById("cons-name").value;
        const pros = document.getElementById("pros-name").value;

        console.log(cons);
        console.log(pros);

        if (username == cons|| username == pros){
            const speechListDiv = document.getElementById('speech-list');
            const newDiv = document.createElement('div');
            newDiv.textContent = message;
            speechListDiv.appendChild(newDiv);
        } else {
            const chatListDiv = document.getElementById('chat-list');
            const newDiv = document.createElement('div');
            newDiv.textContent = message;
            chatListDiv.appendChild(newDiv);
        }
    });
})
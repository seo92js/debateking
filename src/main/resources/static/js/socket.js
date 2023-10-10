let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

const debateRoomId = document.getElementById("debateroom-id").value;

stompClient.connect({}, function(frame) {
    stompClient.subscribe('/sub/chatting/rooms/' + debateRoomId, function (msg){
        const body = JSON.parse(msg.body);

        const type = body.type;

        //임시
        console.log(type);

        if (type == 'chat'){
            const username = body.username;
            const message = body.message;

            const chatListDiv = document.getElementById('chat-list');
            const newDiv = document.createElement('div');
            newDiv.textContent = message;
            chatListDiv.appendChild(newDiv);
        }
        else if (type == 'speech'){
            const username = body.username;
            const message = body.message;

            const speechListDiv = document.getElementById('speech-list');
            const newDiv = document.createElement('div');
            newDiv.textContent = message;
            speechListDiv.appendChild(newDiv);
        } else if (type == 'position') {
            const prosUsername = body.prosUsername;
            const consUsername = body.consUsername;

            const pros = document.getElementById("pros-name");
            const cons = document.getElementById("cons-name");

            pros.innerText = prosUsername;
            cons.innerText = consUsername;

            // 찬성 or 반대 or 구경꾼 포지션 별로 활성화/비활성 화 시켜야 함
        }
    });
})
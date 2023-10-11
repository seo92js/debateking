let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

const debateRoomId = document.getElementById("debateroom-id").value;
const username = document.getElementById("login-username").value;

stompClient.connect({}, function(frame) {

    enter(debateRoomId, username);

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
        } else if (type == 'enter') {
            const username = body.username;

            enter(debateRoomId, username);
        }
    });
});

function enter(debateRoomId, username) {
    var spectorList = document.getElementById('spector-list');
    // spectorList 내의 모든 div 요소를 반복하며 이름 검사
    var divs = spectorList.getElementsByTagName('div');

    for (var i = 0; i < divs.length; i++) {
        if (divs[i].textContent === username) {
            // 이름이 이미 있는 경우 return
            return;
        }
    }

    enterDto = {
        type: 'enter',
        debateRoomId: debateRoomId,
        username: username,
    };

    stompClient.send('/pub/chattings/rooms/enter', {}, JSON.stringify(enterDto));

    var newDiv = document.createElement('div');
    newDiv.textContent = username;
    spectorList.appendChild(newDiv);

    const chatDto = {
        type: 'chat',
        debateRoomId: debateRoomId,
        username: username,
        message: username + '님이 입장 하셨습니다.'
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));
}






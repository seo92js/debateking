const debateRoomId = document.getElementById("debateroom-id").value;
const username = document.getElementById("login-username").value;

enter(debateRoomId, username);

function enter(id, username){

    enterDto = {
        type: 'enter',
        debateRoomId: id,
        username: username,
    };

    stompClient.send('/pub/chattings/rooms/enter', {}, JSON.stringify(enterDto));
}
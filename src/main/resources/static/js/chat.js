
function speech(debateRoomId, username){
    const form = document.getElementById("speech-save-form");
    const formData = new FormData(form);

    const chatDto = {
        debateRoomId: debateRoomId,
        username: username,
        message: formData.get('message')
    };

    event.preventDefault();

    stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(chatDto));
}

function chat(debateRoomId, username){
    const form = document.getElementById("chat-form");
    const formData = new FormData(form);

    event.preventDefault();

    const chatDto = {
        debateRoomId: debateRoomId,
        username: username,
        message: formData.get('message')
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));
}

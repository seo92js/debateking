
function speech(debateRoomId, username){
    const form = document.getElementById("speech-save-form");
    const formData = new FormData(form);

    const speechDto = {
        type: 'speech',
        debateRoomId: debateRoomId,
        username: username,
        message: formData.get('message')
    };

    stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(speechDto));
}

function chat(debateRoomId, username){
    const form = document.getElementById("chat-form");
    const formData = new FormData(form);

    const chatDto = {
        type: 'chat',
        debateRoomId: debateRoomId,
        username: username,
        message: formData.get('message')
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));
}

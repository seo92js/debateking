let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

const debateRoomId = document.getElementById("debateroom-id").value;

stompClient.connect({}, function(frame) {

    stompClient.subscribe('/sub/chatting/rooms/' + debateRoomId, function (msg){
        const message = JSON.parse(msg.body);
        const username = message.username;
        const speech = message.speech;

        $("#speech-list").append(speech);
    });

})


function speechSave(debateRoomId, userId){
    const form = document.getElementById("speech-save-form");
    const formData = new FormData(form);

    const speechSaveRequestDto = {
        debateRoomId: debateRoomId,
        userId: userId,
        speech: formData.get('speech')
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/speech',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(speechSaveRequestDto)
    }).done(function(){
        alert('완료');
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}

function speechRedisSave(debateRoomId, username){
    const form = document.getElementById("speech-save-form");
    const formData = new FormData(form);

    const speechRedisSaveRequestDto = {
        debateRoomId: debateRoomId,
        username: username,
        speech: formData.get('speech')
    };

    event.preventDefault();

    stompClient.send('/pub/chattings/rooms/messages', {}, JSON.stringify(speechRedisSaveRequestDto));

//    $.ajax({
//        type: 'POST',
//        //url: '/api/v2/speech',
//        url: '/chattings/rooms/messages',
//        contentType: 'application/json; charset=utf-8',
//        data: JSON.stringify(speechRedisSaveRequestDto)
//    }).done(function(){
//        alert('완료');
//        location.reload();
//    }).fail(function(error){
//        alert(JSON.stringify(error));
//    })
}

function debateRoomExit(id, debateRoomId){

    const username = document.getElementById("login-username").value;

    exitDto = {
        type: 'exit',
        debateRoomId: debateRoomId,
        username: username
    };

    stompClient.send('/pub/chattings/rooms/exit', {}, JSON.stringify(exitDto));

    const chatDto = {
        type: 'chat',
        debateRoomId: debateRoomId,
        username: username,
        message: username + '님이 퇴장 하셨습니다.'
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));

    $.ajax({
        type: 'PUT',
        url: '/api/v1/user/' + id + '/' + debateRoomId + '/exit',
    }).done(function(){
        alert('토론방 나가기 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
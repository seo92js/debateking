let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

const debateRoomId = document.getElementById("debateroom-id").value;
const username = document.getElementById("login-username").value;
const id = document.getElementById("login-userid").value;

stompClient.connect({}, function(frame) {

    enter(debateRoomId, username);

    stompClient.subscribe('/sub/chatting/rooms/' + debateRoomId, function (msg){
        const body = JSON.parse(msg.body);

        const type = body.type;

        //임시
        console.log(type);

        if (type == 'chat'){
            const username = body.username;

            $("<div>").text(body.message).appendTo("#chat-list");
        } else if (type == 'speech'){
            const username = body.username;

            $("<div>").text(body.message).appendTo("#speech-list");
        } else if (type == 'position') {
            $("#pros-name").text(body.prosUsername);
            $("#cons-name").text(body.consUsername);

            $("#pros-ready").text("false");
            $("#cons-ready").text("false");
            // 찬성 or 반대 or 구경꾼 포지션 별로 활성화/비활성 화 시켜야 함
        } else if (type == 'enter') {
            const username = body.username;

            enter(debateRoomId, username);
        } else if (type == 'exit') {
            //spectors list 에서 지우기
        } else if (type == 'ready') {
            if (body.prosReady == true) {
                $("#pros-ready").text("true");
            } else {
                $("#pros-ready").text("false");
            }

            if ( body.consReady == true) {
                $("#cons-ready").text("true");
            } else {
                $("#cons-ready").text("false");
            }
        } else if (type == 'time') {
            $("#remaining-speaking-time").text(body.speakingTime);
            $("#remaining-debate-time").text(body.discussionTime);
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

    $("<div>").text(username).appendTo("#spector-list");

    const chatDto = {
        type: 'chat',
        debateRoomId: debateRoomId,
        username: username,
        message: username + '님이 입장 하셨습니다.'
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));

    $.ajax({
        type: 'PUT',
        url: '/api/v1/user/' + id + '/' + debateRoomId + '/enter',
    }).done(function(){
        //alert('토론방 나가기 완료');
        //window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}






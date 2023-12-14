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
            $("<div>").text(body.username + ' : ' + body.message).addClass('mb-2 debateroom__chat').appendTo("#chat-list");
            $("#chat-list").scrollTop($("#chat-list")[0].scrollHeight);
        } else if (type == 'speech'){
            const username = body.username;

            if (username === document.getElementById("pros-name").innerText){
                $("<div>").text(body.username + ' : ' +body.message).addClass('mb-2 debateroom__speech debateroom__speech--pros').appendTo("#speech-list");
            } else if (username === document.getElementById("cons-name").innerText) {
                $("<div>").text(body.username + ' : ' +body.message).addClass('mb-2 debateroom__speech debateroom__speech--cons').appendTo("#speech-list");
            } else {
                $("<div>").text(body.username + ' : ' +body.message).addClass('mb-2 debateroom__speech').appendTo("#speech-list");
            }

            $("#speech-list").scrollTop($("#speech-list")[0].scrollHeight);
        } else if (type == 'position') {
            $("#pros-name").text(body.prosUsername);
            $("#cons-name").text(body.consUsername);

            $("#pros-ready").text("false");
            $("#cons-ready").text("false");
            // 찬성 or 반대 or 구경꾼 포지션 별로 활성화/비활성 화 시켜야 함
        } else if (type == 'enter') {
            enter(debateRoomId, body.username);
        } else if (type == 'exit') {
            //spectors list 에서 지우기
            exit(debateRoomId, body.username);
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
        } else if (type == 'speaker') {
            const speaker = body.speakerName;
            const username = document.getElementById('login-username').value;
            console.log(speaker);
            console.log(username);

            if (speaker == username) {
                document.getElementById('speech-btn').removeAttribute('disabled');
            } else {
                document.getElementById('speech-btn').setAttribute('disabled', 'disabled')
            }
        }
    });
});

function exit(debateRoomId, username){
    const spectorList = document.getElementById('spector-list');

    const divs = spectorList.getElementsByTagName('div');

    for (let i = 0; i < divs.length; i++){
        if (divs[i].textContent === username) {
            spectorList.removeChild(divs[i]);
        }
    }

    const chatDto = {
        type: 'chat',
        debateRoomId: debateRoomId,
        username: username,
        message: username + '님이 퇴장 하셨습니다.'
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));

}

function enter(debateRoomId, username) {
    const spectorList = document.getElementById('spector-list');
    // spectorList 내의 모든 div 요소를 반복하며 이름 검사
    const divs = spectorList.getElementsByTagName('div');

    for (let i = 0; i < divs.length; i++) {
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






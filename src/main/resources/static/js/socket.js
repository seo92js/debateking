let socket = new SockJS("/chattings");
let stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {

    //enter(debateRoomId, username);
    onEnterMessage();

    const debateRoomId = document.getElementById("debateroom-id").value;

    stompClient.subscribe('/sub/chatting/rooms/' + debateRoomId, function (msg){
        const body = JSON.parse(msg.body);

        const type = body.type;
        //임시
        console.log(body.type);

        if (type == 'chat'){
            onChatMessage(body.username, body.message);
        } else if (type == 'speech'){
            onSpeechMessage(body.username, body.message);
        } else if (type == 'position') {
            onPositionMessage(body.prosUsername, body.consUsername);
        } else if (type == 'enter') {
            onEnterMessage();
        } else if (type == 'exit') {
            onExitMessage();
        } else if (type == 'ready') {
            onReadyMessage(body.prosReady, body.consReady);
        } else if (type == 'time') {
            onTimeMessage(body.speakingTime, body.discussionTime);
        } else if (type == 'speaker') {
            onSpeakerMessage(body.speakerName);
        } else if (type == 'result') {

        } else if (type == 'debate') {
            onDebateMessage(body.status);
        }
    });
});

function onTimeMessage(speakingTime, discussionTime) {
    $("#remaining-speaking-time").text('발언 시간 : ' + speakingTime);
    $("#remaining-debate-time").text('토론 시간 : ' + discussionTime);
}

function onChatMessage(username, message) {
    $("<div>").text(username + ' : ' + message).addClass('mb-2 debateroom__chat').appendTo("#chat-list");
    $("#chat-list").scrollTop($("#chat-list")[0].scrollHeight);
}

function onExitMessage(){
    const username = document.getElementById("login-username").value;
    const debateRoomId = document.getElementById("debateroom-id").value;
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
        username: 'notify',
        message: '------- ' + username + ' 님이 퇴장 하셨습니다 -------'
    };

    stompClient.send('/pub/chattings/rooms/chat', {}, JSON.stringify(chatDto));

}

function onSpeechMessage(username, message) {
    if (username === document.getElementById("pros-name").innerText){
        $("<div>").text(username + ' : ' + message).addClass('mb-2 debateroom__speech debateroom__speech--pros').appendTo("#speech-list");
    } else if (username === document.getElementById("cons-name").innerText) {
        $("<div>").text(username + ' : ' + message).addClass('mb-2 debateroom__speech debateroom__speech--cons').appendTo("#speech-list");
    } else {
        $("<div>").text(username + ' : ' + message).addClass('mb-2 debateroom__speech').appendTo("#speech-list");
    }

    $("#speech-list").scrollTop($("#speech-list")[0].scrollHeight);
}

function onEnterMessage() {
    const id = document.getElementById("login-userid").value;
    const debateRoomId = document.getElementById("debateroom-id").value;
    const username = document.getElementById("login-username").value;
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
        username: 'notify',
        message: '------- ' + username + ' 님이 입장 하셨습니다 -------'
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

function onPositionMessage(prosUsername, consUsername) {
    const username = document.getElementById("login-username").value;

    $("#pros-name").text(prosUsername);
    $("#cons-name").text(consUsername);

    $("#pros-ready").text("false");
    $("#cons-ready").text("false");

    // 찬성 or 반대 or 구경꾼 포지션 별로 활성화/비활성 화 시켜야 함
    if (prosUsername != null) {
        document.getElementById('set-pros').disabled = true;
    } else {
        document.getElementById('set-pros').disabled = false;
    }

    if (consUsername != null) {
        document.getElementById('set-cons').disabled = true;
    } else {
        document.getElementById('set-cons').disabled = false;
    }

    if (username == prosUsername) {
        document.getElementById('pros-ready').disabled = false;
        document.getElementById('cons-ready').disabled = true;
    } else if (username == consUsername) {
        document.getElementById('pros-ready').disabled = true;
        document.getElementById('cons-ready').disabled = false;
    } else {
        document.getElementById('pros-ready').disabled = true;
        document.getElementById('cons-ready').disabled = true;
    }
}

function onSpeakerMessage(speakerName) {
    const username = document.getElementById('login-username').value;

    if (speakerName == username) {
        document.getElementById('speech-btn').disabled = false;
    } else {
        document.getElementById('speech-btn').disabled = true;
    }

    $("#speaker").text(speakerName);

}

function onReadyMessage(prosReady, consReady) {
    const username = document.getElementById("login-username").value;
    const owner = document.getElementById("owner").innerText;

    if (prosReady == true) {
        $("#pros-ready").text("true");
    } else {
        $("#pros-ready").text("false");
    }

    if (consReady == true) {
        $("#cons-ready").text("true");
    } else {
        $("#cons-ready").text("false");
    }

    if (username == owner) {
        if (prosReady == true && consReady == true) {
           document.getElementById("debate-start-btn").disabled = false;
        } else {
           document.getElementById("debate-start-btn").disabled = true;
        }
    }
}

function onDebateMessage(status) {
    const username = document.getElementById("login-username").value;
    const prosname = document.getElementById("pros-name").innerText;
    const consname = document.getElementById("cons-name").innerText;
    const owner = document.getElementById("owner").innerText;

    document.getElementById("status").innerText = status;

    if (status == true) {
        document.getElementById('set-spector').disabled = true;
        document.getElementById('debate-stop-btn').disabled = false;
        document.getElementById('pros-ready').disabled = true;
        document.getElementById('cons-ready').disabled = true;

        if (username == owner) {
            document.getElementById('debate-start-btn').disabled = true;
            document.getElementById('delete-room').disabled = true;
        } else {
            if (username == prosname || username == consname) {
                document.getElementById('exit-room').disabled = true;
            }
        }
    } else {
        document.getElementById('set-spector').disabled = false;
        document.getElementById('debate-stop-btn').disabled = true;

        if (prosname == username) {
            document.getElementById('pros-ready').disabled = false;
        }
        else if (consname == username) {
            document.getElementById('cons-ready').disabled = false;
        }

        if (username == owner) {
            document.getElementById('delete-room').disabled = false;
        } else {
            if (username == prosname || username == consname) {
                document.getElementById('exit-room').disabled = false;
            }
        }
    }

    if (consname != "") {
        document.getElementById('set-cons').disabled = true;
    } else {
        document.getElementById('set-cons').disabled = false;
    }

    if (prosname != "") {
        document.getElementById('set-pros').disabled = true;
    } else {
        document.getElementById('set-pros').disabled = false;
    }

    //투표
    if (status == false) {
        $.ajax({
            type: 'GET',
            url: '/vote'
        }).done(function(){
        }).fail(function(error){
        })
    }
}






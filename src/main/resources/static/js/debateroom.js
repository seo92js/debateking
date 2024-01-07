//컨텐츠 로드
document.addEventListener('DOMContentLoaded', function () {
    const username = document.getElementById("login-username").value;
    const prosname = document.getElementById("pros-name").innerText;
    const consname = document.getElementById("cons-name").innerText;
    const owner = document.getElementById("owner").innerText;
    const speaker = document.getElementById("speaker").innerText;
    const status = document.getElementById("status").innerText;

    //발언
    if (speaker == username) {
        document.getElementById('speech-btn').disabled = false;
    } else {
        document.getElementById('speech-btn').disabled = true;
    }

    //방 상태
    if (status == "true") {
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
});

function debateStart(id){
    const debateDto = {
        type: 'debate',
        debateRoomId: id,
        status: true,
        prosname: document.getElementById("pros-name").innerText,
        consname: document.getElementById("cons-name").innerText,
        discussionTime: $("#discussionTime").val(),
        speakingTime: $("#speakingTime").val(),
    }

    stompClient.send('/pub/chattings/rooms/debate', {}, JSON.stringify(debateDto));
}

function debateSurrender(id, username) {

    //항복 했다는 메세지 날리고
    let speechDto = {
        type: 'speech',
        debateRoomId: id,
        username: 'notify',
        message: '------- ' + username + ' 님이 항복했습니다 -------'
    };

    stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(speechDto));

    const debateDto = {
        type: 'debate',
        debateRoomId: id,
        status: false,
        prosname: document.getElementById("pros-name").innerText,
        consname: document.getElementById("cons-name").innerText,
        discussionTime: $("#discussionTime").val(),
        speakingTime: $("#speakingTime").val(),
    }

    stompClient.send('/pub/chattings/rooms/debate', {}, JSON.stringify(debateDto));

    // 항복 버튼을 누르는 사람이 패배
    const prosname = document.getElementById("pros-name").innerText;
    const consname = document.getElementById("cons-name").innerText;
    let winner;

    if (username != prosname) {
        winner = prosname;
    } else {
        winner = consname;
    }

    const resultDto = {
        type: 'result',
        debateRoomId: id,
        winner : winner,
        loser : username,
        draw: false
    }

    stompClient.send('/pub/chattings/rooms/result', {}, JSON.stringify(resultDto));
}
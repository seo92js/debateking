let currentSpeaker;
let intervalDebateId;
let debateTime; //총 토론 시간
let speakTime; // 발언 시간
let speechTime; // 현재
let roomId;

let isStart = false;

if (localStorage.getItem('speaker') !== null) {
    isStart = true;
    socket.addEventListener('open', function () {
      debateStart();
    });
} else {
    isStart = false;
}

function debateStart(){
    roomId = $("#debateroom-id").val();
    speakTime = $("#speakingTime").val();

    if (isStart == false) {
        currentSpeaker = "pros";
        localStorage.setItem('speaker', currentSpeaker);
        debateTime = $("#discussionTime").val();
        speechTime = speakTime;

        const statusDto = {
            type: 'status',
            debateRoomId: id,
            status: true
        }

        stompClient.send('/pub/chattings/rooms/status', {}, JSON.stringify(statusDto));

    } else {
        currentSpeaker = localStorage.getItem('speaker');
        debateTime = localStorage.getItem('debateTime');
        speechTime = localStorage.getItem('speechTime');
    }

    startDebateTimer(roomId);

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + roomId + '/start',
    }).done(function(){
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}

function startDebateTimer(id) {
    let speaker;

    if (currentSpeaker === 'pros') {
        speaker = document.getElementById("pros-name").innerText;
    } else {
        speaker = document.getElementById("cons-name").innerText;
    }

    const speechDto = {
        type: 'speech',
        debateRoomId: id,
        username: 'notify',
        message: '------- ' + speaker + ' 발언 -------'
    };

    if (isStart == false) {
        stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(speechDto));
    }

    const speakerDto = {
        type: 'speaker',
        debateRoomId: id,
        speakerName : speaker
    }

    stompClient.send('/pub/chattings/rooms/speaker', {}, JSON.stringify(speakerDto));

    //토론 타이머
    intervalDebateId = setInterval(function() {
        debateTime--;
        speechTime--;

        //localStorage에 저장
        localStorage.setItem('debateTime', debateTime);
        localStorage.setItem('speechTime', speechTime);

        const timeDto = {
            type: 'time',
            debateRoomId: id,
            speakingTime: speechTime,
            discussionTime: debateTime
        }

        stompClient.send('/pub/chattings/rooms/time', {}, JSON.stringify(timeDto));

        if (debateTime <= 0){
            debateStop(id, intervalDebateId);
            return;
        }

        if (speechTime <= 0) {
            speechTime = speakTime;

            if (currentSpeaker === 'pros') {
                currentSpeaker = 'cons';
                speaker = document.getElementById("cons-name").innerText;
            } else {
                currentSpeaker = 'pros';
                speaker = document.getElementById("pros-name").innerText;
            }

            localStorage.setItem('speaker', currentSpeaker);

            const speechDto = {
                type: 'speech',
                debateRoomId: id,
                username: 'notify',
                message: '------- ' + speaker + ' 발언 -------'
            };

            stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(speechDto));

            const speakerDto = {
                type: 'speaker',
                debateRoomId: id,
                speakerName : speaker
            }

            stompClient.send('/pub/chattings/rooms/speaker', {}, JSON.stringify(speakerDto));
        }

    }, 1000);
}

function debateStop(id, intervalId){
    clearInterval(intervalId);

    localStorage.removeItem('speaker');
    localStorage.removeItem('debateTime');
    localStorage.removeItem('speechTime');

    isStart = false;

    const speechDto = {
        type: 'speech',
        debateRoomId: id,
        username: 'notify',
        message: '------- 토론이 끝났습니다 -------'
    };

    stompClient.send('/pub/chattings/rooms/speech', {}, JSON.stringify(speechDto));

    const speakerDto = {
        type: 'speaker',
        debateRoomId: id,
        speakerName : null
    }

    stompClient.send('/pub/chattings/rooms/speaker', {}, JSON.stringify(speakerDto));

    const statusDto = {
        type: 'status',
        debateRoomId: id,
        status: false
    }

    stompClient.send('/pub/chattings/rooms/status', {}, JSON.stringify(statusDto));


    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/stop',
    }).done(function(){
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
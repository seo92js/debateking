let currentSpeaker = 'pros';

let intervalDebateId;

let debateTime; //총 토론 시간
let speakTime; // 발언 시간
let speechTime; // 현재

function debateStart(id, speakingTime, discussionTime){
    debateTime = discussionTime;
    speakTime = speakingTime;
    speechTime = speakTime;

    startDebateTimer(id)

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/start',
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
        debateRoomId: debateRoomId,
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

    //토론 타이머
    intervalDebateId = setInterval(function() {
        debateTime--;
        speechTime--;

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

            const speechDto = {
                type: 'speech',
                debateRoomId: debateRoomId,
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

    const speechDto = {
        type: 'speech',
        debateRoomId: debateRoomId,
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

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/stop',
    }).done(function(){
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
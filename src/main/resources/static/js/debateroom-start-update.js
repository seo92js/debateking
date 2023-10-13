let currentSpeaker = 'pros';

let intervalDebateId;
let intervalProsId;
let intervalConsId;

let debateTime; //총 토론 시간
let speakTime; // 발언 시간
let speechTime; // 현재

function debateStart(id, speakingTime, discussionTime){
    //비활성화
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
    clearInterval(intervalConsId);
    clearInterval(intervalProsId);

    //speechTime = speakTime;

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

        if (debateTime <= 0) {
            debateStop(id, intervalDebateId);
        } else {
            startSpeechTimer(currentSpeaker, id);
        }

    }, 1000);
}

function startSpeechTimer(speaker, id) {
    //speechTime = speakTime;

    if (currentSpeaker === 'pros') {
        intervalProsId = setInterval(function() {
            //speechTime--;

            if (speechTime <= 0) {
                clearInterval(intervalProsId);
                speechTime = speakTime;
                //다음 발언자 타이머
                startSpeechTimer('cons', id);
            }
        }, 1000);
    } else {
        intervalConsId = setInterval(function() {
            //speechTime--;

            if (speechTime <= 0) {
                clearInterval(intervalConsId);
                speechTime = speakTime;
                //다음 발언자 타이머
                startSpeechTimer('pros', id);
            }
        }, 1000);
    }
}

function debateStop(id, intervalId){
    clearInterval(intervalId);

    //비활성화 풀기

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/stop',
    }).done(function(){
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
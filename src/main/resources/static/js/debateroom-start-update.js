let currentSpeaker = 'pros';

let intervalDebateId;

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
//    clearInterval(intervalConsId);
//    clearInterval(intervalProsId);

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
        }

         if (speechTime <= 0) {
            speechTime = speakTime;

            const consname = document.getElementById("cons-name").innerText;
            const prosname = document.getElementById("pros-name").innerText;
            const username = document.getElementById("login-username").value;
            console.log(consname);
            console.log(prosname);
            console.log(username);

            if (currentSpeaker === 'pros') {
                currentSpeaker = 'cons';

                //cons 비활성화 풀기
                if (username === consname)
                    document.getElementById('speech-btn').removeAttribute('disabled');
                if (username === prosname)
                    document.getElementById('speech-btn').setAttribute('disabled', 'disabled');
            }
            else{
                currentSpeaker = 'pros';

                //pros 비활성화 풀기
                if (username === consname)
                    document.getElementById('speech-btn').setAttribute('disabled', 'disabled');
                if (username === prosname)
                    document.getElementById('speech-btn').removeAttribute('disabled');
            }
        }

        //임시
        if (currentSpeaker ==='cons')
            console.log('cons 발언 타임');
        else
            console.log('pros 발언 타임');

        if (debateTime <= 0) {
            debateStop(id, intervalDebateId);
        }
    }, 1000);
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
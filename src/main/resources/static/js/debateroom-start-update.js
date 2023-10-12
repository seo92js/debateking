let timer = 0;
let intervalId;

function debateStart(id, discussionTime){
    //비활성화

    timer = 0;

    intervalId = setInterval(function() {
        timer++;

        const timeDto = {
            type: 'time',
            debateRoomId: id,
            time: timer,
        };

        stompClient.send('/pub/chattings/rooms/time', {}, JSON.stringify(timeDto));

        if (timer >= discussionTime) {
            debateStop(intervalId);
        }
    }, 1000);

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/start',
    }).done(function(){
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}

function debateStop(intervalId){
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
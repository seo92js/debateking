function setPros(id, pros, cons){
    if (pros == cons){
        cons = null;
    }

    debateRoomPositionUpdateRequestDto = {
        debateRoomId: id,
        prosUsername: pros,
        consUsername: cons
    };

//    $.ajax({
//        type: 'PUT',
//        url: '/api/v1/debateroom/' + id + '/position',
//        dataType: 'json',
//        contentType: 'application/json; charset=utf-8',
//        data: JSON.stringify(debateRoomPositionUpdateRequestDto)
//    }).done(function(){
//        location.reload();
//    }).fail(function(error){
//        alert(JSON.stringify(error));
//    })
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(debateRoomPositionUpdateRequestDto));
}

function setCons(id, pros, cons){
    if (pros == cons){
        pros = null;
    }

    debateRoomPositionUpdateRequestDto = {
        debateRoomId: id,
        prosUsername: pros,
        consUsername: cons
    };

//    $.ajax({
//        type: 'PUT',
//        url: '/api/v1/debateroom/' + id + '/position',
//        dataType: 'json',
//        contentType: 'application/json; charset=utf-8',
//        data: JSON.stringify(debateRoomPositionUpdateRequestDto)
//    }).done(function(){
//        location.reload();
//    }).fail(function(error){
//        alert(JSON.stringify(error));
//    })
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(debateRoomPositionUpdateRequestDto));
}

function setSpectors(id, pros, cons, username){
    if (pros == username){
        pros = null;
    } else if (cons == username) {
        cons = null;
    }

    debateRoomPositionUpdateRequestDto = {
        debateRoomId: id,
        prosUsername: pros,
        consUsername: cons
    };

//    $.ajax({
//        type: 'PUT',
//        url: '/api/v1/debateroom/' + id + '/position',
//        dataType: 'json',
//        contentType: 'application/json; charset=utf-8',
//        data: JSON.stringify(debateRoomPositionUpdateRequestDto)
//    }).done(function(){
//        location.reload();
//    }).fail(function(error){
//        alert(JSON.stringify(error));
//    })
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(debateRoomPositionUpdateRequestDto));
}
function setPros(id, pros, cons){
    if (pros == cons){
        cons = null;
    }

    positionDto = {
        type: 'position',
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
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}

function setCons(id, pros, cons){
    if (pros == cons){
        pros = null;
    }

    positionDto = {
        type: 'position',
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
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}

function setSpectors(id, pros, cons, username){
    if (pros == username){
        pros = null;
    } else if (cons == username) {
        cons = null;
    }

    positionDto = {
        type: 'position',
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
    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}
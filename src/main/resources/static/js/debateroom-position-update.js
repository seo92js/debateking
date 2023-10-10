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

    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}
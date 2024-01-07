function setPros(id, username){
    let prosName = document.getElementById('pros-name').innerText;
    let consName = document.getElementById('cons-name').innerText;

    if (username == consName || consName == ""){
        consName = null;
    }

    positionDto = {
        type: 'position',
        debateRoomId: id,
        prosUsername: username,
        consUsername: consName
    };

    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}

function setCons(id, username){
    let prosName = document.getElementById('pros-name').innerText;
    let consName = document.getElementById('cons-name').innerText;

    if (prosName == username || prosName == ""){
        prosName = null;
    }

    positionDto = {
        type: 'position',
        debateRoomId: id,
        prosUsername: prosName,
        consUsername: username
    };

    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}

function setSpectors(id, username){
    let prosName = document.getElementById('pros-name').innerText;
    let consName = document.getElementById('cons-name').innerText;

    initReady(id, prosName, consName, username);

    if (prosName == username || prosName == ""){
        prosName = null;
    }
    if (consName === username || consName === "") {
        consName = null;
    }

    positionDto = {
        type: 'position',
        debateRoomId: id,
        prosUsername: prosName,
        consUsername: consName
    };

    stompClient.send('/pub/chattings/rooms/position', {}, JSON.stringify(positionDto));
}

function initReady(id, pros, cons, username) {
    prosReady = document.getElementById("pros-ready").innerText;
    consReady = document.getElementById("cons-ready").innerText;

    if (pros == username) {
        prosReady = "false";
    } else if (cons == username) {
        consReady = "false";
    } else {
        return;
    }

    readyDto = {
        type: 'ready',
        debateRoomId: id,
        prosReady: prosReady,
        consReady: consReady
    }

    stompClient.send('/pub/chattings/rooms/ready', {}, JSON.stringify(readyDto));
}
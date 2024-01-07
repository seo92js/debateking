function setProsReady(id){
    prosReady = document.getElementById("pros-ready").innerText;
    consReady = document.getElementById("cons-ready").innerText;

    if (prosReady == "false") {
        prosReady = "true";
    } else {
        prosReady = "false";
    }

    readyDto = {
        type: 'ready',
        debateRoomId: id,
        prosReady: prosReady,
        consReady: consReady
    }

    stompClient.send('/pub/chattings/rooms/ready', {}, JSON.stringify(readyDto));
}

function setConsReady(id){
    prosReady = document.getElementById("pros-ready").innerText;
    consReady = document.getElementById("cons-ready").innerText;

    if (consReady == "false") {
        consReady = "true";
    } else {
        consReady = "false";
    }

    readyDto = {
        type: 'ready',
        debateRoomId: id,
        prosReady: prosReady,
        consReady: consReady
    }

    stompClient.send('/pub/chattings/rooms/ready', {}, JSON.stringify(readyDto));
}

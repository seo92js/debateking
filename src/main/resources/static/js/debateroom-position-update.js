//컨텐츠 로드 했을 때
document.addEventListener('DOMContentLoaded', function () {
    var consName = document.getElementById('cons-name');

    if (consName.textContent.trim().length > 0) {
        document.getElementById('set-cons').disabled = true;
    } else {
        document.getElementById('set-cons').disabled = false;
    }

    var prosName = document.getElementById('pros-name');

    if (prosName.textContent.trim().length > 0) {
        document.getElementById('set-pros').disabled = true;
    }else {
        document.getElementById('set-pros').disabled = false;
    }
});

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

    debateRoomReadyUpdateRequestDto = {
        prosReady: prosReady,
        consReady: consReady
    };

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/ready',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(debateRoomReadyUpdateRequestDto)
    }).done(function(){
        //alert('완료');
        //location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
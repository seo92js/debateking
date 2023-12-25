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

function setReady(id, ){

}
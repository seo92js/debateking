function prosReady(id, prosReady, consReady){
    if (prosReady == false) {
        prosReady = true;
    } else {
        prosReady = false;
    }

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
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}

function consReady(id, prosReady, consReady){
    if (consReady == false) {
        consReady = true;
    } else {
        consReady = false;
    }

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
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
function debateStart(id){
    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/start',
        //dataType: 'json',
        //contentType: 'application/json; charset=utf-8',
        //data: JSON.stringify(debateRoomPositionUpdateRequestDto)
    }).done(function(){
        //alert('완료');
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
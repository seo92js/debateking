function debateRoomExit(id, debateRoomId){
    event.preventDefault();

    $.ajax({
        type: 'PUT',
        url: '/api/v1/user/' + id + '/' + debateRoomId,
    }).done(function(){
        alert('토론방 나가기 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })

}
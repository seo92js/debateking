function debateRoomDelete(id){

    event.preventDefault();

    $.ajax({
        type: 'DELETE',
        url: '/api/v1/debateroom/' + id,
    }).done(function(){
        alert('토론방 삭제');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
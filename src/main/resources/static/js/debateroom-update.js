function debateRoomUpdate(id){
    const form = document.getElementById("debateroom-update-form");
    const formData = new FormData(form);

    const debateRoomUpdateRequestDto = {
        title: formData.get('title'),
        speakingTime: formData.get('speakingTime'),
        discussionTime: formData.get('discussionTime')
    };

    event.preventDefault();

    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(debateRoomUpdateRequestDto)
    }).done(function(){
        alert('토론방 수정 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })

}
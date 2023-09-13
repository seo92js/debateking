function debateRoomSave(){
    const form = document.getElementById("debateroom-save-form");
    const formData = new FormData(form);

    const debateRoomSaveRequestDto = {
        userId: formData.get('userId'),
        title: formData.get('title'),
        speakingTime: formData.get('speakingTime'),
        discussionTime: formData.get('discussionTime')
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/debateroom',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(debateRoomSaveRequestDto)
    }).done(function(){
        alert('방생성 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}


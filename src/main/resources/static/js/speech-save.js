function speechSave(debateRoomId, userId){
    const form = document.getElementById("speech-save-form");
    const formData = new FormData(form);

    const speechSaveRequestDto = {
        debateRoomId: debateRoomId,
        userId: userId,
        speech: formData.get('speech')
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/speech',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(speechSaveRequestDto)
    }).done(function(){
        alert('완료');
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}


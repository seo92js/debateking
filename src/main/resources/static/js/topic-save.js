function topicSave(){
    const form = document.getElementById("topic-save-form");
    const formData = new FormData(form);

    const topicSaveRequestDto = {
        category: formData.get('category'),
        name: formData.get('name')
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/topic',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(topicSaveRequestDto)
    }).done(function(){
        alert('주제등록 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}


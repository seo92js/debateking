$('#category-select').trigger('change');

function debateRoomSave(){
    const form = document.getElementById("debateroom-save-form");
    const formData = new FormData(form);

    const speakingTime = document.getElementById("speaking-time-select").value;
    const discussionTime = document.getElementById("discussion-time-select").value;


    const debateRoomSaveRequestDto = {
        userId: formData.get('userId'),
        topicName: formData.get('topicName'),
        title: formData.get('title'),
        speakingTime: speakingTime,
        discussionTime: discussionTime
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/debateroom',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(debateRoomSaveRequestDto)
    }).done(function(response){
        alert('방생성 완료');
        window.location.href = '/debateroom/' + response;
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}

function getTopic(){
    const category = document.getElementById("category-select").value;
    console.log(category);

    $.ajax({
        type:'GET',
        url:'/api/v1/topic/' + category,
    }).done(function(response){
        $('#topic-list').empty();

        response.forEach(function(t) {
             $('#topic-list').append($('<option>').text(t.name));
        });

    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
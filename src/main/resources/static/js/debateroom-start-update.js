function debateStart(id){
    $.ajax({
        type: 'PUT',
        url: '/api/v1/debateroom/' + id + '/start',
    }).done(function(){
        location.reload();
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
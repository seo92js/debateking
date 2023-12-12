$('#category').trigger('change');

function getTopic(){
    const category = document.getElementById("category").value;
    console.log(category);

    $.ajax({
        type:'GET',
        url:'/api/v1/topic/' + category,
    }).done(function(response){
        $('#topic').empty();

        response.forEach(function(t) {
             $('#topic').append($('<option>').text(t.name));
        });

    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}
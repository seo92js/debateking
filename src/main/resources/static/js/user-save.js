function userSave(){
    const form = document.getElementById("user-save-form");
    const formData = new FormData(form);

    const userSaveRequestDto = {
        username: formData.get('username'),
        password: formData.get('password')
    };

    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/api/v1/user',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(userSaveRequestDto)
    }).done(function(){
        alert('회원가입 완료');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
    })
}


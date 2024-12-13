const nameLogin = document.getElementById('nameLogin');
const inputName = document.getElementById('inputName');
const icon = document.querySelector('#icon');
const inputPass = document.getElementById('inputPass');
const btnLogin = document.getElementById('btnLogin');

nameLogin.addEventListener('focus', () => {
    inputName.style.border = '1px solid #007bff';
    if(icon){

    icon.style.top = '50%';
    }
});

nameLogin.addEventListener('blur', () => {
    inputName.style.border = '1px solid #ced4da';
    if (icon){

    icon.style.top = 'calc(50% - 10px)';
    }
});

// Xử lý sự kiện login
icon?.addEventListener('click', () => {
    if (nameLogin.value !== '') {
        inputPass.style.display = 'block';
        btnLogin.style.display = 'block';
        document.querySelector("#alterClick").style.display = 'block';

    }
});



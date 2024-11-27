const btnLogOut = document.getElementById('btnLogOut');

btnLogOut.addEventListener('click', () => {
    localStorage.setItem('isLogin', false);
});
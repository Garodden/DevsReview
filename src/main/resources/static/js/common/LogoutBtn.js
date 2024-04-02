const logoutBtn = document.getElementById("logoutButton");
logoutBtn.addEventListener('click', (e) => {
    fetch('/logout', {
        method: 'GET',
    })
        .then(response => {
            console.log(response);
            if (response.redirected) {
                window.location.href = response.url; // Redirect to login page
            }
        })
        .catch(error => {
            console.error('Error logging out:', error);
        });
})
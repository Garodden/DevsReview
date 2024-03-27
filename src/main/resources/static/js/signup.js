const signUpBtn = document.querySelector("#signupBtn");
console.log(signUpBtn);
signUpBtn.addEventListener("click", () => {
    console.log("clicked")
    signup();
})
function signup() {
    var formData = {
        userId: document.getElementById("userId").value,
        password: document.getElementById("password").value,
        nickname: document.getElementById("nickname").value,
        email: document.getElementById("email").value,
        findPwQuestion: document.getElementById("findPwQuestion").value,
        findPw: document.getElementById("findPw").value
    };

    fetch('/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                alert('회원가입이 완료되었습니다.');
                window.location.href = '/login'; // 회원가입 완료 후 로그인 페이지로 이동
            } else {

                throw new Error('회원가입에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('회원가입에 실패했습니다.');
        });
}
document.getElementById("changePasswordForm").addEventListener("submit", function (event) {
    event.preventDefault();

    var formData = new FormData(this);
    var currentPassword = formData.get("currentPassword");
    var newPassword = formData.get("newPassword");
    var confirmNewPassword = formData.get("confirmNewPassword");

    fetch("/mypage/changePassword", {
        method: "POST",
        body: JSON.stringify({
            currentPassword: currentPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(function (response) {
        if (response.ok) {
            alert("비밀번호가 성공적으로 변경되었습니다.");
            window.location.href = "/logout";
        } else if (response.status === 400) {
            response.text().then(function (errorMessage) {
                alert(errorMessage);
            });
        } else {
            alert("서버와의 통신 중 문제가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    });
});

let elNewPassword = document.querySelector('#newPassword');
let elConfirmNewPassword = document.querySelector('#confirmNewPassword');
let elNewStrongPasswordMessage = document.querySelector('.newStrongPasswordMessage');
let elNewPasswordMismatchMessage = document.querySelector('.newPasswordMismatchMessage');

function validatePassword(elNewPassword) {
    // 8~16자 이내의 영문, 숫자 또는 기호 형식
    const regex = /^[a-zA-Z0-9!@#$%^&*()\-_=+[\]{};:'",.<>/?]{8,20}$/;
    return regex.test(elNewPassword);
}

function isMatch (newPassword, confirmNewPassword) {
    return newPassword === confirmNewPassword;
}

elConfirmNewPassword.disabled = true;

elNewPassword.onkeyup = function () {
    if (elNewPassword.value.length !== 0) {
        if(validatePassword(elNewPassword.value)) {
            elNewStrongPasswordMessage.classList.add('hide');
            elConfirmNewPassword.disabled = false;
        }
        else {
            elNewStrongPasswordMessage.classList.remove('hide');
        }
    }
    else {
        elNewStrongPasswordMessage.classList.add('hide');
        elConfirmNewPassword.disabled = true;
        elConfirmNewPassword.value = "";
    }
};

    elConfirmNewPassword.onkeyup = function () {
        if (elConfirmNewPassword.value.length !== 0) {
            if (isMatch(elNewPassword.value, elConfirmNewPassword.value)) {
                elNewPasswordMismatchMessage.classList.add('hide');
            } else {
                elNewPasswordMismatchMessage.classList.remove('hide');
            }
        } else {
            elNewPasswordMismatchMessage.classList.add('hide');
        }
};
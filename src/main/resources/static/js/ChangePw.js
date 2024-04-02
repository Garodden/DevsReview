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
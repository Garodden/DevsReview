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
            // 비밀번호가 성공적으로 변경되었을 때
            alert("비밀번호가 성공적으로 변경되었습니다.");
            window.location.href = "/mypage"; // 성공적으로 변경되었을 때 /mypage로 리다이렉트
        } else if (response.status === 400) {
            response.text().then(function (errorMessage) {
                // 서버에서 반환한 오류 메시지를 받아와서 처리
                alert(errorMessage);
            });
        } else {
            // 기타 오류가 발생했을 때
            alert("서버와의 통신 중 문제가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    });
});
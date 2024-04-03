document.getElementById("signoutUserForm").addEventListener("submit", function (event) {
    event.preventDefault();

    var formData = new FormData(this);
    var password = formData.get("password");
    var confirmPassword = formData.get("confirmPassword");

    if (confirm("정말로 회원 탈퇴를 하시겠습니까?")) {
        fetch("/mypage/signout", {
            method: "POST",
            body: JSON.stringify({
                password: password,
                confirmPassword: confirmPassword
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(function (response) {
            if (response.ok) {
                alert("회원탈퇴가 성공적으로 처리되었습니다.");
                window.location.href = "/logout";
            } else if (response.status === 400 || response.status === 404) {
                response.text().then(function (errorMessage) {
                    alert(errorMessage);
                });
            } else {
                alert("서버와의 통신 중 문제가 발생했습니다. 나중에 다시 시도해주세요.");
            }
        });
    }
});
document.getElementById('findPwForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var select = document.getElementById("findPwQuestion").value;
    switch (select) {
        case "place":
            select = "기억에 남는 추억의 장소는?";
            break;
        case "treasure":
            select = "자신의 보물 제1호는?";
            break;
        case "elementary":
            select = "자신의 출신 초등학교는?";
            break;
    }
    var formData = {
        userId: document.getElementById('userId').value,
        findPwQuestion: select,
        findPw: document.getElementById('findPwAnswer').value.trim(),
    };

    console.log(formData.userId);
    console.log(formData.findPwQuestion);
    console.log(formData.findPw);

    fetch('/user/find/pw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert("회원정보가 일치합니다");
            return response.text(); // JSON 형식이 아닌 경우 text로 반환
        })
        .then(data => {
            console.log("data: ", data);
            // 서버로부터 받은 초기화된 비밀번호를 화면에 출력
            var resetPwResultDiv = document.getElementById('resetPwResult');
            resetPwResultDiv.innerHTML = "<p>비밀번호가 초기화되었습니다<br>초기화된 비밀번호: " + data + "</p>";
        })
        .catch(error => {
            console.error('Error:', error);
            alert('비밀번호 초기화에 실패했습니다.');
        });
});



const signUpBtn = document.querySelector("#signupBtn");
const duplicateIdCheckButton = document.querySelector("#duplicateIdCheckButton");
const duplicateEmailCheckButton = document.querySelector("#duplicateEmailCheckButton");

let isEmptyChecked = false; // 빈 값 전달 여부
let isIdChecked = false; // id 중복 여부
let isEmailChecked = false; // email 중복 여부
let isFieldChecked = false; // 필드별 형식 준수 여부

signUpBtn.addEventListener("click", () => {
    checkIfValueIsEmpty();
    validateFields();
    if(!isEmptyChecked) {
        alert("모든 항목은 필수로 입력해야 합니다. ");
        return;
    }
    if(!isIdChecked) {
        alert("아이디 중복여부를 확인해주세요.");
        return;
    }
    if(!isEmailChecked) {
        alert("이메일 중복여부를 확인해주세요.");
        return;
    }
    if(!isFieldChecked) {
        alert("입력한 정보의 형식을 확인해주세요.");
        return;
    }
    signup();
})

duplicateIdCheckButton.addEventListener("click", async () => {
    var userId = document.getElementById("userId").value.trim();
    console.log("userId:", userId)
    try {
        if(userId === "") {
            alert("아이디를 입력하세요");
            return;
        } else if(!validateUserId(userId)) {
            document.getElementById("userId-error").innerText = "아이디는 6~12자 이내의 영문 또는 숫자 형식으로 입력해주세요.";
            isFieldChecked = false;
            return;
        }
        const response = await fetch('/user/find/id?userId=' + userId, {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json(); // JSON 데이터를 가져오기 위해 await 사용

        console.log("data: ", data);

        if (data === true) {
            alert("입력하신 아이디는 이미 사용중입니다.");
            // 중복된 아이디가 있으므로 회원가입 버튼 비활성화
            document.getElementById('signupBtn').disabled = true;
            isIdChecked = false;
        } else {
            alert("입력하신 아이디는 사용 가능합니다.");
            // 중복된 아이디가 없으므로 회원가입 버튼 활성화
            document.getElementById('signupBtn').disabled = false;
            isIdChecked = true;
        }
    } catch (error) {
        console.error('Error checking duplicate ID:', error);
        alert("중복 확인에 실패했습니다. 다시 시도해주세요.");
        isIdChecked = false;
    }
});

duplicateEmailCheckButton.addEventListener("click", async () => {
    var email = document.getElementById("email").value.trim();
    console.log("email:", email)
    try {
        if(email === "") {
            alert("이메일을 입력하세요");
            return;
        } else if (!validateEmail(email)) {
            alert("이메일 형식으로 입력하세요");
            isFieldChecked = false;
            return;
        }
        const response = await fetch('/user/find/email?email=' + email, {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json(); // JSON 데이터를 가져오기 위해 await 사용

        console.log("data: ", data);

        if (data === true) {
            alert("입력하신 이메일은 이미 사용중입니다.");
            // 중복된 이메일이 있으므로 회원가입 버튼 비활성화
            document.getElementById('signupBtn').disabled = true;
            isEmailChecked = false;
        } else {
            alert("입력하신 이메일은 사용 가능합니다.");
            // 중복된 이메일가 없으므로 회원가입 버튼 활성화
            document.getElementById('signupBtn').disabled = false;
            isEmailChecked = true;
        }
    } catch (error) {
        console.error('Error checking duplicate email:', error);
        alert("중복 확인에 실패했습니다. 다시 시도해주세요.");
        isEmailChecked = false;
    }
});

function checkIfValueIsEmpty() {
    const userId = document.getElementById("userId").value.trim();
    const password = document.getElementById("password").value.trim();
    const nickname = document.getElementById("nickname").value.trim();
    const email = document.getElementById("email").value.trim();
    const findPw = document.getElementById("findPw").value.trim();
    const findPwQuestion = document.getElementById("findPwQuestion").value;

    if (!userId || !password || !nickname || !email || !findPw || !findPwQuestion) {
        isEmptyChecked = false;
    } else if(userId && password && nickname && email && findPw && findPwQuestion) {
        isEmptyChecked = true
    }
}

function validateFields(){
    let userId = document.getElementById("userId").value;
    let password = document.getElementById("password").value;
    let nickname = document.getElementById("nickname").value;
    let email = document.getElementById("email").value;

    if (!validateUserId(userId)) {
        document.getElementById("userId-error").innerText = "아이디는 6~12자 이내의 영문 또는 숫자 형식으로 입력해주세요.";
        isFieldChecked = false;
    } else {
        document.getElementById("userId-error").innerText = "";
        isFieldChecked = true;
    }
    if (!validatePassword(password)) {
        document.getElementById("password-error").innerText = "비밀번호는 8~20자 이내의 영문, 숫자 또는 기호 형식으로 입력해주세요.";
        isFieldChecked = false;
    } else {
        document.getElementById("password-error").innerText = "";
        isFieldChecked = true;
    }
    if (!validateUsername(nickname)) {
        document.getElementById("nickname-error").innerText = "유저네임은 3~12자 이내의 영문, 한글 또는 숫자 형식으로 입력해주세요.";
        isFieldChecked = false;
    } else {
        document.getElementById("nickname-error").innerText = "";
        isFieldChecked = true;
    }
    if (!validateEmail(email)) {
        document.getElementById("email-error").innerText = "이메일 형식으로 입력해주세요.";
        isFieldChecked = false;
    } else {
        document.getElementById("email-error").innerText = "";
        isFieldChecked = true;
    }
}

function validateUserId(userId) {
    // 6~12자 이내 영문 또는 숫자 형식
    const regex = /^[a-zA-Z0-9]{6,12}$/;
    return regex.test(userId);
}

function validatePassword(password) {
    // 8~16자 이내의 영문, 숫자 또는 기호 형식
    const regex = /^[a-zA-Z0-9!@#$%^&*()\-_=+[\]{};:'",.<>/?]{8,20}$/;
    return regex.test(password);
}

function validateUsername(username) {
    // 3~12자 이내의 문자 또는 숫자 형식
    const regex = /^[a-zA-Z0-9가-힣]{3,12}$/;
    return regex.test(username);
}

function validateEmail(email) {
    // 이메일 형식
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function signup() {
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
        userId: document.getElementById("userId").value,
        password: document.getElementById("password").value,
        nickname: document.getElementById("nickname").value,
        email: document.getElementById("email").value,
        findPwQuestion: select,
        findPw: document.getElementById("findPw").value
    };

    console.log(formData.userId);
    console.log(formData.password);
    console.log(formData.nickname);
    console.log(formData.email);
    console.log(formData.findPwQuestion);
    console.log(formData.findPw);


    fetch('/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            console.log(response);
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
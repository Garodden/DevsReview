const findIdForm = document.getElementById("findIdForm");
const loadingDiv = document.querySelector("#loading");

findIdForm.addEventListener("submit", (e) => {
    e.preventDefault();
    sendEmail();
})

function sendEmail() {
    var formData = new FormData();
    formData.append("email", document.getElementById("email").value);

    loadingDiv.style.display = "block";

    fetch('/user/find/id', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            console.log(response);
            if (response.ok) {
                return response;
            } else {
                throw new Error('아이디 찾기에 실패했습니다.');
            }
        })
        .then(data => {
            loadingDiv.style.display = "none";
            alert("이메일이 발송되었습니다.");
            // isSearching = false;
            // location.href='/login' 고민중
        })
        .catch(error => {
            loadingDiv.style.display = "none";
            console.log('Error: ', error);
            alert('확인할 수 없는 이메일입니다.')
        });
}
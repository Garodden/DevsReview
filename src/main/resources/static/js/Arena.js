const createButton = document.getElementById('create-arena-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch(`/newArena`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body : JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            }),
        }).then(() => {
            alert('등록 완료되었습니다');
            location.replace("/arenas");
        })
    })
}



document.addEventListener('DOMContentLoaded', function() {
    const startButton = document.getElementById('start-button');
    const retryButton = document.getElementById('retry-button');
    const userTypedContent = document.getElementById('user-typed-content');
    const startTimeElement = document.getElementById('start-time');
    function sendAsyncRequest() {
        //콘텐츠 입력창 보이게 함
        userTypedContent.style.display = 'block';

        fetch(`/arenas/${boardId}/start`)
            .then(response => response.json()) // 서버로부터 받은 JSON 응답을 파싱
            .then(data => {
                // 'start-time' 요소에 시작 시간을 표시
                startTimeElement.innerText = `Start time: ${data.startTime}`;
                startTimeElement.style.display = 'block';
            })
            .catch(error => {
                console.error('Error:', error);
                startTimeElement.innerText = '시작 시간을 불러오는데 실패했습니다.\n 새로고침하세요.';
                startTimeElement.style.display = 'block';
            });
    }

    if (startButton) {
        startButton.addEventListener('click', sendAsyncRequest);
    }

    if (retryButton) {
        retryButton.addEventListener('click', sendAsyncRequest);
    }
});


document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('user-typed-content'); // 폼의 ID를 사용합니다.
    const contentInput = document.getElementById('contentInput'); // 사용자가 입력한 텍스트를 가져올 요소의 ID입니다.

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // 폼의 기본 제출 동작을 방지합니다.

        const formData = new FormData();
        formData.append('userTypedText', contentInput.value); // key-value 쌍을 FormData 객체에 추가합니다.

        fetch(`/arenas/${boardId}`, {
            method: 'POST',
            body: formData
        })
            .then(response => response.text()) // 서버로부터 받은 응답을 텍스트로 변환
            .then(data => {
                alert(data); // 서버로부터 받은 텍스트 표사
                window.location.href = `/arenas/${boardId}`; // 지정된 페이지로 리다이렉tus
            })
            .catch(error => console.error('Error:', error));
    });
});

document.addEventListener('copy', function(e) {
    e.preventDefault(); // 복사 이벤트를 막음
});

document.addEventListener('cut', function(e) {
    e.preventDefault(); // 잘라내기 이벤트를 막음
});

document.addEventListener('paste', function(e) {
    e.preventDefault(); // 붙여넣기 이벤트를 막음
});
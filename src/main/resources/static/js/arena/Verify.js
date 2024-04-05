

document.addEventListener('DOMContentLoaded', function() {
    const startVerifyButton = document.getElementById('start-verify-button');
    const userTypedContent = document.getElementById('user-typed-verify-content');
    const startTimeElement = document.getElementById('start-time');
    const contentInput = document.getElementById('content-input');

    function sendAsyncRequest() {
        //콘텐츠 입력창 보이게 함
        userTypedContent.style.display = 'block';
        console.log(verifyBoardId);
        fetch(`/arenas/${verifyBoardId}/verify/start`)
            .then(response => response.json()) // 서버로부터 받은 JSON 응답을 파싱
            .then(data => {
                // 'start-time' 요소에 시작 시간을 표시
                startTimeElement.innerText = `Start time: ${data.startTime}`;
                startTimeElement.style.display = 'block';
                contentInput.value ='';
                contentInput.value ='';
            })
            .catch(error => {
                console.error('Error:', error);
                startTimeElement.innerText = '시작 시간을 불러오는데 실패했습니다.\n 새로고침하세요.';
                startTimeElement.style.display = 'block';
            });
    }
    if (startVerifyButton) {
        startVerifyButton.addEventListener('click', sendAsyncRequest);
    }
});

//verify post 작동 로직
document.addEventListener('DOMContentLoaded', function() {
    const submitButton = document.getElementById('arena-verify-btn');
    const form = document.getElementById('user-typed-verify-content'); // 폼의 ID를 사용합니다.
    const contentInput = document.getElementById('content-input'); // 사용자가 입력한 텍스트를 가져올 요소의 ID

    if (form) {
        form.addEventListener('submit', function(event) {
            // 폼의 기본 제출 동작을 방지합니다.
            event.preventDefault();

            // FormData 객체를 생성하고, 사용자가 입력한 텍스트를 추가합니다.
            const formData = new FormData();
            formData.append('userTypedText', contentInput.value);

            // 서버로 POST 요청을 보냅니다.
            fetch(`/arena/${verifyBoardId}/verify`, {
                method: 'POST',
                body: formData
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                    if(data ==="아레나 개장 인증 로직 검증 아레나를 120초 내에 클리어하지 못했습니다. 다시 시도해보세요!")
                        window.location.href = `/arena/${verifyBoardId}/verify`;
                    else
                        window.location.href = `/arenas/${verifyBoardId}`;
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                });
        });
    }
});

const deleteArenaButton = document.getElementById('delete-arena-button');
if(deleteArenaButton) {
    deleteArenaButton.addEventListener('click', () => {
        let boardId = document.getElementById("board-id").value;
        fetch(`/arenas/${boardId}`, {method: 'DELETE'})
            .then(() => {
                alert('삭제가 완료되었습니다.');
                if (!document.referrer) {
                    window.location.href = '/arenas';
                }
                else {
                    window.history.back();
                }

            });
    });
}

document.addEventListener('copy', function(e) {
    e.preventDefault(); // 복사 이벤트를 막음
});

document.addEventListener('cut', function(e) {
    e.preventDefault(); // 잘라내기 이벤트를 막음
});

document.addEventListener('paste', function(e) {
    e.preventDefault(); // 붙여넣기 이벤트를 막음
});
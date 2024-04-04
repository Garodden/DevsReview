const createButton = document.getElementById('create-arena-btn');

if (createButton) {
    createButton.addEventListener('click', event => {

        fetch(`/newArena`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                boardRank: document.getElementById('selectBoardRank').value
            }),
        })
            .then(response => response.text()) // 응답을 텍스트로 변환하여 boardId 추출
            .then(boardId => {
                if(boardId) { // 성공적으로 boardId를 받았다면
                    alert('예비 등록이 완료됐습니다!\n'+
                        '아레나를 게시판에 등록하기 위해서는 본인이 생성한 아레나를 2분 이내로 클리어하여야 합니다\n ' +
                        '등록에 실패하더라도 [마이페이지->내가 생성한 아레나] 페이지에서 재시도 할 수 있습니다');
                    console.log(boardId);
                    window.location.href = `/arena/${boardId}/verify`; // boardId를 사용하여 리다이렉트

                } else {
                    // boardId가 없거나 잘못된 응답을 받았을 경우
                    console.log(boardId);
                    alert('등록 과정에 문제가 발생했습니다. 다시 시도해 주세요.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
    });
}


//시작 버튼 작동 로직
document.addEventListener('DOMContentLoaded', function() {
    const startButton = document.getElementById('start-button');
    const retryButton = document.getElementById('retry-button');
    const userTypedContent = document.getElementById('user-typed-content');
    const startTimeElement = document.getElementById('start-time');
    let boardId = document.getElementById("board-id").value;
    function sendAsyncRequest() {
        //콘텐츠 입력창 보이게 함
        userTypedContent.style.display = 'block';

        fetch(`/arenas/${boardId}/start`)
            .then(response => response.json()) // 서버로부터 받은 JSON 응답을 파싱
            .then(data => {
                // start-time 요소에 시작 시간을 표시
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

//post 작동 로직
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('user-typed-content'); // 폼의 ID를 사용합니다.
    const contentInput = document.getElementById('content-input'); // 사용자가 입력한 텍스트를 가져올 요소의 ID입니다.
    let boardId = document.getElementById("board-id").value;
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
                location.replace(`/arenas/${boardId}`); // 지정된 페이지로 리다이렉트
            })
            .catch(error => console.error('Error:', error));
    });
});

const deleteArenaButton = document.getElementById('delete-arena-button');
if(deleteArenaButton){
    deleteArenaButton.addEventListener('click',()=>{
        let boardId = document.getElementById("board-id").value;
        fetch(`/arenas/${boardId}`,{method: 'DELETE'})
            .then(()=>{alert('삭제가 완료되었습니다.');
                location.replace('/arenas');
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
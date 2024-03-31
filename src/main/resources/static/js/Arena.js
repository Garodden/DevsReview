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

document.addEventListener('copy', function(e) {
    e.preventDefault(); // 복사 이벤트를 막음
});

document.addEventListener('cut', function(e) {
    e.preventDefault(); // 잘라내기 이벤트를 막음
});

document.addEventListener('paste', function(e) {
    e.preventDefault(); // 붙여넣기 이벤트를 막음
});
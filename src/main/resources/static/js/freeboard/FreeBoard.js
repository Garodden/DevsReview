const deleteFreeButton = document.querySelector('#delete-freeboard-button');
if(deleteFreeButton){
    deleteFreeButton.addEventListener('click',()=>{
        let board_id = document.getElementById("board-id").value;
        fetch(`/board/${board_id}`,{method: 'DELETE'})
            .then(()=>{alert('삭제가 완료되었습니다.');
                location.replace('/board');});
    });
}

let deactivatedButton = document.querySelectorAll('.deactivated');
deactivatedButton.forEach(btn=>btn.addEventListener('click',()=>{alert("랭크가 낮아 게시글에 접근이 불가능합니다.")}));

const deleteFreeButton = document.querySelector('#delete-freeboard-button');
if(deleteFreeButton){
    deleteFreeButton.addEventListener('click',()=>{
        let board_id = document.getElementById("board-id").value;
        fetch(`/board/${board_id}`,{method: 'DELETE'})
            .then(()=>{alert('삭제가 완료되었습니다.');
                location.replace('/board');});
    });
}
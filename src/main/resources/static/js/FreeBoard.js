const deleteFreeButton = document.querySelector('#delete-freeboard-button');
if(deleteFreeButton){
    deleteFreeButton.addEventListener('click',()=>{
        let board_id = document.getElementById("board-id").value;
        fetch(`/board/${board_id}`,{method: 'DELETE'})
            .then(()=>{alert('삭제가 완료되었습니다.');
                location.replace('/board');});
    });
}

const likeButton = document.querySelector('.like-button');
likeButton.addEventListener('click',()=>{
    let board_id = document.getElementById("board-id").value;
    let id = document.getElementById('logined-id').value;
    if(id !== ""){
        fetch(`/api/like?boardId=${board_id}&id=${id}`,{method:'POST'})
            .then(()=>location.replace(`/board/${board_id}`));

    }
    console.log("포인트");
})

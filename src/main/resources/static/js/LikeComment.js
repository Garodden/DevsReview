const likeButton = document.querySelector('.like-button');
likeButton.addEventListener('click',()=>{
    let board_id = document.getElementById('board-id').value;
    let id = document.getElementById('logined-id').value;
    if(id !== ""){
        fetch(`/api/like?boardId=${board_id}&id=${id}`,{method:'POST'})
            .then(()=>location.replace(`/board/${board_id}`));

    }
});

const commitCommentButton = document.getElementById('commit-comment');
commitCommentButton.addEventListener('click',()=>{
    let board_id = document.getElementById('board-id').value;
    let content = document.getElementById('write-comment').value;
    let id = document.getElementById('logined-id').value;
    if(id !== ""){
        fetch(`/comments/${board_id}`,{
            method:'POST',
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                "content":content
            })
        })
            .then(()=>location.replace(`/board/${board_id}`));

    }
});

const deleteCommentButton = document.getElementById('delete-comment');

deleteCommentButton.addEventListener('click',()=>{
    let board_id = document.getElementById('board-id').value;
    let commentId=document.getElementById('comment-id').value;
    fetch(`/comments/${commentId}`,{method:'DELETE'})
        .then(()=>location.replace(`/board/${board_id}`));
})
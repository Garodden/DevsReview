const likeButton = document.querySelector('.like-button');

if(likeButton) {
    likeButton.addEventListener('click', () => {
        let boardId = document.getElementById('board-id').value;
        let id = document.getElementById('logged-in-id').value;
        if (id !== "") {
            fetch(`/api/like?boardId=${boardId}&id=${id}`, {method: 'POST'})
                .then(() => location.reload(true));
        }else{
            alert("로그인하지 않았습니다.");
        }
    });
}
else{
    console.log("there's no like button! the value is null")
}

const commitCommentButton = document.getElementById('commit-comment');
if(commitCommentButton) {
    commitCommentButton.addEventListener('click', () => {
        let boardId = document.getElementById('board-id').value;
        let content = document.getElementById('write-comment').value;
        let id = document.getElementById('logged-in-id').value;
        if (id !== "") {
            fetch(`/comments/${boardId}`, {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    "content": content
                })
            })
                .then(() => location.reload());
        }else{
            alert("로그인하지 않았습니다.");
        }
    });
}
else{
    console.log("there's no commit comment button! the value is null")
}


const deleteCommentButton = document.querySelectorAll('.delete-comment');

if(deleteCommentButton){
    deleteCommentButton.forEach(btn=>{
        btn.addEventListener('click',()=>{

            let commentId=btn.getAttribute('comment-id');
            fetch(`/comments/${commentId}`,{method:'DELETE'})
                .then(()=>location.reload());
        });
    });
}
else{
    console.log("there's no delete comment button! the value is null");
}
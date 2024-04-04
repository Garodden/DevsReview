function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";
    oScript.src = "http://code.jquery.com/jquery-1.6.2.min.js";
    document.getElementsByTagName("head")[0].appendChild(oScript);
}

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

let selectedSort = document.getElementById('sorting');
if(selectedSort) {
    selectedSort.addEventListener('change', (event) => {
        let selected = selectedSort.value;
        if (selected === "likes") {
            location.href = "/board";
            // $("#sorting").val("likes").prop("selected",true);

        } else if (selected === "created") {
            location.href = "/board/sort=2";
            // selectedSort.options[0].selected=false;
            // $("#sorting").val("created").prop("selected",true);

        }
    });
}
const updateButton = document.querySelector('.edit');
if(updateButton) {
    updateButton.addEventListener('click', () => {
        let board_id=document.getElementById('board-id').value;
        let userId = document.getElementById('logined-id').value;
        let signoutStr= userId.slice(-4);
        if(signoutStr!=='(탈퇴)'){
            location.href = `/board/${board_id}/update`;
        }else{
            alert('탈퇴유저는 수정이 불가합니다.');
        }
    });
}

if(window.location.pathname==="/board"){
    selectedSort.options[0].innerText = 'likes'
}else if(window.location.pathname==="/board/sort=2"){
    selectedSort.options[0].innerText = 'created'
}
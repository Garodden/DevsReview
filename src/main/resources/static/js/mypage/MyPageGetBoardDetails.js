const rows = document.querySelectorAll(".clickable-row");
rows.forEach(row => {
    row.addEventListener("click", () => {
        const boardId = row.getAttribute("board-id");
        const boardType = row.getAttribute("board-type");

        if (boardType === "1") {
            window.location.href = `/board/${boardId}`;
        } else if (boardType === "2" || boardType === "3") {
            window.location.href = `/arenas/${boardId}`;
            console.log(window.location.href);
        }
    })
})

// mypage 공통 메뉴 (.list-group-container의 top 값 자동 조정)
document.addEventListener("DOMContentLoaded", function() {
    var headerHeight = document.querySelector('.p-5').offsetHeight; // 헤더의 높이 측정
    var listGroupContainer = document.querySelector('.list-group-container');
    listGroupContainer.style.top = headerHeight + 'px'; // top 값을 헤더의 높이로 설정
});

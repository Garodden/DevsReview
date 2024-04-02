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

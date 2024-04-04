document.addEventListener('DOMContentLoaded', function() {
    const referenceTextElement = document.getElementById('reference-text');
    const contentInput = document.getElementById('content-input');
    const originalText = referenceTextElement.innerText;

    contentInput.addEventListener('input', function() {
        const userInput = contentInput.value;
        let matchIndex=0;

        const matchLength = userInput.length; // 사용자 입력 길이
        const matchedPart = originalText.slice(0, matchLength); // 일치하는 부분
        const unmatchedPart = originalText.slice(matchLength); // 일치하지 않는 부분

        // 입력에 따라 참조 텍스트 업데이트
        if(originalText.startsWith(userInput)) {
            referenceTextElement.innerHTML = `<span style="color: #3296f5"> ${matchedPart} </span><span style= "color: black"> ${unmatchedPart}</span>`;
        }
        else{
            referenceTextElement.innerHTML = `<span style="color: #c77171"> ${matchedPart} </span><span style= "color: black"> ${unmatchedPart}</span>`;
        }
    })
});
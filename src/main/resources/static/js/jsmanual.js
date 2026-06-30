"use strict";

// 文字数カウント
const textarea = document.getElementById('textArea');
const insert = document.getElementById('textCount');

textarea.addEventListener('input', function () {
    const count = textarea.value.length;
    insert.textContent = count;
});

// ラジオボタンの背景色切り替え
const radioContent = document.getElementsByName('radio1');

function updateBackgrounds() {
    radioContent.forEach(radio => {
        const label = radio.nextElementSibling;
        if (radio.checked) {
            label.classList.add('bg-selected');
            label.classList.remove('bg-unselected');
        } else {
            label.classList.add('bg-unselected');
            label.classList.remove('bg-selected');
        }
    });
}

radioContent.forEach(radio => {
    radio.addEventListener('change', updateBackgrounds);
});

updateBackgrounds();

// カウントダウン
window.onload = function () {
    let timeLeft = 10;
    const timerElement = document.getElementById('timer');
    const timerInterval = setInterval(function () {
        timeLeft -= 1;
        timerElement.textContent = timeLeft;

        if (timeLeft <= 0) {
            clearInterval(timerInterval);
            timerElement.textContent = 'カウントダウン終了';
        }
    }, 1000);
};
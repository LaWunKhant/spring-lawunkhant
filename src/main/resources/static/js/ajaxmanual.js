"use strict";
document.addEventListener("DOMContentLoaded", function () {
    const allBtn = document.querySelector(".allBtn");
    const listEl = document.querySelector(".list");
    let xhr = new XMLHttpRequest();

    allBtn.addEventListener("click", function () {
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    const data = xhr.response;
                    console.log(data);
                    data.forEach(emp => {
                        const li = document.createElement("li");
                        li.textContent = `ID: ${emp.id}, 名前: ${emp.name}, 年齢: ${emp.age}`;
                        listEl.appendChild(li);
                    });
                }
            }
        }
        xhr.open("GET", "/ajax/all");
        xhr.responseType = "json";
        xhr.send();
    });

    const form = document.getElementById("find");
    const result = document.getElementById("result");

    let xhr2 = new XMLHttpRequest();
    let token = document.querySelector('input[name=_csrf]').value;

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const employeeId = document.getElementById("id").value;

        xhr2.onreadystatechange = function () {
            if (xhr2.readyState == 4) {
                if (xhr2.status == 200) {
                    const searchId = xhr2.response;
                    result.textContent = `ID: ${searchId.id}, 名前: ${searchId.name}`;
                }
            }
        }
        xhr2.open("POST", "/ajax/find");
        xhr2.responseType = "json";
        xhr2.setRequestHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        xhr2.setRequestHeader("X-CSRF-Token", token);
        xhr2.send("id=" + encodeURIComponent(employeeId));
    });

    // REST API - GET
    document.getElementById("getEmp").addEventListener("click", function() {
        const xhrGet = new XMLHttpRequest();
        xhrGet.onreadystatechange = function() {
            if (xhrGet.readyState == 4) {
                if (xhrGet.status == 200) {
                    const data = xhrGet.response;
                    document.getElementById("apiResult").textContent = JSON.stringify(data, null, 2);
                }
            }
        };
        xhrGet.open("GET", "/ajax/api/employees/1");
        xhrGet.responseType = "json";
        xhrGet.send();
    });

    // REST API - POST
    document.getElementById("createEmp").addEventListener("click", function() {
        const xhrPost = new XMLHttpRequest();
        xhrPost.onreadystatechange = function() {
            if (xhrPost.readyState == 4) {
                if (xhrPost.status == 200) {
                    const data = xhrPost.response;
                    document.getElementById("apiResult").textContent = "登録内容：" + JSON.stringify(data, null, 2);
                }
            }
        };
        const requestData = JSON.stringify({
            code: "0100",
            name: "町田",
            age: 34
        });
        xhrPost.open("POST", "/ajax/api/employees/create");
        xhrPost.responseType = "json";
        xhrPost.setRequestHeader("content-type", "application/json");
        xhrPost.setRequestHeader("X-CSRF-Token", token);
        xhrPost.send(requestData);
    });

    // 練習問題1: 名前で検索
    const searchForm = document.getElementById("searchForm");
    const searchResult = document.getElementById("searchResult");

    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const keyword = document.getElementById("keyword").value;

        fetch("/ajax/searchByName", {
            method: "POST",
            headers: {
                "content-type": "application/x-www-form-urlencoded;charset=UTF-8",
                "X-CSRF-Token": token
            },
            body: "keyword=" + encodeURIComponent(keyword)
        })
        .then(response => response.json())
        .then(data => {
            searchResult.innerHTML = "";
            data.forEach(emp => {
                const li = document.createElement("li");
                li.textContent = `ID: ${emp.id}, 名前: ${emp.name}, 年齢: ${emp.age}`;
                searchResult.appendChild(li);
            });
        })
        .catch(error => console.error(error));
    });
	
	// 練習問題2: セレクトボックスで年齢確認
	const empSelect = document.getElementById("empSelect");
	const ageResult = document.getElementById("ageResult");

	empSelect.addEventListener("change", function () {
	    const selectedId = empSelect.value;
	    if (!selectedId) {
	        ageResult.textContent = "";
	        return;
	    }

	    fetch("/ajax/api/employees/" + selectedId, { method: "GET" })
	    .then(response => response.json())
	    .then(data => {
	        ageResult.textContent = `${data.name}さんの年齢: ${data.age}歳`;
	    })
	    .catch(error => console.error(error));
	});
	
	// jQuery Ajax実装
	$(function() {
	    // 全データ取得してコンソールに表示 & HTMLに書き込み
	    $(".allBtn").on("click", function(e) {
	        $.ajax({
	            url: "/ajax/all",
	            type: "GET",
	        }).done(function(data) {
	            console.log(data);
	            $.each(data, function(index, value) {
	                let html = `<li>ID：${value.id} , name：${value.name}</li>`;
	                $(".list").append(html);
	            });
	        }).fail(function(error) {
	            console.log("fail!");
	            console.log(error);
	        });
	    });

	    // IDで検索
	    $('#find').on('submit', function(e) {
	        e.preventDefault();
	        $.ajax({
	            headers: {
	                'X-CSRF-TOKEN': $("input[name=_csrf]").val(),
	            },
	            url: $(this).attr("action"),
	            type: 'POST',
	            data: {
	                'id': $('#id').val(),
	            },
	            datatype: 'json',
	        }).done(function(data) {
	            console.log(data);
	            $('#result').append('ID：' + data["id"] + '<br>name：' + data["name"] + "<br>");
	        }).fail(function() {
	            console.log('fail!');
	        });
	    });
	});
});
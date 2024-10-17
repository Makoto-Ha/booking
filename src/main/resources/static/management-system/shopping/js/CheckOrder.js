
// 加載頁面時，檢查所有 input 欄位
document.addEventListener("DOMContentLoaded", function() {
	let allEmpty = Array.from(document.querySelectorAll('input')).every(input => input.value.trim() === "");
	if (allEmpty) {
		document.querySelector('button[type="submit"]').disabled = true; //禁用提交按鈕
	}
});

// 欄位檢查
function checkUserId(showError = true) {
	let userId = document.getElementById("userId").value.trim();
	let sp = document.getElementById("userIdSP");
	if (userId === "") {
		if (showError) sp.innerHTML = "不可為空";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	} else {
		sp.innerHTML = "";
		return true;
	}
}

// 檢查所有欄位是否符合條件
function checkFormValidity() {
	document.querySelector('button[type="submit"]').disabled = !(checkUserId(false));
}

document.getElementById("userId").addEventListener("input", checkUserId);
document.getElementById("userId").addEventListener("change", checkFormValidity);




// 加載頁面時，檢查所有 input 欄位
document.addEventListener("DOMContentLoaded", function() {
	let allEmpty = Array.from(document.querySelectorAll('input')).every(input => input.value.trim() === "");
	if (allEmpty) {
		document.querySelector('button[type="submit"]').disabled = true; //禁用提交按鈕
	}
});

// 欄位檢查
function checkName(showError = true) {
	let categoryName = document.getElementById("categoryName").value.trim();
	let sp = document.getElementById("categoryNameSP");
	if (categoryName === "") {
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
	document.querySelector('button[type="submit"]').disabled = !(checkName(false));
}

document.getElementById("categoryName").addEventListener("input", checkName);
document.getElementById("categoryName").addEventListener("change", checkFormValidity);



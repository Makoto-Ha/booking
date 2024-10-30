



// 加載頁面時，檢查所有 input 欄位

document.addEventListener("DOMContentLoaded", function() {
	let allEmpty = Array.from(document.querySelectorAll('input')).every(input => input.value.trim() === "");
	if (allEmpty) {
		document.querySelector('button[type="submit"]').disabled = true; //禁用提交按鈕
	}
});
// 欄位檢查 (新增一個參數來控制是否顯示錯誤訊息)
function checkName(showError = true) {
	let productName = document.getElementById("productName").value.trim();
	let sp = document.getElementById("productNameSP");
	if (productName === "") {
		if (showError) sp.innerHTML = "不可為空";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	} else {
		sp.innerHTML = "";
		return true;
	}
}

function checkCategory(showError = true) {
	let category = document.getElementById("categoryId").value.trim();
	let sp = document.getElementById("categoryIdSP");
	let rule = /^[1-5]00$/;
	if (category === "") {
		if (showError) sp.innerHTML = "不可為空";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	} else if (rule.test(category)) {
		sp.innerHTML = "";
		return true;
	} else {
		if (showError) sp.innerHTML = "格式錯誤";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	}
}

function checkState(showError = true) {
	let state = document.getElementById("productState").value.trim();
	let sp = document.getElementById("productStateSP");
	let rule = /^[1-2]$/;
	if (state === "") {
		if (showError) sp.innerHTML = "不可為空";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	} else if (rule.test(state)) {
		sp.innerHTML = "";
		return true;
	} else {
		if (showError) sp.innerHTML = "格式錯誤";
		document.querySelector('button[type="submit"]').disabled = true;
		return false;
	}
}

document.getElementById("productName").addEventListener("blur", checkName);
document.getElementById("categoryId").addEventListener("blur", checkCategory);
document.getElementById("productState").addEventListener("blur", checkState);

// 檢查所有欄位是否符合條件
function checkFormValidity() {
	document.querySelector('button[type="submit"]').disabled = !(checkName(false) && checkCategory(false) && checkState(false));
}

document.getElementById("productName").addEventListener("change", checkFormValidity);
document.getElementById("categoryId").addEventListener("change", checkFormValidity);
document.getElementById("productState").addEventListener("change", checkFormValidity);

//-------------------------------------------------------------------------

// -----------------------------------------------------------------------------

// 送出時將其他空欄位設為預設值
document.getElementById("productForm").addEventListener("submit", function(event) {

	let productDescription = document.getElementById("productDescription").value.trim();
	if (productDescription === "") {
		document.getElementById("productDescription").value = "未輸入";
	}

	let productPrice = document.getElementById("productPrice").value.trim();
	if (productPrice === "") {
		document.getElementById("productPrice").value = "0";
	}

	let productSales = document.getElementById("productSales").value.trim();
	if (productSales === "") {
		document.getElementById("productSales").value = "0";
	}

	let productInventory = document.getElementById("productInventory").value.trim();
	if (productInventory === "") {
		document.getElementById("productInventory").value = "0";
	}

});
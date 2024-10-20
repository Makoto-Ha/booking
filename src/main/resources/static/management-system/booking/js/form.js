const selectCity = document.getElementById("select-city");

data.forEach(location => {
	let optionCity = document.createElement("option");
	optionCity.textContent = location.name;
	selectCity?.appendChild(optionCity);
});

let selectDistrict = document.getElementById("select-district");

data[0].districts.forEach(area => {
	let option = document.createElement("option");
	option.textContent = area.name;
	selectDistrict?.appendChild(option);
});

selectCity?.addEventListener("change", e => {
	selectDistrict.innerHTML = "";
	let selectCityName = e.target.value;

	data.find(location => location.name === selectCityName).districts.forEach(area => {
		let option = document.createElement("option");
		option.textContent = area.name;
		selectDistrict?.appendChild(option);
	});
});

const submitButton = document.getElementById('submit');
const requiredInputs = document.querySelectorAll('.form-group input[type="text"]');

// 正則表達式
const validationRules = {
    'roomtypeName': /^[\u4e00-\u9fa5][\w\W]{3,}/, // 開頭中文且必須要有四個中文
    'roomtypeAddress': /^[\u4e00-\u9fa5]{2,3}市[\u4e00-\u9fa5]{2,3}區[\u4e00-\u9fa5]+(?:街|路|大道)[\u4e00-\u9fa5\d]+號(?:\d+樓)?$/, // 台灣地址  
    'roomtypeDescription': /^(?=(?:.*[\u4e00-\u9fa5]){4})(?=.{8,}).*$/ // 至少八個字，且必須要有四個中文
};

// 添加驗證所需事件
requiredInputs.forEach(input => {
    input.addEventListener('input', validateInput);
    input.addEventListener('blur', validateInput);
});

function validateInput(event) {
    const input = event.target;
    const rule = validationRules[input.name];

    if (rule) {
        if (!rule.test(input.value)) {
            input.classList.add('invalid'); // 添加無效類名
        } else {
            input.classList.remove('invalid'); // 移除無效類名
        }
    }

    // 檢查所有輸入
    validateForm();
}

function validateForm() {
    let allValid = true;

    requiredInputs.forEach(input => {
        const rule = validationRules[input.name];

        if (rule && !rule.test(input.value)) {
            allValid = false; // 一個規則不成功，則失敗。
            submitButton.disabled = true;
        }
    });

    // 確認驗證是否全部通過
    submitButton.disabled = !allValid;
}

// 上傳檔案

const fileInput = document.getElementById('file-upload');
const fileNameDisplay = document.getElementById('file-name');

fileInput.addEventListener('change', function() {
   if (fileInput.files.length > 0) {
       fileNameDisplay.textContent = fileInput.files[0].name;
   } else {
       fileNameDisplay.textContent = '尚未選擇檔案';
   }
});

// 返回上一頁
document.querySelector('.cancel')?.addEventListener('click', function() {
	history.back();
});
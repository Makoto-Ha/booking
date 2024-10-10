// 當用戶選擇入住日和退房日後自動新增日期
document.getElementById('check-out-date').addEventListener('change', function() {
	const checkInDate = document.getElementById('check-in-date').value;
	const checkOutDate = this.value;

	// 如果兩個日期都有選中，則新增下一組日期欄位
	if (checkInDate && checkOutDate) {
		addNewDateFields();
	}
});

let count = 1; // 用來追蹤新增的日期欄位數量

function addNewDateFields() {
	const container = document.getElementById('date-container');

	// 新增入住日欄位
	const checkInDiv = document.createElement('div');
	checkInDiv.classList.add('form-group');
	const checkInLabel = document.createElement('label');
	checkInLabel.innerText = `入住日 ${count + 1}`;
	const checkInInput = document.createElement('input');
	checkInInput.type = 'date';
	checkInInput.name = `checkInDate${count + 1}`;

	checkInDiv.appendChild(checkInLabel);
	checkInDiv.appendChild(checkInInput);
	container.appendChild(checkInDiv);

	// 新增退房日欄位
	const checkOutDiv = document.createElement('div');
	checkOutDiv.classList.add('form-group');
	const checkOutLabel = document.createElement('label');
	checkOutLabel.innerText = `退房日 ${count + 1}`;
	const checkOutInput = document.createElement('input');
	checkOutInput.type = 'date';
	checkOutInput.name = `checkOutDate${count + 1}`;

	checkOutDiv.appendChild(checkOutLabel);
	checkOutDiv.appendChild(checkOutInput);
	container.appendChild(checkOutDiv);

	// 更新 count
	count++;

	// 設置新的退房日監聽器
	checkOutInput.addEventListener('change', function() {
		const newCheckInDate = checkInInput.value;
		const newCheckOutDate = this.value;

		if (newCheckInDate && newCheckOutDate) {
			addNewDateFields(); // 當用戶選擇新的入住和退房日期後，繼續新增下一組
		}
	});
	
	// 獲取表單數據並且發送
	const form = document.getElementById('form');
	form.addEventListener('submit', async function(e) {
		e.preventDefault();

		let formData = new FormData(e.target);
		let data = {};
		let bookingOrderItems = [], index = 0;
		let formParams = {};
		for(let [k, v] of formData.entries()) {
			formParams[k] = v;
			if(index % 2 == 0) {
				bookingOrderItems.push(formParams);
				formParams = {};
			}
			index++;
		}
		
		let roomtype = bookingOrderItems.shift();
		bookingOrderItems.pop();
		data.bookingOrderItems = bookingOrderItems;
		data.roomtypeId = roomtype.roomtypeId;
		console.log(data);
		
		await fetch('/booking/management/booking/create', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		});
		
		location.href = "/booking/management/booking";
	});
}

document.querySelector('.cancel').addEventListener('click', function() {
	history.back();
});
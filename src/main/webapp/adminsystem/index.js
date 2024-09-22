function bindAdminSystemEvent() {
	const sidebarLinks = document.querySelectorAll('.sidebar ul li a');
	const pageTitle = document.getElementById('page-title');
	const contentArea = document.getElementById('content');

	// 頁面數據模擬
	const pages = {
		'admin-management': '這裡顯示管理員列表的內容...',
		'member-management': '這裡顯示會員管理的內容...',
		'booking-management': '這裡顯示訂房管理的內容...',
		'package-management': '這裡顯示套裝行程管理的內容...',
		'store-management': '這裡顯示商城管理的內容...',
		'marketing-management': '這裡顯示行銷活動管理的內容...',
		'review-management': '這裡顯示評論管理的內容...'
	};

	const currentList = {
		id: null
	};

	// 導航按鈕點擊事件處理
	sidebarLinks.forEach(link => {
		link.addEventListener('click', (event) => {
			// 移除之前的活躍狀態
			sidebarLinks.forEach(link => link.classList.remove('active'));

			// 添加當前按鈕的活躍狀態
			link.classList.add('active');

			const page = link.getAttribute('data-page');
			pageTitle.textContent = link.textContent;
			contentArea.textContent = pages[page];
		});
	});

	// 轉送到新增
	document.querySelector('.add-btn').addEventListener('click', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		location.href = `/booking/${lastHref}/create/jsp`;
	});

	// 轉送到編輯
	document.querySelectorAll('.edit-btn').forEach(editButton => {
		editButton.addEventListener('click', function(e) {
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			let hrefSplit = location.pathname.split('/');
			let lastHref = hrefSplit[2];
			location.href = `/booking/${lastHref}/edit/jsp?${lastHref}Id=${currentListId}`;
		});
	});

	// 轉送到查詢
	document.getElementById('search-btn').addEventListener('click', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		location.href = `/booking/${lastHref}/select/jsp`;
	});

	// 開啟檢查視窗
	document.querySelectorAll('.check-btn').forEach(button => {
		button.addEventListener('click', function(e) {
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			currentList.id = currentListId;
			let hrefSplit = location.pathname.split('/');
			let lastHref = hrefSplit[2];

			fetch(`/booking/${lastHref}/json/${currentListId}`)
				.then(res => res.json())
				.then(data => {
					let values = [];
					for (let k in data) {
						values.push(data[k]);
					}

					values.shift();
					const inputs = document.querySelectorAll('.form-group > input');
					inputs.forEach((input, i) => input.value = values[i]);
				});

			// 顯示視窗
			document.getElementById('modal-overlay').style.display = 'flex';
		});
	});

	// 取消按鈕行為
	document.getElementById('modal-cancel-btn').addEventListener('click', function() {
		document.getElementById('modal-overlay').style.display = 'none';
	});

	// 為所有刪除按鈕添加事件監聽
	document.querySelectorAll('.delete-btn').forEach(button => {
		button.addEventListener('click', function(e) {
			document.getElementById('delete-overlay').style.display = 'flex';
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			currentList.id = currentListId;
		});
	});

	// 為確認刪除按鈕添加事件監聽
	document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
		// 在這裡添加刪除項目的邏輯
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		fetch(`/booking/${lastHref}/delete`, {
			method: 'POST',
			body: new URLSearchParams({
				[`${lastHref}Id`]: currentList.id
			})
		})
			.then(res => {
				location.reload();
			});

		alert('項目已刪除！');  // 示例提示，可根據需求替換

		// 隱藏彈出視窗
		document.getElementById('delete-overlay').style.display = 'none';
	});

	// 為取消按鈕添加事件監聽
	document.getElementById('cancelDeleteBtn').addEventListener('click', function() {
		document.getElementById('delete-overlay').style.display = 'none';
	});

	document.querySelectorAll('.content-area-page').forEach(function(page, i) {
		if (i >= 1) {
			page.style.left = i * 130 + "px";
		}
		page.addEventListener('click', function(e) {
			let url = e.target.dataset.url;
			location.href = url;
		});

	});

	const prevPageBtn = document.getElementById("prev-page");
	const nextPageBtn = document.getElementById("next-page");
	const pageInput = document.getElementById("page-input");
	let currentPage = parseInt(pageInput.value);
	// 上一頁
	prevPageBtn.addEventListener("click", () => {
		if (currentPage <= 1) {
			return;
		}

		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		let jsonData = requestParameters ? JSON.parse(requestParameters) : "";
		let paramters = jsonData.paramters, extraValues = jsonData.extraValues;

		const params = {};
		Object.assign(params, paramters, extraValues, {switchPage: currentPage-1});
		
		let queryString = new URLSearchParams(params).toString();
		
		fetch(`/booking/${lastHref}/select?${queryString}`, {
		}).then(res => res.text()).then(html => {
			document.documentElement.innerHTML = html
			bindAdminSystemEvent();
			const scripts = document.querySelector('.main-list').getElementsByTagName('script');
			for (let script of scripts) {
				const newScript = document.createElement('script');
				newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
				document.head.appendChild(newScript); // 将脚本添加到页面中执行
				document.head.removeChild(newScript); // 执行后移除脚本标签
			}
		});
	});

	// 下一頁
	nextPageBtn.addEventListener("click", () => {
		if (parseInt(pageInput.value) >= totalPages) {
			return;
		}

		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		let jsonData = requestParameters ? JSON.parse(requestParameters) : "";
		let paramters = jsonData.paramters, extraValues = jsonData.extraValues;
		
		const params = {};
		Object.assign(params, paramters, extraValues, {switchPage: currentPage+1});

		let queryString = new URLSearchParams(params).toString();
		fetch(`/booking/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				document.documentElement.innerHTML = html
				bindAdminSystemEvent();
				const scripts = document.querySelector('.main-list').getElementsByTagName('script');
				for (let script of scripts) {
					const newScript = document.createElement('script');
					newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
					document.head.appendChild(newScript); // 将脚本添加到页面中执行
					document.head.removeChild(newScript); // 执行后移除脚本标签
				}
			});
	});

	// 輸入跳轉頁數
	pageInput.addEventListener("input", () => {
		let page = parseInt(pageInput.value);
		if (page < 1) {
			pageInput.value = 1;
			page = 1;
		}

		if (page > totalPages) page = totalPages;

		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		let jsonData = requestParameters ? JSON.parse(requestParameters) : "";
		let paramters = jsonData.paramters, extraValues = jsonData.extraValues;

		const params = {};
		Object.assign(params, paramters, extraValues, {switchPage: page});
		
		let queryString = new URLSearchParams(params).toString();
		fetch(`/booking/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				document.documentElement.innerHTML = html
				bindAdminSystemEvent();
				const scripts = document.querySelector('.main-list').getElementsByTagName('script');
				for (let script of scripts) {
					const newScript = document.createElement('script');
					newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
					document.head.appendChild(newScript); // 将脚本添加到页面中执行
					document.head.removeChild(newScript); // 执行后移除脚本标签
				}
			});
	});

	// 根據名字簡單查詢
	document.getElementById('search-input').addEventListener('change', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		
		let queryString = new URLSearchParams({
				[`${lastHref}Name`]: this.value
			}).toString();
		
		fetch(`/booking/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				document.documentElement.innerHTML = html
				bindAdminSystemEvent();
				const scripts = document.querySelector('.main-list').getElementsByTagName('script');
				for (let script of scripts) {
					const newScript = document.createElement('script');
					newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
					document.head.appendChild(newScript); // 将脚本添加到页面中执行
					document.head.removeChild(newScript); // 执行后移除脚本标签
				}
			})
	});

	// 選擇排序種類
	document.getElementById('select-order-by').addEventListener('change', function(e) {
		let selectedAttr = e.target.selectedOptions[0].value;
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		let jsonData = requestParameters ? JSON.parse(requestParameters) : "";
		let paramters = jsonData.paramters, extraValues = jsonData.extraValues;

		const params = {};
		Object.assign(params, paramters, extraValues, {switchPage: currentPage, attrOrderBy: selectedAttr});
		
		let queryString = new URLSearchParams(params).toString();
		
		fetch(`/booking/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				document.documentElement.innerHTML = html
				bindAdminSystemEvent();
				const scripts = document.querySelector('.main-list').getElementsByTagName('script');
				for (let script of scripts) {
					const newScript = document.createElement('script');
					newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
					document.head.appendChild(newScript); // 将脚本添加到页面中执行
					document.head.removeChild(newScript); // 执行后移除脚本标签
				}
			});

	});
	
	// 選擇排序
	document.getElementById('select-sort').addEventListener('change', function(e) {
		let selectedSort = e.target.selectedOptions[0].value;
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[2];
		let jsonData = requestParameters ? JSON.parse(requestParameters) : "";
		let paramters = jsonData.paramters, extraValues = jsonData.extraValues;

		const params = {};
		Object.assign(params, paramters, extraValues, {switchPage: currentPage, selectedSort});
		
		let queryString = new URLSearchParams(params).toString();
		
		fetch(`/booking/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				document.documentElement.innerHTML = html
				bindAdminSystemEvent();
				const scripts = document.querySelector('.main-list').getElementsByTagName('script');
				for (let script of scripts) {
					const newScript = document.createElement('script');
					newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
					document.head.appendChild(newScript); // 将脚本添加到页面中执行
					document.head.removeChild(newScript); // 执行后移除脚本标签
				}
			});
	});
}

bindAdminSystemEvent();
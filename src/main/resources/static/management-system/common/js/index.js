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
		target: null,
		id: null
	};

	// 導航按鈕點擊事件處理
	sidebarLinks.forEach(link => {
		link.addEventListener('click', () => {
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
	document.querySelector('.add-btn')?.addEventListener('click', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		location.href = `/booking/management/${lastHref}/create/page`;
	});

	// 轉送到編輯
	document.querySelectorAll('.edit-btn')?.forEach(editButton => {
		editButton.addEventListener('click', function(e) {
			e.stopPropagation();
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			let hrefSplit = location.pathname.split('/');
			let lastHref = hrefSplit[3];
			location.href = `/booking/management/${lastHref}/edit/page?${lastHref}Id=${currentListId}`;
		});
	});

	// 轉送到查詢
	document.getElementById('search-btn')?.addEventListener('click', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		location.href = `/booking/management/${lastHref}/select/page`;
	});

	// 開啟檢查視窗
	document.querySelectorAll('.check-btn')?.forEach(button => {
		button.addEventListener('click', function(e) {
			e.stopPropagation();
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			currentList.id = currentListId;
			let hrefSplit = location.pathname.split('/');
			let lastHref = hrefSplit[3];
			
			deleteNullValue(requestParameters);
			let queryString = new URLSearchParams(requestParameters).toString();	
			fetch(`/booking/api/${lastHref}/${currentListId}?${queryString}`)
				.then(res => res.json())
				.then(data => {
					let values = [];
					for (let k in data) {
						values.push(data[k]);
					}

					values.shift();
					const inputs = document.querySelectorAll('.form-group > .input-show-value');
					inputs.forEach((input, i) => input.value = values[i]);
				});

			// 顯示視窗
			document.getElementById('modal-overlay').style.display = 'flex';
		});
	});

	// 取消按鈕行為
	document.getElementById('modal-cancel-btn')?.addEventListener('click', function() {
		document.getElementById('modal-overlay').style.display = 'none';
	});

	// 為所有刪除按鈕添加事件監聽
	document.querySelectorAll('.delete-btn').forEach(button => {
		button.addEventListener('click', function(e) {
			e.stopPropagation();
			currentList.target = e.target;
			document.getElementById('delete-overlay').style.display = 'flex';
			let currentListId = e.target.parentElement.parentElement.dataset.currentListId;
			currentList.id = currentListId;
		});
	});

	// 為確認刪除按鈕添加事件監聽
	document.getElementById('confirmDeleteBtn')?.addEventListener('click', async function(e) {
		// 在這裡添加刪除項目的邏輯
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		
		// 隱藏彈出視窗
		document.getElementById('delete-overlay').style.display = 'none';
		
		let response = await fetch(`/booking/management/${lastHref}/delete`, {
			method: 'POST',
			body: new URLSearchParams({
				[`${lastHref}Id`]: currentList.id
			})
		});
		
		if(response.ok) {
			currentList.target.parentElement.parentElement.remove();
		}
		
		let message = await response.text();

		alert(message);
	});

	// 為取消按鈕添加事件監聽
	document.getElementById('cancelDeleteBtn')?.addEventListener('click', function() {
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
	prevPageBtn?.addEventListener("click", () => {
		if (currentPage <= 1) {
			return;
		}

		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		
		deleteNullValue(requestParameters);
		
		Object.assign(requestParameters, {pageNumber: currentPage-1});
		
		let queryString = new URLSearchParams(requestParameters).toString();
		
		fetch(`/booking/management/${lastHref}/select?${queryString}`, {
		}).then(res => res.text()).then(html => {
			const parser = new DOMParser();
			const doc = parser.parseFromString(html, 'text/html');
			document.body.innerHTML = doc.body.innerHTML;
			bindAdminSystemEvent();
			enableScript('.main-list');
		});
	});

	// 下一頁
	nextPageBtn?.addEventListener("click", () => {
		if (parseInt(pageInput.value) >= totalPages) {
			return;
		}

		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		deleteNullValue(requestParameters);
		Object.assign(requestParameters, {pageNumber: currentPage+1});
		
		let queryString = new URLSearchParams(requestParameters).toString();
		fetch(`/booking/management/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				const parser = new DOMParser();
				const doc = parser.parseFromString(html, 'text/html');
				document.body.innerHTML = doc.body.innerHTML;
				bindAdminSystemEvent();
				enableScript('.main-list');
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
		let lastHref = hrefSplit[3];
		
		deleteNullValue(requestParameters);
		Object.assign(requestParameters, {pageNumber: currentPage+1});
		
		Object.assign(requestParameters, {pageNumber: page});
		let queryString = new URLSearchParams(requestParameters).toString();
		fetch(`/booking/management/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				const parser = new DOMParser();
				const doc = parser.parseFromString(html, 'text/html');
				document.body.innerHTML = doc.body.innerHTML;
				bindAdminSystemEvent();
				enableScript('.main-list');
			});
	});

	// 根據名字簡單查詢
	document.getElementById('search-input').addEventListener('change', function() {
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		
		let queryString = new URLSearchParams({
				[`${lastHref}Name`]: this.value
			}).toString();
		
		fetch(`/booking/management/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				const parser = new DOMParser();
				const doc = parser.parseFromString(html, 'text/html');
				document.body.innerHTML = doc.body.innerHTML;
				bindAdminSystemEvent();
				enableScript('.main-list');
			});
	});

	// 選擇排序種類
	document.getElementById('select-order-by').addEventListener('change', function(e) {
		let selectedAttr = e.target.selectedOptions[0].value;
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		deleteNullValue(requestParameters);
		Object.assign(requestParameters, {pageNumber: currentPage, attrOrderBy: selectedAttr});
		
		let queryString = new URLSearchParams(requestParameters).toString();
		
		fetch(`/booking/management/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				const parser = new DOMParser();
				const doc = parser.parseFromString(html, 'text/html');
				document.body.innerHTML = doc.body.innerHTML;
				bindAdminSystemEvent();
				enableScript('.main-list');
			});

	});
	
	// 選擇排序
	document.getElementById('select-sort').addEventListener('change', function(e) {
		let selectedSort = e.target.selectedOptions[0].value;
		let hrefSplit = location.pathname.split('/');
		let lastHref = hrefSplit[3];
		deleteNullValue(requestParameters);
		Object.assign(requestParameters, {pageNumber: currentPage, selectedSort});
		
		let queryString = new URLSearchParams(requestParameters).toString();
		
		fetch(`/booking/management/${lastHref}/select?${queryString}`)
			.then(res => res.text())
			.then(html => {
				const parser = new DOMParser();
				const doc = parser.parseFromString(html, 'text/html');
				document.body.innerHTML = doc.body.innerHTML;
				bindAdminSystemEvent();
				enableScript('.main-list');
			});
	});
	
	
	// 添加登出功能
	  document.querySelector('.logout-btn')?.addEventListener('click', function() {
	      fetch('/booking/management/admin/logout', {
	          method: 'POST'
	      }).then(() => {
	          window.location.href = '/booking/management/admin/login';
	      });
	  });
}

function enableScript(dom) {
	const scripts = document.querySelector(dom).getElementsByTagName('script');
	for (let script of scripts) {	
		const newScript = document.createElement('script');
		newScript.textContent = script.textContent; // 将脚本内容复制到新创建的脚本标签中
		
		if(script.src != "") {
			newScript.src = script.src;
		}

		document.head.appendChild(newScript); // 将脚本添加到页面中执行
		document.head.removeChild(newScript); // 执行后移除脚本标签	
	}
}

function deleteNullValue(...objects) {
	objects.forEach(object => {
		for(let k in object) {
			if(object[k] === null || object[k] === undefined	) {
				delete object[k];
			}
		}
	});
}

bindAdminSystemEvent();
{
	let currentDate = new Date();
	let selectedDate = null;
	const calendar = document.getElementById('calendar');
	const dateInput = document.getElementById('date-input');

	// 點擊 input 顯示日曆
	dateInput.addEventListener('click', function () {
	    calendar.style.display = 'block';
	    renderCalendar();
	    // 調整日曆的位置到 input 下方
	    const inputRect = dateInput.getBoundingClientRect();
	    calendar.style.left = inputRect.left + 'px';
	    calendar.style.top = (inputRect.bottom + window.scrollY) + 'px';
	});
	
	// 點擊日期填充 input 並關閉日曆
	function selectDate(date, callback) {
	    selectedDate = date;
		date = formatDate(selectedDate);
		dateInput.setAttribute('value', date);
	    dateInput.value = date;
	    calendar.style.display = 'none'; // 選擇日期後關閉日曆
		
		callback();
	}
	
	// 切換月份
	function changeMonth(offset) {
	    currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + offset, 1);
	    renderCalendar();
	}

	// 渲染日曆
	function renderCalendar() {
	    const calendarGrid = document.getElementById('calendar-grid');
	    const monthYearEl = document.getElementById('month-year');
	    
	    // 顯示年份和月份
	    monthYearEl.innerHTML = `
	        <button onclick="changeMonth(-1); event.stopPropagation();">◀</button>
	        <span>${currentDate.getFullYear()}年${currentDate.getMonth() + 1}月</span>
	        <button onclick="changeMonth(1); event.stopPropagation();">▶</button>
	    `;
	    
	    calendarGrid.innerHTML = ''; // 清空日曆格
	
	    const firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
	    const lastDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
	    const daysInMonth = lastDayOfMonth.getDate();
	
	    // 生成空白格子
	    for (let i = 0; i < firstDayOfMonth.getDay(); i++) {
	        const emptyCell = document.createElement('div');
	        emptyCell.classList.add('day');
	        calendarGrid.appendChild(emptyCell);
	    }

	    // 生成每一天的格子
	    for (let day = 1; day <= daysInMonth; day++) {
            const dayCell = document.createElement('div');
            dayCell.classList.add('day');
            dayCell.innerText = day;
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
            
            // 點選日期
            dayCell.onclick = function () {
                selectDate(date, dateCallback);
            };

            // 檢查是否選中日期
            if (selectedDate && date.getTime() === selectedDate.getTime()) {
                dayCell.classList.add('selected');
            }

            calendarGrid.appendChild(dayCell);
        }	
		
    }

    // 格式化日期 YYYY-MM-DD
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // 點擊日曆外部隱藏日曆
    document.addEventListener('click', function (event) {
        if (!calendar.contains(event.target) && event.target !== dateInput) {
            calendar.style.display = 'none';
        }
    });

    // 阻止切換月份時日曆關閉
    calendar.addEventListener('click', function (event) {
        event.stopPropagation();
    });						        

    renderCalendar();
}
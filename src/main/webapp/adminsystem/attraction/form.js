const selectCity = document.getElementById("select-city");

data.forEach(location => {
	let optionCity = document.createElement("option");
	optionCity.textContent = location.name;
	selectCity.appendChild(optionCity);
});
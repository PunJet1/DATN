function search() {
	var number = document.getElementById("keyword").value;
	window.location.assign("/shoesstore/account/history/search?keyword" + "=" + number);
}
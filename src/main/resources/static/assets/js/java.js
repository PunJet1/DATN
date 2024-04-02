function search() {
	var number = document.getElementById("keyword").value;
	window.location.assign("/itbalo/account/history/search?keyword" + "=" + number);
}
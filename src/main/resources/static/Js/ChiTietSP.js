let data1 = [];
let perPage = 3;
let currentPage = 1;
let start = 0;
let end = perPage;
var productID;
let totalPages;

let i = 0;//so sao
let result = [];
let star = document.querySelectorAll('input');
let showValue = document.querySelector('#rating-value');
for (i; i < star.length; i++) {
	star[i].addEventListener('click', function () {
		i = this.value;
		if(i > 5){
			i==5;
		}
		//alert("sao" + i)
		//showValue.innerHTML = i + " out of 5";
	});
}

function loadComment(id) {
	//alert("Chi Tiết SP" + id)
	productID = id
	var url1 = `http://localhost:8080/rest/feedback/${id}`;
	fetch(url1, {
		method: "GET"
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		data1 = data;
		console.info("data="+data);
		console.info("data1" + data1)
		totalPages = Math.ceil(data.length / perPage)
		console.log("totalPages" + totalPages);
		let html = '';
		data.map((item, index) => {
			
			console.info("size" + data.length)
			document.getElementById("soFB").innerHTML = '(' + data.length + ')';
			result.push(item.rate)
			if (index >= start && index < end) {

				html += ' 	<div class="comments-top-top"> ';
				html += '		<div class="top-comment-left"> ';
				html += '			<img class="img-responsive" ';
				html += '				src="/assets/images/' + item.product.image + '" alt=""> ';
				html += '		</div> ';
				html += '		<div class="top-comment-right"> ';
				html += '			<h4 href="#0" id="fullname" style="font-style: italic;"> ' + item.account.fullname + '</h4> ';
				html += '			<ul class="star-footer"> ';
				for (let x = 0; x < item.rate; x++) {
					html += '				<li><a href="#0" style="color: orange;"><i class="fa fa-star" aria-hidden="true"></i></a></li> ';
				}
				html += '			</ul> ';
				html += '			<p style="">' + item.comment + '</p> ';
				html += '			<p style="margin-left: 0px;">' +item.feedbackDate+ '</p> ';
				html += '		</div> ';
				html += '		<div class="clearfix"></div> ';
				html += '	</div> ';
			
				return html;
			}

		})
		document.getElementById("comen").innerHTML = html;
		console.info("Số sao " + result );
		console.info("size result= " + result.length );
		saoTB();
	
		//alert("lấy dữ liệu thành công " + comment)
	})
		
}
function next() {
	//alert("next")
	currentPage++;
	if (currentPage > totalPages) {
		currentPage = 1;
	}
	start = (currentPage - 1) * perPage;
	end = currentPage * perPage;
	console.log(start, end, currentPage);
	loadComment(productID);
}
function Prev() {
	//alert("next")
	currentPage--;
	if (currentPage <= 1) {
		currentPage = 1;
	}
	start = (currentPage - 1) * perPage;
	end = currentPage * perPage;
	console.log(start, end, currentPage);
	loadComment(productID);
}
function comments() { 
	var comment = document.getElementById("comment").value;
	if (comment == "") {
		alert("Bạn chưa viết đánh giá")
		return false;
	} else {
		if (i > 5) {
			i = 5;
		} 
		//alert("comment " + productID + "---" + comment + "số sao = " + i);
		var url2 = `http://localhost:8080/rest/feedback/${productID}`;
		fetch(url2, {
			method: "POST",
			body: JSON.stringify({
				"comment": comment,
				"rate": i
			}),
			headers: {
				"Content-Type": "application/json"
			}
		}).then(function (res) {
			console.info(res)
			return res.json();
		}).then(function (data) {
			console.info(data);	
			loadComment(productID);
			document.getElementById('comment').value = "";
			
		})
	}

}


function saoTB() {
	//alert("sap tb")
	var sum = 0;
	
	for (var i = 0; i < result.length; i++) {
		sum = sum + result[i]
	}
	var avg = Math.ceil(sum / result.length);
	console.info("sum= " + sum);
	console.info("avg= " + avg);
	let html ='';
	if (avg >0) {
		for (let x = 0; x < avg; x++) {
			html += '	<ul class="star-footer"> ';
			html += '									<li><a href="#0"><i> </i></a></li> ';
			html += '							</ul> ';		
		}
		html += '<div class="review">';
		html += '<a href="#0"> ('+result.length +' đánh giá) </a>';
		html += '</div>';
	}

	if (totalPages == 0) {
		document.getElementById("saoTB").style.display = 'none';
		// document.getElementById("dieuhuong").style.display = 'none';
	} else {
		document.getElementById("saoTB").style.display = 'block';
		// document.getElementById("dieuhuong").style.display = 'block';
		document.getElementById("saoTB").innerHTML =  html;
	}
	

}
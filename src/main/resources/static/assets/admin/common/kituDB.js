function remoteKiTuDB(obj){
	var string ="";
	var chuoiKt = $("#"+obj).val();
	string = chuoiKt.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
	$("#"+obj).val(string);
}

	var sweetalert = function(text) {
		Swal.fire({
			icon: "success",
			title: text,
			showConfirmButton: false,
			timer: 2000,
		});
	}

	//thông báo lỗi
	var swarning = function(text) {
		Swal.fire({
			icon: "error",
			title: text,
			showConfirmButton: false,
			timer: 2000,
		});
	}


function toDateString(sDate, sFormatMark) {
//	    var sDatetemp
	    var values = sDate.split("-");
	    var yr_num;
	    var mo_num;
	    var day_num;
	    if ((sFormatMark.toUpperCase() == "DD/MM/YYYY")) {
	        yr_num = values[0];
	        mo_num = values[1];
	        day_num = values[2];
	    } 
	    return day_num+"/"+mo_num+"/"+yr_num;
	}
	
	function toDate(sDate, sFormatMark) {
//	    var sDatetemp
	    var values = sDate.split("/");
	    var yr_num;
	    var mo_num;
	    var day_num;
	    if ((sFormatMark.toUpperCase() == "DD/MM/YYYY") || (sFormatMark.toUpperCase() == "D")) {
	        yr_num = values[2];
	        mo_num = values[1] - 1;
	        day_num = values[0];
	    } else if ((sFormatMark.toUpperCase() == "MM/YYYY") || (sFormatMark.toUpperCase() == "M")) {
	        yr_num = values[1];
	        mo_num = values[0] - 1;
	        day_num = 1;
	    } else if ((sFormatMark.toUpperCase() == "Q/YYYY") || (sFormatMark.toUpperCase() == "Q")) {
	        yr_num = values[1];
	        values = values[0].split("Q");
	        mo_num = (values[1] * 3 - 2) - 1;
	        day_num = 1;
	    } else if ((sFormatMark.toUpperCase() == "YYYY") || (sFormatMark.toUpperCase() == "Y")) {
	        yr_num = values[0];
	        mo_num = 0;
	        day_num = 1;
	    }
	    return new Date(yr_num, mo_num, day_num);
	}
	
function checkDateHtai(obj){
	var date = new Date();
	var chuoiKt = $("#"+obj).val();
	var stringNgay = toDateString(chuoiKt,"DD/MM/YYYY");
	if(toDate(stringNgay,'DD/MM/YYYY') > date){
		swarning("Nhập ngày không được lớn hơn ngày hiện tại");
		$("#"+obj).val("");
		return false;
	}
	
	
}


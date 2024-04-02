app.controller("voucher-ctrl", function($scope, $http) {
	//alert("voucher-ctrl")
	$scope.items = [];
	$scope.form = [];
	$scope.initialize = function() {
		//load product
		$http.get("/rest/vouchers").then(resp => {
			$scope.items = resp.data;
			$scope.reset();
		})



	}
	//khởi đầu
	$scope.initialize();



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


	//hiện lên form
	$scope.edit = function(item) {
		item.ngayBatDau = new Date(item.ngayBatDau);
		item.ngayKetThuc = new Date(item.ngayKetThuc);
		$scope.form = angular.copy(item);


		$(".nav-tabs a:eq(1)").tab('show')
	}
	//xóa form
	$scope.reset = function() {
		//alert("Xóa Form")
		$scope.form = {
			statustt: true,
		}
	}
	$scope.luu = function() {
		var item = angular.copy($scope.form);
		//alert("lưu voucher " + item.voucherName)
		var voucherName1 = document.getElementById("voucherName").value;
		if (voucherName1.trim() == "" || voucherName1 == null) {
			swarning("Tên voucher không được để trống");
			return false;
		}
		var triGia = document.getElementById("triGia").value;
		if (triGia < 0) {
			swarning("Trị giá không được là số âm");
			return false;
		}
		var ngayBatDau = document.getElementById("ngayBatDau").value;
		var ngayKetThuc = document.getElementById("ngayKetThuc").value;

		if (ngayBatDau > ngayKetThuc) {
			swarning("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
			return false;
		}

		if (ngayBatDau == "" || ngayBatDau == null) {
			swarning("Ngày bắt đầu không được để trống");
			return false;
		}
		if (ngayKetThuc == "" || ngayKetThuc == null) {
			swarning("Ngày kết thúc không được để trống");
			return false;
		}

		if (item.voucherName != null) {
			//alert("cập nhật voucher " + item.voucherName)
			$http.post(`/rest/vouchers`, item).then(resp => {
				var index = $scope.items.findIndex(p => p.voucherName == item.voucherName);
				$scope.items[index] = item;
				this.initialize();
				console.log(item);
				sweetalert("Lưu voucher thành công " + item.voucherName);
				$(".nav-tabs a:eq(0)").tab('show');
				//$("#exampleModal").css("display","none");
			}).catch(error => {
				swarning("Lưu voucher thất bại")
				console.log("error", error);
			})
		} else {
			//alert("Thêm mới voucher ")
			$http.post(`/rest/vouchers/`, item).then(resp => {
				$scope.items.push(resp.data);
				this.initialize();
				sweetalert("Thêm Thành Công");
				$(".nav-tabs a:eq(0)").tab('show')
			}).catch(error => {
				swarning("Lỗi Thêm Mới ")
				console.log("error", error);
			});
		}
	}
	//thêm category mới
	$scope.create = function() {
		//alert("Thêm sp")
		var item = angular.copy($scope.form);
		$http.post(`/rest/vouchers/`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			this.initialize();
			sweetalert("Thêm mới thành công");
		}).catch(error => {
			swarning("Lỗi thêm mới")
			console.log("error", error);
		});
	}
	//cập nhật category
	$scope.update = function() {
		//alert("update sp")
		var item = angular.copy($scope.form);
		$http.put(`/rest/vouchers/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			sweetalert("Cập nhật vocher thành công");
			$(".nav-tabs a:eq(0)").tab('show')
			this.initialize();
		}).catch(error => {
			swarning("Lỗi cập nhật" + error)
			console.log("error", error);
		})
	}
	//xóa category
	$scope.delete = function(item) {
		//alert("delete sp")
		/*$http.delete(`/rest/vouchers/${item.voucherName}`).then(resp => {
			var index = $scope.items.findIndex(p => p.voucherName == item.voucherName);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa Thành Công");
		}).catch(error => {
				alert("Lỗi xóa !")
				console.log("Error", error);
	   })*/

		$http.put(`/rest/vouchers/delete/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			sweetalert("Xóa vocher thành công");
			this.initialize();
		}).catch(error => {
			swarning("Lỗi Xóa" + error)
			console.log("error", error);
		})
	}

	///tìm kiếm
	$scope.timKiem = function() {
		var name = document.getElementById("keyword").value;
		var trangThai = document.getElementById("trangThai").value;
		if (trangThai == "") {
			trangThai = null;
			//alert("Tìm Kiếm: " + name + " trang thai= " + trangThai)
			$http.get(`/rest/vouchers/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		} else {
			$http.get(`/rest/vouchers/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		}

	}

	$scope.genderStart = function(obj) {
		var trangThai = document.getElementById("trangThai").value;
		//alert("Trang thái " + trangThai )
		if (trangThai == "") {
			$http.get("/rest/vouchers").then(resp => {
				$scope.items = resp.data;
			})
		} else {
			$http.get(`/rest/vouchers/timKiem/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		}

	}


	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}

	$scope.removeAccents = function(obj) {
		var str = $("#voucherName").val();
		str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
		str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
		str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
		str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
		str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
		str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
		str = str.replace(/đ/g, "d");
		str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
		str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
		str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
		str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
		str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
		str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
		str = str.replace(/Đ/g, "D");
		// Some system encode vietnamese combining accent as individual utf-8 characters
		// Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
		str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
		str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
		// Remove extra spaces
		// Bỏ các khoảng trắng liền nhau
		str = str.replace(/ + /g, " ");
		str = str.trim();
		// Remove punctuations
		// Bỏ dấu câu, kí tự đặc biệt
		str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, " ");
		$("#voucherName").val(str.toUpperCase());
		return str;
	}

})
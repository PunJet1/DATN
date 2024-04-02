app.controller("feedback-ctrl", function($scope, $http) {
	//alert("feedback-ctrl")
	$scope.items = [];
	$scope.form = [];
	$scope.cates = [];

	var check;
	$scope.initialize = function() {
		//load product
		$http.get("/rest/feedback").then(resp => {
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

	var swarning = function(text) {
		Swal.fire({
			icon: "error",
			title: text,
			showConfirmButton: false,
			timer: 2000,
		});
	}


	//xóa form
	$scope.reset = function() {
		//alert("reset")
		$scope.form = {
			status: true,
			image: 'cloud-upload.jpg',
		}
	}
	//hiện lên form
	$scope.edit = function(item) {
		//alert("edit")
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(1)").tab('show')
	}

	$///tìm kiếm
	$scope.timKiem = function() {
		var name = document.getElementById("keyword").value;
		var trangThai = document.getElementById("trangThai").value;
		if (trangThai == "") {
			trangThai = null;
			//alert("Tìm Kiếm: " + name + " trang thai= " + trangThai)
			$http.get(`/rest/brand/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		} else {
			$http.get(`/rest/brand/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		}

	}
	$scope.genderStart = function(obj) {
		var trangThai = document.getElementById("trangThai").value;
		//alert("Trang thái " + trangThai )
		if (trangThai == "") {
			$http.get("/rest/brand").then(resp => {
				$scope.items = resp.data;
			})
		} else {
			$http.get(`/rest/brand/timKiem/${trangThai}`).then(resp => {
				$scope.items = resp.data;
			})
		}

	}

	//cập nhật 
	$scope.update = function() {
		//alert("update ")
		var item = angular.copy($scope.form);
		//alert("id " + item.discountId); 
		$http.put(`/rest/feedback/${item.brandId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.brandId == item.brandId);
			$scope.items[index] = item;
			sweetalert("cập nhật thành công");
		}).catch(error => {
			swarning("Lỗi cập nhật sp" + error)
			console.log("error", error);
		})
	}
	//xóa 
	$scope.delete = function(item) {
		//alert("delete")
		//var t = confirm("Bạn muốn xóa");
		var t = true;
		if (t == false) {
			//alert("Không Xóa");
		} else {
			//alert("có xóa");
			$http.delete(`/rest/feedback/${item.feedbackId}`).then(resp => {
				var index = $scope.items.findIndex(p => p.feedbackId == item.feedbackId);
				$scope.items.splice(index, 1);
				$scope.reset();
				sweetalert("Xóa Thành Công");
			})
				.catch(error => {
					swarning("Lỗi xóa!")
					console.log("Error", error);
				})

		}
	}

	$scope.check = function(name) {
		$http.get(`/rest/brand/${name}`).then(resp => {
			if (resp.data == '') {
				//alert("Không tìm thấy  " + name)
				check = true;
			} else {
				//alert("Tìm thấy  " + name)
				check = false;
				console.log(resp.data)
			}
		})
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
});
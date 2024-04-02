app.controller("order-ctrl", function($scope, $http) {
	$scope.order = [];
	$scope.orderDetail = [];
	$scope.total = [];
	$scope.form = {};
	$scope.initialize = function() {

		$http.get("/rest/orders/all").then(resp => {
			$scope.order = resp.data;
			$scope.total = resp.data.length;
			console.log(resp.data);
		})
	}
	$scope.initialize();
	
	//thông báo thành công
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


	$scope.updateUp = function(item) {
		var t = true;
		if (t == false) {

		} else {
			$http.put(`/rest/orders/up/${item.orderId}`, item).then(resp => {
				$scope.initialize();
				sweetalert("Cập nhật trạng thái thành công")
			})
		}
	}

	$scope.updateDown = function(item) {
		var t = true;
		/*if (item.status == 1) {
			t = confirm("Bạn muốn hủy đơn hàng này?");
		}*/
		if (t == false) {

		} else {
			$http.put(`/rest/orders/down/${item.orderId}`, item).then(resp => {
				$scope.initialize();
				alert("Cập nhật nhật trạng thành công")
			})
		}

	}
	$scope.updateClose = function(item) {

		//var t = confirm("Bạn muốn hủy đơn hàng?");
		var t = true;
		if (t == false) {

		} else {
			$http.put(`/rest/orders/close/${item.orderId}`, item).then(resp => {
				$scope.initialize();
				sweetalert("Đơn hàng đã được hủy thành công")
			})
		}

	}
	$scope.findId = function(item) {
		$http.get(`/rest/orders/${item.orderId}`, item).then(resp => {
			$scope.orderDetail = resp.data;
			//alert(orderDetail)
		})
	}


	$scope.timKiemDh = function(item) {
		var tenTk = document.getElementById("tenTk").value;
		var ngayTk = document.getElementById("ngayTk").value;
		var tthaiTk = document.getElementById("tthaiTk").value;
		if (tenTk == "") {
			tenTk = null;
		}
		if (ngayTk == "") {
			ngayTk = null;
		}
		if (tthaiTk == "") {
			tthaiTk = null;
		}
		$http.get(`/rest/orders/searchDH/${tenTk}/${ngayTk}/${tthaiTk}`, item).then(resp => {
			$scope.order = resp.data;
		})
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.order.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.order.length / this.size);
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
})
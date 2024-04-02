app.controller("news-ctrl2", function ($scope, $http) {
	//alert("Tin tức đã vào")
	$scope.items = [];
	$scope.form = {};
	$scope.initialize = function () {
		//load product
		$http.get("/rest/news").then(resp => {
			$scope.items = resp.data;
		})
	}
	//Khời đầu
	$scope.initialize();

	//thông báo thành công
	var sweetalert = function (text) {
		Swal.fire({
			icon: "success",
			title: text,
			showConfirmButton: false,
			timer: 2000,
		});
	}

	//thông báo lỗi
	var swarning = function (text) {
		Swal.fire({
			icon: "error",
			title: text,
			showConfirmButton: false,
			timer: 2000,
		});
	}

	$scope.reset = function () {
		$scope.form = {
			newsId: '',
			title: '',
			shortcontent: '',
			image: 'cloud-upload.jpg',
			//image: document.getElementById("image-news").value = "",
			// content: CKEDITOR.instances['editor'].setData(""),
		};
		console.log($scope.form)
	}
	//Hiển thị lên form
	$scope.edit = function (item) {
		//alert("Edit")
		$scope.form = angular.copy(item);
		document.getElementById("editor").value=item.content;
		// CKEDITOR.instances['editor'].setData(item.content);
		$(".nav-tabs a:eq(1)").tab('show')
		//lấy dữ liệu đổ sang form input
		console.log($scope.form)
	}

	//Thêm mới
	$scope.create = function () {
		//alert("Create")
		// $scope.form.content = CKEDITOR.instances['editor'].getData();
		var item = angular.copy($scope.form);
		console.log(item);
		$http.post(`/rest/news`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			$scope.initialize();
			sweetalert("Thêm mới tin tức thành công!");
			$(".nav-tabs a:eq(0)").tab('show')
		}).catch(error => {
			swarning("Thêm mới thất bại");
			console.log("Lỗi nè===:", error);
		});
	}

	//Cập nhật
	$scope.update = function () {
		//alert("Update")
		var item = angular.copy($scope.form);
		// item.content = CKEDITOR.instances['editor'].getData();
		console.log(item.content);
		$http.put(`/rest/news/${item.newsId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.newsId == item.newsId);
			$scope.items[index] = item;
			$scope.initialize();
			//$scope.reset();
			sweetalert("Cập nhật thành công!");
			$(".nav-tabs a:eq(0)").tab('show')
		}).catch(error => {
			swarning("Cập nhật thất bại");
			console.log("Lỗi nè===:", error);
		});
	}

	//Xóa
	$scope.delete = function (item) {
		//alert("Delete")
		$http.delete(`/rest/news/${item.newsId}`).then(resp => {
			var index = $scope.items.findIndex(p => p.newsId == item.newsId);
			$scope.items.splice(index, 1);
			$scope.reset();
			sweetalert("Xóa thành công!");
		}).catch(error => {
			swarning("Xóa thất bại");
			console.log("Lỗi nè===:", error);
		});
	}

	//upload hình
	$scope.imageChanged = function (files) {
		//alert("hình")
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			swarning("Lỗi upload hình" + error);
			console.log("Error", error);
		})
	}

	//Lấy giá trị ô input nhập vào
	function getValueInput(id) {
		return document.getElementById(id).value;
	}
	$scope.validate = function () {
		//alert("Đã vào")
		// var content = CKEDITOR.instances['editor'].getData();
		var title = getValueInput('title');
		var tmage = getValueInput("image");
		var shortcontent = getValueInput("shortcontent");
		var content = getValueInput('content');
		if (title == "") {
			console.log("Không để trống tiêu đề")
		} else if (tmage == '') {
			console.log("Không để trống hình ảnh")
		} else if (shortcontent == '') {
			console.log("Không để trống nội dung ngắn")
		} else if (content == '') {
			console.log("Không để trống nội dung")
		} else {
			console.log("Dữ liệu oke");
			$scope.form.content = content;
			$scope.create();
			return true;
		}
		//Test
		console.log("chạy đến false")
		return false;
	}
	//Phân trang
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

	//Phần editor


})

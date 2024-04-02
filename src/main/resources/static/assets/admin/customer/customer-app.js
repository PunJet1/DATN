app.controller("customer-ctrl", function ($scope, $http) {
	//alert("customer-ctrl")
	$scope.items = [];
	$scope.form = [];
	$scope.cates = [];

	var check ;
	$scope.initialize = function () {
		//load product
		$http.get("/rest/customer").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
						item.birthdate = new Date(item.birthdate)
						})
			$scope.reset();
		})

	}
	
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
	
	//khởi đầu
	$scope.initialize();
	//xóa form
	$scope.reset = function () {
		//alert("reset")
		$scope.form = {
			accountId: $scope.form.accountId,
			status: true,
			image: 'cloud-upload.jpg',
		}
	}
	//hiện lên form
	$scope.edit = function(item) {
		//alert("edit")
		$scope.form = angular.copy(item);
		console.log('AngularJS controller is working');
		$(".nav-tabs a:eq(1)").tab('show')
	}

	$///tìm kiếm
    $scope.timKiem = function () {
        var name = document.getElementById("keyword").value;
        var trangThai = document.getElementById("trangThai").value;
        if (trangThai == "") {
            trangThai = null;
             if(name ==""){
				name ="null";
			}
            //alert("Tìm Kiếm: " + name + " trang thai= " + trangThai)
            $http.get(`/rest/customer/timKiem/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/customer/timKiem/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        }
       
    }
    $scope.genderStart = function (obj) {
        var trangThai = document.getElementById("trangThai").value;
        //alert("Trang thái " + trangThai )
        if (trangThai == "") {
            $http.get("/rest/customer").then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/customer/timKiem/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        }

    }
    
	//Thêm mới
	$scope.create = function () {
		//alert("Create ")
		var item = angular.copy($scope.form);
		//alert("Tên Thương Hiệu " + item.name)
		$http.get(`/rest/customer/${item.name}`).then(resp =>{
			if (resp.data == '') {
				//alert("Thương hiệu mới")
				$http.post(`/rest/customer`, item).then(resp => {
					$scope.items.push(resp.data);
					$scope.initialize();
					sweetalert("Thêm mới thành công!");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Thêm mới thất bại");
					console.log(error);
				});
			} else {
				swarning("Đã có khách hàng này")
				console.log(resp.data)
			}
		})		


	}

	//cập nhật 
	$scope.update = function () {
		//alert("update ")
		var item = angular.copy($scope.form);
		
		var hoTen = document.getElementById("hoTen").value;
		if(hoTen.trim() =="" || hoTen ==null){
			swarning("Họ tên không được để trống");
			return false;
		}
		var email = document.getElementById("email").value;
		if(email.trim() =="" || email ==null){
			swarning("Email không được để trống");
			return false;
		}
		var pass = document.getElementById("pass").value;
		if(pass.trim() =="" || pass ==null){
			swarning("Password không được để trống");
			return false;
		}
		var sdt = document.getElementById("sdt").value;
		if(sdt.trim() =="" || sdt ==null){
			swarning("Số điện thoại không được để trống");
			return false;
		}
		var diaChi = document.getElementById("diaChi").value;
		if(diaChi.trim() =="" || diaChi ==null){
			swarning("Địa chỉ không được để trống");
			return false;
		}
		
		//alert("id " + item.discountId); 
		$http.put(`/rest/customer/${item.accountId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.accountId == item.accountId);
			$scope.items[index] = item;
			sweetalert("cập nhật thành công");
			$(".nav-tabs a:eq(0)").tab('show')
		}).catch(error => {
			swarning("Lỗi cập nhật sp" + error)
			console.log("error", error);
		})
	}
	//xóa 
	$scope.delete = function (item) {
		//alert("delete")
		var t = confirm("Bạn muốn xóa");
		if (t == false) {
			//alert("Không Xóa");
		} else {
			//alert("có xóa");
			$http.delete(`/rest/customer/${item.accountId}`).then(resp => {
				var index = $scope.items.findIndex(p => p.accountId == item.accountId);
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

	$scope.check = function (name){
		$http.get(`/rest/customer/${name}`).then(resp =>{
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


	//upload hình
	$scope.imageChanged = function (files) {
		//alert("hìn")
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


	$scope.pager = {
		page: 0,
		size: 10,
		get items(){
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count(){
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first(){
			this.page = 0;
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){
				this.first();
			}
		},
		last(){
			this.page = this.count - 1;
		}
	}
});
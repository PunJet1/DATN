app.controller("brand-ctrl", function ($scope, $http) {
	//alert("brand-ctrl")
	$scope.items = [];
	$scope.form = [];
	$scope.cates = [];
	var nameCheck="";
	var check ;
	$scope.initialize = function () {
		//load product
		$http.get("/rest/brand").then(resp => {
			$scope.items = resp.data;
			$scope.reset();
		})

	}
	
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
	//khởi đầu
	$scope.initialize();
	//xóa form
	$scope.reset = function () {debugger;
		//alert("reset")
		$("#chonAnh").val("");
		$scope.form = {
			brandId: $scope.form.brandId,
			status: true,
			image: 'cloud-upload.jpg',
		}
	}
	//hiện lên form
	$scope.edit = function (item) {debugger;
		//alert("edit")
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(1)").tab('show');
		nameCheck = $scope.form.name;
		$("#chonAnh").val("");
	}

	///tìm kiếm
    $scope.timKiem = function () {
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
    $scope.genderStart = function (obj) {
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
    
	//Thêm mới
	$scope.create = function () {
		//alert("Create ")
		var item = angular.copy($scope.form);
		var brandName = document.getElementById("brandName").value;
		if(brandName.trim() =="" || brandName ==null){
			swarning("Tên thương hiệu không được để trống");
			return false;
		}
		//alert("Tên Thương Hiệu " + item.name)
		$http.get(`/rest/brand/${item.name}`).then(resp =>{
			if (resp.data == '') {
				//alert("Thương hiệu mới")
				$http.post(`/rest/brand`, item).then(resp => {
					$scope.items.push(resp.data);
					$scope.initialize();
					sweetalert("Thêm mới thành công!");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Thêm mới thất bại");
					console.log(error);
				});
			} else {
				swarning("Đã có thương hiệu này")
				console.log(resp.data)
			}
		})		


	}

	//cập nhật 
	$scope.update = function () {
		//alert("update ")
		var item = angular.copy($scope.form);debugger;
		//alert("id " + item.discountId); 
		var brandName = document.getElementById("brandName").value;
		if(brandName.trim() =="" || brandName ==null){
			swarning("Tên thương hiệu không được để trống");
			return false;
		}
		
		$http.get(`/rest/brand/${item.name}`).then(resp =>{
			if(resp.data !=null && resp.data !="" && resp.data.name !='' && nameCheck!=resp.data.name){ 
				swarning("Đã có thương hiệu này")
				console.log(resp.data)
				return false;
			}else{
				$http.put(`/rest/brand/${item.brandId}`, item).then(resp => {
					var index = $scope.items.findIndex(p => p.brandId == item.brandId);
					$scope.items[index] = item;
					sweetalert("cập nhật thành công");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Lỗi cập nhật thương hiệu" + error)
					console.log("error", error);
				});
			}
		})
	}
	//xóa 
	$scope.delete = function (item) {
		//alert("delete")
		var t = true;
		if (t == false) {
			//alert("Không Xóa");
		} else {
			//alert("có xóa");
			/*$http.delete(`/rest/brand/${item.brandId}`).then(resp => {
				var index = $scope.items.findIndex(p => p.brandId == item.brandId);
				$scope.items.splice(index, 1);
				$scope.reset();
				alert("Xóa Thành Công");
			})
				.catch(error => {
					alert("Lỗi xóa!")
					console.log("Error", error);
				})*/
			$http.put(`/rest/brand/delete/${item.brandId}`, item).then(resp => {
						var index = $scope.items.findIndex(p => p.brandId == item.brandId);
						$scope.items[index] = item;
						sweetalert("Xoá thành công");
						$scope.initialize();
					}).catch(error => {
						swarning("Lỗi xóa sp" + error)
						console.log("error", error);
					})
		}
	}

	$scope.check = function (name){
		$http.get(`/rest/brand/${name}`).then(resp =>{
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
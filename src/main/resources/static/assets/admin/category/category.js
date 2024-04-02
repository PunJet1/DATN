app.controller("category-ctrl", function($scope, $http) {
	//alert("category")
	$scope.items = [];
	$scope.form = [];

	$scope.discount = [];
	$scope.initialize = function() {
		//load account
		$http.get("/rest/categories").then(resp => {
			$scope.items = resp.data;
			$scope.reset();
			console.log(resp.data);
		})
		
		//load discount
        $http.get("/rest/discount/statusTrue").then(resp => {
            $scope.discount = resp.data;
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

	//hiện lên form
	$scope.edit = function(item) {
		//alert("edit")
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(1)").tab('show')
	}
	$scope.reset = function() {
		//alert("Xóa Form")
		$scope.form = {
			//birthdate: new Date(),
			categoryId: $scope.form.categoryId,
            image: 'cloud-upload.jpg',
            status: true,
		}
	}
	//thêm category mới
	$scope.create = function() {
		//alert("Thêm sp")
		var item = angular.copy($scope.form);
		
		var categoryName = document.getElementById("categoryName").value;
		if(categoryName.trim() =="" || categoryName ==null){
			swarning("Tên danh mục không được để trống");
			return false;
		}
		$http.get(`/rest/categories/${item.name}`).then(resp =>{
			if (resp.data == '') {
				$http.post(`/rest/categories/`, item).then(resp => {
					$scope.items.push(resp.data);
					$scope.reset();
					$scope.initialize();
					sweetalert("Thêm mới thành công");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Lỗi Thêm mới account");
					console.log("error", error);
				});
			} else {
				swarning("Đã có loại balo này")
				console.log(resp.data)
			}	
		})	
	}
	//cập nhật category
	$scope.update = function() {
		//alert("update sp")
		var categoryName = document.getElementById("categoryName").value;
		if(categoryName.trim() =="" || categoryName ==null){
			swarning("Tên danh mục không được để trống");
			return false;
		}
		var item = angular.copy($scope.form);
		$http.put(`/rest/categories/${item.categoryId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
			$scope.items[index] = item;
			sweetalert("cập nhật category thành công");
			$(".nav-tabs a:eq(0)").tab('show')
			$scope.initialize();
		}).catch(error => {
			swarning("Lỗi cập nhật category" + error);
			console.log("error", error);
		})
	}
	//xóa account
	$scope.delete = function(item) {
		//alert("delete sp")
		/*$http.delete(`/rest/categories/${item.categoryId}`).then(resp => {
			var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa Thành Công");
		}).catch(error => {
			alert("Lỗi xóa Account" + error);
			console.log("error", error);
		})*/
		$http.put(`/rest/categories/delete/${item.categoryId}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
			$scope.items[index] = item;
			sweetalert("xóa category thành công");
			$scope.initialize();
		}).catch(error => {
			swarning("Lỗi xóa category" + error);
			console.log("error", error);
		})
		
	}
	
	
	$///tìm kiếm
    $scope.timKiem = function () {
        var name = document.getElementById("keyword").value;
        var trangThai = document.getElementById("trangThai").value;
        if (trangThai == "") {
            trangThai = null;
            //alert("Tìm Kiếm: " + name + " trang thai= " + trangThai)
            $http.get(`/rest/categories/timKiem/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/categories/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        }
       
    }
    $scope.genderStart = function (obj) {
        var trangThai = document.getElementById("trangThai").value;
        //alert("Trang thái " + trangThai )
        if (trangThai == "") {
            $http.get("/rest/categories").then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/categories/timKiem/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        }

    }

	//phân trang
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
})
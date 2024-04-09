app.controller("product-ctrl", function ($scope, $http) {
	//alert("product")
	$scope.itemsProduct = [];
	$scope.formProduct = {};

	$scope.cates = [];
	$scope.brand = [];
	$scope.size = [];
	$scope.glass = [];
	$scope.strap = [];
	$scope.water = [];
	$scope.shell = [];
	$scope.products = [];

	$scope.productSizes = [];
	$scope.selectedProductId = '';
	$scope.selectedSizeId = '';

	$scope.itemsImagePr = [];
	$scope.formImagePr = {};

	$scope.itemsProductSizePr = [];
	$scope.formProductSizePr = {};


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

	var showphoto = function (text) {
		Swal.fire({
			//	icon: "error",
			title: text,
			showConfirmButton: true,
			//timer: 2000,
		});
	}


	var productId;
	$scope.initialize = function () {
		//load product
		$http.get("/rest/products").then(resp => {
			$scope.itemsProduct = resp.data;
			$scope.itemsProduct.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
			$scope.reset();
		})
		//load category
		$http.get("/rest/categories").then(resp => {
			$scope.cates = resp.data;
		})
		//load Brand
		$http.get("/rest/brand").then(resp => {
			$scope.brand = resp.data;
		})
		$http.get(`/rest/ImageProduct`).then(resp => {
			$scope.itemsImagePr = resp.data;
			console.log($scope.itemsImagePr);
		})

		//load size
		$http.get("/rest/size").then(resp => {
			$scope.size = resp.data;
		})

		//load glass
		$http.get("/rest/glass").then(resp => {
			$scope.glass = resp.data;
		})

		//load strap
		$http.get("/rest/strap").then(resp => {
			$scope.strap = resp.data;
		})
		//		//load water
		//		$http.get("/rest/water").then(resp => {
		//			$scope.water = resp.data;
		//		})

		//load shell
		$http.get("/rest/shell").then(resp => {
			$scope.shell = resp.data;
		})

		//load productsize
		$http.get("/rest/productsizes").then(resp => {
			$scope.itemsProductSize = resp.data;
			console.log($scope.itemsProductSize)
		})
		productId = "";
	}
	//khởi đầu
	$scope.initialize();

	//Thêm mới
	$scope.create = function () {
		//alert("Create")
		var item = angular.copy($scope.formProduct);
		item.createDate = item.createDate;

		var productName = document.getElementById("productName").value;
		if (productName.trim() == "" || productName == null) {
			swarning("Tên sản phẩm không được để trống");
			return false;
		}
		var categoySelect = document.getElementById("categoySelect").value;
		if (categoySelect == "" || categoySelect == null) {
			swarning("Loại giày không được để trống");
			return false;
		}
		var brandSelect = document.getElementById("brandSelect").value;
		if (brandSelect == "" || brandSelect == null) {
			swarning("Hãng sản phẩm không được để trống");
			return false;
		}
		// var waterSelect = document.getElementById("waterSelect").value;
		// if(waterSelect =="" || waterSelect ==null){
		// 	swarning("Chống nước không được để trống");
		// 	return false;
		// }
		var proCreateDate = document.getElementById("proCreateDate").value;
		if (proCreateDate == "" || proCreateDate == null) {
			swarning("Ngày thêm sản phẩm không được để trống");
			return false;
		}
		var sizeSelect = document.getElementById("sizeSelect").value;
		if (sizeSelect == "" || sizeSelect == null) {
			swarning("Size không được để trống");
			return false;
		}
		var glassSelect = document.getElementById("glassSelect").value;
		if (glassSelect == "" || glassSelect == null) {
			swarning("Chất liệu không được để trống");
			return false;
		}
		var strapSelect = document.getElementById("strapSelect").value;
		if (strapSelect == "" || strapSelect == null) {
			swarning("Chất dây đeo không được để trống");
			return false;
		}
		var productPrice = document.getElementById("productPrice").value;
		if (productPrice == "" || productPrice == null) {
			swarning("Giá sản phẩm không được để trống");
			return false;
		}
		var productQuantity = document.getElementById("productQuantity").value;
		if (productQuantity == "" || productQuantity == null) {
			swarning("Số lượng sản phẩm không được để trống");
			return false;
		}
		// var shellSelect = document.getElementById("shellSelect").value;
		// if(shellSelect =="" || shellSelect ==null){
		// 	swarning("Vỏ bọc sản phẩm không được để trống");
		// 	return false;
		// } 
		var productOrigin = document.getElementById("productOrigin").value;
		if (productOrigin == "" || productOrigin == null) {
			swarning("Xuất xứ sản phẩm không được để trống");
			return false;
		}
		var genderRadio = document.getElementsByName("genderRadio");
		var len = genderRadio.length;
		var checkValue = '';
		var checkGender = 0;
		for (var i = 0; i < len; i++) {
			if (genderRadio.item(i).checked) {
				checkValue = genderRadio.item(i).value;
				if (checkValue != "") {
					checkGender++;
				}
			}
		}
		if (checkGender == 0) {
			swarning("Yêu cầu chọn giới tính.");
			return false;
		}

		var image = document.getElementById("image").value;
		if (image == "" || image == null) {
			swarning("Chọn hình cho sản phẩm");
			return false;
		}
		$http.post(`/rest/products/checkName`, item).then(resp => {
			if (resp.data != null && resp.data != "") {
				swarning("Mã sản phẩn đã tồn tại");
				return false;
			} else {
				$http.post(`/rest/products`, item).then(resp => {
					resp.data.createDate = new Date(resp.data.crateDate)
					$scope.itemsProduct.push(resp.data);
					//$scope.reset();
					$scope.initialize();
					sweetalert("Thêm mới sản phẩm thành công!");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Thêm mới thất bại");
					console.log(error);
				});
			}
		}).catch(error => {
			swarning("Thêm mới thất bại");
			console.log(error);
		});

	}
	//cập nhật sp
	$scope.update = function () {
		//alert("update sp")
		var item = angular.copy($scope.formProduct);
		item.crateDate = item.createDate;
		//alert("id " + item.productId +" "+ "crateDate " + item.crateDate);


		var productName = document.getElementById("productName").value;
		if (productName == "" || productName == null) {
			swarning("Tên sản phẩm không được để trống");
			return false;
		}
		var categoySelect = document.getElementById("categoySelect").value;
		if (categoySelect == "" || categoySelect == null) {
			swarning("Loại giày không được để trống");
			return false;
		}
		var brandSelect = document.getElementById("brandSelect").value;
		if (brandSelect == "" || brandSelect == null) {
			swarning("Hãng sản phẩm không được để trống");
			return false;
		}
		//		var waterSelect = document.getElementById("waterSelect").value;
		//		if(waterSelect =="" || waterSelect ==null){
		//			swarning("Chống nước không được để trống");
		//			return false;
		//		}
		var proCreateDate = document.getElementById("proCreateDate").value;
		if (proCreateDate == "" || proCreateDate == null) {
			swarning("Ngày thêm sản phẩm không được để trống");
			return false;
		}
		var sizeSelect = document.getElementById("sizeSelect").value;
		if (sizeSelect == "" || sizeSelect == null) {
			swarning("Size không được để trống");
			return false;
		}
		var glassSelect = document.getElementById("glassSelect").value;
		if (glassSelect == "" || glassSelect == null) {
			swarning("Chất liệu thủy tinh không được để trống");
			return false;
		}
		var strapSelect = document.getElementById("strapSelect").value;
		if (strapSelect == "" || strapSelect == null) {
			swarning("Chất dây đeo không được để trống");
			return false;
		}
		var productPrice = document.getElementById("productPrice").value;
		if (productPrice == "" || productPrice == null) {
			swarning("Giá sản phẩm không được để trống");
			return false;
		}
		var productQuantity = document.getElementById("productQuantity").value;
		if (productQuantity == "" || productQuantity == null) {
			swarning("Số lượng sản phẩm không được để trống");
			return false;
		}
		var shellSelect = document.getElementById("shellSelect").value;
		if (shellSelect == "" || shellSelect == null) {
			swarning("Vỏ bọc sản phẩm không được để trống");
			return false;
		}
		var productOrigin = document.getElementById("productOrigin").value;
		if (productOrigin == "" || productOrigin == null) {
			swarning("Xuất xứ sản phẩm không được để trống");
			return false;
		}
		var genderRadio = document.getElementsByName("genderRadio");
		var len = genderRadio.length;
		var checkValue = '';
		var checkGender = 0;
		for (var i = 0; i < len; i++) {
			if (genderRadio.item(i).checked) {
				checkValue = genderRadio.item(i).value;
				if (checkValue != "") {
					checkGender++;
				}
			}
		}
		if (checkGender == 0) {
			swarning("Yêu cầu chọn giới tính.");
			return false;
		}



		$http.post(`/rest/products/checkName`, item).then(resp => {
			if (resp.data != null && resp.data != "" && resp.data.name != '' && nameCheck != resp.data.name) {
				swarning("Mã sản phẩn đã tồn tại");
				return false;
			} else {
				$http.put(`/rest/products/${item.productId}`, item).then(resp => {
					var index = $scope.itemsProduct.findIndex(p => p.productId == item.productId);
					$scope.itemsProduct[index] = item;
					sweetalert("cập nhật thành công");
					$(".nav-tabs a:eq(0)").tab('show')
				}).catch(error => {
					swarning("Lỗi cập nhật sp" + error)
					console.log(error);
				})
			}
		}).catch(error => {
			swarning("Lỗi cập nhật sp");
			console.log(error);
		});;
	}
	//xóa sp
	$scope.delete = function (item) {
		//alert("delete sp" + item.productId)
		var t = true;
		if (t == false) {
			//alert("Không Xóa");
		} else {
			//alert("có xóa");
			/*$http.delete(`/rest/products/${item.productId}`).then(resp => {
				var index = $scope.itemsProduct.findIndex(p => p.productId == item.productId);
				$scope.itemsProduct.splice(index, 1);
				$scope.reset();
				alert("Xóa Thành Công");
			}).catch(error => {
				alert("Lỗi xóa!")
				console.log("Error", error);
			})*/
			$http.put(`/rest/products/delete/${item.productId}`, item).then(resp => {
				var index = $scope.itemsProduct.findIndex(p => p.productId == item.productId);
				$scope.itemsProduct[index] = item;
				sweetalert("Xóa sp thành công");
				$scope.initialize();
			}).catch(error => {
				swarning("Lỗi Xóa Sp" + error)
				console.log(error);
			})

		}

	}

	$scope.formatSoNguyenDuong = function (value, obj) {
		var strvalue;
		strvalue = value;
		var str = strvalue.split('.');
		var num;
		var val = strvalue;
		strvalue = '';
		for (var i = 0; i < val.length; i++) {
			strvalue += $scope.getVal(val.charAt(i));
		}
		num = strvalue.toString().replace(/\$|\./g, '');
		if (num.length > 15) num = num.substring(0, 15);
		if (!$scope.IsNumeric(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num * 100 + 0.50000000001);
		num = Math.floor(num / 100).toString();
		for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) +
				num.substring(num.length - (4 * i + 3));
		//return (((sign)?'':'-') + num);
		value = (((sign) ? '' : '-') + num);
		if (obj == "gia") {
			$scope.formProduct.price = value;
		}

		if (obj == "sl") {
			$scope.formProduct.quantity = value;
		}

	}

	$scope.allowPressNeg = function (value) {
		var dsChar = '-';
		if (value.indexOf(dsChar) >= 0) {
			if ((event.keyCode < 48 && event.keyCode != 9) || event.keyCode > 57) event.returnValue = false;
		} else {
			if ((event.keyCode < 48 && event.keyCode != 9 && event.keyCode != 45) || event.keyCode > 57) event.returnValue = false;
		}
	}

	$scope.formatSoNguyenDuongMaxLength = function (value, maxLength) {
		var strvalue;
		strvalue = value;
		var strvalue2 = strvalue.replace(/\./g, '');
		if (strvalue2.length > maxLength) {
			strvalue = strvalue2.substr(0, maxLength);
		}
		var str = strvalue.split('.');
		var num;
		var val = strvalue;
		strvalue = '';
		for (var i = 0; i < val.length; i++) {
			strvalue += getVal(val.charAt(i));
		}
		num = strvalue.toString().replace(/\$|\./g, '');
		if (num.length > 15) num = num.substring(0, 15);
		if (!IsNumeric(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num * 100 + 0.50000000001);
		num = Math.floor(num / 100).toString();
		for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + '.' +
				num.substring(num.length - (4 * i + 3));
		//return (((sign)?'':'-') + num);
		eval(obj).value = (((sign) ? '' : '-') + num);
	}

	$scope.IsNumeric = function (sText) {
		var ValidChars = "0123456789.,-";
		var IsNumber = true;
		var Char;

		for (var i = 0; i < sText.length && IsNumber == true; i++) {
			Char = sText.charAt(i);
			if (ValidChars.indexOf(Char) == -1) {
				IsNumber = false;
				break;
			}
		}
		return IsNumber;
	}

	$scope.getVal = function (num) {
		if (num == '' || $scope.checkNumeric(num)) {
			return '';
		} else {
			return (num);
		}
	}

	$scope.checkNumeric = function (sText) {
		var ValidChars = "0123456789";
		var IsNumber = true;
		if (ValidChars.indexOf(sText) != -1) {
			IsNumber = false;
		}
		return IsNumber;
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
			$scope.formProduct.image = resp.data.name;
			$scope.formImagePr.images = resp.data.name;
		}).catch(error => {
			swarning("Lỗi upload hình" + error);
			console.log("Error", error);
		})
	}

	//xóa form
	$scope.reset = function () {
		//alert("Xóa Form")
		$("#image").val("");
		$scope.formProduct = {
			productId: $scope.formProduct.productId,
			createDate: new Date(),
			image: 'cloud-upload.jpg',
			status: true,
		}
		$scope.formImagePr = {
			images: 'cloud-upload.jpg',
		}
	}
	///tìm kiếm
	$scope.timKiem = function () {
		var name = document.getElementById("keyword").value;
		var trangThai = document.getElementById("trangThai").value;
		if (trangThai == "") {
			trangThai = null;
			//alert("Tìm Kiếm: " + name + " trang thai= " + trangThai)
			$http.get(`/rest/products/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.itemsProduct = resp.data;
			})
		} else {
			$http.get(`/rest/products/timKiem/${name}/${trangThai}`).then(resp => {
				$scope.itemsProduct = resp.data;
			})
		}

	}
	$scope.genderStart = function (obj) {
		var trangThai = document.getElementById("trangThai").value;
		//alert("Trang thái " + trangThai )
		if (trangThai == "") {
			$http.get("/rest/products").then(resp => {
				$scope.itemsProduct = resp.data;
			})
		} else {
			$http.get(`/rest/products/timKiem/${trangThai}`).then(resp => {
				$scope.itemsProduct = resp.data;
			})
		}

	}

	//imageProduct
	//hiện lên form product
	var nameCheck = "";
	$scope.edit = function (item) {
		$scope.formProduct = angular.copy(item);
		//$scope.formProduct.createDate = $filter('date')(item.createDate, "dd/MM/yyyy");
		$(".nav-tabs a:eq(1)").tab('show');
		nameCheck = $scope.formProduct.name;
		$("#image").val("");
	}

	//hiện image
	$scope.editImage = function (item) {
		showphoto("Ảnh: " + item.productId)
		console.log(item)
		$scope.formImagePr = {
			images: item.image,
		}
		$scope.formImagePr.product = {
			productId: item.productId,
		}
		productId = item.productId;
		$scope.tableImage(item.productId);
		$(".nav-tabs a:eq(2)").tab('show')
	}

	$scope.image = function (item) {
		// alert("product " + item.product.productId)
		console.log(item)
		$scope.formImagePr = angular.copy(item);
		$scope.tableImage(item.product.productId);
		$(".nav-tabs a:eq(2)").tab('show')
	}


	$scope.genderChanged = function () {
		productId = $scope.formImagePr.product.productId;
		if (productId == "") {
			//alert("chọn tất cả ")
			$http.get(`/rest/ImageProduct`).then(resp => {
				$scope.itemsImagePr = resp.data;
			})
			productId = "";
		} else {
			//alert("genderChanged " + productId)
			productId = $scope.formImagePr.product.productId;
			$scope.tableImage(productId);
			$scope.pagerImage();

		}
	}


	$scope.tableImage = function (id) {
		$http.get(`/rest/ImageProduct/${id}`).then(resp => {
			$scope.itemsImagePr = resp.data;
		})
	}


	//Thêm mới ảnh
	$scope.createImage = function () {
		//$scope.formImagePr.image = resp.data.name;
		var item = angular.copy($scope.formImagePr);
		if (productId == "") {
			swarning("Chưa chọn sp")
		} else {
			//alert("Thêm Mới Anh cho SP " + item.product.productId)
			console.log(item)
			$http.post(`/rest/ImageProduct`, item).then(resp => {
				$scope.itemsImagePr.push(resp.data);
				//$scope.reset();
				$scope.tableImage(productId);
				sweetalert("Thêm mới ảnh thành công!");
			}).catch(error => {
				swarning("Thêm mới ảnh thất bại");
				console.log(error);
			});
		}
	}
	//cập nhật anh
	$scope.updateImage = function () {
		//alert("updateImage")
		var item = angular.copy($scope.formImagePr);
		$http.put(`/rest/ImageProduct/${item.imageId}`, item).then(resp => {
			var index = $scope.itemsImagePr.findIndex(p => p.imageId == item.imageId);
			$scope.itemsImagePr[index] = item;
			sweetalert("cập nhật thành công");
		}).catch(error => {
			swarning("Lỗi cập nhật sp" + error)
			console.log(error);
		})
	}
	//xóa ảnh
	$scope.deleteImage = function (item) {
		//alert("delete Iamge" + item.imageId)
		$http.delete(`/rest/ImageProduct/${item.imageId}`).then(resp => {
			debugger
			var index = $scope.itemsImagePr.findIndex(p => p.imageId == item.imageId);
			$scope.itemsImagePr.splice(index, 1);

			$scope.formImagePr = {
				images: 'cloud-upload.jpg',
			}
			sweetalert("Xóa Thành Công");
		})
			.catch(error => {
				swarning("Lỗi xóa!")
				console.log("Error", error);
			})
	}



	// Hiển thị productsize
	$scope.editProductSize = function (item) {
		$scope.formProductSizePr = angular.copy(item);
		$scope.formProductSize = {
			product: {
				id: item.productid
			},
			size: {
				id: item.id
			},
			price: item.price
		};
		$scope.tableProductSize(item.productid);
		$(".nav-tabs a:eq(3)").tab('show');
	};

	$scope.productsize = function (item) {
		$scope.formProductSizePr = angular.copy(item);
		$scope.formProductSize = {
			product: {
				id: item.productid
			},
			size: {
				id: item.sizeid
			},
			price: item.price
		};
		$scope.tableProductSize(item.productid);
		$(".nav-tabs a:eq(3)").tab('show');
	};

	$scope.genderChanged2 = function () {
		var productId = $scope.formProductSizePr.product.id;
		if (!productId) {
			$http.get(`/rest/productsizes`).then(resp => {
				$scope.itemsProductSizePr = resp.data;
			});
			productId = "";
		} else {
			$scope.tableProductSize(productId);
			$scope.pagerProductSize();
		}
	};

	// Load bảng productsize
	$scope.tableProductSize = function (id) {
		$http.get(`/rest/productsizes/${id}`).then(resp => {
			$scope.itemsProductSizePr = resp.data;
		});
	};

	// Thêm mới productsize
	$scope.createProductSize = function () {
		var item = angular.copy($scope.formProductSize);
		if (!productId) {
			swarning("Chưa chọn sản phẩm.");
		} else {
			$http.post(`/rest/productsizes`, item).then(resp => {
				$scope.itemsProductSizePr.push(resp.data);
				$scope.tableProductSize(productId);
				sweetalert("Thêm mới size sản phẩm thành công!");
			}).catch(error => {
				swarning("Thêm mới size sản phẩm thất bại.");
				console.log(error);
			});
		}
	};

	// Cập nhật productsize
	$scope.updateProductSize = function () {
		var item = angular.copy($scope.formProductSize);
		$http.put(`/rest/productsizes/${item.productSizeId}`, item).then(resp => {
			var index = $scope.itemsProductSizePr.findIndex(p => p.productSizeId == item.productSizeId);
			$scope.itemsProductSizePr[index] = item;
			sweetalert("Cập nhật size sản phẩm thành công.");
		}).catch(error => {
			swarning("Lỗi cập nhật size sản phẩm: " + error);
			console.log(error);
		});
	};

	//phân trang product
	$scope.pager = {
		page: 0,
		size: 10,
		get itemsProduct() {
			var start = this.page * this.size;
			return $scope.itemsProduct.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsProduct.length / this.size);
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

	///phân trang image
	$scope.pagerImage = {
		page: 0,
		sizeImage: 4,
		get itemsImagePr() {
			var start = this.page * this.sizeImage;
			return $scope.itemsImagePr.slice(start, start + this.sizeImage);
		},
		get countImage() {
			return Math.ceil(1.0 * $scope.itemsImagePr.length / this.sizeImage);
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
			if (this.page >= this.countImage) {
				this.first();
			}
		},
		last() {
			this.page = this.countImage - 1;

		}
	}
	///phân trang productsize
	$scope.pagerProductSize = {
		page: 0,
		sizeProduct: 5,
		get itemsProductSizePr() {
			var start = this.page * this.sizeProduct;
			return $scope.itemsProductSizePr.slice(start, start + this.sizeProduct);
		},
		get countProduct() {
			return Math.ceil(1.0 * $scope.itemsProductSizePr.length / this.sizeProduct);
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
			if (this.page >= this.countProduct) {
				this.first();
			}
		},
		last() {
			this.page = this.countProduct - 1;

		}

	}



})
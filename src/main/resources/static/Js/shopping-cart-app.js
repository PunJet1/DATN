const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function ($scope, $http) {
	
	//alert("Giỏ Hàng")
	//Quản lý giỏ hàng	
	var tienKhachTra = 25;
	$scope.cart = {
		items: [],
		add(productId) {
			//alert(id)
			var item = this.items.find(item => item.productId == productId);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${productId}`).then(resp => {
					resp.data.qty = 1;
					resp.data.price = (resp.data.price - ((resp.data.price * resp.data.category.discount.percentDiscount) / 100));
					this.items.push(resp.data);
					this.saveToLocalStorage();
				})
			}
		},

		//xóa 1 mặt hàng
		remove(productId) {
			var index = this.items.findIndex(item => item.productId == productId);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		//xóa hết giỏ hàng
		clear() {
			this.items = []
			this.saveToLocalStorage();
		},
		//tổng sô lượng các mặt hàng
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total -= qty, 0);
		},
		//tổng thành tiền các mặt hàng
		get amount() {
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);

		},
		//lưu giỏ hàng vào Local Storage
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},

		//Đọc giỏ hàng từ Local Storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];

			tienKhachTra = $scope.cart.amount;
			
			//console.log(total)
		}
	}


	$scope.cart.loadFromLocalStorage();

  
	
	var vouchers = [];
	//var tienTraSauVoucher ;
	$scope.voucherVal = {
		tienTraSauVoucher: '',
		tienTru : '',
		vouchers(productId) {
			//alert("productId " + productId)
			$http.get(`/rest/vouchers/${productId}`).then(resp => {
				vouchers = resp.data,
					console.log(resp.data)
				console.info(vouchers, vouchers.conditions)
				let voucherName = vouchers.voucherName + "(-" + vouchers.valued + "%)";
				let valued = vouchers.valued;
				document.getElementById('vouche').value = voucherName;
			})

		},

		suDung() {
			var vouche = document.getElementById("vouche").value;
			if (vouche == "") {
				this.tienTru = 0;
				alert("Chưa chọn  ")
			} else {
				var dieukien = vouchers.conditions;
				if ($scope.cart.amount >= dieukien) {
					alert("Dùng thành công (Đơn hàng lớn hơn " + dieukien + ")")
					//	Math.round( 20.7 );
					const totalItem = $scope.cart.amount;
					this.tienTru = ((totalItem * vouchers.valued) / 100);
					console.log(this.tienTru)
					console.log(dieukien)
					tienKhachTra = totalItem - this.tienTru;
					document.getElementById("GiamGia").style.display = 'block';
				} else {
					alert("Không dùng được (Đơn hàng phải lớn hơn hoặc bằng " + dieukien + ")")
					this.tienTru = 0;
					document.getElementById("GiamGia").style.display = 'none';
					tienKhachTra = Math.round($scope.cart.amount);
					//alert("đã chọn ")
				}
				//alert("Sử Dụng Voucher " + vouche + " " + totalItem + " " + this.tienTru)
			}
		},

		get amount2() {
			return this.cates
				.map(item => item.finalPrice)
				.reduce((total, finalPrice) => total += finalPrice, 0);
		},
	}

	$scope.order = {
		createDate: new Date(),
		address: "Hà Nội",
		status: "Trạng thái",
		//lấy tổng tiền khi áp dụng voucher $scope.voucherVal.amount2 cart.amount  totalFinal

		total: tienKhachTra,

		account: { username: $("#username").text() },
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.productId },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchanse() {
			var order = angular.copy(this);
			order.total = tienKhachTra;

			// console.log(order.total);
			console.log(order);
			console.log(tienKhachTra);
			alert("Đặt hàng")
			//thực hiện đặt hàng
			// $http.post("/rest/order", order).then(resp => {
			// 	alert("đặt hàng thành công");
			// 	$scope.cart.clear();
			// 	location.href = "/order/detail/" + resp.data.id;
			// }).catch(error => {
			// 	alert("lỗi đặt hàng");
			// 	console.log(error)
			// })
		}
	}

	
})
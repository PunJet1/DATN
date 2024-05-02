const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function($scope, $http,$window) {


    var sweetalert = function(text) {
				Swal.fire({
					icon: "success",
					title: text,
					showConfirmButton: false,
					timer: 2000,
				});
			};
			//thông báo lỗi
			var swarning = function(text) {
				Swal.fire({
					icon: "error",
					title: text,
					showConfirmButton: false,
					timer: 2000,
				});
			};

    //QL voucher: 
    //var tienKhachTra = 25;
    /*alert("Quản giỏ hàng")*/
    /*
    Quản lý giỏ hàng
    */
    $scope.cart = {
        items: [],
        //Thêm sản phẩm
       /* add(productId) {
            //alert(productId)
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
        },*/
        add(productId){
			var item = this.items.find(item => item.productId == productId);
			if (item) {
				if(item.qty >= item.quantity){
					 swarning("Số lượng sản phẩm đã đạt giới hạn!");
					 location.href = "/shoesstore/cartItem";
					 return;
				}
				else{
				
                item.qty++;
                this.saveToLocalStorage();}
            } else {
                $http.get(`/rest/products/${productId}`).then(resp => {
                    resp.data.qty = 1;
                    if(resp.data.category.discount != null && resp.data.category.discount != ""){
						resp.data.price = (resp.data.price - ((resp.data.price * resp.data.category.discount.percentDiscount) / 100));
					}
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
		},
        //Xóa sản phẩm khỏi giỏ hàng
        remove(index) {
           // var index = this.items.findIndex(item => item.id == id);
            console.log(index);
  				console.log(this.items[index]);
            //this.items.splice(this.items, 1);
            this.items.splice(index,1);
            this.saveToLocalStorage();
        },
        //Xóa sạch các mặt hàng trong giỏ
        clear() {
            this.items = []
            this.saveToLocalStorage();
        },
        //Tính thành tiền của 1 sản phẩm
        amt_of(item) {},
        //Tính tổng số lượng các mặt hàng trong giỏ
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        
        checkQuantity(index){
			//var index = this.items.findIndex(item => item.id == id);
				//this.getIndex(id);
				console.log(index);
				var qty = document.getElementById("qty").value;
  				console.log(this.items[index].quantity);
  				if(qty >= this.items[index].quantity){
					 swarning("Số lượng sản phẩm đã đạt giới hạn!");
					 //location.href = "/itbalo/cartItem";
					 this.items[index].qty = this.items[index].quantity;
					 this.saveToLocalStorage();
					 return;
				  }else if(this.items[index].qty <= 0 ){
					  var cf= confirm("Bạn muốn xóa "+this.items[index].name +" ra khỏi giỏ hàng?");
					  if(cf == true){
						  this.remove(index);
					  }else{
						  this.items[index].qty=null;
						  this.saveToLocalStorage();
					  }
				  }
				  
				  else{
					  this.saveToLocalStorage();
				  }	
		},
		formatSoNguyenDuong(value,obj) {
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
        num = num.substring(0, num.length - (4 * i + 3))  +
        num.substring(num.length - (4 * i + 3));
    //return (((sign)?'':'-') + num);
   	value = (((sign) ? '' : '-') + num);
   	if(obj =="gia"){
		  $scope.items[index].price = value;
	  }
	  
	  if(obj =="sl"){
		  this.items[index].qty = value;
	  }
	  
},

		reload(index){
			console.log(index);
  				console.log(this.items[index].quantity);
  				if(this.items[index].qty >= this.items[index].quantity){
					 swarning("Số lượng sản phẩm đã đạt giới hạn!");
					 //location.href = "/itbalo/cartItem";
					 this.items[index].qty = this.items[index].quantity;
					 this.saveToLocalStorage();
					 return;
				  }else if(this.items[index].qty == "" || this.items[index].qty == null ){
					  var cf= confirm("Bạn muốn xóa "+this.items[index].name +" ra khỏi giỏ hàng?");
					  if(cf == true){
						  this.remove(index);
					  }else{
						  this.items[index].qty = 1;
						  this.saveToLocalStorage();
					  }
				  }
				  
				  else{
					  this.saveToLocalStorage();
				  }	
				  $window.location.reload();
		},
		getIndex(index){
			index;
		},
		reload1(){
			$window.location.reload();
		},
        //Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items
                .map(item => item.qty * item.price)
                .reduce((total, qty) => total += qty, 0);
        },
        saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);		
            setInterval(json,1);
        },
        //Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }

    }

    $scope.cart.loadFromLocalStorage();
    //Đưa giá về ban đầu
    $scope.totalFinal = $scope.cart.amount;
    //Tên voucher
    $scope.result = "";
    //Giá trị voucher
    $scope.result2 = '0';
    $scope.voucherVal = {
        cates: [],
        //lấy % của voucher
        getValuedVoucher(result) {
            const resultName = $scope.result;
            $http.get(`/rest/vouchers/${resultName}`).then(resp => {
                $scope.result2 = resp.data.valued;
                resp.data.finalPrice = $scope.cart.amount - (resp.data.valued);
                resp.data.priceStart = $scope.cart.amount;
                const dieuKien = resp.data.conditions;
                if ($scope.cart.amount >= dieuKien) {
                   
                    // Lấy phần tử HTML có id là "totalAmount"
                    var totalAmountElement = document.getElementById("amount");
                    var totalShipfeeElement = document.getElementById("shipfee");
                    // Lấy và xử lý nội dung văn bản của phần tử
                    var totalAmountText = totalAmountElement.textContent || totalAmountElement.innerText;
                    var totalShipFeeText = totalShipfeeElement.textContent || totalShipfeeElement.innerText;
                    // Loại bỏ các ký tự không phải số và chuyển đổi thành số
                    var totalAmountValue = parseFloat(totalAmountText.replace(/[^\d]/g, ''));
                    var totalShipFeeValue = parseFloat(totalShipFeeText.replace(/[^\d]/g, ''));

                    var totalAmount = totalAmountValue + totalShipFeeValue - (resp.data.valued);

                    // Sử dụng Intl.NumberFormat để định dạng số tiền
                    var formatter = new Intl.NumberFormat('vi-VN', {
                        style: 'currency',
                        currency: 'VND'
                    });
                    var totalAmountFormat = formatter.format(totalAmount);
                    document.getElementById('sumPrice').innerText = totalAmountFormat.replace(/₫/, 'VNĐ');
                    
                    sweetalert("Áp dụng thành công")
                    $scope.voucherVal.infoVoucher();
                } else {
                    swarning("Không dùng được (Đơn hàng phải lớn hơn hoặc bằng " + dieuKien + ")")
                    $scope.result = "";
                    $scope.result2 = '0';
                    $scope.totalFinal = "";
                    $scope.voucherVal.infoNameVoucher();
                }
                // this.cates.push(resp.data);
            })
        },

        

        get amount2() {
            return this.cates
                .map(item => item.finalPrice)
                .reduce((total, finalPrice) => total += finalPrice, 0);
        },
        get nameVoucher() {
            return this.cates
                .map(item => item.voucher_Name)
                .reduce((total, voucher_Name) => total += voucher_Name, "");
        },
        get valueVoucher() {
            return this.cates
                .map(item => item.valued)
                .reduce((total, valued) => total += valued, "");
        },
        get valueCondition() {
            return this.cates
                .map(item => item.condition)
                .reduce((total, condition) => total += condition, "");
        },
        //đưa dữ liệu vào mảng voucher
        infoVoucher() {
            return $scope.voucherVal.cates.map(item => {
                item.finalPrice = $scope.totalFinal;
                return {
                    voucher_Name: item.voucher_Name,
                    quantity: item.quantity--,
                    finalPrice: item.finalPrice,
                }
            });
        },
        //Add giá trị vào mảng cates
        infoNameVoucher() {
            return $scope.voucherVal.cates.map(item => {
                item.voucher_Name = $scope.result;
                item.finalPrice = $scope.totalFinal;
                item.valued = $scope.result2;
                return {
                    voucher_Name: item.voucher_Name,
                    finalPrice: item.finalPrice,
                    valued: item.valued,
                }
            });
        },

    }
    

    // $scope.cart.loadFromLocalStorage();
    // $scope.orders = {

    //     createDate: new Date(),
    //     address: "",
    //     status: "",
    //     //lấy tổng tiền khi áp dụng voucher 
    //     total: $scope.totalFinal,
    //     //{username:$("#username").text()}
    //     account:{accountId: $("#account").val()},
        
    //     voucher: {voucherName: $scope.result},
    //     //Hàm duyệt các mặt hàng trong giỏ
    //     get orderDetails() {
    //         const selectedItems = $scope.cart.items.filter(item => item.selected);
        
    //         const orderDetails = selectedItems.map(item => {
    //             return {
    //                 product: { productId: item.productId , name: item.name},
    //                 price: item.price,
    //                 quantity: item.qty,
    //                 valueVoucher: item.valueVoucher,
    //             };
    //         });
    //         localStorage.setItem('orderDetails', JSON.stringify(orderDetails));
    //         return orderDetails;
    //     },              
    //     purchase() { 
    //         var order = angular.copy(this);
    //         console.log(order);
    //         $http.post(`/rest/orders`, order).then(resp => {
    //            /* $scope.cart.clear();*/
    //             location.href = "/itbalo/order/checkout";
    //         }).cath(error => {
    //             swarning("Dat hang loi");
    //             console.log(error)
    //         })
    //     }
    // }



    $scope.orders = {
        createDate: new Date(),
        address: "",
        status: "",
        // Khai báo total là một phương thức getter
        get total() {
            // Tính tổng tiền dựa trên số lượng sản phẩm trong giỏ hàng
            return $scope.cart.items.reduce((total, item) => {
                // Nếu sản phẩm được chọn, cộng thêm vào tổng tiền
                if (item.selected) {
                    total += (item.price * item.qty);
                }
                return total;
            }, 0);
        },
        account: {accountId: $("#account").val()},
        voucher: {voucherName: $scope.result},
        // Phương thức orderDetails không thay đổi
        get orderDetails() {
            const selectedItems = $scope.cart.items.filter(item => item.selected);
        
            const orderDetails = selectedItems.map(item => {
                return {
                    product: { productId: item.productId , name: item.name},
                    price: item.price,
                    quantity: item.qty,
                    valueVoucher: item.valueVoucher,
                };
            });
            localStorage.setItem('orderDetails', JSON.stringify(orderDetails));
            return orderDetails;
        },
        purchase() { 
            var order = angular.copy(this);
            console.log(order);
            $http.post(`/rest/orders`, order).then(resp => {
               /* $scope.cart.clear();*/
                location.href = "/shoesstore/order/checkout";
            }).cath(error => {
                swarning("Dat hang loi");
                console.log(error)
            })
        }
    };
    
    // Khởi tạo biến totalAmount
    $scope.totalAmount = 0;

    // Hàm tính tổng số tiền khi có sự thay đổi trong các mục được chọn

    $scope.calculateTotalAmount = function() {
        const selectedItems = $scope.cart.items.filter(item => item.selected);
        $scope.totalAmount = selectedItems.reduce((total, item) => {
            return total + (item.price * item.qty);
        }, 0);
    };
    

    $scope.orderShoppingCart = JSON.parse(localStorage.getItem('orderDetails')) || [];
    
    $scope.form = [];
    //upload hình
	$scope.imageChanged = function(files) {
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
	
	
	 $scope.checkKyThangInput = function (obj){
	   var thangNam = document.getElementById(obj);
		validDateFormat(thangNam,'dd/mm/yyyy');
	}
	
	function validDateFormat(dateField, dateFormat){
	var dt=dateField;

	var returndate;

    if ((dt.value==null) || (dt.value == ""))
    	return true;

	if (dateFormat.toUpperCase() == 'DD/MM/YYYY'){
		returndate = isDate(dt.value);
	}
	else if (dateFormat.toUpperCase() == 'MM/YYYY')	{
		returndate = isMonth(dt.value);
	}
	else if (dateFormat.toUpperCase() == 'YYYY'){
		returndate = isYear(dt.value);
	}else{
		returndate = false;
	}

	if (returndate ==false){
		alert('Đề nghị nhập giá trị dạng (' + dateFormat + ') cho trường này');
		dt.value= '';
		dt.focus();
		return false;
	}else{
		dt.value = returndate;
		return returndate;
	}
 }
 	var strSeparatorArray= new Array("-"," ","/",".");
	var minYear=1000;
	var maxYear=9999;
 function isMonth(dtStr){
	var dtCh= "*";
	if (dtStr == "") return "";

	for (var intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (dtStr.indexOf(strSeparatorArray[intElementNr]) != -1)
			dtCh = strSeparatorArray[intElementNr];
	}

	if (dtCh != "*") //neu co ky hieu phan cach
	{
		var pos1=dtStr.indexOf(dtCh);

		var strMonth=dtStr.substring(0,pos1);
		var strYear=dtStr.substring(pos1+1);
	}
	else	//khong co ky hieu phan cach
	{
		if (dtStr.length>3) {
			strMonth = dtStr.substr(0, 2);
			strYear = dtStr.substr(2);
   		}
		else
			return false;
	}

	if (!isInteger(strYear) || !isInteger(strMonth))
		return false;
	strYr=strYear;

	if (strMonth.charAt(0)=="0" && strMonth.length>1)
		strMonth=strMonth.substring(1);

	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
	}

	month=parseInt(strMonth);
	year=parseInt(strYr);

	if (strMonth.length<1 || month<1 || month>12){
		return false;
	}

	if(year < 50 ) year += 2000;
	if(year >50 && year <1000) year += 1900;
	if (strYear.length < 1 || year==0 || year<minYear || year>maxYear){
		return false;
	}
	if (month < 10) month = "0" + month ;
	return "" + month + "-" + year;
}
	
	function isInteger( s )
{ 
	var isInteger = RegExp(/^[0-9]+$/);
	return ( isInteger.test(s) );
}
	 
	 
function isDate(dtStr) {
    var dtCh = "*";
    if (dtStr == "") return true;
    var daysInMonth = DaysArray(12);

    for (var intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
        if (dtStr.indexOf(strSeparatorArray[intElementNr]) != -1)
            dtCh = strSeparatorArray[intElementNr];
    }
    if (dtCh != "*") //neu co ky hieu phan cach
    {
        var pos1 = dtStr.indexOf(dtCh);
        var pos2 = dtStr.indexOf(dtCh, pos1 + 1);
        if (pos1 == -1 || pos2 == -1) {
            return false;
        }
        var strDay = dtStr.substring(0, pos1);
        var strMonth = dtStr.substring(pos1 + 1, pos2);
        var strYear = dtStr.substring(pos2 + 1);
    } else //khong co ky hieu phan cach
    {
        if (dtStr.length > 5) {
            strDay = dtStr.substr(0, 2);
            strMonth = dtStr.substr(2, 2);
            strYear = dtStr.substr(4);
        } else
            return false;
    }

    if (!isInteger(strYear) || !isInteger(strMonth) || !isInteger(strDay))
        return false;

    strYr = strYear;

    if (strDay.charAt(0) == "0" && strDay.length > 1)
        strDay = strDay.substring(1);

    if (strMonth.charAt(0) == "0" && strMonth.length > 1)
        strMonth = strMonth.substring(1);

    for (var i = 1; i <= 3; i++) {
        if (strYr.charAt(0) == "0" && strYr.length > 1) strYr = strYr.substring(1);
    }

    month = parseInt(strMonth);
    day = parseInt(strDay);
    year = parseInt(strYr);

    if (strMonth.length < 1 || month < 1 || month > 12) {
        return false;
    }
    if (strDay.length < 1 || day < 1 || day > 31 || (month == 2 && day > daysInFebruary(year))
    		|| ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) || day > daysInMonth[month]) {
        return false;
    }

    if (year < 50) year += 2000;
    if (year > 50 && year < 1000) year += 1900;
    if (strYear.length < 1 || year == 0 || year < minYear || year > maxYear) {
        return false;
    }
    if (day < 10) day = "0" + day;
    if (month < 10) month = "0" + month;

    return "" + day + "/" + month + "/" + year;
}

function DaysArray(n) {
    for (var i = 1; i <= n; i++) {
        this[i] = 31;
        if (i == 4 || i == 6 || i == 9 || i == 11) {
            this[i] = 30;
        }
        if (i == 2) {
            this[i] = 29;
        }
    }
    return this;
}

})
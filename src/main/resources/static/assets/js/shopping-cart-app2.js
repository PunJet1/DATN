const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function ($scope, $http) {

    //QL voucher: 
    var tienKhachTra = 25;

	
    /*alert("Quản giỏ hàng")*/
    /*
    Quản lý giỏ hàng
    */
    $scope.cart = {
        items: [],
        //Thêm sản phẩm
        add(productId) {
            //alert(productId)
            var item = this.items.find(item => item.productId == productId);
            if (item) {
                item.qty++;
                this.saveToLocalStorage();
            } else {
                $http.get(`/rest/products/${productId}`).then(resp => {
                    resp.data.qty = 1;
                    console.log("đặt hàng");
                    console.log(resp.data);
                    resp.data.price=(resp.data.price -((resp.data.price*resp.data.category.discount.percentDiscount)/100));
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
        },
        //Xóa sản phẩm khỏi giỏ hàng
        remove(id) {
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },
        //Xóa sạch các mặt hàng trong giỏ
        clear() {
            this.items = []
            this.saveToLocalStorage();
        },
        //Tính thành tiền của 1 sản phẩm
        amt_of(item) { },
        //Tính tổng số lượng các mặt hàng trong giỏ
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        //Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items
                .map(item => item.qty * item.price )
                .reduce((total, qty) => total += qty, 0);
        },
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        //Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }

    }
    $scope.cart.loadFromLocalStorage();
    //Đưa giá về ban đầu
    $scope.totalFinal=$scope.cart.amount;
    //Phần order
   
    $scope.result="";
    $scope.result2='0';
    $scope.voucherVal = {
        cates:[],
        //lấy % của voucher
        getValuedVoucher() {
            const resultName = $scope.result;
            console.log(resultName)
            $http.get(`/rest/vouchers/${resultName}`).then(resp => {
                //gán số voucher được giảm vào biến result 2
                $scope.result2=resp.data.valued;
                resp.data.finalPrice='';
                resp.data.priceStart=$scope.cart.amount,
                this.cates.push(resp.data)
				console.log($scope.result2)
                console.log(this.cates)
            })
        },
        //tính tổng tiền áp dụng với voucher
        getaMountVoucher() {
            const voucherInput = $scope.result;
            const totalItem = $scope.cart.amount;
            // console.log(totalItem)
            if (voucherInput == '') {
               alert("Có voucher giảm giá tội gì mà không sử dụng chúng")
            } else {
                console.log("Ô chọn mã voucher đã điền")
                $scope.totalFinal = totalItem * (100 - $scope.result2) / 100;
                $scope.voucherVal.demo();
                console.log($scope.totalFinal);
            } 
            // return $scope.voucherVal.cates.map(item => {
            //     item.finalPrice=$scope.totalFinal;
            //     return {
            //          voucher_Name: item.voucher_Name ,
            //         quantity:item.quantity--,
            //         finalPrice:item.finalPrice,
            //     }
            // });
        },
        get amount2() {
            return this.cates
                .map(item =>item.priceStart * (100 -  item.valued) / 100)
                .reduce((total, valued) => total += valued, 0);
        },
        get nameVoucher(){
            return this.cates
                .map(item => item.voucher_Name)
                .reduce((total, voucher_Name) => total += voucher_Name, "");
        },
        //Add giá trị vào mảng cates
        demo(){
            return $scope.voucherVal.cates.map(item => {
                item.finalPrice=$scope.totalFinal;
                return {
                     voucher_Name: item.voucher_Name ,
                    quantity:item.quantity--,
                    finalPrice:item.finalPrice,
                }
            });
        },
        

    }
    $scope.cart.loadFromLocalStorage();

    $scope.orders = {
       
        createDate: new Date(),
        address: "",
        status: "",
        //lấy tổng tiền khi áp dụng voucher $scope.result==undefined ? $scope.cart.amount : $scope.voucherVal.amount2
        total:$scope.totalFinal,
        //{username:$("#username").text()}
         account:1,

         vouchername:$scope.voucherVal.amount2,
        //Hàm duyệt các mặt hàng trong giỏ
        get orderDetails() {
            return $scope.cart.items.map(item => {
                return {
                    product: { productId: item.productId },
                    price: item.price,
                    quantity: item.qty,
                    valueVoucher:item.valueVoucher,
                }
            });

        },
        get voucherName_test() {
            return $scope.voucherVal.cates.map(item => {
                return {
                     voucher:{voucher_Name: item.voucher_Name },
                    // valued: item.valued,
                    // condition: item.condition,
                    // statustt:item.statustt,
                    // note:item.note,
                    // quantity:item.quantity,
                    // finalPrice:item.finalPrice,
                }
            });

        },
        purchase() {
            alert("Đặt hàng")
            if($scope.result== ''){
                //var demo= document.getElementById("commentInput").value;
                //console.log(demo);
                $scope.orders.total=$scope.cart.amount;
                $scope.orders.voucher=$scope.result;
                console.log($scope.totalFinal);
                var order = angular.copy(this);
                //console.log(demo);
                console.log(order);
            }else{ 
                $scope.orders.total= $scope.voucherVal.amount2;
                $scope.orders.voucher=$scope.result;
                var order = angular.copy(this);
                console.log(order.voucherName);
                console.log(order);
            }
            console.log($scope.voucherVal.nameVoucher);
            console.log(order.total);
            //Thực hiện đặt hàng
            // $http.post("/rest/orders", order).then(resp => {
            //     alert("Đặt hàng thành công");
                // $scope.cart.clear();
                // location.href = "/order/detail/" + resp.data.id;
            // }).cath(error => {
            //     alert("Đặt hàng lỗi")
            //     console.log(error)
            // })
        },
        thanhToans(){
            console.log($scope.orders.purchase)
            // console.log($scope.totalFinal)
            // console.log($scope.result)
            // console.log($scope.totalFinal)
            // var demo= document.getElementById("commentInput").value;
            // console.log(demo)
        }
    }

    // Khởi tạo biến totalAmount
    $scope.totalAmount = 0;

    // Hàm tính tổng số tiền khi có sự thay đổi trong các mục được chọn
    $scope.calculateTotalAmount = function() {
        const selectedItems = $scope.cart.items.filter(item => item.selected);
        $scope.totalAmount = selectedItems.reduce((total, item) => {
            return total + (item.price * item.qty);
        }, 0);
    };
}
)
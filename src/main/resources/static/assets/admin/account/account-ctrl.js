app.controller("account-ctrl", function($scope, $http, $location) {
	//alert("acoount")
	$scope.items = [];
	$scope.form = [];
	$scope.roles = [];
	$scope.admins = [];
	$scope.authorities = [];
	$scope.checkadmin= [];
	
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
	
	alert
	
	$scope.initialize = function() {
		//load account
		$http.get("/rest/accounts/checkRole").then(resp => {
				if(resp.data == true){
					$scope.checkadmin = true;
					$http.get("/rest/accounts").then(resp => { 
						$scope.items = resp.data;
						$scope.items.forEach(item => {
						item.birthdate = new Date(item.birthdate)
						})
						$scope.reset();
					})
				}else{
					$http.get("/rest/accounts/acountUser").then(resp => {
						$scope.checkadmin = false;
						$scope.items = resp.data;
						$scope.items.forEach(item => {
						item.birthdate = new Date(item.birthdate)
						})
						$scope.reset();
					})
				}
				
		})
		
		

		//load all roles
		$http.get("/rest/roles").then(resp => { 
			$scope.roles = resp.data;
		})
		
		//load staffs và directors (administrators)
		$http.get(`/rest/accounts?admin=true`).then(resp => {
			$scope.admins = resp.data;
		})

		//load authorities of staff và directors
		$http.get(`/rest/authorities?admin=false`).then(resp => {
			$scope.authorities = resp.data
		}).catch(error => {
			$location.path("/unauthorized")
		})

	}



	//khởi đầu
	$scope.initialize();
	//hiện lên form
	$scope.edit = function(item) {
		//alert("edit")
		$("#image").val("");
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(1)").tab('show')
	}
	//xóa form
	$scope.reset = function() {
		//alert("Xóa Form")
		$("#image").val("");
		$scope.form = {
			accountId: $scope.form.accountId,
			//birthdate: new Date(),
            image: 'cloud-upload.jpg',
            status: true,
		}
	}
	
	//Lấy giá trị ô input=text nhập vào
	function getValueInput(id){
		return document.getElementById(id).value.trim();
	}
	
	
	//validate
	$scope.validate = function() {
	//alert("Check trống")
	var username=getValueInput('username');
	var fullname=getValueInput('fullname');
	var sdt=getValueInput('sdt');
	var password=getValueInput('password');
	var email=getValueInput('email');
	var adress=getValueInput('adress');
	var image=getValueInput('image');
	var bithdate=getValueInput('bithdate');
	
	if(username ==""){
		swarning("Không để trống username")
	}else if(fullname==""){	
	swarning("Không để trống fullname")
	}else if(sdt==""){	
	swarning("Không để trống số điện thoại")
	}else if(password==""){	
	swarning("Không để trống password")
	}else if(password.length < 6 ){	
	swarning("Password phải từ 6 ký tự trở lên.")
	}else if(email==""){	
	swarning("Không để trống email")
	}else if(adress==""){	
	swarning("Không để trống địa chỉ")
	}else if(adress==""){	
	swarning("Không để trống địa chỉ")
	}else if(bithdate==""){	
	swarning("Không để trống bithdate")
	}else if(image==""){	
	swarning("Không để trống image")
	}else{
		if(sdt != ""){
			var vnf_regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
			if (vnf_regex.test(sdt) == false){
            	swarning('Số điện thoại của bạn không đúng định dạng!');
            	return false;
        	}
		}
		
		if(email!=""){debugger;
			var email =  document.getElementById('email');
			if(email.value.length > 0){
				var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
				var flag = re.test(email.value);
				if(flag == false){
					swarning('Đề nghị NTT nhập email đúng định dạng quy định');
					setTimeout(function(){
						$(email).focus();
					}, 100);
					$(email).val('');
					return false;
				}
				//return flag;
			}
			
		}
		
		
	//Điều kiện đúng
	console.log("Dữ liệu oke");
	
	
	$scope.create();	
	return true;
	}
	console.log("chạy đến false")
			return false;
	}
	
	
 $scope.validateEmail = function(e){debugger;
	if(e.value.length > 0){
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		var flag = re.test(e.value);
		if(flag == false){
			swarning('Đề nghị NTT nhập email đúng định dạng quy định');
			setTimeout(function(){
				$(e).focus();
			}, 100);
			$(e).val('');
		}
		return flag;
	}
	else{
		return true;
	}
}
	
	//thêm account mới
	$scope.create = function() {
		//alert("Thêm sp")
		var item = angular.copy($scope.form);
		
		$http.post(`/rest/accounts/`, item).then(resp => { 
		
				if(resp.data.phone ==""){
					swarning("Số điện thoại đã được sử dụng");
					return false;
				}
				
				if(resp.data.email ==""){
					swarning("Email đã được sử dụng");
					return false;
				}
				$scope.items.push(resp.data);
				
				$scope.reset();
				sweetalert("Thêm mới thành công");
				$(".nav-tabs a:eq(0)").tab('show')
			}).catch(error => {
				swarning("Tài khoản đã tồn tại");
				console.log("error", error);
			});		
	}
	//cập nhật account
	$scope.update = function() {
		var username=getValueInput('username');
	var fullname=getValueInput('fullname');
	var sdt=getValueInput('sdt');
	var password=getValueInput('password');
	var email=getValueInput('email');
	var adress=getValueInput('adress');
	var image=getValueInput('image');
	var bithdate=getValueInput('bithdate');
	
	if(username ==""){
		swarning("Không để trống username")
	}else if(fullname==""){	
	swarning("Không để trống fullname")
	}else if(sdt==""){	
	swarning("Không để trống số điện thoại")
	}else if(password==""){	
	swarning("Không để trống password")
	}else if(password.length < 6 ){	
	swarning("Password phải từ 6 ký tự trở lên.")
	}else if(email==""){	
	swarning("Không để trống email")
	}else if(adress==""){	
	swarning("Không để trống địa chỉ")
	}else if(adress==""){	
	swarning("Không để trống địa chỉ")
	}else if(bithdate==""){	
	swarning("Không để trống bithdate")
	}else if(image==""){	
	swarning("Không để trống image")
	}else{
		if(sdt != ""){
			var vnf_regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
			if (vnf_regex.test(sdt) == false){
            	alert('Số điện thoại của bạn không đúng định dạng!');
            	return false;
        	}
		}
		
		if(email!=""){debugger;
			var email =  document.getElementById('email');
			if(email.value.length > 0){
				var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
				var flag = re.test(email.value);
				if(flag == false){
					alert('Đề nghị NTT nhập email đúng định dạng quy định');
					setTimeout(function(){
						$(email).focus();
					}, 100);
					$(email).val('');
					return false;
				}
				//return flag;
			}
			
		}
		
		//alert("update sp")
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/${item.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items[index] = item;
			sweetalert("cập nhật Account thành công");
			$(".nav-tabs a:eq(0)").tab('show')
		}).catch(error => {
			swarning("Lỗi cập nhật Account" + error);
			console.log("error", error);
		})
	}
	}
	//xóa account
	$scope.delete = function(item) {
		//alert("delete sp")
		/*$http.delete(`/rest/accounts/${item.accountId}`).then(resp => {
			var index = $scope.items.findIndex(p => p.accountId == item.accountId);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa Thành Công");
		}).catch(error => {
				alert("Lỗi xóa Account"  + error);
			})*/
			
		//alert("update sp")
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/delete/${item.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items[index] = item;
			sweetalert("Xóa Account thành công");
			$scope.initialize();
		}).catch(error => {
			swarning("Lỗi xóa Account" + error);
			console.log("error", error);
		})	
	}

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
	
	$///tìm kiếm
    $scope.timKiem = function () {
        var name = document.getElementById("keyword").value;
        var trangThai = document.getElementById("trangThai").value;
        if (trangThai == "") {
            trangThai = null;
            if(name ==""){
				name ="null";
			}
            //alert("Tìm Kiếm: " + username + " trang thai= " + trangThai)
            $http.get(`/rest/accounts/timKiem/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/accounts/${name}/${trangThai}`).then(resp => {
                $scope.items = resp.data;
            })
        }
       
    }
    $scope.genderStart = function (obj) {
        var trangThai = document.getElementById("trangThai").value;
        //alert("Trang thái " + trangThai )
        if (trangThai == "") {
            $http.get("/rest/accounts").then(resp => {
                $scope.items = resp.data;
            })
        } else {
            $http.get(`/rest/accounts/timKiem/${trangThai}`).then(resp => {
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

	//phân quyền
	$scope.authority_of = function(acc, role) {
		if ($scope.authorities) {
			return $scope.authorities.find(ur => ur.account.username == acc.username && ur.role.roleId == role.roleId);
		}
	}

	$scope.authority_changed = function(acc, role) {
		var authority = $scope.authority_of(acc, role);
		if (authority) {//đã cấp quyền => thu hồi quyền(xóa)
			$scope.revoke_authority(authority);
		}
		else {//chưa được cấp quyền => cấp quyền(thêm mới)
			authority = { account: acc, role: role };
			$scope.grant_authority(authority);
		} $scope.initialize();
	}

	//thêm mới authority
	$scope.grant_authority = function(authority) {
		$http.post(`/rest/authorities`, authority).then(resp => {
			$scope.authorities.push(resp.data)
			sweetalert("Cấp quyền sử dụng thành công");
		}).catch(error => {
			swarning("Cấp quyền sử dụng thất bại");
			console.log("Error", error)
		})
	}
	//Xóa authority
	$scope.revoke_authority = function(authority) {
		$http.delete(`/rest/authorities/${authority.authorityId}`).then(resp => {
			var index = $scope.authorities.findIndex(a => a.id == authority.authorityId);
			$scope.authorities.splice(index, 1);
			sweetalert("Thu hồi quyền sử dụng thành công");
		}).catch(error => {
			swarning("Thu hồi quyền sử dụng thất bại");
			console.log("Error", error);
		})
	}
	
	$scope.removeAccents = function (obj) {
		var str = $("#username").val();
		  str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
		    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
		    str = str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
		    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
		    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
		    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
		    str = str.replace(/đ/g,"d");
		    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
		    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
		    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
		    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
		    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
		    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
		    str = str.replace(/Đ/g, "D");
		    // Some system encode vietnamese combining accent as individual utf-8 characters
		    // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
		    str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
		    str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
		    // Remove extra spaces
		    // Bỏ các khoảng trắng liền nhau
		    str = str.replace(/ + /g," ");
		    str = str.trim();
		    // Remove punctuations
		    // Bỏ dấu câu, kí tự đặc biệt
		    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
		   $("#username").val(str);
		  return str;
}

	$scope.initialize();

})
$(function(){
	/*focus blur*/
	var path="/AppInfoSystem";
	var devCode=$("#devCode");
	var devName=$("#devName");
	var devPassword=$("#devPassword");
	var reset=$("#reset");
	var userType=$("#userType");
	var count=0;
	var a1;
	var a2;
	var a3;
	var a4;
	var button=$("#button");
	var buttons=$("#buttons");
	/*判断该开发者帐号是否是唯一的*/
	devCode.blur(function(){
		$.ajax({
			type:"GET",//请求类型
			url:path+"/login/sole2",//请求的url
			data:{code:devCode.val()},
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.flag=="true"){
					alert("该账号未被使用");
					a1=0;
				}else if(data.flag=="noexists"){
					alert("请输入后台账号");
				}else if(data.flag=="false"){
					alert("该账号已被使用");
				}
			}
		});
	});
	devName.blur(function(){
		if($(this).val().length>=1 && $(this).val()!=null){
			alert("验证通过");
			a2=0;
		}else{
			alert("请填写名称(必填)");
		}
	});
	devPassword.blur(function(){
		var count2=$(this).val();
		if(count2 != null && count2.length >=6
				&& count2.length <=15){
			alert("验证通过");
			$(this).next().attr("color","aqua");
			a3=0;
		}else{
			alert("密码长度必须大于等于6位,小于等于15 位");
		}
	});
	
	userType.change(function(){
		var a=$(this).val();
		if(a=="0"){
			alert("请选择类型");
		}else{
			alert("验证通过");
			a4=0;
		}
	});
	
	button.click(function(){
		if(a1==0 &&
			a2==0 &&
			a3==0 &&
			a4==0){
			if(confirm("是否进行注册")){
				$("#add").submit();
			}else{
				$("form-control").val("");
			}
		}else{
			alert("请填写完整信息");
		}
	});
	
	buttons.click(function(){
		window.location.href="userList";
	});
})
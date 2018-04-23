$(function(){
	/*focus blur*/
	var path="/AppInfoSystem";
	var devCode=$("#devCode");
	var devName=$("#devName");
	var devPassword=$("#devPassword");
	var devEmail=$("#devEmail");
	var devInfo=$("#devInfo");
	var reset=$("#reset");
	var count=0;
	var a1;
	var a2;
	var a3;
	var a4;
	var a5;
	var regexp=/^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;/*邮箱验证*/
	var button=$("#button");
	devCode.next().html("*");
	devName.next().html("*");
	devPassword.next().html("*");
	devEmail.next().html("*");
	devInfo.next().html("*");
	/*判断该开发者帐号是否是唯一的*/
	devCode.blur(function(){
		$.ajax({
			type:"GET",//请求类型
			url:path+"/login/sole",//请求的url
			data:{code:devCode.val()},
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.flag=="true"){
					devCode.next().html("该账号未被使用");
					devCode.next().attr("color","aqua");
					a1=0;
				}else if(data.flag=="noexists"){
					devCode.next().html("请输入开发者账号");
				}else if(data.flag=="false"){
					devCode.next().html("该账号已被使用");
				}
			}
		});
	});
	devName.focus(function(){
		$(this).next().html("请填写名称(必填)");
		$(this).next().attr("color","red");
	});
	devName.blur(function(){
		if($(this).val().length>=1){
			$(this).next().html("验证通过");
			$(this).next().attr("color","aqua");
			a2=0;
		}else{
			$(this).next().html("请填写名称(必填)");
			$(this).next().attr("color","red");
		}
	});
	devPassword.blur(function(){
		var count2=$(this).val();
		if(count2 != null && count2.length >=6
				&& count2.length <=15){
			$(this).next().html("验证通过");
			$(this).next().attr("color","aqua");
			a3=0;
		}else{
			$(this).next().attr("color","red");
			$(this).next().html("密码长度必须大于等于6位,小于等于15 位");
		}
	});
	devEmail.blur(function(){
		var counts=$(this).val();
		if(regexp.test(counts)==false){
			$(this).next().attr("color","red");
			$(this).next().html("邮箱格式不正确！");
		}else{
			$(this).next().html("验证通过");
			$(this).next().attr("color","aqua");
			a4=0;
		}
	});
	devInfo.focus(function(){
		$(this).next().html("请填写简介(必填)");
		$(this).next().attr("color","red");
	});
	devInfo.blur(function(){
		if($(this).val().length>=1){
			$(this).next().html("验证通过");
			$(this).next().attr("color","aqua");
			a5=0;
		}else{
			$(this).next().html("请填写简介(必填)");
		}
	});
	button.click(function(){
		if(a1==0 &&
			a2==0 &&
			a3==0 &&
			a4==0 &&
			a5==0){
			if(confirm("是否进行注册")){
				$("#add").submit();
			}else{
				$("form-control").val("");
			}
		}else{
			alert("请填写完整信息");
		}
	});
})
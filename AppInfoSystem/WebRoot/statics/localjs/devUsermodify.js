$(function(){
	var regexp=/^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;/*邮箱验证*/
	$("#back").on("click",function(){
		window.location.href = "../devUserList";
	});
	$("#devPassword").blur(function(){
		if($(this).val().length>=6 || $(this).val().length<=15){
			alert("密码格式正确");
		}else{
			alert("请输入6-15长度之间的字符");
		}
	});
	$("#devCode").blur(function(){
		if($(this).val().length>=1){
			alert("账号格式正确");
		}else{
			alert("账号不能为空");
		}
	});
	$("#devInfo").blur(function(){
		if($(this).val().length>=1){
			alert("账号格式正确");
		}else{
			alert("开发者简介不能为空");
		}
	});
	$("#devEmail").blur(function(){
		var counts=$(this).val();
		if(regexp.test(counts)==false){
			alert("邮箱格式不正确！");
		}else{
			alert("验证通过");
		}
	});
	
})

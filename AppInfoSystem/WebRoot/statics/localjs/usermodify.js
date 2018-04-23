$(function(){
	$("#back").on("click",function(){
		window.location.href = "userList";
	});
	$("#interfaceLanguage").blur(function(){
		if($(this).val().length>=6 || $(this).val().length<=15){
			alert("密码格式正确");
		}else{
			alert("请输入6-15长度之间的字符");
		}
	});
	$("#softwareName").blur(function(){
		if($(this).val().length>=1){
			alert("账号格式正确");
		}else{
			alert("账号不能为空");
		}
	});
	
})

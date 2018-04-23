
$(".modifyAppInfo").click(function(){
	var obj = $(this);
	var id=obj.attr("userid");
	var a="";
	if(id==1){
		if(confirm("确定要修改账号嘛？(存在极大的安全隐患)")){
			window.location.href="usermodify?id="+ obj.attr("appinfoid")
			+"&userCode="+obj.attr("status")
			+"&userType="+obj.attr("statusname")
			+"&userPassword="+obj.attr("userpwd")
			+"&userName="+obj.attr("userName")
			+"&error="+a;
		}
	}else{
		alert("你没有权限修改");
	}
	
})

/*查询用户页面跳转*/
$(".viewApp").on("click",function(){
	var obj = $(this);
	window.location.href="userView/"+ obj.attr("appinfoid");
});
/*删除账号但是只有超级管理员才有该权限*/
$(".deleteApp").on("click",function(){
	var obj = $(this);
	var statas=obj.attr("status")
	if(confirm("你确定要删除账号为【"+obj.attr("appsoftwarename")+"】及所有权限吗？")){
		if(statas=="1"){
			$.ajax({
				type:"GET",
				url:"delapp.json",
				data:{id:obj.attr("appinfoid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
						var userCode="";
						var userType="";
						var pageIndex="";
						window.location.href="userList?userCode="+userCode+"&userType="+userType+"&pageIndex="+pageIndex;
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除账号为【"+obj.attr("appsoftwarename")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，账号为【"+obj.attr("appsoftwarename")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}else{
			alert("对不起，你没有权限删除");
		}
	}
});

	

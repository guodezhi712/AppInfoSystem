/*修改页面跳转*/
$(".modifyAppInfo").click(function(){
	var obj = $(this);
	window.location.href="devuserViews/"+ obj.attr("appinfoid");
});
/*查询用户页面跳转*/
$(".viewApp").on("click",function(){
	var obj = $(this);
	window.location.href="devuserView/"+ obj.attr("appinfoid");
});
/*删除账号但是只有超级管理员才有该权限*/
$(".deleteApp").on("click",function(){
	var obj = $(this);
	if(confirm("你确定要删除账号为【"+obj.attr("appsoftwarename")+"】及所有权限吗？")){
			$.ajax({
				type:"GET",
				url:"deldevuser.json",
				data:{id:obj.attr("appinfoid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
						var debName="";
						var pageIndex="";
						window.location.href="devUserList?devName="+debName+"&pageIndex="+pageIndex;
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
	}
});

	

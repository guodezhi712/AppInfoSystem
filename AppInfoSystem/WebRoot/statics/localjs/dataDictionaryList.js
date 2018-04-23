/*修改页面跳转*/
$(".modifyAppInfo").click(function(){
	var obj = $(this);
	window.location.href="dataDictionaryupd/"+ obj.attr("appinfoid");
});
/*查询用户页面跳转*/
$(".viewApp").on("click",function(){
	var obj = $(this);
	window.location.href="dataDictionaryView/"+ obj.attr("appinfoid");
});
/*删除账号但是只有超级管理员才有该权限*/
$(".deleteApp").on("click",function(){
	var obj = $(this);
	var devid=obj.attr("devid");
	if(devid==1){
		if(confirm("你确定要删除账号为【"+obj.attr("appsoftwarename")+"】及所有权限吗？")){
			$.ajax({
				type:"GET",
				url:"deldevDictionary.json",
				data:{id:obj.attr("appinfoid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
						var typeCode="";
						var pageIndex="";
						window.location.href="dataDictionaryList?typeCode="+typeCode+"&pageIndex="+pageIndex;
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除类型为【"+obj.attr("appsoftwarename")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，类型为【"+obj.attr("appsoftwarename")+"】不存在");
					}else if(data.delResult == "notdel"){
						alert("对不起，类型为【"+obj.attr("appsoftwarename")+"】存在已经被使用暂时不能删除");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	}else{
		alert("对不起，你暂时还没有改权限");
	}
	
});

	

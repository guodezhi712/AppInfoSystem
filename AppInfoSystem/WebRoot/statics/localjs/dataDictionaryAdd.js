$(function(){
	$("#back").click(function(){
		location.href="dataDictionaryList";
	});
	/**
	 * 验证该新增的信息是否被使用
	 */
	$("#send").click(function(){
		var valueName=$("#valueName").val();
		var valueId=$("#valueId").val();
		var typeName=$("#typeName").val();
		var typeCode=$("#typeCode").val();
		if(confirm("是否添加该类型")){
			$.ajax({
				type:"GET",
				url:"Dictionarys.json",
				data:{tCode:typeCode,tName:typeName,vId:valueId,vName:valueName},
				dataType:"json",
				success:function(data){
					if(data.map=="true"){
						alert("该类型可以创建");
						$("#Dictionary").submit();
					}else if(data.map=="false"){
						alert("该类型已经存在不能再创建");
					}else if(data.map=="notexis"){
						alert("该新增的类型不存在");
					}
				},error:function(data){
					alert("新增类型ajax异步验证失败！");
				}
			});
		}
	});
})
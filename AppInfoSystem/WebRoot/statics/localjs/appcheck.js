$(function(){
	var path="/AppInfoSystem/backend/down";
	$("#back").on("click",function(){
		window.location.href = "applist";
	});
	$("#apk").on("click",function(){
		window.location.href = path+"?images="+$("#a").val();
	});
});

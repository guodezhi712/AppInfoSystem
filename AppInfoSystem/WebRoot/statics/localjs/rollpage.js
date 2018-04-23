/*用来动态的获取当前页码数，可以控制分页按钮的显示*/
function page_nav(frm,num){
		frm.pageIndex.value = num;
		frm.submit();
}

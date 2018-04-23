var page1=document.getElementById("page1");
var music=document.getElementById("music");
var audio=document.getElementsByTagName("audio")[0];
//当音乐播放完毕时，拨片也是自动停止的
audio.addEventListener("ended",function(event){
	music.setAttribute("class","");
},false);
//点击播放按钮，停止播放音乐
music.onclick=function(){
	if(audio.paused){
		audio.play();
		this.setAttribute("class","play");
		/*this.style.animationPlayState="running";兼容性不好*/
	}else{
		audio.pause();
		this.setAttribute("class","");
		/*this.style.animationPlayState="paused";兼容性不好*/
	}
}
//点击播放按钮，停止播放音乐
music.addEventListener("touchstart",function(event){
	if(audio.paused){
		audio.play();
		this.setAttribute("class","play");
		/*this.style.animationPlayState="running";*//*兼容性不好*/
	}else{
		audio.pause();
		this.setAttribute("class","");
		/*this.style.animationPlayState="paused";*//*兼容性不好*/
	}
},false)

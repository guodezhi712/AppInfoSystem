<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>401</title>

    <!-- Bootstrap -->
    <link href="statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="statics/css/nprogress.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="statics/css/custom.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/401.css">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <!-- page content -->
        <div class="col-md-12">
          <div class="col-middle">
            <div class="text-center text-center">
              <h1 class="error-number">401</h1>
              <!-- <h2>对不起，你没有权限访问，请回到</h2> -->
              <h2>这辈子，敲代码是不可能的啦</h2>
              <p><a href="${pageContext.request.contextPath }/endlogin/login.html"><!-- 请返回到 系统入口 -->
              	因为bug实在是太多了啦，老表
              </a>
              </p>
              <div id="s">
				  	 <div class="music"><!--播放图片-->
				        <img class="play" id="music" src="statics/images/恶搞图片.jpg" style="margin:150px 400px"><!--唱片-->
				     </div>
			         <audio autoplay="true">
			   				<source src="statics/audio/我的看守所.mp3" type="audio/mp3"></source>
			   		 </audio>
   			</div>
             <!--  <div class="mid_center">
                <h3>Search</h3>
                <form>
                  <div class="col-xs-12 form-group pull-right top_search">
                    <div class="input-group">
                      <input type="text" class="form-control" placeholder="Search for...">
                      <span class="input-group-btn">
                              <button class="btn btn-default" type="button">Go!</button>
                          </span>
                    </div>
                  </div>
                </form>
              </div> -->
            </div>
          </div>
        </div>
        <!-- /page content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="statics/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="statics/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="statics/js/fastclick.js"></script>
    <!-- NProgress -->
    <script src="statics/js/nprogress.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="statics/js/custom.min.js"></script>
    <script type="text/javascript" src="statics/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="statics/js/401.js"></script>
  </body>
</html>
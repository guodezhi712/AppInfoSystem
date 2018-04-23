<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath}/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath}/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
   <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath}/statics/css/custom.min.css" rel="stylesheet">
    <title>系统登录入口</title>
  </head>
  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
           <c:if test="${uid==1}">
						<form
							action="${pageContext.request.contextPath}/login/selectdevlogin.html"
							method="post">
							<h1>APP信息管理系统</h1>
							<h3 style="color:red ">${e.message}</h3>
							<div>
								<input id="devCode" name="devCode" type="text"
									class="form-control" placeholder="请输入用户名" required="" />
							</div>
							<div>
								<input id="devPassword" name="devPassword" type="password"
									class="form-control" placeholder="请输入密码" required="" />
							</div>
							<div>
								<input type="submit" class="btn btn-default submit" value="登录" />
								<a class="btn btn-default submit"
									href="${pageContext.request.contextPath}/login/add.html">注册</a>
							</div>

							<div class="clearfix"></div>
						
							<div class="separator">
								<p class="change_link">
									<a href="devlogin.html" class="to_register"> 返回首页</a>
								</p>

								<div class="clearfix"></div>
								<br />

								<div>
									<!-- <h1>
										<i class="fa fa-paw"></i> Gentelella Alela!
									</h1> -->
									<p>©2018,该项目版权所属</p>
								</div>
							</div>
						</form>
					</c:if>
            <c:if test="${uid==2}">
						<form
							action="${pageContext.request.contextPath}/login/backendUser.html"
							method="post">
							<h1>后台登录管理系统</h1>
							<h3 style="color:red ">${e.message}</h3>
							<div>
								<input id="devCode" name="devCode" type="text"
									class="form-control" placeholder="请输入用户名" required="" />
							</div>
							<div>
								<input id="devPassword" name="devPassword" type="password"
									class="form-control" placeholder="请输入密码" required="" />
							</div>
							<div>
								<input type="submit" class="btn btn-default submit" value="登录" />
								<input type="reset" class="btn btn-default submit" value="重置" />
							</div>

							<div class="clearfix"></div>

							<div class="separator">
								<p class="change_link">
									<a href="devlogin.html" class="to_register">返回首页</a>
								</p>

								<div class="clearfix"></div>
								<br />

								<div>
									<!-- <h1>
										<i class="fa fa-paw"></i> Gentelella Alela!
									</h1> -->
									<p>©2018,该项目版权所属,加上sso单点登录功能<如：qq登录></p>
								</div>
							</div>
						</form>
					</c:if>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>Create Account</h1>
              <div>
                <input type="text" class="form-control" placeholder="Username" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="Email" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">Submit</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Gentelella Alela!</h1>
                  <p>©2016 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and Terms</p>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
    <%-- <div>
		<marquee direction="left">
		<img src="${pageContext.request.contextPath}/statics/images/img.jpg"/>
		<img src="${pageContext.request.contextPath}/statics/images/img1.jpg"/>
		</marquee>
	</div> --%>
  </body>
</html>
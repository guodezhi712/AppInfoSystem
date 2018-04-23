<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
   <%--  <link href="${pageContext.request.contextPath}/statics/css/font-awesome.min.css" rel="stylesheet"> --%>
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath}/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
   <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath}/statics/css/custom.min.css" rel="stylesheet">
    <title>后台用户注册页面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/login/Useradd.js"></script>
  </head>
  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="${pageContext.request.contextPath}/login/register2.html" method="post" id="add">
              <h1>后台用户注册页面</h1>
	              <div>
	                <input name="userCode" id="devCode" type="text" class="form-control" placeholder="请输入用户帐号" required="" />
	              	<font color="red"></font>
	              </div>
	              <div>
	                <input name="userName" id="devName" type="text" class="form-control" placeholder="请输入用户名称" required="" />
	              	<font color="red"></font>
	              </div>
	              <div>
	                <select id="userType" name="userType" class="form-control" required="">
	                	<option value="0">请选择</option>
	                	<c:forEach items="${tion}" var="dictionary">
	                		<option value="${dictionary.valueId}">${dictionary.valueName}</option>
	                	</c:forEach>
	                </select>
	              	<font color="red"></font>
	              </div>
	              <div>
	                <input style="margin-top:15px" name="userPassword" id="devPassword" type="password" class="form-control" placeholder="请输入开发者密码" required="" />
	              	<font color="red"></font>
	              </div>
	                  <input type="button" id="button" class="btn btn-default submit" value="注册">
	                  <input type="reset" id="reset" class="btn btn-default" value="重置">
	                  <input type="reset" id="buttons" class="btn btn-default" value="返回">
              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">New to site?
                  <a href="${pageContext.request.contextPath}/endlogin/login.html" class="to_register"> Create Account </a>
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
  </body>
</html>
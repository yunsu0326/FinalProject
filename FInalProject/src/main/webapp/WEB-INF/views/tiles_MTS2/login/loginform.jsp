<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%
	String ctxPath = request.getContextPath();
   //      /board
%> 

    <style>
        /* Custom styles for the login page */
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 100px;
        }
        .login-form {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 40px;
        }
        label {
            font-weight: bold;
        }
        .btn-login {
            width: 150px;
            height: 40px;
        }
    </style>
     
<script type="text/javascript">

$(document).ready(function(){
	
	$("button#btnLOGIN").click(function(){
		func_ligin();
	});
	
	$("input:password#pwd").keydown(function(e){
		if(e.keyCode == 13){ // enter key 입력시
			func_ligin();	}
	});
}); // end of $(document).ready(function(){
	
	// function Declaration 
	function func_ligin(){
		const userid = $("input#email").val(); 
	     const pwd = $("input#pwd").val(); 
	   
	     if(userid.trim()=="") {
	        alert("이메일를 입력하세요!!");
	       $("input#email").val(""); 
	       $("input#email").focus();
	       return; // 종료 
	     }
	   
	     if(pwd.trim()=="") {
	       alert("비밀번호를 입력하세요!!");
	       $("input#pwd").val(""); 
	       $("input#pwd").focus();
	       return; // 종료 
	     }
	     
	     const frm = document.loginFrm;
	     
	     frm.action = "<%= ctxPath%>/loginEnd.gw";
	     frm.method = "post";
	     frm.submit();
	};
	
</script>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-6">
            <div class="login-form">
                <h2>Groupware 로그인</h2>
                <form name="loginFrm">
                    <div class="form-group">
                        <label for="email">이메일</label>
                        <input type="text" class="form-control" name="email" id="email" value=""/>
                    </div>
                    <div class="form-group">
                        <label for="pwd">비밀번호</label>
                        <input type="password" class="form-control" name="pwd" id="pwd" value=""/>
                    </div>
                    <div class="form-group text-center">
                        <button class="btn btn-primary btn-login" type="button" id="btnLOGIN">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图形验证码</title>
</head>
<script type="text/javascript" src="/static/js/gVerify.js"></script>
<body>
<div id="v_container" style="width: 200px;height: 50px;"></div>
<input type="text" id="code_input" value="" placeholder="请输入验证码"/><button id="my_button">验证</button>
</body>
<script>
    var verifyCode = new GVerify({
        id:"v_container",
        randomString:${requestScope.code}
    });
    document.getElementById("my_button").onclick = function(){
        var res = verifyCode.validate(document.getElementById("code_input").value);
        if(res){
            alert("验证正确");
        }else{
            alert("验证码错误");
        }
    }
</script>
</html>

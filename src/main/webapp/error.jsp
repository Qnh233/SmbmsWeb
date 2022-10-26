<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
<h1></h1>


<div id="app">


    <el-empty
            description="无用户数据,请登录后再访问该页面！">
        <el-link type="primary" href="login.jsp">返回登录</el-link>
    </el-empty>
<%--    <a href="login.jsp" >返回</a>--%>



</div>

<script src="js/webpack.base.conf.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script>
   new Vue({
       el:"#app",
        data() {
            return {
                message: 'Hello Vue!'
            }
        },
       mothods:{


       }
    })

</script>
</body>
</html>
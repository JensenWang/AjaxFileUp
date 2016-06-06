<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Ajax实现多图片上传</title>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="styles.css">
  </head>
  <body>
    <div class="container">
    
      <!-- 标题 start -->
      <h3>Ajax实现图片上传</h3>
      <!-- 标题 end -->
      
      <!-- 按钮 start -->
      <input type="file" multiple="multiple" accept="image/*" onchange="fileUpload()" style="display:none" id="fileUp"/>
      <a href="javascript:void(0);" class="btn" onClick="openBrows()">上传图片</a>
      <!-- 按钮 end -->
      
      <!-- 进度条 start -->
      <div id="progress">
        <div id="percent"></div>
      </div>
      <!-- 进度条 end -->
      
      <!-- 上传结果的展示区域 start -->
      <ul id="imgBox">
      </ul>
      <!-- 上传结果的展示区域 end -->
      
    </div>
  </body>
  <script type="text/javascript">
    function openBrows() {
      	var fileObj = document.getElementById("fileUp");
      	fileObj.click();
    }
  
    /* 文件上传 */
    function fileUpload() {
        var fileObj = document.getElementById("fileUp");
        // 获取文件选择框所选文件的所有文件的集合
        var files = fileObj.files;
        // formdata对象将所有文件添加到formdata中
        var form = new FormData();
        for ( var i = 0; i < files.length; i++) {
          // console.log(files[i]);
          form.append("file"+i,files[i]);
        }
        // Ajax做文件上传
        // 创建Ajax对象,XMLHttpRequest
        var xhr = new XMLHttpRequest();
        // 打开一个URL地址，form提交方式，提交目的地，是否打开异步
        xhr.open("post", "FileUp.do", true);
        // 监听请求状态
        xhr.onreadystatechange = function(){
          if(xhr.readyState==4 && xhr.status==200){
            // trim();去掉空格
            var result = xhr.responseText.trim();
            // console.log(result);
            eval(result); // 将字符串变为代码执行
            // 遍历结果数组获取上传文件路径，并做展示
            var imgUl = document.getElementById("imgBox");
            for (var i = 0; i < filePaths.length; i++){
              imgUl.innerHTML+="<li style='display:inline'><img src='"+filePaths[i]+"'></li>";
              //console.log(filePaths[i]);
            }
          }
        };
        // 获取进度
        // 新版本的XMLHttpRequest对象，传送数据的时候，有一个progress事件，用来返回进度信息。
        xhr.upload.addEventListener("progress",function(event){
          var pro = document.getElementById("progress");
          var per = document.getElementById("percent");
          pro.style.display="block";	// 显示进度条
          if (event.lengthComputable){
            //如果event.lengthComputable不为真，则event.total等于0
            //event.total是需要传输的总字节，event.loaded是已经传输的字节
            var percent = Math.round(event.loaded/event.total*100) + "%";
            //console.log(percent);
            per.style.width=percent;
            per.innerHTML="&nbsp;&nbsp;"+percent;
          }
        },false);
        // 发送请求，带上表单数据提交
        xhr.send(form);
    }
  </script>
</html>

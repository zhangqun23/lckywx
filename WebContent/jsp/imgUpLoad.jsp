<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.addBorder{  
    border:1px solid #ccc;  
}  
  
#imgDiv{  
    width:100px;  
    height:100px;  
}  
  
#imgContent{  
    width: 100%;  
    height:100%;  
}  
</style>
</head>
<body>
<input type="file">
<br/>
<p>-------------爱你的分割线--------------</p>
<br/>
<form id="imgForm">  
    <input type="file" class="addBorder">  
    <br/>  
    <button type="button" onclick="loadImg()">获取图片</button>  
</form>  
  
<div class="addBorder" id="imgDiv">  
    <img id="imgContent">  
</div>
<br/>
<p>-------------爱你的分割线--------------</p>
<br/>
<input type="file" id="fileElem" multiple accept="image/*"  onchange="handleFiles(this)">
<div id="fileList" style="width:200px;height:200px;"></div>

</body>

<script>
function loadImg(){  
    //获取文件  
    var file = $("#imgForm").find("input")[0].files[0];  
  
    //创建读取文件的对象  
    var reader = new FileReader();  
  
    //创建文件读取相关的变量  
    var imgFile;  
  
    //为文件读取成功设置事件  
    reader.onload=function(e) {  
        alert('文件读取完成');  
        imgFile = e.target.result;  
        console.log(imgFile);  
        $("#imgContent").attr('src', imgFile);  
    };  
  
    //正式读取文件  
    reader.readAsDataURL(file);  
}

window.URL = window.URL || window.webkitURL;
var fileElem = document.getElementById("fileElem"),
fileList = document.getElementById("fileList");
function handleFiles(obj) {
    var files = obj.files,
    img = new Image();
    if(window.URL){
        //File API
        alert(files[0].name + "," + files[0].size + " bytes");
        img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
        img.width = 200;
        img.onload = function(e) {
            window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
        }
        fileList.appendChild(img);
    }else if(window.FileReader){
        //opera不支持createObjectURL/revokeObjectURL方法。我们用FileReader对象来处理
        var reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onload = function(e){
            alert(files[0].name + "," +e.total + " bytes");
            img.src = this.result;
            img.width = 200;
            fileList.appendChild(img);
        }
    }else{
        //ie
        obj.select();
        obj.blur();
        var nfile = document.selection.createRange().text;
        document.selection.empty();
        img.src = nfile;
        img.width = 200;
        img.onload=function(){
            alert(nfile+","+img.fileSize + " bytes");
        }
        fileList.appendChild(img);
    }
}
</script>
<script type="text/javascript" src="../js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/lib/jquery.json-2.2.min.js"></script>
</html>
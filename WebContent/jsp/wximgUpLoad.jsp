<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="button" onclick="get9img()">点我获取图片
</body>
<script type="text/javascript" src="../js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/lib/jquery.json-2.2.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script language = "javascript" >
window.onload = function(){
	$.ajax({
		type: "POST",
			url: "/lckywx/upLoadImg/getImgCon.do",  
			data: "",
			success: function(data){
				var tt = JSON.parse(data)
				 wx.config({      
				        debug: true,
				        appId: tt.appID,
				        timestamp: tt.timestamp,
				        nonceStr: tt.nonce,
				        signature: tt.signature,
				        jsApiList: [
				                     'chooseImage',
				                     'previewImage', 
				                     'uploadImage',
				                     'downloadImage'
				                 ]
				    });
			}
	});
}
var images = {
	localId: [],
	serverId: []
};

function get9img(){
    wx.chooseImage({
        count: 9, // 最多能选择多少张图片，默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片    
            var localIdImg=localId.toString().split(",");
            //上传图片接口                            
                   
                if (localIdImg.length == 0) {
                  return;
                }
                var i = 0, length = images.localId.length;
                images.serverId = [];
                function upload() {
                  wx.uploadImage({
                    localId: localId[i],
                    success: function (res) {                                     
                    	alert(JSON.stringify(res));
                    },
                    
                    fail: function (res) {
                      alert(JSON.stringify(res));
                    }
                  });
                }
                upload();
               
        }                    
    });
}
                                                   
</script>

</html>
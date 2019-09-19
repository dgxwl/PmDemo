<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>私信</title>
<link rel="stylesheet" type="text/css" href="../css/chat.css">
<script src="../js/jquery-3.1.1.min.js"></script>
<style type="text/css">
	* {
		margin: 0px;
		padding: 0px;
	}
	
	h3 {
		width: 500px;
		margin: 0px auto;
	}
	
	.wrapper {
		border: 1px solid #006699;
		width: 600px;
		height: 750px;
		margin: 0px auto;
		overflow: auto;
	}
	
	#form_div {
		width: 300px;
		margin: 0px auto;
		margin-top: 20px;
	}

	.me {
		top: 100px;
		left: 100px;
		display: inline-block;
		background: #009966;
		border: 1px solid #009966;
		-moz-border-radius: 12px;
		-webkit-border-radius: 12px;
		border-radius: 12px;
	}

	.me p {
		padding: 10px;
		color: white;
	}
	
	.other {
		top: 100px;
		left: 100px;
		display: inline-block;
		border: 1px solid #009966;
		-moz-border-radius: 12px;
		-webkit-border-radius: 12px;
		border-radius: 12px;
	}

	.other p {
		padding: 10px;
		color: black;
	}
	
	.ptime {
		color: gray;
		font-size: 10px;
	}
	
	.outer {
		margin-top: 10px;
	}
</style>
</head>
<body>
	<h3>正在与 <b>${param.recvName}</b> 对话</h3>
	<div id="wrapper" class="wrapper">
		
	</div>
	
	<div id="form_div">
		<div id="form_send" method="post">
			<input id="text" name="text" size="30" placeholder="请输入私信内容..">
			<input id="btn_send" type="button" value="发文字" onclick="doSend()">
			<input id="btn_send1" type="button" value="发图片" onclick="doSend1()">
			<input id="select_img" type="file" style="display: none;">
		</div>
	</div>
</body>
<script>
	
	var websocket = null;
	$(function() {
		if ('WebSocket' in window) {
			websocket = new WebSocket('ws://localhost:8080/wspm_img.do');
		} else {
			alert('当前浏览器不支持websocket');
		}
		
		websocket.onopen = function() {
			
		}
		
		websocket.onerror = function() {
					
		}
				
		websocket.onmessage = function(event) {
			let bubbleStyle = "<div class='left'>";
			let newNode = "<div class='clearfloat'>" +
							"<div class='author-name'>" +
								"<small class='chat-date'>" + new Date() + "</small>" +
							"</div>" +
							bubbleStyle +
								"<div class='chat-message'>" +
									event.data +
								"</div>" +
							"</div>" +
						"</div>";
			$("#wrapper").append(newNode);
		}
		
		websocket.onclose = function() {
			
		}
		
		$("#select_img").change(function() {
			let imgFile = document.getElementById("select_img").files[0];
			let reader = new FileReader();
			let rawData = new ArrayBuffer();
			reader.loadend = function(e) {};
			reader.onload = function(e) {
				let rawData = e.target.result;
				let byteArray = new Uint8Array(rawData);
				let fileByteArray = [];
				websocket.send(byteArray.buffer);
			}
			reader.readAsArrayBuffer(imgFile);
			console.log('已发送')
		});
	})
	
	function doSend() {
		if ($("#text").val() == "") {
			return ;
		}
		let message = {};
		message.toUser = ${param.receiverUid};
		message.content = $("#text").val();
		websocket.send(JSON.stringify(message));
		let bubbleStyle = "<div class='right'>";
		let newNode = "<div class='clearfloat'>" +
			"<div class='author-name'>" +
				"<small class='chat-date'>" + new Date() + "</small>" +
			"</div>" +
			bubbleStyle +
				"<div class='chat-message'>" +
					$("#text").val() +
				"</div>" +
			"</div>" +
		"</div>";
		$("#wrapper").append(newNode);
		$("#text").val("");
	}
	
	function doSend1() {
		$("#select_img").click();
	}
	
	window.onbeforeunload = function() {
		websocket.close();
	}
</script>
</html>
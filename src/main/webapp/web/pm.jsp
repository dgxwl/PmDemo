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
<%-- 			<input type="hidden" id="senderUid" name="senderUid" value="${sessionScope.user.id}"> --%>
<%-- 			<input type="hidden" id="receiverUid" name="receiverUid" value="${param.receiverUid}"> --%>
			<input id="text" name="text" size="30" placeholder="请输入私信内容..">
			<input id="btn_send" type="button" value="SEND" onclick="doSend()">
		</div>
	</div>
</body>
<script>
	
	$(function() {
		
		var lastId = 0
		
		//首次获取私信记录
		$.ajax({
			"url": "${pageContext.request.contextPath}/pm/getPm.do",
			"data": "senderUid=" + ${sessionScope.user.id} + "&receiverUid=" + ${param.receiverUid},
			"type": "GET",
			"dataType": "json",
			"success": function(obj) {
				if (obj.state == 1) {
// 					console.log('初始化的ajax')
					for (var i = 0; i < obj.data.length; i++) {
						var hour = new Date(obj.data[i].createTime).getHours()
						var minute = new Date(obj.data[i].createTime).getMinutes()
						var second = new Date(obj.data[i].createTime).getSeconds()
						minute = minute < 10 ? "0"+minute : minute
						var time = hour + ":" + minute + ":" + second
						var bubbleStyle = obj.data[i].senderUid == ${sessionScope.user.id} ? "<div class='right'>" : "<div class='left'>"
						
						var newNode = "<div class='clearfloat'>" +
											"<div class='author-name'>" +
												"<small class='chat-date'>" + time + "</small>" +
											"</div>" +
											bubbleStyle +
												"<div class='chat-message'>" +
													obj.data[i].text +
												"</div>" +
											"</div>" +
										"</div>"
						$("#wrapper").append(newNode)
						
						lastId = obj.data[i].id
					}
// 					console.log("初始化最后一条id:" + lastId)
				}
			}
		})
		
		//刷新聊天列表
		setInterval(function() {
			$.ajax({
				"url": "${pageContext.request.contextPath}/pm/getNewPm.do",
				"data": "id=" + lastId + "&senderUid=" + ${sessionScope.user.id} + "&receiverUid=" + ${param.receiverUid},
				"type": "GET",
				"dataType": "json",
				"success": function(obj) {
					if (obj.state == 1) {
// 						console.log('更新的ajax')
						console.log('obj.data.length:'+obj.data.length)
						if (obj.data.length > 0) {
							for (var i = 0; i < obj.data.length; i++) {
								var hour = new Date(obj.data[i].createTime).getHours()
								var minute = new Date(obj.data[i].createTime).getMinutes()
								var second = new Date(obj.data[i].createTime).getSeconds()
								minute = minute < 10 ? "0"+minute : minute
								var time = hour + ":" + minute + ":" + second
								var bubbleStyle = obj.data[i].senderUid == ${sessionScope.user.id} ? "<div class='right'>" : "<div class='left'>"
								
								var newNode = "<div class='clearfloat'>" +
												"<div class='author-name'>" +
													"<small class='chat-date'>" + time + "</small>" +
												"</div>" +
												bubbleStyle +
													"<div class='chat-message'>" +
														obj.data[i].text +
													"</div>" +
												"</div>" +
											"</div>"
								$("#wrapper").append(newNode)
								//将滚动条移动到底部:
								$("#wrapper").scrollTop($("#wrapper").prop('scrollHeight'))
								
								lastId = obj.data[i].id
							}
// 							console.log("更新de最后一条id:" + lastId)
						}
					}
				}
			})
		}, 1000)
	})

	//异步发送私信
	function doSend() {
		if ($("#text").val() == "") {
			return
		}
		
		$.ajax({
			"url": "${pageContext.request.contextPath}/pm/sendPm.do",
			"data": "senderUid=" + ${sessionScope.user.id} + "&receiverUid=" + ${param.receiverUid} + "&text=" + $("#text").val(),
			"type": "POST",
			"dataType": "json",
			"success": function(obj) {
				if (obj.state == 1) {
					//OK!
				}
			}
		})
		
		$("#text").val("")
	}
</script>
</html>
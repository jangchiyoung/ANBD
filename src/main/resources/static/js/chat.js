/*<![CDATA[*/
           const socket = new SockJS('/stomp/chat');
           const stomp = Stomp.over(socket);
            
            let product_no = '';
            let send_id = '';
            let receive_id = '';
            let str = '';
            stomp.connect({}, function (){
            	
            });
           function getChat(index,id){
                var product_no = index;
                var send_id = $('#send_id').val();
                var receive_id = id;
                var date = new Date();
               	var data  = {
            		   	product_no   : product_no, 
            		   	send_id  :  send_id,
            		   	receive_id : receive_id,
            		   	date :date
                       		};
               
                  $.ajax({
                     	type      : "POST",
                     	url       : "/anbd/message",
                    	data      :  JSON.stringify(data), 
                     	contentType : "application/json",
                    	success    : function(data) {
                    		
                         	var msgtext = '';
                            $("#msgArea").empty();
                             
                        	 for(key in data.list){
                             	if(data.list[key].sender == send_id){
                              		msgtext = "<div class='right_div'>";
                              		msgtext += "<div class='mail_right_content'>"+ data.list[key].message+"</div>";
                               		msgtext += "<div class='mail_right_time'>"+ timeForToday(data.list[key].date)+"</div>";
                              		msgtext += "</div>";
                              		 $("#msgArea").scrollTop($("#msgArea")[0].scrollHeight);
                             	}else {
                               		msgtext = "<div class='left_div'>";
                               		msgtext += "<div class='mail_id_content'>"+data.list[key].nickname+"</div>";
                              		msgtext += "<div class='mail_left_content'>"+ data.list[key].message+"</div>";
                              		msgtext += "<div class='mail_left_time'>"+ timeForToday(data.list[key].date)+"</div>";
                              		msgtext += "</div>";
                              		 $("#msgArea").scrollTop($("#msgArea")[0].scrollHeight);
                            	 }
                           	 		 $("#msgArea").append(msgtext);
                       		  }
                       		  $("#user_img").attr("th:src", data.list[key].img);
                       		  
                       		  var user_nickname = '';
                       		  var sold_out = '';
                              $("#userNickName").empty();
                              $("#soldOut").empty();
                              
                              	user_nickname = "<span class='span_point'>"+ data.list[key].nickname +'님' + "</span>";
                              	sold_out = "<button class='btn-outline-secondary Complete' onclick='sold_out("+data.list[key].product_no+",\""+data.list[key].product_seller+"\")'>판매 완료</button>";
                              $("#userNickName").append(user_nickname);
                              $("#soldOut").append(sold_out);
                              
                       		 
                                  
                         		$("#aas").empty();
                           		 var str2 = '';
                            		 str2  = "<input type='text' id='msg' onkeyup='if(window.event.keyCode==13){sendMsg()}' class='form-control' style='width:500px'>";
                                	 str2 += "<div class='input-group-append'>";
                                	 str2 += "<input type='hidden' id='product_no' value='"+product_no+"'>";
                               		 str2 += "<input type='hidden' id='send_id' value='"+send_id+"'>";
                               		 str2 += "<input type='hidden' id='receive_id' value='"+receive_id+"'>";
                                	 str2 += "<button class='btn-outline-secondary' type='button' onclick='sendMsg()' id='button-send'>전송</button>";
                                	 str2 += "</div>";
                                
                                $("#aas").append(str2);
                                   
                                 product_No = product_no;
                                 send_Id = send_id;
                                 receive_Id = receive_id; 
                                 dateTime = date;
                                 
                        		 stomp.unsubscribe(product_No);
                                 stomp.subscribe("/sub/chat/room/" + product_No, function (chat) {
                                   var content = JSON.parse(chat.body);
                                   if(send_Id == content.chat_send_client_id){
                                       str = "<div class='right_div'>";
                                       str += "<div class='mail_right_content'>"+ content.chat_message+"</div>";
                                       str += "<div class='mail_right_time'>"+ timeForToday(new Date())+"</div>";
                                       str += "</div>";
                                   $("#msgArea").append(str);
                                   str='';
                                   $("#msgArea").scrollTop($("#msgArea")[0].scrollHeight);
                                   }
                                   else {
                                       str = "<div class='left_div'>";
                                       str += "<div class='mail_id_content'>"+ data.list[key].nickname+"</div>";
                                       str += "<div class='mail_left_content'>"+ content.chat_message+"</div>";
                                       str += "<div class='mail_left_time'>"+ timeForToday(new Date())+"</div>";
                                       str += "</div>";
                                   $("#msgArea").append(str);
                                   str='';
                                   $("#msgArea").scrollTop($("#msgArea")[0].scrollHeight);
                                   }
                     },{id:product_No});
                      },
                      error      : function(error) {
                         console.log(error);
                      }
                  });
             }
             
               function sendMsg(){
                    var msg = document.getElementById("msg");
                    stomp.send('/pub/chat/message', {},
                    		
                    JSON.stringify({
                    	chat_product_no: product_No, 
                    	chat_message: msg.value, 
                    	chat_send_client_id: send_Id,
                    	chat_receive_client_id : receive_Id, 
                    	chat_date : new Date()
                    }));
                    msg.value = '';
                };
                
                const input = document.getElementById("msg");
                input.addEventListener('keyup',function(e){
                    if (e.keyCode === 13) {
                       sendMsg();
                  }  
                });
                
                function timeForToday(createdAt) {
                	const today = new Date();
                	const createdDay = new Date(createdAt);
                	const seconds = Math.floor((today.getTime() - createdDay.getTime()) / 1000);
                	  if (seconds < 60) return `방금 전`;
                	  const minutes = seconds / 60;
                	  if (minutes < 60) return `${Math.floor(minutes)}분 전`;
                	  const hours = minutes / 60;
                	  if (hours < 24) return `${Math.floor(hours)}시간 전`;
                	  const days = hours / 24;
                	  if (days < 7) return `${Math.floor(days)}일 전`;
                	  const weeks = days / 7;
                	  if (weeks < 5) return `${Math.floor(weeks)}주 전`;
                	  const months = days / 30;
                	  if (months < 12) return `${Math.floor(months)}개월 전`;
                	  const years = days / 365;
                	  return `${Math.floor(years)}년 전`;
                }
                function sold_out(no,seller) {
                		var product_no = no;
                		var product_seller = seller;
                		var product_buyer = $('#send_id').val();
                		if(product_seller == product_buyer) {
                			product_buyer = $('#receive_id').val();
                		}
						var data = {
									product_no : product_no,
									product_seller_client_id : product_seller,
									product_buyer_client_id : product_buyer
						};
							$.ajax({
									type: 'post',
									url: '/anbd/soldOut', 
									data  :  JSON.stringify(data), 
       								contentType : "application/json",
									success: function(data) { 
										alert("거래완료가 되었습니다."+data.status);
										}
									});
				}
                		
                /*]]>*/
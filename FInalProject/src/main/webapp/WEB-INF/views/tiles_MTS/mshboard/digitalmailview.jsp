<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String ctxPath = request.getContextPath();
%>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">

<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined"
      rel="stylesheet">
  
  <script>
       var fileseq = 0;
       
       var all_file = [];
      var receieveEmailList = [];
      var fileSizeList = [];
   
       // 페이지 로딩 후 실행되는 함수
      $(document).ready(function(){
            
         var fileList = new Object();

           <%-- === #166. 스마트 에디터 구현 시작 === --%>
            //전역변수
            var editor = [];
            
            //스마트에디터 프레임생성
            nhn.husky.EZCreator.createInIFrame({
               oAppRef: editor,
               elPlaceHolder: "contents", // id가 content인 textarea에 에디터를 넣어준다.
               sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
               htParams : {
                  // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                  bUseToolbar : true,            
                  // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                  bUseVerticalResizer : true,    
                  // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                  bUseModeChanger : true,
               }
            });
            <%-- === 스마트 에디터 구현 끝 === --%>
           
          // 각 섹션에 대한 클릭 이벤트 리스너 등록
          $('div.section').click(function() {
              alert('섹션 클릭됨!');
              // 기존에 선택된 섹션들에서 'section_selected' 클래스 제거
              $('div.section').removeClass('section_selected');
           // 현재 클릭된 섹션에 'section_selected' 클래스 추가
              $(this).addClass('section_selected');
        });
          
          // 글쓰기 버튼
          $("button#btnWrite").click(function(){

             // id가 content인 textarea에 에디터에서 대입
               editor.getById['contents'].exec("UPDATE_CONTENTS_FIELD", []);
     
             // 받는 사람 이메일 검사
             if($("div.emailinf").length ==0 ){
                // 받는사람이 존재하지 않음
                alert('발송 실패! 메일을 보낼 대상을 선택해주세요.'+ $("span.emailinf").length , 'warning')
                return false;
             }
           
             if($("div.emailinf").hasClass("x_span") === true ) {
             // class가 존재함.
                alert('발송 실패! 메일을 보낼 수 없는 대상이 포함되어 있습니다.', 'warning')
                return false;
              

           }
           var receieve_Email = "";
           for(let i = 0; i < receieveEmailList.length; i++) {
                
              receieve_Email += receieveEmailList[i]+",";
                  
             }
           receieve_Email = receieve_Email.slice(0, -1);
           
           console.log(receieve_Email);
           
           
           // 글제목 유효성 검사
           const subject = $("input#subject").val().trim();
           if(subject == "") {
              alert('발송 실패! 글 제목을 입력하세요.');
           
              return;
           } 
        
           <%-- === 글내용 유효성 검사(스마트 에디터 사용 할 경우) 시작 === --%>
          var contentval = $("textarea#contents").val();
            contentval = contentval.replace(/&nbsp;/gi, ""); // 공백을 "" 으로 변환         
            contentval = contentval.substring(contentval.indexOf("<p>")+3);   // "             </p>"
            contentval = contentval.substring(0, contentval.indexOf("</p>")); // "             "
                  
            if(contentval.trim().length == 0) {
               alert("글내용을 입력하세요!!");
                return;
            }
           
            console.log("contentval"+contentval);
             
            // 시간 검사
             
            var date = "";
            var time ="";
            var hour = "";
            var minute = "";
            var send_time = "";
           
            if($('#reservationTime').is(':visible')){
              
              date = $("input#yymmdd").val();
              
                 var redate = date.split("-");
                 var reYear =  redate[0];
                var reMonth = redate[1];
                 var reDay =   redate[2];
              
               time = $("input#hh24mi").val();
            
               var retime = time.split(":"); 
               var rehour =  retime[0];
             var remi =    retime[1];
                           
                 var reservation_date = new Date(reYear, reMonth, reDay, rehour, remi);    

                // 현재시간 
                var now = new Date();
                var year = now.getFullYear();     // 연도
                var month = now.getMonth()+1;     // 월(+1해줘야됨)     ????                        
                var day = now.getDate();          // 일
                var hours = now.getHours();       // 현재 시간
                var minutes = now.getMinutes();   // 현재 분
                
                now_date = new Date(year, month, day, hours, minutes);
                
                console.log("now_date"+now_date);
                console.log("reservation_date"+reservation_date);
                
                if(now_date>reservation_date){
                   // 지금 시점보다 이전으로 예약을 하면
                   alert('발송 실패! 현 시각 이전으로 예약메일을 전송할 순 없습니다.');
                   return false;
                }
                
                send_time = date+" "+retime;
                console.log("send_time=>"+send_time);
              
           }
           
         // 암호 검사
           var pwd = "";

           if($('#password').is(':visible')){
              
              console.log($("input#pwd").val());      

              pwd = $("input#pwd").val();
              alert('발송 실패! 비밀번호 입력하세요'+pwd);
              if(pwd.trim()==""){
                  alert('발송 실패! 비밀번호 입력하세요');
                 return false;
              }
           } 
           
           // 파일 사이즈리스트 
           var fileSizeStr = "";
           for(let i = 0; i < fileSizeList.length; i++) {
                
              fileSizeStr += fileSizeList[i]+",";
                  
             }
           fileSizeStr = fileSizeStr.slice(0, -1);
          
           console.log("fileSizeStr"+fileSizeStr);
           
           // 폼(form)을 전송(submit)
           var formData = new FormData();
           if(total_fileList.length > 0){
              total_fileList.forEach(function(f){
                     formData.append("fileList", f);
                 });
             } 
           formData.append("contents",contentval);
           formData.append("subject",subject);
           formData.append("recipient_address",recipient_address);
           // 시간
           formData.append("date",date);
           formData.append("hour",hour);
           formData.append("minute",minute);
           formData.append("send_time",send_time);
           // 비밀번호
           formData.append("password",pwd);
           // 
           formData.append("fileSize",fileSizeStr);
             
           console.log(date);      
           console.log(hour);
           console.log(minute);

          
           $.ajax({
                 url : '<%= ctxPath%>/addMail.on',
                 data : formData,
                 type:'POST',
                 enctype:'multipart/form-data',
                 processData:false,
                 contentType:false,
                 dataType:'json',
                 cache:false,
                 success:function(json){
                    if(json.n == 1){
                       swal('메일발송에 성공하였습니다.', "버튼을 누르면 보낸 메일함으로 이동합니다.", 'success')
                       .then((value) => {
                          location.href = "<%=ctxPath%>/mail/sendMailBox.on";
                       });

                       
                    }
                    if(json.n==0){
                       swal('메일발송이 실패하셨습니다.', "이 상태가 지속되면 지원팀에 문의해주세요.", 'warning')
                       return false;
                    }
                    if(json.n==-1){
                       swal('메일발송이 실패하셨습니다.', "파일 업로드 진행중 문제가 발생하였습니다.", 'warning')
                       return false;
                    }
                    
                 },error: function(request, status, error){
                     alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                 } 
             }); 

        
           
           
        });
        
           
           
  
          
          // 이메일 자동완성
          var emailList = ${requestScope.EmailList}
        // console.log("emailList=>"+emailList);
      
        $("#receieveEmail").autocomplete({
           source : emailList,
           select: function(event, ui){
               // console.log("ui.item"+event);
           },
           focus: function(event, ui){ return false;} // 사라짐 방지용 
        });
      
      // 받는사람 입력후 스페이스바나 엔터 누르면 한 단위로 묶기
      $("#receieveEmail").keydown(function(e){
         if(e.keyCode == 13 || e.keyCode == 32){
            var getEmail = $(this).val();
            console.log("이거 왜 안됨 getEmail: "+ getEmail);
            var oxEmail = false;
            for(let i = 0; i < emailList.length; i++) {
                 if(emailList[i] == getEmail )  {
                    // 맞는 이메일일때
                    oxEmail = true;
                    break;
                   }
              }

            var emailStartIdx = getEmail.indexOf("<")+2;
            var emailEndIdx = getEmail.indexOf(">")-1;
            var emailOnly = getEmail.substring(emailStartIdx, emailEndIdx);
            
            console.log("emailStartIdx"+emailStartIdx)
            console.log("emailEndIdx"+emailEndIdx)
            console.log("emailOnly"+emailOnly)
/*             // 이미 가져온 값인지 비교
            for(let i = 0; i < receieveEmailList.length; i++) {
                 if(receieveEmailList[i] == emailOnly )  {
                    swal('중복된 이메일입니다', "다른 이메일을 선택해주세요.", 'warning')
                    $("input#receieveName").val('');
                    return false;
                   }
              } */
            
              console.log("emailOnly"+emailOnly);
            
            if(oxEmail == true){
               /*
               if(emailOnly == '${sessionScope.loginuser.email}'){
                  $("#recipient").append(`<span class="rounded-pill email-ids myMail" name="email-container">`
                         + '${sessionScope.loginuser.fk_department_id } '+ '${sessionScope.loginuser.fk_job_id } '+ '${sessionScope.loginuser.name}'
                         + '&lt;'+'${sessionScope.loginuser.email}' +'&gt;'
                         + '<span class = "removeEmail" name="removeEmail"><i class="far fa-window-close"></i></span></span>');
                  $("#selfMail").prop("checked", true);
               }
                         
         
        
               
               */
               /*else{*/
                  $("#recipient").append('<div class="emailinf o_span col-5" style="display: flex; align-items: center;">' +
                   '<span class="removeEmail" name="removeEmail" style="font-size: 10pt; margin: 0 auto; display: flex; align-items: center;">' + getEmail +
                   '<span class="material-icons-outlined" style="margin-left: 5px;">clear</span></span></div>');
               /*}*/
            }
            else{
               $("#recipient").append('<div class="emailinf x_span col-5" style="display: flex; align-items: center;">' +
                '<span class="removeEmail" name="removeEmail" style="font-size: 10pt; margin: 0 auto; display: flex; align-items: center;">' + getEmail +
                '<span class="material-icons-outlined" style="margin-left: 5px;">clear</span></span></div>');
            }
            $("#receieveEmail").val("");
            
            
            
            receieveEmailList.push(emailOnly);
            console.log("receieveEmailList=>"+receieveEmailList);
            /* if(emailOnly == '${sessionScope.loginuser.email}'){
               $("#selfMail").prop("checked", true);
            } */
         }
         
         
      });
      
      $("#selfMail").change(function(){
           if($("#selfMail").is(":checked")){
              
              for(let i = 0; i < emailList.length; i++) {
                 
               var myemailStartIdx = emailList[i].indexOf("<")+2;
               var myemailEndIdx = emailList[i].indexOf(">")-1;
               var myemailOnly = emailList[i].substring(myemailStartIdx, myemailEndIdx);
                 
                 if(myemailOnly == '${sessionScope.loginuser.email}' )  {
                     var emailinf = emailList[i];
                     // alert("ddd"+emailinf);
                   }
              
              }
              
              for(let i = 0; i < receieveEmailList.length; i++) {
                 if(receieveEmailList[i] == '${sessionScope.loginuser.email}' )  {
                    alert('중복된 이메일입니다', "다른 이메일을 선택해주세요.", 'warning')
                    $("input#receieveName").val('');
                    $("#selfMail").prop("checked", false);
                    return false;
                   }
              }
              
              
              $("#recipient").append('<div class="emailinf o_span col-5 myMail" style="display: flex; align-items: center;">' +
                   '<span class="removeEmail" name="removeEmail" style="font-size: 10pt; margin: 0 auto; display: flex; align-items: center;">' + emailinf +
                   '<span class="material-icons-outlined" style="margin-left: 5px;">clear</span></span></div>');
              
              receieveEmailList.push('${sessionScope.loginuser.email}');
              
           }else{
              for(let i = 0; i < receieveEmailList.length; i++) {
                 if(receieveEmailList[i] == '${sessionScope.loginuser.email}' )  {
                    receieveEmailList.splice(i, 1);
                     i--;
                   }
              }
              $(".myMail").remove();
           }
       });
      
      // 주소 삭제버튼 클릭
      $(document).on('click','[name=removeEmail]', function(){
         $(this).parent().remove();
         /*
         if($(this).parent().hasClass("myMail")){
            $("input:checkbox[id='selfMail']").prop("checked", false);
         }*/
         
         var emailStartIdx = $(this).parent().text().indexOf("<")+2;
         var emailEndIdx = $(this).parent().text().indexOf(">")-1;
         var delete_address = $(this).parent().text().substring(emailStartIdx, emailEndIdx);
         
         for(let i = 0; i < receieveEmailList.length; i++) {
              if(receieveEmailList[i] == delete_address )  {
                 receieveEmailList.splice(i, 1);
                  i--;
                }
           }
         console.log(receieveEmailList);
         
      });
      
      // 파일 다중 선택시 리스트로 받아와서 처리하기
      $('#ex_file').change(function(e) {
          fileList = $(this)[0].files;  //파일 대상이 리스트 형태로 넘어온다.
          addFile(fileList);
      });
      
      // 파일 드래그 드랍 시작
      var obj = $("#dropzone");

        obj.on('dragenter', function (e) {
             e.stopPropagation();
             e.preventDefault();
             $(this).css('border', '2px solid #5272A0');
        });

        obj.on('dragleave', function (e) {
             e.stopPropagation();
             e.preventDefault();
             $(this).css('border', '2px dotted #8296C2');
        });

        obj.on('dragover', function (e) {
             e.stopPropagation();
             e.preventDefault();
        });

        obj.on('drop', function (e) {
             e.preventDefault();
             $(this).css('border', '2px dotted #8296C2');
             var fileList = e.originalEvent.dataTransfer.files
             console.log(fileList);
             
             addFile(fileList);
             
        });
         // 파일 드래그 드랍 끝
         
       // 파일 삭제버튼 클릭시
        $(document).on('click','[name=removeFile]', function(){
              console.log(this);
              console.log($(this).attr('file_name'));
              delete_file_name =$(this).attr('file_name');
              console.log($(this).attr('file_size'));
              delete_file_size =$(this).attr('file_size');
              
              for(let i = 0; i < all_file.length; i++) {
                 if(all_file[i].name = delete_file_name && delete_file_size == all_file[i].size)  {
                    all_file.splice(i, 1);
                    fileSizeList.splice(i, 1);
                     i--;
                   }
              }
              

              console.log(all_file);
              
           // FormData의 값 확인
           /*
               for (var pair of formData.entries()) {
                console.log(pair[0]+ ', ' + pair[1]);
                console.log(pair[1])
              } 
           */
            $(this).parent().remove();
            uploadFileCheck();
         });
         
         
         // 버튼 누르면 해당 기능 나오도록
        $('#reservationch').change(function(){
              if(this.checked){
                  $('div#reservationTime').toggle();
              } else {
                  $('div#reservationTime').toggle();
              }
        });
         
         // 버튼 누르면 해당 기능 나오도록
        $('#passwordch').change(function(){
              if(this.checked){
                  $('div#password').toggle();
              } else {
                  $('div#password').toggle();
              }
        });
        
        $('#reservationTime').hide();
        $('#password').hide();
        
        // 눈 아이콘 클릭시 비밀번호 보이도록
        $('.fa-eye').on('click',function(){
              
              $('input#pwd').toggleClass('active');
              
              if($('input#pwd').hasClass('active')){
                  $(this).attr('class',"fa fa-eye-slash fa-lg")
                  $('input#pwd').attr('type',"text");
              }
              else{
                  $(this).attr('class',"fa fa-eye fa-lg")
                  $('input#pwd').attr('type','password');
              }
          });

           
       
      });
      
          function filesize_tostirng (file_size) {
            var basic_file_size;
            var sUnit;
            
            if(file_size < 0 ){
               file_size = 0;
            }
            
            if( file_size < 1024) {
               basic_file_size = Number(file_size);
               sUnit = 'B';
               return basic_file_size + sUnit;
            } else if( file_size > (1024*1024)) {
               basic_file_size = Number(parseInt((file_size || 0), 10) / (1024*1024));
               sUnit = 'MB';
               return basic_file_size.toFixed(2) + sUnit;
            } else {
               basic_file_size = Number(parseInt((file_size || 0), 10) / 1024);
               sUnit = 'KB';
               return basic_file_size.toFixed(0) + sUnit;
            }
       }
            
      
      function addFile(fileList){
        
         for(var i=0;i < fileList.length;i++){
                 
            var file = fileList[i];
               all_file.push(file);
               console.log(all_file);
             
               fileSize = filesize_tostirng(file.size);
               fileSizeList.push(fileSize);
             
               $('#dropzone').append('<div class="emailinf o_span uploadFile col-5" style="display: flex; align-items: center;">'
               +'<span class="removeFile" name="removeFile" file_size="'+file.size+'"file_name="'+file.name+'" style="font-size: 10pt; margin: 0 auto; display: flex; align-items: center;">' + file.name + fileSize + '<span class="material-icons-outlined" style="margin-left: 5px;">clear</span>');
               fileseq++;
               uploadFileCheck();
           }
     }
    
     function uploadFileCheck(){
      if($('.uploadFile').length){
         $('div#dropzoneMessage').hide();
      }
      else{
         $('div#dropzoneMessage').show();
      }
   }
      
                
  </script>
    
  <style type="text/css">
    
      .section-title span.left_span:after {
         position: absolute;
        left: 0;
        top: -6px;
        height: 32px;
        width: 4px;
        background: #e53637;
        content: "";
      }
      
      .o_span{
          padding: 1px 5px;
          margin: 1px 5px;
          background-color: #E3F2FD;
     }
      .x_span{
         padding: 1px 5px;
          margin: 1px 5px;
          background-color: lightcoral;
      }
      
      /* 파일 필드 숨기기 */
      .filebox input[type="file"] {  
        position: absolute;
        width: 1px;
        height: 1px;
        padding: 0;
        margin: -1px;
        overflow: hidden;
        clip:rect(0,0,0,0);
        border: 0;
      }
      
      #dropzone{
         min-height: 100px;
         border: dashed 2px gray;
      }
       
  </style>
   
   
   <!-- 결과물 시작하기 <div class="main_body"> -->
   <!-- Inicio Email List  <div class="emailList"> -->
   <!-- Inicio Email List Settings-->
   <div class="emailList_settings">
      <!--왼쪽 세팅-->
      <div class="emailList_settingsLeft">
         <div class="icon_set ml-3 mr-3">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt;">redo</span>
            <span class="icon_text">보내기</span>
         </div>
         <div class="icon_set mr-3">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt; color: red;">favorite</span>
            <span class="icon_text">예약</span>
         </div>                             
         <div class="icon_set mr-3">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt;">delete</span>
            <span class="icon_text">내게쓰기</span>
         </div>
         <div class="icon_set mr-3">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt;">delete</span>
            <span class="icon_text">임시저장</span>
         </div>                         
      </div>
        <!--오른쪽 세팅-->
        <div class="emailList_settingsRight">
         <div class="icon_set">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_left</span>
            <span class="icon_text">이전</span>
         </div>
         <div class="icon_set">
            <span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_right</span>
            <span class="icon_text">다음</span>
         </div>   
      </div>
   </div>
   
   <!--셀렉션 세팅-->
   <div class="emailList_sections">
       <div class="section section_selected show">
           <span class="material-icons-outlined" style="font-size:24px;"> send </span> 
         <span class="list_name">메일쓰기</span>
      </div>
      <div class="section">
         <span class="material-icons-outlined">save_alt</span> 
            <span class="list_name">임시저장메일선택</span>
        </div>
        <div class="section">
         <span class="material-icons-outlined">quiz</span> 
            <span class="list_name">전달하기</span>
        </div>
        <div class="ml-auto" style="display: flex; align-items: center; border-bottom-width: 2px; padding: 15px; min-width: 200px; color: black; border-width: 0; cursor: pointer;">
         <button type="button" id="btnWrite" class="mr-2">메일보내기</button>
         <button class="mr-2">임시저장하기</button>
         <button class="mr-2">내게쓰기</button>
      </div>
   </div> 
   <!--셀렉션 세팅 끝-->
   <!--이메일 리스트-->
   
   <!--이메일 쓰기-->
   
   <div class="emailList_list " style="background-color: #f4f5f6">      
      <div style="margin:0px auto; width:90%; background-color: white;">
         <div style="background-color: #f4f5f6;">
            <div style="margin-bottom: 30px;"><span style="color: #f4f5f6">dd</span></div>
            <div class="section-title" style="background-color: #f4f5f6; display: flex; align-items: center; ">
                   <span class="left_span" style=" font-weight: 600;
                  line-height: 21px;
                  text-transform: uppercase;
                  padding-left: 20px;
                  position: relative; font-size: 18px; width: 10%;">받는사람</span>
               <div style="width: 8%;">
                  <input type="checkbox"/>
                  <span>개인별</span>
               </div>
               <input type="text" id="receieveEmail" name="receieveEmail" placeholder="받는사람을 입력하세요" style="width: 70%;"/>
               <div style="width: 10%;" class="text-center">
                  <button>주소록</button>
               </div>
               <input type="checkbox" id="selfMail" style="margin-left: 3px;">
             </div>
                     
            <div id="recipient" style="margin-left:18%; width: 100%" class="row">
              <!-- 새로운 옵션들이 여기에 동적으로 추가될 것입니다. -->
            </div>
             
             <div style="margin-bottom: 30px;">
             </div>
            
            <div class="section-title" style="background-color: #f4f5f6; margin-bottom: 30px; display: flex; align-items: center; ">
                   <span class="left_span" style=" font-weight: 600;
                  line-height: 21px;
                  text-transform: uppercase;
                  padding-left: 20px;
                  position: relative; font-size: 18px; width: 10%;">제목</span>
               <div style="width: 8%;">
                  <input type="checkbox"/>
                  <span>중요</span>
               </div>
               <input type="text" id="subject" style="width: 70%;"/>
             </div>
             
             
             <div class="section-title" style="background-color: #f4f5f6; margin-bottom: 30px; display: flex; align-items: center; ">
                   <span class="left_span" style="font-weight: 600;
                  line-height: 21px;
                  text-transform: uppercase;
                  padding-left: 20px;
                  position: relative; font-size: 18px; width: 10%;">예약메일</span>
               <div style="width: 8%;">
                  <input type="checkbox" id="reservationch" name="reservationch"/>
                  <span>예약</span>
               </div>
               <div id="reservationTime" style="width: 50%; display: flex; cursor: pointer; color: black;">
                     <input type="date" id="yymmdd" name='yymmdd' class="rounded-lg block w-full p-2.5" style='height: 30px; border: solid 1px gray;' required>   
                          <input id="hh24mi" name='hh24mi' type="time" step='600'/>
               </div>
             </div>
             
             <div class="section-title" style="background-color: #f4f5f6; margin-bottom: 30px; display: flex; align-items: center; ">
                   <span class="left_span" style="font-weight: 600;
                  line-height: 21px;
                  text-transform: uppercase;
                  padding-left: 20px;
                  position: relative; font-size: 18px; width: 10%;">암호메일</span>
               <div style="width: 8%;">
                  <input type="checkbox" id="passwordch" name="passwordch"/>
                  <span>암호</span>
               </div>
               <div id="password" style="width: 50%; display: flex; align-items: center; cursor: pointer; color: black;">
                   <input class="bottomLine" type="password" name="pwd" id="pwd" /> 
                   <i class="fa fa-eye fa-lg"></i>
               </div>
             </div>
                
             <div class="section-title" style="background-color: #f4f5f6; display: flex; align-items: center; ">
                   <span class="left_span" style="font-weight: 600;
                  line-height: 21px;
                  text-transform: uppercase;
                  padding-left: 20px;
                  position: relative; font-size: 18px; width: 10%;">파일첨부</span>
               <div style="width: 8%;">
               </div>
               <div style="width: 50%; display: flex; cursor: pointer; color: black;">
                  <label class="btn btn-outline-secondary" for="ex_file">업로드</label>
                  <input type="file" id="ex_file" multiple>  
               </div>
             </div>
            
            <div class=row id="dropzone" style="margin-left:18%; display: flex; align-items: center; justify-content: center; margin-bottom: 30px;">
                <div id="dropzoneMessage" style="display: inline-block;">여기에 첨부 파일을 끌어 오세요</div>
            </div>
            
            <div style="background-color:#f4f5f6; color:#f4f5f6;">
            ddddd
            </div>

            
         </div>   
         <div>
         <textarea style=" width:100%; height: 612px; " name="contents" id="contents"></textarea>
         </div>
      </div>
   </div>

   <!--이메일 쓰기-->                
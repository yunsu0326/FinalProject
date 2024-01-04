<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ======= #28. tile2 중 footer 페이지 만들기  ======= --%>


<div style="color: #999999; margin: auto; padding: 7px;">
   <div>
      <span style="color: gray;">MTS 엔터테인먼트 주식회사</span> <br>
      <span style="color: gray;">주소: 06164 서울시 강남구 테헤란로 521 파르나스타워 15층</span> <br>
      <span style="color: gray;">대표 번호: 1644-0727 | 팩스 번호: (02)544-3038 | 이메일: MTS_company@gmail.com</span> <br>
      <span style="color: gray;">사업자 등록 번호: 211-87-49910 | 통신 판매업 신고 번호: 강남-6017호</span> <br>
      <span style="color: gray;">대표이사: 서영학 | 개인정보 보호책임자: 이요섭</span>
    </div>
</div> 

<script type="text/javascript">

   $(document).ready(function(){
      
      var mycontent_height = $("div#mycontent").css("height");
      var mysideinfo_height = $("div#mysideinfo").css("height");
      
   //   console.log("mycontent_height : " + mycontent_height);
   //   console.log("mysideinfo_height : " + mysideinfo_height);
      
      if (mycontent_height > mysideinfo_height) {
         $("div#mysideinfo").css({"height":mycontent_height});
      }
      
      if (mysideinfo_height > mycontent_height) {
         $("div#mycontent").css({"height":mysideinfo_height});
      }
      
   });

</script>
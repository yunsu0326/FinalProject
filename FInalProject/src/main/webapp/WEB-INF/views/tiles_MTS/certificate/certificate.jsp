<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String ctxPath = request.getContextPath();
%>



<style type="text/css">
div.container { border:solid 1px red;
}

</style>

<script type="text/javascript">
	
$(document).ready(function() {
	
    // Initialize Bootstrap modal
    $('#myModal').modal({
        show: false
    });

    // Function to show the modal
    function showModal() {
        $('#myModal').modal('show');
    }

    // Function to close the modal
    function closeModal() {
        $('#myModal').modal('hide');
    }

    // Event listener for the Register Certificate button
    $('#register').on('click', function() {
        showModal();
    });
});
</script>

</head>
<body>

	<div class="container">
		<h2>문서&amp;증명서</h2>
	</div>
	
	<div class="container">
		<table>
			<tr>
				<td>문서</td>
				<td>파일</td>
				<td>수정일</td>
			</tr>
		</table>
	</div>
	
	
	<!-- Register Certificate button -->
    <button type="button" id="register" class="btn btn-primary">증명서 등록</button>

    <!-- Bootstrap Modal -->
    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">증명서 등록</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal Body -->
                <div class="modal-body">
                    <!-- Add your form or content for certificate registration here -->
                    <form>
                        <div class="form-group">
                            <label for="title">제목:</label>
                            <input type="text" class="form-control" id="title">
                        </div>
                        <div class="form-group">
                            <label for="content">내용:</label>
                            <textarea class="form-control" id="content" rows="4"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="file">파일 업로드:</label>
                            <input type="file" class="form-control-file" id="file">
                        </div>
                        <button type="button" class="btn btn-success">등록</button>
                    </form>
                </div>

                <!-- Modal Footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>

            </div>
        </div>
    </div>
	
	
	


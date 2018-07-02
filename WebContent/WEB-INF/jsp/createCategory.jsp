<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Create New Category</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="categoryRequest" id="myform" name="myform" onsubmit="return validateform()"
						method="GET" 
						action="${pageContext.request.contextPath}/Category/createOrupdateCategory">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Category Name<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input type="text" class="form-control disabled " id="catname" name="catname"  pattern="^[a-zA-Z][a-zA-Z/\s]+"
									title="Category Name should contain only alphabets."
									path="categoryName" autocomplete="off"/>
									<span id="nameloc" style="color: red"></span>
							</div>
						</div>
						
						 <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Bench Impact</label>
							<div class="col-sm-1">
							
							
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active" value="Y"
									data-onstyle="success" data-offstyle="danger" path="isCategory"  />
									
								<!-- <i class="fa fa-toggle-on" aria-hidden="true"></i> -->
							
							</div>
							<h5><b>(Will be Impacting the Bench Report)</b></h5>
						</div> 
						
	
   				<%-- <div class="form-group">
   				 <label for=" " class="col-sm-5 control-label">Status</label>
   					 <div class="col-sm-7">  
      
   						<c:choose>
								<c:when test="${category.isStatus()}">
								<form:checkbox class="form-control disabled"
										checked="checked" id="status" size="1" path="status" value="${category.isStatus()==true}" />
								</c:when>

								<c:otherwise>
								<form:checkbox class="form-control disabled" id="status" size="1" path="status" value="${category.isStatus()==true}" />
							</c:otherwise>
							</c:choose>
							  </div>
 							 </div>  --%>
 							 
 							

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
						
						
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>
<script>
	function validateform() {
		var catname = document.myform.catname.value;
		
		//var status=false;  

		if (catname == null || catname == "") {
			alert("Error : Category name can't be blank");
			document.getElementById("nameloc").innerHTML = "Category name is required";
			return false;
		} 
	}
</script> 

<jsp:include page="footer.jsp" />

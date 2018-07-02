<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Edit Skill Set</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="categoryRequest" id="myform" name="myform"
						method="GET" onsubmit="return validateform()"
						action="${pageContext.request.contextPath}/Category/createOrupdateSkillSet">
						
						<form:hidden class="form-control disabled" path="skillId"
							value="${skillSet.getSkillId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">SkillSet
								Name<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="skillSet"
									name="skillSet" pattern="^[a-zA-Z][a-zA-Z/\s]+"
									title="skillSet should contain only alphabets." value="${skillSet.getSkillSet()}"
									path="skillSet"  autocomplete="off"/>
									<span id="nameloc" style="color: red"></span>

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Status</label>
							<div class="col-sm-7">

								<c:choose>
									<c:when test="${skillSet.isStatus()}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="status" size="1" path="status"
											value="${skillSet.isStatus()==true}" />
									</c:when>

									<c:otherwise>
										<form:checkbox class="form-control disabled" id="status"
											size="1" path="status" value="${skillSet.isStatus()==true}" />
									</c:otherwise>
								</c:choose>



							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Submit</button>
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
		var skillSet = document.myform.skillSet.value;
		
		//var status=false;  

		if (skillSet == null || skillSet == "") {
			alert("Error : SkillSet name can't be blank");
			document.getElementById("nameloc").innerHTML = "SkillSet name is required";
			return false;
		} 
	}
</script> 

<jsp:include page="footer.jsp" />








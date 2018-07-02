<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Edit Project</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="projectRequest"
						method="POST" id="myform" name="myform" onsubmit="return validateform()"
						action="${pageContext.request.contextPath}/Project/createOrupdateProject">
						<form:hidden class="form-control disabled" path="projectId"
							value="${project.getProjectId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Name<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projectName" name="projectName" pattern="^[a-zA-Z][a-zA-Z/\s]+"
									title="projectName should contain only alphabets."
									 value="${project.getProjectName()}"
									path="projectName" autocomplete="off"/>
<span id="nameloc" style="color: red"></span>

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								projectManager<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:select class="form-control disabled" id="projectManager"
									name="projectManager" path="projectManager.empId">
									<option value="">Select projectManager</option>
									<c:forEach var="projectManager" items="${userEmpList}">
										<c:choose>
											<c:when
												test="${projectManager.empId eq project.getProjectManager().getEmpId()}">
												<option value="${projectManager.empId}" selected>${projectManager.employeeName}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${projectManager.empId}">${projectManager.employeeName}
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Start Date<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" path="startDate"
									value="${project.getStartDateStr()}" id="startdate" name="startdate" autocomplete="off"/>
									<span id="dateloc" style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">EndDate</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" path="endDate"
									value="${project.getEndDateStr()}" id="enddate" autocomplete="off"/>
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Project</label>
							<div class="col-sm-1">
								<c:choose>
									<c:when test="${project.getCalBench()=='Y'}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="role" size="1" path="calBench"
											value="${project.getCalBench()}" />
									</c:when>
									<c:otherwise>
										<form:checkbox class="form-control disabled" id="role"
											size="1" path="calBench" value="${project.getCalBench()}" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form=group">
								<label for=" " class="col-sm-5 control-label">Project Type<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">

								<c:choose>
									<c:when test="${project.getProjectType()=='I'}">
										<form:radiobutton path="projectType" value="I"
											checked="checked" /> Internal 
								<form:radiobutton path="projectType" value="E"
											required="required" /> External
									</c:when>
									<c:when test="${project.getProjectType()=='E'}">
										<form:radiobutton path="projectType" value="I"  /> Internal
										<form:radiobutton path="projectType" value="E"
											checked="checked" />External
									</c:when>
									<c:otherwise>
										<form:radiobutton path="projectType" value="I"
											/> Internal
										<form:radiobutton path="projectType" value="E"
											/>External
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
		var projectName = document.myform.projectName.value;
		var startdate = document.myform.startdate.value;
		//var status=false;  

		if (projectName == null || projectName == "") {
			alert("Error : Project name can't be blank");
			document.getElementById("nameloc").innerHTML = "Project name is required";
			return false;
		} 
		if (startdate == null || startdate == "") {
			alert("Error : startdate can't be blank");
			document.getElementById("dateloc").innerHTML = "startdate name is required";
			return false;
		} 
	}
</script> 
<script>
	$(document)
			.ready(
					function() {
						$("#startdate").datepicker(
								{
									// dateFormat: "yy-M-dd",
									//dateFormat: "dd-M-yy",
									maxDate : new Date(),
									onSelect : function(date) {
										var date2 = $('#startdate').datepicker(
												'getDate');
										$('#enddate').datepicker('option',
												'minDate', date2);
										$('#enddate').datepicker('option',
												'maxDate', new Date());
									}
								});
						$('#enddate')
								.datepicker(
										{
											//dateFormat: "dd-M-yy",
											//dateFormat: "yy-M-dd",
											maxDate : new Date(),
											minDate : $('#startdate')
													.datepicker('getDate'),
											onClose : function() {
												var dt1 = $('#startdate')
														.datepicker('getDate');
												var dt2 = $('#enddate')
														.datepicker('getDate');
												//check to prevent a user from entering a date below date of dt1
												if (dt2 < dt1) {
													$('#enddate')
															.datepicker(
																	'setDate',
																	$(
																			'#startdate')
																			.datepicker(
																					'option',
																					'minDate'));
												}
											}
										});
					});
</script>


<jsp:include page="footer.jsp" />








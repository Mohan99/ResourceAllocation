<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Allocate New Resource</b></h3>
			</div>
			<div class="col-md-6">
				<br>
				<form:form class="form-horizontal" modelAttribute="resourceRequest"
					method="post"
					action="${pageContext.request.contextPath}/Resource/createNewResourceUnderManager">

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Select
							Employee</label>
						<div class="col-sm-7">
							<form:select path="employeeId" id="employeeId"
								class="form-control" title="Select Employee" required="required">
								<option value="">Select emplyee</option>
								<c:forEach var="emp" items="${empListUnderManager}">
									<option value="${emp.empId}">${emp.employeeName}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Project Type</label>
						<div class="col-sm-7">
							<form:radiobutton path="allocation" value="p" required="required" />
							Primary
							<form:radiobutton path="allocation" value="s" required="required" />
							Secondary
						</div>
					</div>

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Assign
							Project</label>
						<div class="col-sm-7">
							<form:select path="projectId" id="primaryProjectId"
								class="form-control" title="Assign Project" required="required">
								<option value="">Assign Project</option>
								<c:forEach var="project" items="${projectList}">
									<option value="${project.projectId}">${project.projectName}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Start Date</label>
						<div class="col-sm-7">
							<form:input type="text" class="form-control disabled date-picker"
								id="fromDate" path="projectFrom" required="required" />
						</div>
					</div>

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">End Date</label>
						<div class="col-sm-7">
							<form:input type="text" class="form-control disabled date-picker"
								id="toDate" path="projectTo" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
							<button type="submit" class="btn btn-primary">Allocate</button>
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
	$(document).ready(
			function() {
				$("#fromDate").datepicker(
						{

							maxDate : new Date(),
							onSelect : function(date) {
								var date2 = $('#fromDate')
										.datepicker('getDate');
								$('#toDate').datepicker('option', 'minDate',
										date2);
								$('#toDate').datepicker('option', 'maxDate',
										new Date());
							}
						});
				$('#toDate').datepicker(
						{

							maxDate : new Date(),
							minDate : $('#fromDate').datepicker('getDate'),
							onClose : function() {
								var dt1 = $('#fromDate').datepicker('getDate');
								var dt2 = $('#toDate').datepicker('getDate');
								//check to prevent a user from entering a date below date of dt1
								if (dt2 < dt1) {
									$('#toDate').datepicker(
											'setDate',
											$('#fromDate').datepicker('option',
													'minDate'));
								}
							}
						});
			});
</script>

<jsp:include page="footer.jsp" />
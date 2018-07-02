<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Set Exit Date</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						method="post"
						action="${pageContext.request.contextPath}/Employee/setExitDate">

						<form:hidden path="empId" value="${empObj.empId }" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee Id
							</label>
							<div class="col-sm-7">
								<form:input type="text" class="form-control disabled"
									path="employeeId" value="${empObj.employeeId }" readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" path="employeeName"
									value="${empObj.employeeName }" readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Exit Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="exitDate"
									path="exitDate" autocomplete="off" />
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
	$(document)
			.ready(
					function() {
						$("#exitDate").datepicker()});
</script>
<jsp:include page="footer.jsp" />

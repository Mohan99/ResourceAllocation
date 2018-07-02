<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Edit Efforts</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="shetReq"
						method="POST"
						action="${pageContext.request.contextPath}/Timesheet/createOrUpdateEfforts">
						<form:hidden class="form-control disabled" path="id"
							value="${sheet.getId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empName" readonly="true"
									name="projectName" value="${sheet.getEmpId().getEmployeeName()}"
									path="projectId.projectId" />
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projectName" readonly="true"
									name="resName" value="${sheet.getProjectId().getProjectName()}"
									path="empId.empId" />
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">From Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" path="fromDate"
									value="${sheet.getFromDateStr()}" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">To Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" path="toDate"
									value="${sheet.getToDateStr()}" readonly="true" />
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Total Efforts</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled" path="numberEfforts"
									value="${sheet.getNumberEfforts()}" readonly="true" />
							</div>
						</div> --%>
						
						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Estimated Efforts</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled" path="estimatedEfforts"
									value="${sheet.getEstimatedEfforts()}" readonly="true" />
							</div>
						</div> --%>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Non-Billable Efforts</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled" path="timesheetEfforts" required="required"
									value="${sheet.getTimesheetEfforts()}" autocomplete="off"/>
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Billable Efforts</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled" path="billableEfforts" required="required"
									value="${sheet.getBillableEfforts()}" autocomplete="off"/>
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


<jsp:include page="footer.jsp" />








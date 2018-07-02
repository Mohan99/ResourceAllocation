<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<!-- <body onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload=""> -->
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue">Weekly Efforts</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form:form class="form-inline" method="post" action=""  modelAttribute="sheetReq">
			 <div class="form-group">
				<label for="startDate">Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="startdate"
						readonly="readonly" required="required" name="startdate"
						placeholder="Start Date" value="${startDate}">
				</div>
			</div> 
			<%-- <div class="form-group">
				<label for="startDate">Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="startdate"
						readonly="readonly" required="required" name="startdate"
						placeholder="Start Date"
						value="${sessionScope['scopedTarget.sessionObj'].getTsFromDate()}">
				</div>
			</div> --%>
			<div class="form-group">
				<label for="endDate">End Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="enddate"
						readonly="readonly" required="required" name="enddate"
						placeholder="End Date" value="${endDate}">
				</div>
			</div>
			</form:form>
			<%-- <div class="form-group">
				<label for="endDate">Total Efforts </label>
				<div class="input-group">
					<input type="text" class="form-control" id="totalEfrts"
						readonly="readonly" required="required" name="totalEfrts"
						placeholder="" value="${sessionScope['scopedTarget.sessionObj'].getTsTotalEfforts()}">
				</div>
			</div> --%>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>Resource List</h5>
						</div>
						<!-- /.panel-heading -->
						
						  <%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reporting
								Manager<br> 
							</label>
							<div class="col-sm-7">
								<form:select path="empId.empId" id="empId"
									class="form-control" title="Select Manager" required="required">
									<option value="">Select Manager</option>
									<c:forEach var="user" items="${usersEmpList}">
										<option value="${user.empId}">${user.employeeName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>  --%>
						
						<%-- <div class="panel-body">
							<div class="form-group form-inline">
								<label for=" " class="col-sm-5 control-label"> Select
									Employee</label>
								<div class="col-sm-7">
									<form:select path="empId.empId" id="userId" class="form-control"
										title="Select Employee" >
										<option value="">Select RP</option>
										<c:forEach var="emp" items="${usersEmpList}">
										
										<option value="${emp.empId}">${emp.employeeId}-${emp.employeeName}</option>
											<c:choose>
												<c:when test="${emp.empId eq empObj.empId}">
													<option value="${emp.empId}" selected>${emp.employeeId}-${emp.employeeName}</option>
												</c:when>
												<c:otherwise>
													<option value="${emp.empId}">${emp.employeeId}-${emp.employeeName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</form:select>
								</div>
							</div> --%>
						
						
						 <%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reporting
								To<br> (Manager)
							</label>
							<div class="col-sm-7">
								<form:select path="empId.empId" id="userId"
									class="form-control" title="Select Manager" required="required">
									<option value="">Select Manager</option>
									<c:forEach var="user" items="${usersEmpList}">
										<option value="${user.empId}">${user.employeeName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div> 
						 --%>
						
						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Manager </label>

							 <div class="col-sm-7">
								<form:select path="reportingTo.empId" id="empId"
									class="form-control" title="Select projectManager" required="required">

									<option value="">Select projectManager</option>

									<c:forEach var="projectManager" items="${userEmpList}">
										<option value="${reportingTo.empId}">${reportingTo.employeeName} </option>
									</c:forEach>

								</form:select>
							</div>
							</div> --%>
							
							
							
							<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Manager </label>

							 <div class="col-sm-7">
								<form:select path="reportingTo.empId" id="empId"
									class="form-control" title="Select projectManager" required="required">

									<option value="">Select projectManager</option>

									<c:forEach var="reportingTo" items="${userEmpList}">
										<option value="${reportingTo.empId}">${reportingTo.employeeName} </option>
									</c:forEach>

								</form:select>
							</div>
							</div> --%>
						
						<%-- <div class="panel-body">
							<div class="form-group form-inline">
								<label for=" " class="col-sm-5 control-label"> Select
									Employee</label>
								<div class="col-sm-7">
									<form:select path="reportingTo.empId" id="empId" class="form-control"
										title="Select Employee" onchange="getEmployeeData(this.value)">
										<option value="">Select Employee</option>
										<c:forEach var="user" items="${usersEmpList}">
											<c:choose>
												<c:when test="${user.empId eq empObj.empId}">
													<option value="${user.empId}" selected>${user.employeeId}-${user.employeeName}</option>
												</c:when>
												<c:otherwise>
													<option value="${user.empId}">${user.employeeId}-${user.employeeName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</form:select>
								</div>
							</div> --%>
						
						
						<%-- <div class="form-group form-inline">
								<label for=" " class="col-sm-5 control-label"> 
									ReportingManager</label>
								<div class="col-sm-7">
									<form:select path="reportingTo.empId" id="userId" class="form-control"
										title="Select ReportingManager" onchange="getEmployeeData(this.value)">
										<option value="">Select ReportingManager</option>
										<c:forEach var="user" items="${usersEmpList}">
										<option value="${user.empId}">${user.employeeName}</option>
									</c:forEach> --%>
										<%-- <c:forEach var="user" items="${usersEmpList}">
											<c:choose>
												<c:when test="${user.empId eq empObj.empId}">
													<option value="${user.empId}" selected>${user.employeeId}-${user.employeeName}</option>
												</c:when>
												<c:otherwise>
													<option value="${user.empId}">${user.employeeId}-${user.employeeName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach> --%>
									<%-- </form:select>
								</div>
							</div>
							 --%>
							
							
							

							<div id="dataTables-example_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap no-footer">
								<div class="row">
									
									<div class="col-sm-12">
										<table width="100%"
											class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
											id="dataTables-example" role="grid"
											aria-describedby="dataTables-example_info"
											style="width: 100%;">
											<thead>
												<tr role="row" class="bg-warning">
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Employee ID</th>

												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Employee Name</th>
											 <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;"> Projects</th> 

												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Billable Efforts</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Non Billable Efforts</th>
													
											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="efforts" items="${timeSheetDtls}">


												<tr class="gradeA odd" role="row">
													<td class="sorting_1">${efforts.employeeId}</td>
													<td class="center">${efforts.employeeName}</td>
													 <td class="center">${efforts.billableProjects}</td>
													<td class="center">${efforts.billableEfforts}</td>
													<td class="center">${efforts.timesheetEfforts}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<button type="button" onclick="getData()"
											class="btn btn-primary">Submit</button>
									</div>
								</div>

							</div>
						</div>

					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
		<%-- </form:form> --%>

	</div>


	<script type="text/javascript">
function getProjectData(projectId) {
	window.location.href="<%=request.getContextPath()%>/Timesheet/getEffortsProjList/"+projectId;
}
	</script>

	<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			"paging": false,searching: false
		});
	});

      //alert("commentsArray="+commentsArray);
     if(commentsArray!=null && commentsArray!="")
      window.location.href = "<%=request.getContextPath()%>/Timesheet/updateMultipleEffortsByProj/"+ commentsArray;
     else
 		alert("Add Efforts for atlest one Project");
		
	</script>
</div>
<!-- </body> -->
<jsp:include page="footer.jsp" />

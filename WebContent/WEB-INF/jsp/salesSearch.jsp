<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteResUrl"
		value="<%=request.getContextPath()%>/Resource/deleteResource/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Bench Employee Skill Set Report</b></h3>
			</div>
			<div class="col-md-6">
			</div>
		</div>
		

		<div class="row" >
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<form:form class="form-inline" modelAttribute="EmployeeRequest" method="post"
							action="${pageContext.request.contextPath}/Employee/getSalesSearch">
							<div class="form-group">
								<label for=" " class="col-sm-6 control-label">Skill Set</label>
								<div class="col-sm-7">
									<form:select path="skills" multiple="multiple"
										items="${skillSetList}" itemValue="skillId" name="skills"
										id="skills" itemLabel="skillSet" required="required"
										class="form-control multiselect col-md-4">
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<label for=" " class="col-sm-6 control-label">Experience</label>
								<div class="col-sm-7">
									<!-- <input type="text" id=""  placeholder="Employee Code" > -->
									<form:input class="form-control disabled" id="experiance"
										path="employeeExperience" autocomplete="off"/>
									<span id="idloc" style="color: red"></span>
								</div>
							</div>
							<div class="form-group">
								<label for=" " class="col-sm-6 control-label">Employee
									Name</label>
								<div class="col-sm-7">

									<!-- <input type="text" id=""  placeholder="Employee Code" > -->
									<form:input class="form-control disabled" id="employeeName"
										path="employeeName" autocomplete="off"/>
									<span id="idloc" style="color: red"></span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-6" style="margin-top: 10px;">
									<button type="submit" class="btn btn-primary">Search</button>
								</div>
							</div>
						</form:form>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>
	<BR>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5><b>Bench Employee Skill Set Report</b></h5>
				</div>
				
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div id="dataTables-example_wrapper"
						class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<div class="row">
							<div class="col-sm-12">
							<div align="right">
							<a href="<%=request.getContextPath()%>/Employee/exportGetSalesSearch/${fetchSkills}/${empExp}/${empName}" class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Export Excel</a> 
									</div>   
							<br>
								<table width="100%"
									class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
									id="dataTables-example" role="grid"
									aria-describedby="dataTables-example_info" style="width: 100%;">

									<thead>
										<tr role="row" class="bg-warning">
											<th class="sorting_asc" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="Employee Name: activate to sort column descending"
												style="width: 171px;">Employee Id</th>
												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Employee Name</th>

											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 148px;">Designation</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">Assignments</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">Mobile No</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">Location</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">Experience</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">SkillSet</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 188px;">Available From</th>

											<th class="sorting_asc" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="User Name: activate to sort column descending"
												style="width: 171px;">Reporting To</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Designation: activate to sort column ascending"
												style="width: 188px;">Reporting Manager Mobile</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Designation: activate to sort column ascending"
												style="width: 188px;">Reporting Manager Email</th>


										</tr>
									</thead>
									<tbody>
										<%--  <% int i=1; %> --%>
										<c:forEach var="report" items="${resourceList}">
											<c:choose>
												<c:when test="${report.emp.employeeId!=null }">

													<tr class="gradeA odd" role="row">


														<td class="sorting_1">${report.emp.employeeId}</td>
														<td class="sorting_1">${report.emp.employeeName}</td>
														<td class="center">${report.emp.employeeDesg}</td>

														<c:choose>
															<c:when test="${report.primaryProjects != null && report.primaryProjects != '_'}">
																<td>${report.primaryProjects}</td>
															</c:when>
															<c:otherwise>
																<td class="center">Bench</td>
															</c:otherwise>
														</c:choose>
														<td class="center">${report.emp.employeeMobilenumber}</td>
														<td class="center">${report.emp.employeeCity.name}</td>
														<td class="center">${report.emp.employeeExperience}</td>

														<td class="center">${report.skillSet}</td>
														<td class="center">${report.benchDate}</td>
														<td class="center">${report.reporting}</td>
														<td class="center">${report.mobileNumber}</td>
														<td class="center">${report.emailId}</td>
													</tr>
												</c:when>

											</c:choose>


										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>

					</div>
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- ... Your content goes here ... -->

		</div>
	</div>
</div>


<script>
	$(document)
			.ready(
					function() {
						$("#fromDate").datepicker(
								{
									//dateFormat: "dd-MM-yyyy",
									maxDate : new Date(),
									onSelect : function(date) {
										var date2 = $('#fromDate').datepicker(
												'getDate');
										$('#toDate').datepicker('option',
												'minDate', date2);
										$('#toDate').datepicker('option',
												'maxDate', new Date());
									}
								});
						$('#toDate')
								.datepicker(
										{
											maxDate : new Date(),
											minDate : $('#fromDate')
													.datepicker('getDate'),
											onClose : function() {
												var dt1 = $('#fromDate')
														.datepicker('getDate');
												var dt2 = $('#toDate')
														.datepicker('getDate');
												//check to prevent a user from entering a date below date of dt1
												if (dt2 < dt1) {
													$('#toDate')
															.datepicker(
																	'setDate',
																	$(
																			'#fromDate')
																			.datepicker(
																					'option',
																					'minDate'));
												}

												if (document
														.getElementById("toDate").Value != "")
													document
															.getElementById("status").checked = false;
												else
													(document
															.getElementById("toDate").Value == "")
												document
														.getElementById("status").checked = true;
											}

										});
					});
</script>

<jsp:include page="footer.jsp" />
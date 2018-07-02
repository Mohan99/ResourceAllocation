<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteEmpUrl"
		value="<%=request.getContextPath()%>/Employee/deleteEmployee/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form:form class="form-inline" method="post" action="">
			<div class="form-group">
				<label for="startDate">Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="startdate"
						readonly="readonly" required="required" name="startdate"
						placeholder="Start Date" value="${startDate}">
				</div>
			</div>
			<div class="form-group">
				<label for="endDate">End Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="enddate"
						readonly="readonly" required="required" name="enddate"
						placeholder="End Date" value="${endDate}">
				</div>
			</div>
			<%-- <div class="form-group">
				<label for="endDate">Total Efforts </label>
				<div class="input-group">
					<input type="text" class="form-control" id="totalEfrts"
						readonly="readonly" required="required" name="totalEfrts"
						placeholder="" value="${sessionScope['scopedTarget.sessionObj'].getTsTotalEfforts()}">
				</div>
			</div> --%>
		</form:form>
		
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Weekly Report</h5>
					</div>
					<div class=" pull-right"></div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
								
									 <div align="right">
										 <a href="<%=request.getContextPath()%>/Reports/exportGetTimeSheetWeeklyDetails/${startDate}/${endDate}" class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Export Excel</a>  </br>
									</div> 
									
									<form method="get">
									<%-- <input type="hidden" value="${startDate}" name="startdate" id="startdate">
									<input type="hidden" value="${endDate}" name="enddate" id="enddate"> --%>
										<table align="left" width="100%"
											class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
											id="dataTables-example" role="grid"
											aria-describedby="dataTables-example_info"
											style="width: 100%;">
											<thead>
												<tr role="row" class="bg-warning">

													<!--  <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1"  style="width: 20px;">SNo</th> -->
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
											
														<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Project Name</th> -->

													<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">From date</th>

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">To date</th>

													<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Estimated Efforts</th> -->
														
														<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Total Efforts</th>

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Non-Billable Efforts</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Billable Efforts</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Employee Logged Efforts</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Actual Utilization</th>
														<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;"></th> -->
														
													

												</tr>
											</thead>
											<tbody>

												<c:forEach var="tsheet" items="${weeklyList}">
													<tr class="gradeA odd" role="row">
													
														<td class="sorting_1">${tsheet.empId.employeeId}</td>
														<td class="sorting_1">${tsheet.empId.employeeName}</td>
														 <%-- <td class="center">${tsheet.projectId.projectName}</td> 
														<td class="sorting_1">${tsheet.fromDateStr}</td>
														<td class="sorting_1">${tsheet.toDateStr}</td>
														 <td class="sorting_1">${tsheet.estimatedEfforts}</td>  --%>
														 
														 <td class="sorting_1">${tsheet.numberEfforts}</td>
														<td class="sorting_1">${tsheet.timesheetEfforts}</td>
														<td class="sorting_1">${tsheet.billableEfforts}</td>
														<td class="sorting_1">${tsheet.sumHours}</td>
														
														<td class="sorting_1">${tsheet.actualUtilization}%</td>
														<%-- <td><c:if
																test="${tsheet.actualUtilization gt 50}">
																<img
																	src="<%=application.getContextPath()%>/resources/images/green.png"
																	width="40">
															</c:if> <c:if
																test="${tsheet.actualUtilization le 50 && tsheet.actualUtilization gt 30}">
																<img
																	src="<%=application.getContextPath()%>/resources/img/blue.png"
																	width="40">
															</c:if> <c:if test="${tsheet.actualUtilization lt 30}">
																<img
																	src="<%=application.getContextPath()%>/resources/images/lite-blue.png"
																	width="40">
															</c:if>
														</td>
 --%>
													</tr>

												</c:forEach>
											</tbody>
										</table>
									</form>
								</div>
							</div>

						</div>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>

<!-- <script>
	function myFunExport() {
		window.location.href = "getTimeSheetDetails";
	}
</script> -->
<script type="text/javascript">
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

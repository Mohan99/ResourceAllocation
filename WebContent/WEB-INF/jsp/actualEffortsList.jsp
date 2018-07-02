<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid" class="bg">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts List</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>

		<form:form class="form-inline" method="post" action="">
				<div class="form-group">
					<label for="endDate">Total Efforts </label>
					<div class="input-group">
						<input type="text" class="form-control" id="enddate"
							readonly="readonly" required="required" name="enddate"
							placeholder="End Date"
							value="${sessionScope['scopedTarget.sessionObj'].getTsTotalEfforts()}">
					</div>
				</div>
		</form:form>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Weekly Efforts List</h5>
					</div>

					<div class="panel-body">
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
												<!--  <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1"  style="width: 20px;">SNo</th> -->

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
													style="width: 148px;">Project Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">From Date</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">To Date</th>
												<!--<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Total Efforts</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Non-Billable Efforts</th>
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Billable Efforts</th>

												<th style="width: 70px;">Edit</th>
											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="efforts" items="${effortsList}">


												<tr class="gradeA odd" role="row">
													<%--  <td class="center"><%=i %> </td> --%>
													<td class="sorting_1">${efforts.empId.employeeId}</td>
													<td class="center">${efforts.empId.employeeName}</td>
													<td class="center">${efforts.projectId.projectName}</td>
													<td class="center">${efforts.fromDateStr}</td>
													<td class="center">${efforts.toDateStr}</td>
													<%--<td class="center">${efforts.numberEfforts}</td> --%>
													<td class="center">${efforts.timesheetEfforts}</td>
													<td class="center">${efforts.billableEfforts}</td>

													<td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Timesheet/editEfforts/${efforts.id}">
															<i><b>Edit</b> </i>
													</a></td>

												</tr>

											</c:forEach>
										</tbody>
									</table>
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
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>
<jsp:include page="footer.jsp" />

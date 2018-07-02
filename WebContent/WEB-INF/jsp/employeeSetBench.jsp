<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee Resources List</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Employee Resources List</b></h5>
					</div>
					<div class=" pull-right"></div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
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
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Employee Name</th>

												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Designation</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Primary Assignments</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Other Assignments</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Skill Set</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Work Location</th>

												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Available From</th>

												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Put As Bench</th>
													
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Edit</th>

											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="report" items="${resourceList}">
											<c:choose>
											<c:when test="${report.emp.employeeId!=null}">
											<tr class="gradeA odd" role="row">
												

													<td class="center">${report.emp.employeeId}</td>
													<td class="center">${report.emp.employeeName}</td>
													<%-- <td class="center">${report.emp.employeeDesg}</td> --%>
													<c:choose>
														<c:when test="${report.status=='Bench'}">
															<td>Bench</td>
														</c:when>
														<c:otherwise>
															<td class="center">${report.primaryProjects}</td>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${report.status=='Bench'}">
															<td></td>
														</c:when>
														<c:otherwise>
															<td class="center">${report.secondaryProjects}</td>
														</c:otherwise>
													</c:choose>
													<td class="center">${report.emp.employeeSpecialization}</td>
													<td class="center">${report.emp.workplace.name}</td>
													<td class="center">${report.emp.benchDate}</td>
													
													<c:choose>
														<c:when test="${report.status=='Bench'}">
															<td></td>
														</c:when>
														<c:otherwise>
													<c:choose>
														<c:when test="${report.emp.benchStatus eq true}">
															<td class="center"><a class="btn-info btn-sm"
																href="<%=request.getContextPath()%>/Employee/setEmployeeBench/${report.emp.empId}">
																	<i><b>UnAssign</b> </i>
															</a></td>
														</c:when>
														<c:otherwise>
															<td class="center"><a class="btn-info btn-sm"
																href="<%=request.getContextPath()%>/Employee/Assign/${report.emp.empId}">
																	<i><b>Assign </b></i>
															</a></td>

														</c:otherwise>
													</c:choose>
													</c:otherwise>
													</c:choose>
													<td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Employee/manageEmployee/${report.emp.empId}">
															<i class="fa fa-edit"> </i>
													</a></td>
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
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>

</div>


<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

<script>
	function myFunExport() {
		window.location.href = "exportEmployeesReport";
	}
</script>


<jsp:include page="footer.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee SkillSet Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		
		
			<h3 class="page-header"><b>Search SkillSet</b></h3>
			</div>
			<div class="col-md-6">
				<br>
				<form:form class="form-horizontal" modelAttribute="skillSetRequest"
					method="post"
					action="${pageContext.request.contextPath}/Employee/getSalesSearch">

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Select
							SkillSet</label>
						<div class="col-sm-7">
							<form:select path="skillId" id="skillId"
								class="form-control" title="Select SkillSet" required="required">
								<option value="">Select SkillSet</option>
								<c:forEach var="skillSet" items="${skillSetList}">
									<option value="${skillSet.skillId}">${skillSet.skillSet}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>


					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
							<button type="submit" class="btn btn-primary">Search</button>
						</div>
					</div>

				</form:form>

			</div>
		</div>
	</div>
		
		
		
		
		
		<br>
		
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Employee SkillSet Report</h5>
					</div>
					<div class=" pull-right"></div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<div align="right">
										<!-- <a href="#" class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;"
											onClick="myFunExport()">Export Excel</a>  --></br>
									</div>
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

												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Designation</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Category</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;"> Assignments</th>
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Other Assignments</th> -->
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Specialization</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Mobile No</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Location</th>
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Experience</th> 
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">SkillSet</th> 
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
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

												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Bench</th> -->

											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="report" items="${resourceList}">


												<tr class="gradeA odd" role="row">
													<c:choose>
														<c:when test="${report.emp.employeeId!=null && report.status=='Bench'}">
															<td class="center">${report.emp.employeeId}</td>
															<td class="center">${report.emp.employeeName}</td>
															<td class="center">${report.emp.employeeDesg}</td>
															<td class="center">${report.emp.employeeCategory.categoryName}</td>

															<c:choose>
																<c:when test="${report.status=='Bench'}">
																	<td>Bench</td>
																</c:when>
																<c:otherwise>
																	<td class="center">${report.primaryProjects}</td>
																</c:otherwise>
															</c:choose>

															<%-- <c:choose>
																<c:when test="${report.status=='Bench'}">
																	<td></td>
																</c:when>
																<c:otherwise>
																	<td class="center">${report.secondaryProjects}</td>
																</c:otherwise>
															</c:choose> --%>

															<%-- <td class="center">${report.emp.employeeSpecialization}</td> --%>
															<td class="center">${report.emp.employeeMobilenumber}</td>
															<td class="center">${report.emp.employeeCity.name}</td>
															<td class="center">${report.emp.employeeExperience}</td>
															
															<td class="center">${report.skillSet}</td>
															<td class="center">${report.benchDate}</td>
															<td class="center">${report.reporting}</td>
															 <td class="center">${report.mobileNumber}</td>
															<td class="center">${report.emailId}</td> 
															
															<%-- <c:choose>
														<c:when test="${report.status=='Bench'}">
															<td></td>
														</c:when>
														<c:otherwise>
															<td class="center"><a class="btn-info btn-sm"
																href="<%=request.getContextPath()%>/Employee/setEmployeeBench/${report.emp.empId}">
																	<i>Bench </i>
															</a></td>
														</c:otherwise>
													</c:choose> --%>

														</c:when>
													</c:choose>

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
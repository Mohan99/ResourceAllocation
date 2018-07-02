<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<script>
	function deleteEmployee(eID) {
		var x = confirm("Are you sure you want to delete this Employee = "
				+ eID);
		if (x)
			location.href = $('#deleteEmpUrl').val() + eID;
	}
</script>
<!-- <body onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload="">
	
</body> -->
<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteEmpUrl"
		value="<%=request.getContextPath()%>/Employee/setExitDate/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header"><b> Employee's List</b></h3>
				<div class="col-md-6">
					<!-- <form class="form-horizontal">
  <div class="form-group">
    <label for=" " class="col-sm-3 control-label">Employee ID</label>
    <div class="col-sm-6">
      <input type="text" class="form-control" id=" " placeholder="Employee ID">
    </div>
    <div class="col-sm-3 pull-left">
      <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  </div>
  <div class="form-group">
    
  </div>
</form>-->
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<!-- <div class="panel-heading">
						<h5>Employee List</h5>
					</div> -->
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Employee/createEmployee">
							<i class="fa fa-user-plus"> </i> Add Employee
						</a>
					</div>
					<!-- /.panel-heading -->
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
													style="width: 148px;">Designation</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Skill Set</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Mobile Number</th>
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Years of Experience</th>
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 120px;">Email</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Department</th>
													
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Reporting To</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Workplace</th>
													
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">Status</th> --> 
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Join Date</th> -->
												<!-- <th style="width: 70px;">Edit</th>
												<th style="width: 80px;">Delete</th> -->
											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="emp" items="${employeeList}">


												<tr class="gradeA odd" role="row">
													<%--  <td class="center"><%=i %> </td> --%>
													<td class="sorting_1">${emp.employeeId}</td>
													<td class="center">${emp.employeeName}</td>

													<td class="center">${emp.employeeDesg}</td>
													<%-- <td class="center">${emp.employeeSpecialization}</td> --%>
													<td> <c:forEach var="empskill" items="${skillsList}">
														 <c:choose>
														 <c:when test="${empskill.empId.empId==emp.empId}">
														  ${empskill.skillSet.skillSet}
														 </c:when>
														 </c:choose>
														
														</c:forEach> </td>
													<td class="center">${emp.employeeMobilenumber}</td>
													<td class="center">${emp.employeeExperience}</td>
													<td class="center">${emp.emailId}</td>
													<td class="center">${emp.employeeCategory.categoryName}</td>
													<td class="center">${emp.reportingTo.employeeName}</td>
													<td class="center">${emp.workplace.name}</td>
													
													<%-- <td><c:choose>
															<c:when test="${emp.status=='true'}">Active<br />
															</c:when>
															<c:otherwise>In Active<br />
															</c:otherwise>
														</c:choose></td> --%>

													<%-- <td class="center">${emp.status}</td> --%>
													<%-- <td class="center">${emp.joinDateStr}</td> --%>
													<%-- <td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Employee/editEmployee/${emp.empId}">
															<i class="fa fa-edit"> </i>
													</a></td>
													<td class="center"><c:choose>
															<c:when test="${emp.status=='true'}">
																<a class="btn-danger btn-sm" href="#"
																	onclick="deleteEmployee('${emp.empId}')"> <i
																	class="fa fa-trash-o fa-lg"> </i>
																</a>
																<br />
															</c:when>
															<c:otherwise>Deleted<br />
															</c:otherwise>
														</c:choose></td> --%>
														
												</tr>
												<%--   <%
                        i++; %> --%>

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
<script type="text/javascript">
        window.history.forward();
        function noBack()
        {
            window.history.forward();
        }
</script>
<jsp:include page="footer.jsp" />

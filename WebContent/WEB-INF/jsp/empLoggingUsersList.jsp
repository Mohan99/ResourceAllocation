<%@page import="com.gcs.db.businessDao.Users"%>
<%@page import="com.gcs.dbDao.UsersDao"%>
<%@page import="com.gcs.dbDaoImpl.UsersDaoImpl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<script>
	function removeUserEmp(eID) {
		var x = confirm("Are you sure you want to Remove this Employee as Reporting Manager  "
				);
		if (x)
			location.href = $('#removeUserEmpUrl').val() + eID;
	}
</script>
<div id="page-wrapper" class="bg">
	<input type="hidden" id="removeUserEmpUrl"
		value="<%=request.getContextPath()%>/Users/removeUserEmp/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue">
					<b>Employee Logging Users List</b>
				</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form action="deleteMultiple" method="get">
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>
								<b>Employee Logging List</b>
							</h5>
						</div>
						<div class=" pull-right"></div>
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

													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="User Name: activate to sort column descending"
														style="width: 171px;">Employee Id</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Employee Name</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Category</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Designation</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Mobile No</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Location</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Reset PassWord</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;"></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="users" items="${users}">
													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${users.getEmployeeId()}</td>
														<td>${users.getEmployeeName()}</td>
														<td>${users.getEmployeeCategory().getCategoryName()}</td>
														<td>${users.getEmployeeDesg()}</td>
														<td>${users.getEmployeeMobilenumber()}</td>
														<td>${users.getEmployeeCity().getName()}</td>
														<td class="center"><a class="btn btn-primary"
															href="<%=request.getContextPath()%>/Users/upadateEmployeeLoginUsers/${users.empId}">
																 <i  href="#">Reset Password </i> 
														</a></td>
														<td class="center"><a class="btn btn-primary"
															href="#" onclick="removeUserEmp('${users.empId}')">REMOVE
														</a></td>
														<%--  <td class="center">
															<div class="input-group" align="center">
						<button type="submit" class="btn btn-primary" onclick="removeUserEmp('${users.empId}')">REMOVE</button><br>
						</div> 
						</td>  --%>
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
		</form>
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

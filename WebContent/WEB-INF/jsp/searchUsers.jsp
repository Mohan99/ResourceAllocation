<%@page import="com.gcs.db.businessDao.Users"%>
<%@page import="com.gcs.dbDao.UsersDao"%>
<%@page import="com.gcs.dbDaoImpl.UsersDaoImpl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<script>
	function deleteUsers(userId) {
		var x = confirm("Are you sure you want to delete this Users  "
				);
		if (x)
			location.href = $('#deleteUsersUrl').val() + userId;
	}
</script>

<input type="hidden" id="deleteUsersUrl"
	value="<%=request.getContextPath()%>/Users/deleteUsers/" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Adimin/PMO/Sales List</b></h3>
				<div class="col-md-6">
					
				</div>
			</div>
		</div>
		<form action="deleteMultiple" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Adimin/PMO/Sales List</b></h5>
					</div>
					<div class=" pull-right">
						
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Users/createUsers"> <i
							class="fa fa-user-plus"> </i> Add Adimin/PMO/Sales List
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
												<!-- <th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="User Name: activate to sort column descending"
													style="width: 171px;"><input type="checkbox"
													name="userId" id="checkBoxAll" />Select All</th> -->
												
												<!-- <th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="User Name: activate to sort column descending"
													style="width: 171px;">User ID</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">User Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Login Name</th>
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Login Password</th> -->
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">isActive</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Email</th>
												<th style="width: 70px;">Edit</th>
												<th style="width: 80px;">Delete</th>
												
											</tr>
										</thead>
										<tbody>

											<c:forEach var="users" items="${Users}">


												<tr class="gradeA odd" role="row">
													<%-- <td><input type="checkbox" class="chkCheckBoxId"
														value="${users.userId}" name="userId" id="userId"></td> --%>
													<%-- <td class="sorting_1">${users.userId}</td> --%>
													<td>${users.userName}</td>
													<td>${users.loginName}</td>
													<%-- <td>${users.loginPassword}</td> --%>
													<%-- <td>${users.isActive}</td> --%>
													<td>${users.email}</td>
													<%--  <td class="center">${manager.isActive==1?true:false}</td> --%>
													<td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Users/editUsers/${users.userId}">
															<i class="fa fa-edit"> </i>
													</a></td>

													<td class="center"><a class="btn-danger btn-sm"
														href="#" onclick="deleteUsers('${users.userId}')"> <i
															class="fa fa-trash-o fa-lg"> </i>
													</a></td>

													
												</tr>

												
											</c:forEach>
										
										</tbody>
									</table>

									
									 <input type="submit" name="userId" value="Delete" class="btn btn-danger" onclick="return confirm ('Are You Sure?')" >
									
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

<script type="text/javascript">
	$(document).ready(function() {
		$('#checkBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkCheckBoxId').prop('checked', true);
			else
				$('.chkCheckBoxId').prop('checked', false);

		});
	});
</script>
<jsp:include page="footer.jsp" />

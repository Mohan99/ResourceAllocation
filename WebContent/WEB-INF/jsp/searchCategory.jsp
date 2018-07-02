<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<script>
	function deleteCategory(cID) {
		var x = confirm("Are you sure you want to delete this Category  "
				);
		if (x)
			location.href = $('#deleteCategoryUrl').val() + cID;
	}
</script>
<div id="page-wrapper" class="bg">
<input type="hidden" id="deleteCategoryUrl"
		value="<%=request.getContextPath()%>/Category/deleteCategory/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"> Category List</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Category List</h5>
					</div>
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Category/createCategory"> <i
							class="fa fa-user-plus"> </i> Add Category
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
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Category Id</th> -->
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Category Name</th>

												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Status</th> -->
												 <th style="width: 70px;">Edit</th>
												  <th style="width: 70px;">Delete</th> 

											</tr>
										</thead>
										<tbody>

											<c:forEach var="category" items="${categoryList}">


												<tr class="gradeA odd" role="row">
													<%-- <td class="sorting_1">${category.categoryId}</td> --%>
													<td>${category.categoryName}</td>
													
													<%-- <td>
													<c:choose>
														<c:when test="${category.status=='true'}">Active <br /></c:when>
														<c:otherwise>In Active<br /></c:otherwise>
													</c:choose></td> --%>
													
													 <td class="center"><a class="btn-info btn-sm" href="<%=request.getContextPath()%>/Category/editCategory/${category.categoryId}"> 
													<i class="fa fa-edit"> </i>
													</a></td> 
													<td class="center">
															
																<a class="btn-danger btn-sm" href="#"
																	onclick="deleteCategory('${category.categoryId}')"> <i
																	class="fa fa-trash-o fa-lg"> </i>
																</a>
																<br />
															</td>
													<%-- <td>${project.status}</td> --%>

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

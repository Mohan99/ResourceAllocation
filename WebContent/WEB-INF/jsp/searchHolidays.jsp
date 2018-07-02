<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Holiday Calender</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Holidays List</b></h5>
					</div>
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Holiday/createHolidays">
							<i class="fa fa-user-plus"> </i> Add Holiday
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
												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Holiday Date</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Reason</th>

												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Status</th> -->
												<th style="width: 70px;">Holiday Type</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach var="holidays" items="${calenderList}">


												<tr class="gradeA odd" role="row">
													<td class="sorting_1">${holidays.dateStr}</td>
													<td>${holidays.reason}</td>

													<td><c:choose>
															<c:when test="${holidays.type==1}">GCS <br />
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${holidays.type==2}">WCM <br />
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${holidays.type==3}">ALL <br />
																			</c:when>
																		</c:choose>
																		<br />
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose></td>

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

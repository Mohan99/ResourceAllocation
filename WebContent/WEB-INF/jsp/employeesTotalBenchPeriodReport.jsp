<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteEmpUrl"
		value="<%=request.getContextPath()%>/Employee/deleteEmployee/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b> Employee Wise Bench Period Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Employee Bench Report</b></h5>
					</div>
					<div class=" pull-right"></div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
								
									<div align="right">
										<a href="<%=request.getContextPath()%>/Reports/exportEmployeesTotalBenchPeriod/${startdate}/${enddate}" class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Export Excel</a> </br>
									</div>
									
									<form>
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
														aria-label="Designation: activate to sort column ascending"
														style="width: 188px;">Total Bench Days</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="bench" items="${benchList}">
													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${bench.employeeId}</td>
														<td class="center">${bench.employeeName}</td>
														<c:choose>
															<c:when test="${bench.benchPeriod==-1}">
																<td class="center">Still Bench</td>
															</c:when>
															<c:otherwise>
																<td class="center">${bench.benchPeriod}</td>
															</c:otherwise>
														</c:choose>




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
<script>
	function myFunExport() {
		window.location.href = "exportEmployeesTotalBenchPeriod";
	}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

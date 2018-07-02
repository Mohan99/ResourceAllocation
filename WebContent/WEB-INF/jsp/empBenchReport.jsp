<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee Bench Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Employee Report</b></h5>
					</div>

					<div class="panel-body">
						<form:form class="form-inline" modelAttribute="resReq"
							method="post">
							<div class="form-group">
								<label for="startDate">Start Date </label>
								<div class="input-group">
									<form:input type="text" class="form-control" id="startdate"
										placeholder="Start Date" path="projectFrom" />
								</div>
							</div>
							<div class="form-group">
								<label for="endDate">End Date </label>
								<form:input type="text" class="form-control" id="enddate"
									placeholder="End Date" path="projectTo" />
							</div>

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
														style="width: 171px;">Employee Id</th>
													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">Employee Name</th>
													<th style="width: 70px;">Fetch</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="emp" items="${empList}">


													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${emp.employeeId}</td>
														<td>${emp.employeeName}</td>

														<td><%-- <a
															href="<%=request.getContextPath()%>/Reports/empBenchPeriod/${emp.empId}">
																<span class="glyphicon glyphicon-plus-sign"></span>
														</a> --%><input type="button" value="fetch" onclick="empBenchReport(${emp.empId})"></td>
														

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

							</div>
						</form:form>
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

	function empBenchReport(empId){
		var id=empId;
		var startDate=document.getElementById('startdate').value;
		var endDate=document.getElementById('enddate').value;
		
		window.location.href="<%=request.getContextPath()%>/Reports/empBenchPeriod?empId="+empId+"&startDate="+startDate+"&endDate="+endDate;
	};
</script>
<script>
   $(document).ready(function () {
       $("#startdate").datepicker({
    	// dateFormat: "yy-M-dd",
    	dateFormat: "dd-M-yy",
           maxDate: new Date(),
           onSelect: function (date) {
               var date2 = $('#startdate').datepicker('getDate');
               $('#enddate').datepicker('option', 'minDate', date2);
               $('#enddate').datepicker('option', 'maxDate', new Date());
           }
       });
       $('#enddate').datepicker({
           dateFormat: "dd-M-yy",
            //dateFormat: "yy-M-dd",
           maxDate: new Date(),
           minDate: $('#startdate').datepicker('getDate'),
           onClose: function () {
               var dt1 = $('#startdate').datepicker('getDate');
               var dt2 = $('#enddate').datepicker('getDate');
               //check to prevent a user from entering a date below date of dt1
               if (dt2 < dt1) {
                   $('#enddate').datepicker('setDate', $('#startdate').datepicker('option', 'minDate'));
               }
           }
       });
   });
</script>
<jsp:include page="footer.jsp" />

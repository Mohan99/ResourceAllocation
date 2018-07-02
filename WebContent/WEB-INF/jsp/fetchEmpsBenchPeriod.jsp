<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Total Bench Period Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Total Bench Period Report</b></h5>
					</div>

					<div class="panel-body">
						<form:form class="form-inline" method="post"
							action="${pageContext.request.contextPath}/Reports/getEmpsTotalBenchPeriod">
							<div class="form-group">
								<label for="startDate">Start Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="startdate" required="required"
										name="startdate" placeholder="Start Date" autocomplete="off">
								</div>
							</div>
							<div class="form-group">
								<label for="endDate">End Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="enddate" required="required"
										name="enddate" placeholder="End Date" autocomplete="off">
								</div>
							</div>

							<div class="input-group">
								<input type="submit" value="Fetch" class="btn btn-primary">

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

	function empsTotalBenchPeriod(){
		var startDate=document.getElementById('startdate').value;
		var endDate=document.getElementById('enddate').value;
		
		window.location.href="<%=request.getContextPath()%>/Reports/getEmpsTotalBenchPeriod";
	};
</script>
<script>
	$(document)
			.ready(
					function() {
						$("#startdate").datepicker(
								{
									// dateFormat: "yy-M-dd",
									dateFormat : "dd-M-yy",
									maxDate : new Date(),
									onSelect : function(date) {
										var date2 = $('#startdate').datepicker(
												'getDate');
										$('#enddate').datepicker('option',
												'minDate', date2);
										$('#enddate').datepicker('option',
												'maxDate', new Date());
									}
								});
						$('#enddate')
								.datepicker(
										{
											dateFormat : "dd-M-yy",
											//dateFormat: "yy-M-dd",
											maxDate : new Date(),
											minDate : $('#startdate')
													.datepicker('getDate'),
											onClose : function() {
												var dt1 = $('#startdate')
														.datepicker('getDate');
												var dt2 = $('#enddate')
														.datepicker('getDate');
												//check to prevent a user from entering a date below date of dt1
												if (dt2 < dt1) {
													$('#enddate')
															.datepicker(
																	'setDate',
																	$(
																			'#startdate')
																			.datepicker(
																					'option',
																					'minDate'));
												}
											}
										});
					});
</script>
<jsp:include page="footer.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee TimeSheet Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<!-- <div class="panel-heading">
						<h5>Bench Period</h5>
					</div> -->

					<div class="panel-body">
						<form:form class="form-inline" method="Post" 
							action="${pageContext.request.contextPath}/Reports/getAccountsProjectsList" modelAttribute="TimesheetRequest">
							<div class="form-group">
								<label for="startDate">Start Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="startdate" required="required"
										name="startdate" placeholder="Start Date" autocomplete="off">
								</div>
							</div>
							<div class="form-group">&ensp;&ensp;
								<label for="endDate">End Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="enddate" required="required"
										name="enddate" placeholder="End Date" autocomplete="off">
								</div>
							</div>
							<%-- <div class="form-group" >&ensp;&ensp;
								<label for=" " > 
									Projects</label>
								<div class="input-group">
									<form:select path="projectId.projectId" id="projectId" class="form-control"
										title="Select Project"  onchange="getProjectData(this.value)">
										<option value="">Select Project</option>
										 <c:forEach var="proj" items="${projList}">
										
													<option value="${proj.projectId}">${proj.projectName}</option>
												
										</c:forEach> 
									</form:select>
								</div>
							</div> 
							&ensp; 
						 <div class="input-group">&ensp;&ensp;
								<input type="submit" class="btn btn-primary" value="Fetch" class="form-control">

							</div> --%>
							
							<br> <br>
							<div class="input-group" align="center">
						<button type="submit" class="btn btn-primary"  onclick="return dateValidation()" formaction="<%=request.getContextPath()%>/Reports/getReportingManagerList" >By ReportingManager</button><br>
						</div> 
						<div class="input-group" align="center">
						<button type="submit" class="btn btn-primary"  onclick="return dateValidation()" formaction="<%=request.getContextPath()%>/Reports/getProjectsList">By Projects</button>
						
							</div>
							
					<%--  <div class="input-group" align="center">
						<button type="submit" class="btn btn-primary" formaction="<%=request.getContextPath()%>/Reports/getAccountsReportingManagerList">Reporting Manager </button><br>
						</div> 
						<div class="input-group" align="center">
						<button type="submit" class="btn btn-primary" formaction="<%=request.getContextPath()%>/Reports/getAccountsProjectsList">Projects</button>
						
							</div>   --%>

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

	function allEmpsTotalBenchPeriod(){
		var startDate=document.getElementById('startdate').value;
		var endDate=document.getElementById('enddate').value;
		
		window.location.href="<%=request.getContextPath()%>/Reports/getAccountsTimeSheetDetails";
	};
</script>
<!-- <script>
   $(document).ready(function () {
       $("#startdate").datepicker({
    	// dateFormat: "yy-M-dd",
    	//dateFormat: "dd-M-yy",
           maxDate: new Date(),
           onSelect: function (date) {
               var date2 = $('#startdate').datepicker('getDate');
               $('#enddate').datepicker('option', 'minDate', date2);
               $('#enddate').datepicker('option', 'maxDate', new Date());
           }
       });
       $('#enddate').datepicker({
           //dateFormat: "dd-M-yy",
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
</script> -->

<!-- <script>
	$(document)
			.ready(
					function() {
						$("#startdate").datepicker(
								{
									// dateFormat: "yy-M-dd",
									//dateFormat: "dd-M-yy",
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
											//dateFormat: "dd-M-yy",
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
</script> -->

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

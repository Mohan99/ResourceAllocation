<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"> <b>Create New Leave</b> </h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="leavesRequest"
						method="GET"
						action="${pageContext.request.contextPath}/Leaves/createOrupdateLeaves">
						
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Select
								Employee</label>
							<div class="col-sm-7">
								<form:select path="empId" id="empId"
									class="form-control" title="Select Employee" required="required">
									<option value="">Select employee</option>
									<c:forEach var="emp" items="${empList}">
										<option value="${emp.empId}">${emp.employeeId}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">From Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="fromdate"
									required="required" path="fromdate" />
							</div>
							</div>
							<div class="form-group">
							<label for=" " class="col-sm-5 control-label">To Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="todate"
									required="required" path="todate" />
							</div>
							
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Total Leaves</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="totalleaves"
									path="totalleaves" />
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reason</label>
							<div class="col-sm-7">
								<form:textarea class="form-control disabled " path="reason" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Calender
								Type</label>
							<div class="col-sm-7">
								<form:radiobutton path="type" value="1" required="required" />
								GCS
								<form:radiobutton path="type" value="2" required="required" />
								WCM
								<form:radiobutton path="type" value="3" required="required" />
								ALL
							</div>
						</div> --%>


						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>
<!-- <script>
	$(function() {
		$("#date").datepicker();
	});
</script> -->
<script>
	$(document).ready(
			function() {
				$("#fromdate").datepicker(
						{
							
							maxDate : new Date(),
							onSelect : function(date) {
								var date2 = $('#fromdate')
										.datepicker('getDate');
								$('#todate').datepicker('option', 'minDate',
										date2);
								$('#todate').datepicker('option', 'maxDate',
										new Date());
							}
						});
				$('#todate').datepicker(
						{
							
							maxDate : new Date(),
							minDate : $('#fromdate').datepicker('getDate'),
							onClose : function() {
								var dt1 = $('#fromdate').datepicker('getDate');
								var dt2 = $('#todate').datepicker('getDate');
								//check to prevent a user from entering a date below date of dt1
								if (dt2 < dt1) {
									$('#todate').datepicker(
											'setdate',
											$('#fromdate').datepicker('option',
													'minDate'));
								}
							}
						});
			});
</script>

<jsp:include page="footer.jsp" />

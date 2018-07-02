<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Weekly Efforts Report</b></h5>
					</div>

					<div class="panel-body">
						<form:form class="form-inline" method="post"
							action="${pageContext.request.contextPath}/Reports/getTimeSheetDetails">
							<div class="form-group">
								<label for="startDate">Start Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="startdate" required="required"
										name="startdate" placeholder="Start Date">
								</div>
							</div>
							<div class="form-group">
								<label for="endDate">End Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="enddate" required="required"
										name="enddate" placeholder="End Date">
								</div>
							</div>

							<div class="input-group">
								<input type="submit" value="Fetch" class="form-control">

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
		
		window.location.href="<%=request.getContextPath()%>/Reports/getTimeSheetDetails";
	};
</script>
<script type="text/javascript">
	$(function() {
		$('#startdate').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true,
			beforeShowDay : $.datepicker.noWeekends
		});

		$('#enddate').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true,
			beforeShowDay : $.datepicker.noWeekends
		});
	});
</script>
<jsp:include page="footer.jsp" />

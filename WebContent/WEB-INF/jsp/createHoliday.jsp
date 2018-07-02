<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b> Create New Holiday</b> </h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="calenderRequest"
						method="GET"
						action="${pageContext.request.contextPath}/Holiday/createOrupdateHoliday">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Select Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="date"
									required="required" path="date" />
							</div>
						</div>

						<div class="form-group">
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
						</div>


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
<script>
	$(function() {
		$("#date").datepicker();
	});
</script>
<jsp:include page="footer.jsp" />

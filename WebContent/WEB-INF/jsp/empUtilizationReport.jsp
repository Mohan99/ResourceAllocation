<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteResUrl"
		value="<%=request.getContextPath()%>/Resource/deleteResource/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee Monthly Utilization Report</b></h3>
			</div>
			<div class="col-md-6">
			</div>
		</div>

		<div class="row" >
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
							<form:form class="form-inline" modelAttribute="EmployeeRequest" method="post"  
							action="${pageContext.request.contextPath}/Employee/getMonthlyUtilizationDetails">
							<div class="form-group">
								<label for="startDate" class="col-sm-7 control-label"> Select Month </label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="month" name="month"
										required="required" placeholder="Select Month" onkeyup="removeText()" autocomplete="off">
								</div>
							</div>
							
							<div class="form-group">
								<label for=" " class="col-sm-7 control-label">Skill Set</label>
								<div class="col-sm-7">
									<form:select path="skills" multiple="multiple"
										items="${skillSetList}" itemValue="skillId" name="skills"
										 required="required" id="skills" itemLabel="skillSet" 
										class="form-control multiselect col-md-4">
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<label for=" " class="col-sm-7 control-label">Experience</label>
								<div class="col-sm-7">
									<!-- <input type="text" id=""  placeholder="Employee Code" > -->
									<form:input class="form-control disabled" id="experiance"
										path="employeeExperience" autocomplete="off"/>
									<span id="idloc" style="color: red"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-7" style="margin-top: 10px;">
									<button type="submit" class="btn btn-primary">Search</button>
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
	</div>
	
				</div>
				
<link href="<%=application.getContextPath() %>/resources/css/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<script src="<%=application.getContextPath() %>/resources/js/MonthPicker.js"></script>
<script src="<%=application.getContextPath() %>/resources/js/MonthPicker.min.js"></script>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
		$('#month').MonthPicker({ Button: false });
	});

	function removeText() {
		document.getElementById("month").value="";
	}
</script>

<jsp:include page="footer.jsp" />
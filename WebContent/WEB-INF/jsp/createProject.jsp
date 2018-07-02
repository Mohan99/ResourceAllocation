<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Create New Project</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="projectRequest"
						method="post" id="myform" name="myform" onsubmit="return validateform()"
						action="${pageContext.request.contextPath}/Project/createOrupdateProject">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Name<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input type="text" class="form-control disabled" id="projectName" name="projectName" pattern="^[a-zA-Z][a-zA-Z/\s]+"
									title="projectName should contain only alphabets."
									path="projectName" autocomplete="off"/>
									<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Start Date<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="startdate" name="startdate"
									path="startDate" autocomplete="off"/>
									<span id="dateloc" style="color: red"></span>
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">End Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="enddate"
									path="endDate" autocomplete="off"/>
							</div>
							</div>   --%>
							
							 <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Manager<span class="asteriskField" style="color: red"> * </span> </label>

							 <div class="col-sm-7">
								<form:select path="projectManager.empId" id="id"
									class="form-control" title="Select projectManager" required="required">

									<option value="">Select projectManager</option>

									<c:forEach var="projectManager" items="${userEmpList}">
										<option value="${projectManager.empId}">${projectManager.employeeName} </option>
									</c:forEach>

								</form:select>
							</div>
							</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Project</label>
							<div class="col-sm-1">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active" value="Y"
									data-onstyle="success" data-offstyle="danger" path="calBench"  />
								<!-- <i class="fa fa-toggle-on" aria-hidden="true"></i> -->
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project Type<span class="asteriskField" style="color: red"> * </span></label>
							<div class="col-sm-7">
								<form:radiobutton path="projectType" value="I" required="required" />
								Internal
								<form:radiobutton path="projectType" value="E" required="required" />
								External
								
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
	function validateform() {
		var projectName = document.myform.projectName.value;
		var startdate = document.myform.startdate.value;
		//var status=false;  

		if (projectName == null || projectName == "") {
			alert("Error : Project name can't be blank");
			document.getElementById("nameloc").innerHTML = "Project name is required";
			return false;
		} 
		if (startdate == null || startdate == "") {
			alert("Error : startdate can't be blank");
			document.getElementById("dateloc").innerHTML = "startdate name is required";
			return false;
		} 
	}
</script> 
<script>
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
</script>
<jsp:include page="footer.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Actual Efforts</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="sheetReq"
						name="myform" method="post" onsubmit="return validateEfforts()"
						action="${pageContext.request.contextPath}/Timesheet/createOrUpdateEfforts">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Select
								Project</label>
							<div class="col-sm-7">
								<form:select path="projectId.projectId" id="projId"
									name="projId" class="form-control"
									onchange="getResource(this.value)" title="Select Project"
									required="required">
									<option value="">Select Project</option>
									<c:forEach var="project" items="${projList}">
										<option value="${project.projectId}">${project.projectName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Select
								Resource</label>
							<div class="col-sm-7">
								<form:select path="empId.empId" id="emp_Id" name="emp_Id"
									class="form-control" title="Select Resource"
									required="required">
									<option value="">Select Resource</option>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Start Date</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="startdate"
									path="fromDate" required="required" />
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">End Date</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="enddate"
									path="toDate" onchange="getEfforts()" required="required" />
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Number Of Efforts</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id='noOfEfforts'
									path="numberEfforts" required="required" value=""
									readonly="true" />
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Estimated
								Efforts</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="estimatedEfforts"
								id="estimatedEfforts"/>
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary" onclick="validateEfforts">Create</button>
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
function validateEfforts() {
    var var1=document.getElementById("noOfEfforts").value;
    var var2=document.getElementById("estimatedEfforts").value;
    if(var2 > var1){
   	 alert("Efforts must not greaterthan Actual Efforts");
   	 return false;
    }
    else
   	 return true;
};

function getResource(projId){
	//alert(contryId);
		$.ajax({
	        url: '<%=application.getContextPath()%>/Timesheet/getResourcesList',
					dataType : "json",
					type : "GET",
					contentType : 'application/json',
					mimeType : 'application/json',
					data : {
						"projectId" : projId
					},
					success : function(response) {
						if (response.responseText != null) {
							var sEmpId = $("#emp_Id");
							sEmpId
									.empty()
									.append(
											'<option value="">Select Resource</option>');
							sEmpId.append(response.responseText);

						}
					},
					error : function(response) {
						if (response.responseText != null) {
							var sEmpId = $("#emp_Id");
							sEmpId
									.empty()
									.append(
											'<option value="">Select Resource</option>');
							sEmpId.append(response.responseText);

						}
					}
				});

	};
	
function getEfforts(){
	var sDate=document.getElementById("startdate").value;
	var eDate=document.getElementById("enddate").value;
		$.ajax({
	        url: '<%=application.getContextPath()%>/Timesheet/getActualEfforts',
					dataType : "json",
					type : "GET",
					contentType : 'application/json',
					mimeType : 'application/json',
					data : {
						"startDate" : sDate,
						"endDate" : eDate
					},
					success : function(response) {
						if (response.responseText != null) {
							var effrt = $("#noOfEfforts");
							var num = (response.responseText).replace(/["']/g,
									"");
							effrt.empty().append(num);

						}
					},
					error : function(response) {
						if (response.responseText != null) {
							var effrt = $("#noOfEfforts");
							var num = (response.responseText).replace(/["']/g,
									"");
							effrt.empty().val(num);

						}
					}
				});

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


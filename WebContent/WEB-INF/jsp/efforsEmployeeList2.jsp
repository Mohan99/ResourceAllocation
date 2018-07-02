<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<%
	int count = 0;
%>
<!-- <body onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload=""> -->
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form:form method="get" class="form-inline" modelAttribute="sheetReq" action="">
			<div class="form-group">
				<label for="startDate">Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="startdate"
						readonly="readonly" required="required" name="startdate"
						placeholder="Start Date"
						value="${sessionScope['scopedTarget.sessionObj'].getTsFromDate()}">
				</div>
			</div>
			<div class="form-group">
				<label for="endDate">End Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="enddate"
						readonly="readonly" required="required" name="enddate"
						placeholder="End Date"
						value="${sessionScope['scopedTarget.sessionObj'].getTsToDate()}">
				</div>
			</div>
			<div class="form-group">
				<label for="endDate">Total Efforts </label>
				<div class="input-group">
					<input type="text" class="form-control" id="totalEfrts"
						readonly="readonly" required="required" name="totalEfrts"
						placeholder="" value="${sessionScope['scopedTarget.sessionObj'].getTsTotalEfforts()}">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>Resource List</h5>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="form-group form-inline">
								<label for=" " class="col-sm-5 control-label"> Select
									Employee</label>
								<div class="col-sm-7">
									<form:select path="empId.empId" id="empId" class="form-control"
										title="Select Employee" onchange="getEmployeeData(this.value)">
										<option value="">Select Employee</option>
										<c:forEach var="emp" items="${empList}">
											<c:choose>
												<c:when test="${emp.empId eq empObj.empId}">
													<option value="${emp.empId}" selected>${emp.employeeId}-${emp.employeeName}</option>
												</c:when>
												<c:otherwise>
													<option value="${emp.empId}">${emp.employeeId}-${emp.employeeName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</form:select>
								</div>
							</div>

							<div id="dataTables-example_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap no-footer">
								<div class="row">
									<input type="hidden" id="projId" name="projId"
										value="${projectId }"> <input type="hidden"
										id="fromDate" name="fromDate" value="${fromDate }"> <input
										type="hidden" id="endDate" name="endDate" value="${endDate }">

									<input type="hidden" id="totalEfforts" name="totalEfforts"
										value="${totalEfforts }">
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
														style="width: 171px;">S.No</th>

													<!-- <th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">Employee Id</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Employee Name</th> -->

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Project Name</th>

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Project Name: activate to sort column ascending"
														style="width: 148px;">Non-Billable Efforts</th>
													 <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Project Name: activate to sort column ascending"
														style="width: 148px;">Billable Efforts</th> 
												</tr>
											</thead>
											<tbody>

												<c:forEach var="sheet" items="${sheetList}"
													varStatus="status">
													<%
														count++;
													%>

													<tr class="gradeA odd" role="row">
														<td align="center">${status.count}</td>
														<%-- <td>${sheet.empId.employeeId}</td>
														<td>${sheet.empId.employeeName}</td> --%>
														<td>${sheet.projectId.projectName}</td>
														<td><input type="hidden"
															name="sheet[${status.index}].empId.empId"
															id="sheet[${status.index}].empId.empId"
															value="${sheet.empId.empId}" class="form-control" /> <input
															type="hidden"
															name="sheet[${status.index}].projectId.projectId"
															id="sheet[${status.index}].projectId.projectId"
															value="${sheet.projectId.projectId}" class="form-control" />
															<input type="number" name="sheet[${status.index}].timesheetEfforts"
															id="sheet[${status.index}].timesheetEfforts"
															value="${sheet.timesheetEfforts}" class="form-control" /></td>
														 <td><input type="hidden"
															name="sheet[${status.index}].empId.empId"
															id="sheet[${status.index}].empId.empId"
															value="${sheet.empId.empId}" class="form-control" /> <input
															type="hidden"
															name="sheet[${status.index}].projectId.projectId"
															id="sheet[${status.index}].projectId.projectId"
															value="${sheet.projectId.projectId}" class="form-control" />
															<input type="number" name="sheet[${status.index}].billableEfforts"
															id="sheet[${status.index}].billableEfforts"
															value="${sheet.billableEfforts}" class="form-control" /></td> 
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<button type="button" onclick="getData()"
											class="btn btn-primary">Submit</button>
									</div>
								</div>

							</div>
						</div>

					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
		</form:form>

	</div>

	<script type="text/javascript">
function getEmployeeData(empId) {
	window.location.href="<%=request.getContextPath()%>/Timesheet/getEffortsEmpList/"+empId;
}
	</script>

	<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			"paging": false,searching: false
		});
	});

	function getData() {
		//alert("came");
		var commentsArray = []
		
		for (var p = 0; p <<%=count%>; p++) {
			var commentObj = {} 
			var nctObj = {}
			 if(document.getElementById("sheet[" + p + "].empId.empId")!=null) 
			empObj = document.getElementById("sheet[" + p + "].empId.empId").value;
			if(document.getElementById("sheet[" + p + "].projectId.projectId")!=null) 
				projObj = document.getElementById("sheet[" + p + "].projectId.projectId").value;
			 if(document.getElementById("sheet[" + p + "].timesheetEfforts")!=null) 
			effortsObj = document.getElementById("sheet[" + p + "].timesheetEfforts").value;
			 if(document.getElementById("sheet[" + p + "].billableEfforts")!=null) 
					billableEffortsObj = document.getElementById("sheet[" + p + "].billableEfforts").value;
			 if(effortsObj<0 || billableEffortsObj<0) 
				{
				alert("Efforts can't be negative")
					}

	if((effortsObj!=0 || billableEffortsObj!=0) && (effortsObj>0 || billableEffortsObj>0) ){
			commentsArray.push(effortsObj);
			commentsArray.push(empObj);
			commentsArray.push(projObj);
			commentsArray.push(billableEffortsObj);
			 }

	
		}
		
      //alert("commentsArray="+commentsArray);
     if(commentsArray!=null && commentsArray!="")
      window.location.href = "<%=request.getContextPath()%>/Timesheet/updateMultipleEfforts/"+ commentsArray;
     else
 		alert("Add Efforts for atlest one Project");
		}
	</script>
	<!-- <script type="text/javascript">
        window.history.forward();
        function noBack()
        {
            window.history.forward();
        }
</script> -->
</div>
<!-- </body> -->
<jsp:include page="footer.jsp" />

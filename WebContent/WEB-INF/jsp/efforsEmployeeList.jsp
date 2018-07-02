<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<%
	int count = 0;
%>
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Efforts List</b></h5>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<form:form method="get" modelAttribute="sheetForm" action="">

									<input type="hidden" id="projId" name="projId"
										value="${projectId }">
									<input type="hidden" id="fromDate" name="fromDate"
										value="${fromDate }">
									<input type="hidden" id="endDate" name="endDate"
										value="${endDate }">

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
													<!-- <th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">S.No</th> -->

													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">Employee Id</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Employee Name</th>

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Project Name</th>

													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Project Name: activate to sort column ascending"
														style="width: 148px;">Actual Efforts</th>

													<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Project Name: activate to sort column ascending"
														style="width: 148px;">Add</th> -->

												</tr>
											</thead>
											<tbody>

												<c:forEach var="sheet" items="${sheetList}"
													varStatus="status">
													<%
														count++;
													%>

													<tr class="gradeA odd" role="row">
														<%-- <td align="center">${status.count}</td> --%>
														<td>${sheet.empId.employeeId}</td>
														<td>${sheet.empId.employeeName}</td>
														<td>${sheet.projectId.projectName}</td>
														<td><input type="hidden"
															name="sheet[${status.index}].empId.empId"
															id="sheet[${status.index}].empId.empId"
															value="${sheet.empId.empId}" class="form-control" /> <input
															type="hidden"
															name="sheet[${status.index}].projectId.projectId"
															id="sheet[${status.index}].projectId.projectId"
															value="${sheet.projectId.projectId}" class="form-control" />
															<input name="sheet[${status.index}].timesheetEfforts"
															id="sheet[${status.index}].timesheetEfforts"
															value="${sheet.timesheetEfforts}" class="form-control" /></td>
														<%-- <td class="center">
															<button type="submit" class="btn btn-primary"
																formaction="<%=request.getContextPath()%>/Timesheet/updateSingleEfforts/${sheet.empId.empId}">Add</button>
														</td> --%>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<button type="button" onclick="getData()"
											class="btn btn-primary">Submit</button>
									</div>
								</form:form>
							</div>

						</div>
					</div>
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
			"paging": false
		});
	});

	function getData() {
		//alert("came");
		var commentsArray = []
		
		for (var p = 0; p <=<%=count%>; p++) {
			var commentObj = {} 
			var nctObj = {}
			 if(document.getElementById("sheet[" + p + "].empId.empId")!=null) 
			empObj = document.getElementById("sheet[" + p + "].empId.empId").value;
			if(document.getElementById("sheet[" + p + "].projectId.projectId")!=null) 
				projObj = document.getElementById("sheet[" + p + "].projectId.projectId").value;
			 if(document.getElementById("sheet[" + p + "].timesheetEfforts")!=null) 
			effortsObj = document.getElementById("sheet[" + p + "].timesheetEfforts").value;

			 if(effortsObj!=0){
			commentsArray.push(effortsObj);
			commentsArray.push(empObj);
			commentsArray.push(projObj);
			 }
		}
     // alert("commentsArray="+commentsArray);
      window.location.href = "<%=request.getContextPath()%>/Timesheet/updateMultipleEfforts/"+ commentsArray;
	}
</script>
<jsp:include page="footer.jsp" />

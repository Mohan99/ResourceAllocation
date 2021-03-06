<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid" class="bg">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee TimeSheet Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>

		
		 <form:form class="form-inline" method="post" action="">
			 <div class="form-group">
				<label for="startDate">Selected Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="startdate"
						readonly="readonly" required="required" name="startdate"
						placeholder="Start Date" value="${sessionScope['scopedTarget.sessionObj'].getStartDateStr()}">
				</div>
			</div> 
		
			<div class="form-group">
				<label for="endDate">Selected End Date </label>
				<div class="input-group">
					<input type="text" class="form-control" id="enddate"
						readonly="readonly" required="required" name="enddate"
						placeholder="End Date" value="${sessionScope['scopedTarget.sessionObj'].getEndDateStr()}">
				</div>
			</div>
			<%-- <div class="form-group">
				<label for="billableProjects">Selected Project </label>
				<div class="input-group">
				 <c:forEach var="proj" items="${projList}">
				 <c:choose>
				<c:when test="${id==proj.projectId}"> 
				<input type="text" class="form-control " name="projectName" 
						 readonly="readonly" id="projectName" value="${proj.projectName}"/>
											
				  </c:when>
			</c:choose> 
				</c:forEach> 
			</div>
			</div> --%>
		 </form:form> 
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Employee TimeSheet Report</b></h5>
					</div>

					<div class="panel-body">
					<form:form class="form-inline" method="Post"
							action="${pageContext.request.contextPath}/Reports/getAccountsProjectsList" modelAttribute="TimesheetRequest">
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;	<div class="form-group form-inline">
								<label for=" " class="col-sm-3 control-label"> 
									<b>Projects List</b></label>
									
								<div class="col-sm-4">
									<form:select path="projectId.projectId" id="projectId" class="form-control"
										title="Select Project" onchange="getProjectData(this.value)">
										<option value="">Select Project</option>
										<c:forEach var="proj" items="${projList}">
											<c:choose>
												<c:when test="${proj.projectId eq projectId}">
													<option value="${proj.projectId}" selected>${proj.projectName}</option>
												</c:when>
												<c:otherwise>
													<option value="${proj.projectId}">${proj.projectName}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</form:select>
								</div>
							</div>
							</form:form>
							<div align="right">
										 <a
											href="<%=request.getContextPath()%>/Reports/exportProjectsList/${projectId}/${sessionScope['scopedTarget.sessionObj'].getStartDateStr()}/${sessionScope['scopedTarget.sessionObj'].getEndDateStr()}"
											class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Export
											Excel</a> </br>
									</div> 
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<table width="100%"
										class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
										id="dataTables-example" role="grid"
										aria-describedby="dataTables-example_info"
										style="width: 100%;">
										<thead>
											<tr role="row" class="bg-warning">
												<!--  <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1"  style="width: 20px;">SNo</th> -->

												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Employee ID</th>

												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Employee Name</th>
											 <!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;"> Projects</th>  -->
                                                <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">From Date</th>
													<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Designation: activate to sort column ascending"
													style="width: 188px;">To Date</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Billable Efforts</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Non Billable Efforts</th>
													
													
													

												<!-- <th style="width: 70px;">Edit</th> -->
											</tr>
										</thead>
										<tbody>
											<%--  <% int i=1; %> --%>
											<c:forEach var="efforts" items="${timeSheetList}">


												<tr class="gradeA odd" role="row">
													<%--  <td class="center"><%=i %> </td> --%>
													<td class="sorting_1">${efforts.employeeId}</td>
													<td class="center">${efforts.employeeName}</td>
												
													 <%-- <td class="center">${efforts.billableProjects}</td> --%>
													 <td class="center">${efforts.fromDate}</td>
													 <td class="center">${efforts.toDate}</td>
													
													<td class="center">${efforts.billableEfforts}</td>
													<td class="center">${efforts.timesheetEfforts}</td>
													

													<%-- <td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Timesheet/editEfforts/${efforts.id}">
															<i><b>Edit</b> </i>
													</a></td> --%>

												</tr>

											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>

						</div>
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
<script type="text/javascript">
function getProjectData(projectId) {
	window.location.href="<%=request.getContextPath()%>/Reports/getProjectsList/"+projectId;
}
	</script>

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>
<jsp:include page="footer.jsp" />

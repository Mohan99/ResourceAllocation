<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Mass Allocation</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form class="form-inline" method="post">
			<div class="form-group">
				<label for="startDate">Start Date </label>
				<div class="input-group">
					<input type="text" class="form-control datepicker" id="startdate"
						name="startdate" placeholder="Start Date" autocomplete="off">
				</div>
			</div>

			<div class="form-group">
				<label for="endDate">End Date </label>
				<div class="input-group">
					<input type="text" class="form-control datepicker" id="enddate"
						name="enddate" placeholder="End Date" autocomplete="off">
				</div>
			</div>
			<div class="form-group">
				<label for="endDate">Project Name </label>
				<div class="input-group">
				<c:forEach var="proj" items="${projectList}">
				<c:choose>
				<c:when test="${projId==proj.projectId}">
				<input type="text" class="form-control disabled" name="projectName" 
						 readonly="true" 	id="projectName" value="${proj.projectName}"/>
											
				</c:when>
			</c:choose>
				</c:forEach>
			
					
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div id="dataTables-example_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap no-footer">
								<div class="row">
									<div class="col-sm-12">
										<input type="hidden" value="${projId }" name="projId"
											id="projId">
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
														style="width: 171px;"><input type="checkbox" id="checkBoxAll" />Assigned Project</th>
														<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;"><input type="checkbox" id="checkBillBoxAll" />Billable </th>
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
														style="width: 207px;">Skill Set</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="emp" items="${empList}">


													<tr class="gradeA odd" role="row">
														<td><c:choose>
																<c:when test="${emp.assigned eq true}">
																	<input type="checkbox" id="empId" class="chkCheckBoxId"
																		name="empId" value="${emp.empId}" checked>
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="empId" class="chkCheckBoxId"
																		name="empId" value="${emp.empId}">
																</c:otherwise>
															</c:choose></td>
															
															<td>
															
															<c:choose>
															
																<c:when test="${emp.billable=='Y' || emp.billable=='y'}">
																	<input type="checkbox" id="billable" class="chkBillCheckBoxId"
																		name="billable" value='${emp.empId}' checked>
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="billable" class="chkBillCheckBoxId"
																		name="billable" value='${emp.empId}'>
																</c:otherwise>
																
															</c:choose>
															</td>
															
														<td>${emp.employeeId}</td>
														<td>${emp.employeeName}</td>
													<%-- 	<td> ${emp.skillSet}</td> --%>
														<td> <c:forEach var="empskill" items="${empSkillList}">
														 <c:choose>
														 <c:when test="${empskill.empId.empId==emp.empId}">
														  ${empskill.skillSet.skillSet}
														 </c:when>
														 </c:choose>
														
														</c:forEach> </td>
														<%-- <td>${emp.employeeSpecialization}</td> --%>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="col-sm-offset-4 col-sm-8"
											style="margin-top: 10px;">
											<button type="submit" class="btn btn-primary" formaction="<%=request.getContextPath()%>/Resource/updateMultipleResources">Allocate</button>
											<%-- <button type="submit" class="btn btn-primary" formaction="<%=request.getContextPath()%>/Resource/updateMultipleResourcesBillable">Allocate Billable</button> --%>
											
											<button type="submit" class="btn btn-danger" formaction="<%=request.getContextPath()%>/Resource/removeMultipleResources/${projId}">Remove</button>
											<%-- <button type="submit" class="btn btn-danger" formaction="<%=request.getContextPath()%>/Resource/removeMultipleResourcesBillable/${projId}">Non-Billable</button> --%>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
		</form>
		<!-- /.panel -->
	</div>
	<script type="text/javascript">
    function removeAllocation() {
	window.location.href="<%=request.getContextPath()%>/Resource/removeMultipleResources/${projId}";
		}
	</script>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('.datepicker').each(function() {
			$(this).removeClass('hasDatepicker').datepicker();
		});

	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#checkBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkCheckBoxId').prop('checked', true);
			else
				$('.chkCheckBoxId').prop('checked', false);

		});
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#checkBillBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkBillCheckBoxId').prop('checked', true);
			else
				$('.chkBillCheckBoxId').prop('checked', false);

		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>
<jsp:include page="footer.jsp" />

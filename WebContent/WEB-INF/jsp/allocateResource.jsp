<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<script>
	function deleteResource(rID) {
		var x = confirm("Are you sure you want to delete this Resource "
				);
		if (x)
			location.href = $('#deleteResUrl').val() + rID;
	}
</script>
<div id="page-wrapper" class="bg">
	<input type="hidden" id="deleteResUrl"
		value="<%=request.getContextPath()%>/Resource/deleteResource/" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Allocate Resource</b></h3>
			</div>
			<div class="col-md-6">
				<form:form class="form-inline" method="post" action="">
					<c:choose>
						<c:when test="${resourceObj.getEmpId() eq null}">
							<div class="form-group">
								<label for="Employee Id"><h4>&nbsp; Employee ID:</h4></label>
								<div class="input-group">
									<h4>
										<b><i>${empId}</i></b>
									</h4>
								</div>
							</div>
							<div class="form-group">
								<label for="Employee Name"><h4> &ensp; Employee Name:</h4></label>
								<div class="input-group">
									<h4>
										<b><i>${empName}</i></b>
									</h4>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label for="Employee Id"><h4>Employee ID:</h4></label>
								<div class="input-group">
									<h4>
										<b><i>${resourceObj.getEmpId()}</i></b>
									</h4>
								</div>
							</div>
							<div class="form-group">
								<label for="Employee Name"><h4>,Employee Name:</h4></label>
								<div class="input-group">
									<h4>
										<b><i>${resourceObj.getEmployeeName()}</i></b>
									</h4>
								</div>
							</div>

						</c:otherwise>
					</c:choose>
				</form:form>
				<br>
				<form:form class="form-horizontal" modelAttribute="resourceRequest"
					method="post"
					action="${pageContext.request.contextPath}/Resource/createOrUpdateResource">
					<form:hidden class="form-control disabled" path="resId"
						value="${resourceObj.getResId()}" readonly="true" />

					

					
					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Assign
							Project<span class="asteriskField" style="color: red"> * </span></label>
						<div class="col-sm-7">
							<form:select path="projectId" id="projectId" required="required"
								class="form-control" title="Select Project">
								<option value="">Assign Project</option>
								<c:forEach var="project" items="${projectList}">
									<c:choose>
										<c:when
											test="${project.projectId eq resourceObj.getProjectId()}">
											<option value="${project.projectId}" selected>${project.projectName}</option>
										</c:when>
										<c:otherwise>
											<option value="${project.projectId}">${project.projectName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Start Date<span class="asteriskField" style="color: red"> * </span></label>
						<div class="col-sm-7">
							<form:input type="text" class="form-control disabled date-picker"
								id="fromDate" required="required" path="projectFrom"
								value="${resourceObj.getProjectFromStr()}" autocomplete="off"/>
						</div>
					</div>

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">End Date</label>
						<div class="col-sm-7">
							<form:input type="text" class="form-control disabled date-picker"
								id="toDate" path="projectTo"
								value="${resourceObj.getProjectToStr()}" autocomplete="off"/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Project Type<span class="asteriskField" style="color: red"> * </span></label>
						<div class="col-sm-7">
							<c:choose>
								<c:when test="${resourceObj.allocation == 'p'}">
									<form:radiobutton path="allocation" value="p" checked="checked"
										required="required" />Primary
								<form:radiobutton path="allocation" value="s"
										required="required" />Secondary
							</c:when>
								<c:when test="${resourceObj.allocation == 's'}">
									<form:radiobutton path="allocation" value="p"
										required="required" />Primary
								<form:radiobutton path="allocation" value="s" checked="checked"
										required="required" />Secondary
							</c:when>
								<c:otherwise>
									<form:radiobutton path="allocation" value="p"
										required="required" />Primary
								<form:radiobutton path="allocation" value="s"
										required="required" />Secondary
							</c:otherwise>
							</c:choose>
						</div>
					</div>
					
				 <div class="form-group">
						<label for=" " class="col-sm-5 control-label">Resource Type<span class="asteriskField" style="color: red"> * </span></label>
						<div class="col-sm-7">
							<c:choose>
								<c:when test="${resourceObj.billable == 'Y'}">
									<form:radiobutton path="billable" value="Y" checked="checked"
										required="required" />Billable
								<form:radiobutton path="billable" value="N"
										required="required" />Non-Billable
							</c:when>
								<c:when test="${resourceObj.billable == 'N'}">
									<form:radiobutton path="billable" value="Y"
										required="required" />Billable
								<form:radiobutton path="billable" value="N" checked="checked"
										required="required" />Non-Billable
							</c:when>
								<c:otherwise>
									<form:radiobutton path="billable" value="Y"
										required="required" />Billable
								<form:radiobutton path="billable" value="N"
										required="required" />Non-Billable
							</c:otherwise>
							</c:choose>
						</div>
					</div> 
					
					
					
					<%-- 
					  <div class="form-group">
						<label for=" " class="col-sm-5 control-label">Resource
							Type</label>
						<div class="col-sm-7">
							<c:choose>
								<c:when test="${resourceObj.billable == 'N' }">
									<input type="checkbox" id="billable" class="chkCheckBoxId" path="billable"
										name="billable" value="N" unchecked>
								</c:when>
								
								<c:when test="${resourceObj.billable == 'Y'}">
									<input type="checkbox" id="billable" class="chkCheckBoxId" path="billable"
										name="billable" value="Y" checked>
								</c:when>
								<c:otherwise>
									<input type="checkbox" id="billable" class="chkCheckBoxId" path="billable"
										name="billable" value="Y" >
								</c:otherwise>
								
							</c:choose> 
							
							</div>
							</div> --%>
							
							<%-- <c:choose>
								<c:when test="${resourceObj.billable == 'Y'}">
									<form:checkbox path="billable" value="Y" checked="checked"
										required="required" />Billable
									
								<form:checkbox path="billable" value="N" required="required" />Non-Billable
							</c:when>
							
								<c:when test="${resourceObj.billable == 'N'}">
									<form:checkbox path="billable" value="Y" required="required" />Billable
								<form:checkbox path="billable" value="N" checked="checked"
										required="required" />Non-Billable
								</c:when>
							
							
								 <c:otherwise>
									<form:checkbox path="billable" value="Y" required="required" />Billable
								<form:checkbox path="billable" value="N" required="required" />Non-Billable
							</c:otherwise> 
							</c:choose>
						</div>
					</div>
					 --%>
					<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Billable</label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active"
									data-onstyle="success" data-offstyle="danger" path="billable" />
								<!-- <i class="fa fa-toggle-on" aria-hidden="true"></i> -->
							</div>
						</div> --%>
						
						
					

					<c:if test="${!empty resourceObj.projectName}">

						<div style="text-align: center;">
							<div style="display: inline-block; color: green;">
								<button type="submit" class="btn btn-primary">Reallocate</button>
							</div>
							<div style="display: inline-block; color: green;">
								<input type="button" class="btn btn-info" value="Back"
									onclick="location.href = '<%=request.getContextPath()%>/Resource/searchResource';">
							</div>
						</div>

					</c:if>
					<c:if test="${empty resourceObj.projectName}">
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Allocate</button>
							</div>
						</div>
					</c:if>
				</form:form>

			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5><b>Resources</b></h5>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div id="dataTables-example_wrapper"
						class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<div class="row">
							<div class="col-sm-12">
								<table width="100%"
									class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
									id="dataTables-example" role="grid"
									aria-describedby="dataTables-example_info" style="width: 100%;">
									<thead>
										<tr role="row" class="bg-warning">
											<th class="sorting_asc" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="Employee Name: activate to sort column descending"
												style="width: 171px;">Project Name</th>
											<th class="sorting_asc" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="Employee Name: activate to sort column descending"
												style="width: 171px;">Project Type</th>
											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												style="width: 207px;">Start Date</th>

											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												id="toDate" onselect="status" style="width: 148px;">End
												Date</th>

											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending"
												id="toDate" onselect="status" style="width: 148px;">Resource
												Type</th>

											<th class="sorting" tabindex="0"
												aria-controls="dataTables-example" rowspan="1" colspan="1"
												aria-label="Project Name: activate to sort column ascending"
												id="status" style="width: 148px;">Status</th>
											<th style="width: 70px;">Edit</th>

										</tr>
									</thead>
									<tbody>

										<c:forEach var="resource" items="${resourceList}">


											<tr class="gradeA odd" role="row">
												<td>${resource.projectName}</td>
												<td><c:choose>
														<c:when
															test="${resource.allocation=='p' || resource.allocation=='P'}">Primary<br />
														</c:when>
														<c:otherwise>
															Secondary<br />
														</c:otherwise>
													</c:choose></td>

												<td>${resource.projectFromStr}</td>
												<td>${resource.projectToStr}</td>
												<td><c:choose>
														<%-- <c:when test="${resource.projectTo == null}"> --%>

														<c:when
															test="${resource.billable=='n' || resource.billable=='N' }">Non-Billable<br />
														</c:when>
														<c:when
															test="${resource.billable=='Y' || resource.billable=='y' }">Billable<br />
														</c:when>

													</c:choose></td>
													
													
													
												<td><c:choose>
														<%-- <c:when test="${resource.projectTo == null}"> --%>

														<c:when test="${resource.projectCompleted=='Y'}">Closed<br />
														</c:when>

														<%-- 	</c:when> --%>
														<c:otherwise>Active<br />

														</c:otherwise>

													</c:choose></td>
												<td class="center"><a class="btn-info btn-sm"
													href="<%=request.getContextPath()%>/Resource/editResource/${resource.projectId}">
														<i class="fa fa-edit"> </i>
												</a></td>

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
			<!-- ... Your content goes here ... -->

		</div>
	</div>
</div>


<script>
	$(document)
			.ready(
					function() {
						$("#fromDate").datepicker(
								{
									//dateFormat: "yy-M-dd",
									//dateFormat: "dd-MM-yyyy",
									maxDate : new Date(),
									onSelect : function(date) {
										var date2 = $('#fromDate').datepicker(
												'getDate');
										$('#toDate').datepicker('option',
												'minDate', date2);
										$('#toDate').datepicker('option',
												'maxDate', new Date());
									}
								});
						$('#toDate')
								.datepicker(
										{
											//dateFormat: "yy-M-dd",
											//dateFormat: "dd-M-yy",
											//dateFormat: "dd-MM-yyyy",
											maxDate : new Date(),
											minDate : $('#fromDate')
													.datepicker('getDate'),
											onClose : function() {
												var dt1 = $('#fromDate')
														.datepicker('getDate');
												var dt2 = $('#toDate')
														.datepicker('getDate');
												//check to prevent a user from entering a date below date of dt1
												if (dt2 < dt1) {
													$('#toDate')
															.datepicker(
																	'setDate',
																	$(
																			'#fromDate')
																			.datepicker(
																					'option',
																					'minDate'));
												}

												if (document
														.getElementById("toDate").Value != "")
													document
															.getElementById("status").checked = false;
												else
													(document
															.getElementById("toDate").Value == "")
												document
														.getElementById("status").checked = true;
											}

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

<jsp:include page="footer.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b> Manager Employee's</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						method="post"
						action="${pageContext.request.contextPath}/Employee/createOrUpdateManagerEmployee">
						<form:hidden class="form-control disabled" path="empId"
							value="${empObj.getEmpId()}" readonly="true" />
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeId"
									value="${empObj.getEmployeeId()}" readonly="true" />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeName"
									value="${empObj.getEmployeeName()}"  />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Category</label>
							<div class="col-sm-7">
								<form:select path="category.categoryId" id="categoryId"
									class="form-control" title="Select Category">
									<option value="">Select Category</option>
									<c:forEach var="category" items="${categoryList}">
										<c:choose>
											<c:when
												test="${category.categoryId eq empObj.getEmployeeCategory().getCategoryId()}">
												<option value="${category.categoryId}" selected>${category.categoryName}</option>
											</c:when>
											<c:otherwise>
												<option value="${category.categoryId}">${category.categoryName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeDesg"
									value="${empObj.getEmployeeDesg()}" />
							</div>
						</div>
						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Specialization</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization"
									value="${empObj.getEmployeeSpecialization()}" />

							</div>
						</div> --%>
						
						
					 <div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Specialization </label>
							<div class="col-sm-7">

								<form:select path="skills" id="skills" name="skills"
									class="form-control" title="Select Skills" multiple="multiple">
	
									<c:forEach var="skills" items="${skills}">
										<c:set var="count" value="0" scope="page" />
										<c:forEach var="empSkills" items="${skillsList}">
											<c:choose>
												<c:when
													test="${skills.skillId eq empSkills.skillSet.skillId}">
													<option value="${skills.skillId}" selected>${skills.skillSet}</option>
													<c:set var="count" value="1" scope="page" />
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${count == 0}">
											<option value="${skills.skillId}">${skills.skillSet}</option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
						</div> 
						
                <div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Workplace</label>
							<div class="col-sm-7">
								<form:select path="Workplace.id" id="WorkplaceId"
									class="form-control" title="Select Workplace">
									<option value="">Select Workplace</option>
									<c:forEach var="workplace" items="${workplaceList}">
										<c:choose>
											<c:when
												test="${workplace.id eq empObj.getWorkplace().getId()}">
												<option value="${workplace.id}" selected>${workplace.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${workplace.id}">${workplace.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeMobilenumber"
									value="${empObj.getEmployeeMobilenumber()}" />

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Years of Experience<br> (In years & Months)</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeExperience"
									value="${empObj.getEmployeeExperience()}" autocomplete="off" />

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Email</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="emailId"
									value="${empObj.getEmailId()}" />

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Join Date</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="joinDate" id="joinDate"
									value="${empObj.getJoinDateStr()}" autocomplete="off"/>

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reporting To<br> (Manager) </label>
							<div class="col-sm-7">
								<form:select path="reportingTo.empId" id="userId"
									class="form-control" title="Select Manager">
									<option value="">Select Manager</option>
									<c:forEach var="reportingTo" items="${usersList}">
										<c:choose>
											<c:when
												test="${reportingTo.empId eq empObj.getReportingTo().getEmpId()}">
												<option value="${reportingTo.empId}" selected>${reportingTo.employeeName}</option>
											</c:when>
											<c:otherwise>
												<option value="${reportingTo.empId}">${reportingTo.employeeName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Submit</button>
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
   $(document).ready(function () {
      
       $('#joinDate').datepicker({
           //dateFormat: "dd-M-yy",
            //dateFormat: "yy-M-dd",
           maxDate: new Date(),
           minDate: $('#joinDate').datepicker('getDate'),
           onClose: function () {
               var dt1 = $('#joinDate').datepicker('getDate');
               var dt2 = $('#joinDate').datepicker('getDate');
               //check to prevent a user from entering a date below date of dt1
               if (dt2 < dt1) {
                   $('#joinDate').datepicker('setDate', $('#joinDate').datepicker('option', 'minDate'));
               }
           }
       });
   });
</script>
<jsp:include page="footer.jsp" />

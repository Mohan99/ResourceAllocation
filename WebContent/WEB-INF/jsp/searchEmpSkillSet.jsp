<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Search SkillSet List</b></h3>
			</div>
			<div class="col-md-6">
				<br>
				<form:form class="form-horizontal" modelAttribute="skillSetRequest"
					method="post"
					action="${pageContext.request.contextPath}/Employee/getSalesSearch">
					
					
					
					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Select
							Employee</label>
						<div class="col-sm-7">
							<form:select path="employeeId" id="employeeId"
								class="form-control" title="Select Employee" required="required">
								<option value="">Select emplyee</option>
								<c:forEach var="emp" items="${empList}">
									<option value="${emp.empId}">${emp.employeeName}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label for=" " class="col-sm-5 control-label">Select
							SkillSet</label>
						<div class="col-sm-7">
							<form:select path="skillId" id="skillId"
								class="form-control" title="Select SkillSet" required="required">
								<option value="">Select SkillSet</option>
								<c:forEach var="skillSet" items="${skillSetList}">
									<option value="${skillSet.skillId}">${skillSet.skillSet}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					

					
					

					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
							<button type="submit" class="btn btn-primary">Search</button>
						</div>
					</div>

				</form:form>

			</div>
		</div>
	</div>

	<!-- ... Your content goes here ... -->

</div>
</div>


<jsp:include page="footer.jsp" />
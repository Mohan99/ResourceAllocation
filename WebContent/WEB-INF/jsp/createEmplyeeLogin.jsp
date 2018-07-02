<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Employee Login Creation</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						method="post"
						action="${pageContext.request.contextPath}/Users/createEmployeeUser">
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
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeName"
									value="${empObj.getEmployeeName()}" readonly="true" />

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeDesg"
									value="${empObj.getEmployeeDesg()}" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Specialization</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization"
									value="${empObj.getEmployeeSpecialization()}" readonly="true"/>

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Location</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization"
									value="${empObj.getEmployeeCity().getName()}" readonly="true"/>

							</div>
						</div>
			
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeMobilenumber" 
									value="${empObj.getEmployeeMobilenumber()}" readonly="true"/>

							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Create Password<span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<form:password class="form-control disabled" id="password" name="password"
									path="password" required="required"/>
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
	</div>
</div>
<script>
	function validateform() {
		
		var password = document.myform.password.value;
		
		
		 if (password == null || password == "") {
			alert("Password can't be blank");
			document.getElementById("passwordloc").innerHTML = "Password is required";
			return false;
		} else if (password.length < 4) {
			alert("Password must be at least 4 characters long.");
			document.getElementById("passwordloc").innerHTML = "LoginPassword must contain minimum 4 letters";
			return false;
		} 
	}
</script>
<jsp:include page="footer.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<script>

	function validateform() {
		var employeeId = document.myform.employeeId.value;
		var empname = document.myform.empname.value;
		/* var empdesg = document.myform.empdesg.value; */
		/* var mobileno = document.myform.mobileno.value; */
		var expr = document.myform.expr.value;
		/* var password = document.myform.loginPassword.value;
		var x = document.myform.email.value;
		var atposition = x.indexOf("@");
		var dotposition = x.lastIndexOf("."); */
		//var status=false;  

		if (employeeId == null || employeeId == "") {
			alert("Error : employeeId can't be blank");
			document.getElementById("idloc").innerHTML = "employeeId is required";
			return false;
		} else if (empname == null || empname == "") {
			alert("Employee Name can't be blank");
			document.getElementById("nameloc").innerHTML = "Employee name is required";
			return false;
		
	}
		/* else if (empdesg == null || empdesg == "") {
			alert("Employee Designation can't be blank.");
			document.getElementById("desgloc").innerHTML = "Employee Designation is required";
			return false;
		} */
		/* else if (mobileno == null || mobileno == "") {
			alert("Mobile Number can't be blank.");
			document.getElementById("mobileloc").innerHTML = "Mobile Number is required";
			return false;
			
		} */ /* else if (mobileno.length < 10 || mobileno.length > 10) {
			alert("Mobile Number Should contain 10 numbers .");
			document.getElementById("mobileloc").innerHTML = "Mobile Number Should contain 10 numbers";
			return false;
			
		} */
		else if (expr<0) {
			alert("Experience should be support only greater than or equal to Zero");
			document.getElementById("exprloc").innerHTML = "Experience should be support only greater than or equal to Zero";
			return false;
			
		}
		
	}
</script>


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Edit Employee</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest" id="myform" name="myform" onsubmit="return validateform()"
						method="post"
						action="${pageContext.request.contextPath}/Employee/createOrUpdateEmployee">
						<form:hidden class="form-control disabled" path="empId" 
							value="${empObj.getEmpId()}" readonly="true" />
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID<span class="asteriskField" style="color: red">*</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeId"
									value="${empObj.getEmployeeId()}" required="required"  autocomplete="off" />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Name<span class="asteriskField" style="color: red">*</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empname" name="empname"
									path="employeeName" pattern="^[a-zA-Z][a-zA-Z/\s]+" title="Employee Name should contain only alphabets." 
									value="${empObj.getEmployeeName()}" autocomplete="off" />
<span id="nameloc" style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Category<span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<form:select path="category.categoryId" id="categoryId" required="required"
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
								Designation<span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeDesg"  id="empdesg" name="empdesg"
									value="${empObj.getEmployeeDesg()}" autocomplete="off" />
									<span id="desgloc" style="color: red"></span>
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
								Skill Set</label> 
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
							<label for=" " class="col-sm-5 control-label"> Workplace<span class="asteriskField" style="color: red"> *</label>
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
						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Country </label>
							<div class="col-sm-7">
								<form:select path="countries.id" id="countryId"
									class="form-control" onchange="getState(this.value)"
									title="Select Country">
									<option value="">Select Coutry</option>
									<c:forEach var="country" items="${countryList}">
										<c:choose>
											<c:when
												test="${country.id eq empObj.getEmployeeCountry().getId()}">
												<option value="${country.id}" selected>${country.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${country.id}">${country.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">State</label>
							<div class="col-sm-7">
								<form:select path="states.id" id="stateId" class="form-control"
									onchange="getCity(this.value)" title="Select State">
									<option value="">Select State</option>
									<c:forEach var="state" items="${stateList}">
										<c:choose>
											<c:when
												test="${state.id eq empObj.getEmployeeState().getId()}">
												<option value="${state.id}" selected>${state.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${state.id}">${state.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">City</label>
							<div class="col-sm-7">
								<form:select path="cities.id" id="cityid" class="form-control"
									title="Select City">
									<option value="">Select City</option>
									<c:forEach var="city" items="${cityList}">
										<c:choose>
											<c:when test="${city.id eq empObj.getEmployeeCity().getId()}">
												<option value="${city.id}" selected>${city.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${city.id}">${city.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div> --%>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeMobilenumber" id="mobileno" name="mobileno" pattern="^\d{10}$" 
									value="${empObj.getEmployeeMobilenumber()}" autocomplete="off"/>
									<span id="mobileloc" style="color: red"></span>

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Years of
								Experience<br> (In years & Months)
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeExperience" id="expr" name="expr"
									value="${empObj.getEmployeeExperience()}" autocomplete="off"/>
									
									<span id="exprloc" style="color: red"></span>

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Email</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="emailId"
									value="${empObj.getEmailId()}" autocomplete="off"/>

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Join Date</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="joinDate"
									id="joinDate" value="${empObj.getJoinDateStr()}" autocomplete="off"/>

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reporting
								To<br> (Manager)<span class="asteriskField" style="color: red"> *</span>
							</label>
							<div class="col-sm-7">
								<form:select path="reportingTo.empId" id="userId" required="required"
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

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Status</label>
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${empObj.isStatus()}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="status" size="1" path="status"
											value="${empObj.isStatus()==true}" />
									</c:when>

									<c:otherwise>
										<form:checkbox class="form-control disabled" id="status"
											size="1" path="status" value="${empObj.isStatus()==true}" />
									</c:otherwise>
								</c:choose>
							</div>
						</div> --%>

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
<%-- <script>
var userCountryId;
var userStateId;
var userCityId;
var country_id;
function getState(contryId){
	//alert(contryId);
		$.ajax({
	        url: '<%=application.getContextPath()%>/Employee/getStatesList',
	        dataType: "json",
	        type: "GET",
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        data : {"country_Id" : contryId},
	        success: function(response){
	        	//alert("success")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	}},
	        error: function(response) { 
	        	//alert("fail")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	//alert(response.responseText);
	        	}
	        }
	    });
		
	}
function getCity(stateId){
  // alert(stateId);
	$.ajax({
 url: '<%=application.getContextPath()%>
	/Employee/getCitiesList',
			dataType : "json",
			type : "GET",
			contentType : 'application/json',
			mimeType : 'application/json',
			data : {
				"state_Id" : stateId
			},
			success : function(response) {
				if (response.responseText != null) {
					$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if (response.responseText != null) {
					$("#cityid").append(response.responseText);
					//alert(response.responseText);
				}
			}
		});

	}
</script> --%>
<script>
	$(document).ready(
			function() {

				$('#joinDate').datepicker(
						{
							//dateFormat: "dd-M-yy",
							//dateFormat: "yy-M-dd",
							maxDate : new Date(),
							minDate : $('#joinDate').datepicker('getDate'),
							onClose : function() {
								var dt1 = $('#joinDate').datepicker('getDate');
								var dt2 = $('#joinDate').datepicker('getDate');
								//check to prevent a user from entering a date below date of dt1
								if (dt2 < dt1) {
									$('#joinDate').datepicker(
											'setDate',
											$('#joinDate').datepicker('option',
													'minDate'));
								}
							}
						});
			});
</script>

<%--  = ${category.categoryId} = ${empObj.getEmployeeCategory().getCategoryId()} --%>
<jsp:include page="footer.jsp" />

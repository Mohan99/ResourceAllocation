<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

 <script>

	function validateform() {
		var employeeId = document.myform.employeeId.value;
		var empname = document.myform.empname.value;
		/* var empdesg = document.myform.empdesg.value; */
		/* var mobileno = document.myform.mobileno.value;   pattern="[0-9]{10}" title="MobileNumber should contain only Numbers." */
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
		/*  else if (empdesg == null || empdesg == "") {
			alert("Employee Designation can't be blank.");
			document.getElementById("desgloc").innerHTML = "Employee Designation is required";
			return false;
		} */ 
		/* else if (mobileno == null || mobileno == "") {
			alert("Mobile Number can't be blank.");
			document.getElementById("mobileloc").innerHTML = "Mobile Number is required";
			return false;
			
		} */ else if (mobileno.length < 10 || mobileno.length > 10) {
			alert("Mobile Number Should contain 10 numbers .");
			document.getElementById("mobileloc").innerHTML = "Mobile Number Should contain 10 numbers";
			return false;
			
		}
		else if (expr<0) {
			alert("Experience should be support only greater than or equal to Zero");
			document.getElementById("exprloc").innerHTML = "Experience can not be negative";
			return false;
			
		}
		
	}
</script>


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header"  style="color:blue"><b>Create New Employee</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest" method="post"  id="myform" name="myform"
						
						action="${pageContext.request.contextPath}/Employee/createOrUpdateEmployee" onsubmit="return validateform()" >

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID <span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">

								<!-- <input type="text" id=""  placeholder="Employee Code" > -->
								<form:input class="form-control disabled" id="employeeId" name="employeeId"
									path="employeeId"   autocomplete="off"/>
								<span id="idloc" style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Name <span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empname" name="empname"
									path="employeeName" pattern="^[a-zA-Z][a-zA-Z/\s]+" title="Employee Name should contain only alphabets." autocomplete="off"/>
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empdesg" name="empdesg"
									path="employeeDesg" autocomplete="off"/>
								<span id="desgloc" style="color: red"></span>
							</div>
						</div>
						
						 <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Skill Set</label>
							<div class="col-sm-7">
								<form:select path="skills" multiple="multiple" id="skillId"
									items="${skills}" itemValue="skillId" name="skills" 
									itemLabel="skillSet" class="form-control multiselect col-md-4">
								</form:select>
							</div> 
							</div> 

						

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="mobileno" name="mobileno" 
									path="employeeMobilenumber" autocomplete="off" />
								<span id="mobileloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Years of
								Experience<br> (In years & Months)
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="expr" name="expr" pattern = "(/^-?\d*(\.\d+)?$/)"
									path="employeeExperience" autocomplete="off"/>
								<span id="exprloc" style="color: red"></span>
							</div>
						</div>

						 <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Category <span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<form:select path="category.categoryId" id="categoryId"
									class="form-control" title="Select Category" required="required">
									<option value="">Select Category</option>
									<c:forEach var="category" items="${categoryList}">
										<option value="${category.categoryId}">${category.categoryName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div> 
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Workplace <span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<form:select path="Workplace.id" id="WorkplaceId"
									class="form-control" title="Select Workplace" required="required">
									<option value="">Select workplace</option>
									<c:forEach var="workplace" items="${workplaceList}">
										<option value="${workplace.id}">${workplace.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Email Id </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" 
									path="emailId" autocomplete="off"/>
								<span id="emailId" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Join Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="joinDate"
									path="joinDate" autocomplete="off" />
							</div>
						</div>


						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Reporting
								To<br> (Manager) <span class="asteriskField" style="color: red"> *
							</label>
							<div class="col-sm-7">
								<form:select path="reportingTo.empId" id="userId"
									class="form-control" title="Select Manager" required="required">
									<option value="">Select Manager</option>
									<c:forEach var="user" items="${usersEmpList}">
										<option value="${user.empId}">${user.employeeName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
 <div class="row table-gap">
                    <div class="col-lg-6 col-xs-10 col-sm-6 col-md-5 col-sm-offset-5 col-md-offset-5 col-xs-offset-1">
                        <div id="alert_display_boxParent">
                            <div id="alert_display_box">
                             <span style="color:red">   ${Response.statusMessage}</span>
                            </div>
                        </div>
                    </div>
                </div>
		<!-- ... Your content goes here ... -->

	</div>
</div>

<script>

$(document).ready(function(){

	 // Add new element
	 $(".add").click(function(){

	  // Finding total number of elements added
	  var total_element = $(".element").length;
	 
	  // last <div> with element class id
	  var lastid = $(".element:last").attr("id");
	  var split_id = lastid.split("_");
	  var nextindex = Number(split_id[1]) + 1;

	  var max = 5;
	  // Check total number elements
	  if(total_element < max ){
	   // Adding new div container after last occurance of element class
	   $(".element:last").after("<div class='element' id='div_"+ nextindex +"'></div>");
	 
	   // Adding element to <div>
	   $("#div_" + nextindex).append("<input type='text' placeholder='Enter your skill' id='txt_"+ nextindex +"'>&nbsp;<span id='remove_" + nextindex + "' class='remove'>X</span>");
	  }
	 
	 });

	 // Remove element
	 $('.container').on('click','.remove',function(){
	 
	  var id = this.id;
	  var split_id = id.split("_");
	  var deleteindex = split_id[1];

	  // Remove <div> with id
	  $("#div_" + deleteindex).remove();

	 }); 
	});
	
function listbox_moveacross(sourceID, destID) {
	var src = document.getElementById(sourceID);
	var dest = document.getElementById(destID);

	for(var count=0; count < src.options.length; count++) {

		if(src.options[count].selected == true) {
				var option = src.options[count];

				var newOption = document.createElement("option");
				newOption.value = option.value;
				newOption.text = option.text;
				newOption.selected = true;
				try {
						 dest.add(newOption, null); //Standard
						 src.remove(count, null);
				 }catch(error) {
						 dest.add(newOption); // IE only
						 src.remove(count);
				 }
				count--;
		}
	}
}

(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
	  js.async=true;
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));



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
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
					//$("#stateId").append(response.responseText);
	        	}
	        },
	        error: function(response) { 
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
	        	//$("#stateId").append(response.responseText);
	        	}
	        }
	    });
		
	}
function getCity(stateId){
  // alert(stateId);
	$.ajax({
        url: '<%=application.getContextPath()%>/Employee/getCitiesList',
			dataType : "json",
			type : "GET",
			contentType : 'application/json',
			mimeType : 'application/json',
			data : {
				"state_Id" : stateId
			},
			success : function(response) {
				if (response.responseText != null) {
					var sEmpId = $("#cityid");
					sEmpId.empty().append(
							'<option value="">Select City</option>');
					sEmpId.append(response.responseText);
					//$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if (response.responseText != null) {
					var sEmpId = $("#cityid");
					sEmpId.empty().append(
							'<option value="">Select City</option>');
					sEmpId.append(response.responseText);
					//$("#cityid").append(response.responseText);
				}
			}
		});

	}
</script>
<script>
	$(document).ready(function() {
		$("#joinDate").datepicker({

			maxDate : new Date(),
			onSelect : function(date) {
				var date2 = $('#joinDate').datepicker('getDate');
				$('#joinDate').datepicker('option', 'minDate', date2);
				$('#joinDate').datepicker('option', 'maxDate', new Date());
			}
		});
	});
</script>
<%-- <script type="text/javascript">
var employee_Id;
function getState(employeeId){
	//alert(contryId);
		$.ajax({
	        url: '<%=application.getContextPath()%>/Employee/getEmpList',
	        dataType: "json",
	        type: "GET",
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        data : {"employee_Id" : contryId},
	        success: function(response){
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
					//$("#stateId").append(response.responseText);
	        	}
	        },
	        error: function(response) { 
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
	        	//$("#stateId").append(response.responseText);
	        	}
	        }
	    });
		
	}

</script> --%>


<jsp:include page="footer.jsp" />



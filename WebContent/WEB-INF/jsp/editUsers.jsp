<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Edit Adimin/PMO/Sales List</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" id="myform" name="myform" modelAttribute="UsersRequest"
						method="GET" onsubmit="return validateform()"
						action="${pageContext.request.contextPath}/Users/createOrUpdateUsers">
						<form:hidden class="form-control disabled" path="userId"
							value="${Users.getUserId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">User Name<span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="userName" id="userName" name="userName"
									pattern="^[a-zA-Z][a-zA-Z/\s]+"
									title="Username should contain only alphabets."
									 autocomplete="off" value="${Users.getUserName()}" />
									 <span id="nameloc" style="color: red"></span>
							</div>
						</div>


						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Login Name<span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="loginName" id="loginname"
									pattern="^[a-zA-Z][a-zA-Z/\s]+" title="LoginName should contain only alphabets." autocomplete="off" value="${Users.getLoginName()}" />
									 <span id="loginloc" style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-7">
								<form:hidden class="form-control disabled" path="loginPassword"
									 autocomplete="off" value="${Users.getLoginPassword()}" />
									 
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Email ID<span class="asteriskField" style="color: red"> *</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="email" id="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
									title="Format:example@ex.com" autocomplete="off" value="${Users.getEmail()}" />
									 <span id="emailloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">User Type<span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${Users.getRole()=='a' || Users.getRole()=='A'}">
										<form:radiobutton path="role" value="a" required="required"
											checked="checked" />
								Admin
								<form:radiobutton path="role" value="p" required="required" />
								Pmo
								<form:radiobutton path="role" value="s" required="required" />
								Sales
								<form:radiobutton path="role" value="Ac" required="required" />
								Accounts
									</c:when>
									<c:when test="${Users.getRole()=='p' || Users.getRole()=='P'}">
										<form:radiobutton path="role" value="a" required="required" />
								Admin
								<form:radiobutton path="role" value="p" required="required"
											checked="checked" />
								Pmo
								<form:radiobutton path="role" value="s" required="required" />
								Sales
								<form:radiobutton path="role" value="Ac" required="required" />
								Accounts
									</c:when>
									<c:when test="${Users.getRole()=='s' || Users.getRole()=='S'}">
										<form:radiobutton path="role" value="a" required="required" />
								Admin
								<form:radiobutton path="role" value="p" required="required" />
								Pmo
								<form:radiobutton path="role" value="s" required="required"
											checked="checked" />
								Sales
								<form:radiobutton path="role" value="Ac" required="required" />
								Accounts
									</c:when>
									
									<c:when test="${Users.getRole()=='ac' || Users.getRole()=='Ac'}">
										<form:radiobutton path="role" value="a" required="required" />
								Admin
								<form:radiobutton path="role" value="p" required="required" />
								Pmo
								<form:radiobutton path="role" value="s" required="required"
											checked="checked" />
								Sales
								<form:radiobutton path="role" value="Ac" required="required" />
								Accounts
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Is Active</label>
							<div class="col-sm-1">
								<c:choose>
									<c:when test="${Users.isIsActive()}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="isActive" size="1" path="isActive"
											value="${Users.isIsActive()==true}" />
									</c:when>
									<c:otherwise>
										<form:checkbox class="form-control disabled" id="isActive"
											size="1" path="isActive" value="${Users.isIsActive()==true}" />
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
<!-- <script>
	function validateform() {
		var username = document.myform.userName.value;
		var loginname = document.myform.loginName.value;
		
		var x = document.myform.email.value;
		var atposition = x.indexOf("@");
		var dotposition = x.lastIndexOf(".");
		//var status=false;  

		if (username == null || username == "") {
			alert("Error : Username can't be blank");
			document.getElementById("nameloc").innerHTML = "Username is required";
			return false;
		} else if (loginname == null || loginname == "") {
			alert("Login Name can't be blank");
			document.getElementById("loginloc").innerHTML = "Loginname is required";
			return false;
		
		} else if (x == null || x == "") {
			alert("EmailId can't be blank");
			document.getElementById("emailloc").innerHTML = "Email Id is required";
			return false;
		} else if (atposition < 1
				|| dotposition<atposition+2 || dotposition+2>x.length) {
			document.getElementById("emailloc").innerHTML = " Please enter a valid E-mail address";
			return false;
		}
	}
</script> -->
<script>
	function validateform() {
		var username = document.myform.userName.value;
		var loginname = document.myform.loginName.value;
		var password = document.myform.loginPassword.value;
		var x = document.myform.email.value;
		var atposition = x.indexOf("@");
		var dotposition = x.lastIndexOf(".");
		//var status=false;  

		if (username == null || username == "") {
			alert("Error : Username can't be blank");
			document.getElementById("nameloc").innerHTML = "Username is required";
			return false;
		} else if (loginname == null || loginname == "") {
			alert("Login Name can't be blank");
			document.getElementById("loginloc").innerHTML = "Loginname is required";
			return false;
		} else if (password == null || password == "") {
			alert("Password can't be blank");
			document.getElementById("passwordloc").innerHTML = "Password is required";
			return false;
		} else if (password.length < 4) {
			alert("Password must be at least 4 characters long.");
			document.getElementById("passwordloc").innerHTML = "LoginPassword must contain minimum 4 letters";
			return false;
		} else if (x == null || x == "") {
			alert("EmailId can't be blank");
			document.getElementById("emailloc").innerHTML = "Email Id is required";
			return false;
		} else if (atposition < 1
				|| dotposition<atposition+2 || dotposition+2>x.length) {
			document.getElementById("emailloc").innerHTML = " Please enter a valid E-mail address";
			return false;
		}
	}
</script>
<jsp:include page="footer.jsp" />
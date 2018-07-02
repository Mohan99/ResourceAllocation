<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Change Password</b></h3>
				<div class="col-md-6">
					<form:form class="form-horizontal"
						modelAttribute="ChangePasswordRequest" method="post" id="form"
						action="${pageContext.request.contextPath}/updatePassword">

						<div class="form-group">
							<label for="exampleInputPassword1" class="col-sm-5 control-label">Old
								Password <span class="asteriskField" style="color: red"> *</span></label>

							<div class="col-sm-7">

								<!-- <input type="text" id=""  placeholder="Employee Code" > -->
								<form:input type="password" class="form-control"
									path="oldPassword" id="password" autocomplete="off"/>
								<%-- <form:input class="form-control disabled"  path="oldPassword" /> --%>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">New
								Password<span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<form:input type="password" class="form-control"
									id="newPassword" path="newPassword" autocomplete="off"/>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Confirm
								Password<span class="asteriskField" style="color: red"> *</span></label>
							<div class="col-sm-7">
								<input type="password" class="form-control" id="confirmPassword" autocomplete="off"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="button" class="btn btn-primary"
									onclick="verifyChangePassword()">Update Password</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
<div class="row" class="col-lg-12"
			style="${Response.getStatusCode()=='1'?'':'display:none'}">
			<div class="" id="alert_display_boxParent">
				<div id="alert_display_box" class="AlertClose">
					<div>
						<div class="alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">×</button>
							<i class="fa fa-ban"></i> ${Response.getStatusMessage()}
						</div>

					</div>
				</div>
			</div>

		</div>
		<!-- ... Your content goes here ... -->

	</div>
</div>


<jsp:include page="footer.jsp" />

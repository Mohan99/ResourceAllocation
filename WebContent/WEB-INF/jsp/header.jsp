<%@page import="com.gcs.db.businessDao.Employee"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- <jsp:useBean id="sesionObj" class="com.gcs.controller.SessionData" scope="session" /> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<title>ResourceAllocation</title>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
<spring:url value="/resources/css/startmin.css" var="startmincss" />
<spring:url value="/resources/css/font-awesome.min.css"
	var="fontawesome" />
<spring:url value="/resources/css/pmo-track-css.css" var="pmotrack" />
<spring:url value="/resources/css/jquery-ui.css" var="jqueryuicss" />

<spring:url value="/resources/js/jquery.min.js" var="jquerymin" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapmin" />
<spring:url value="/resources/js/metisMenu.min.js" var="metisMenumin" />
<spring:url value="/resources/js/startmin.js" var="startmin" />
<spring:url value="/resources/js/jquery-ui.js" var="jqueryuijs" />
<spring:url value="/resources/js/reports.js" var="reportsjs" />
<spring:url value="/resources/js/country_state_city.js"
	var="country_state_cityjs" />
<spring:url value="/resources/js/validate.js" var="validatejs" />

<spring:url value="/resources/img" var="img" />

<spring:url
	value="/resources/js/vendor/datatables/js/jquery.dataTables.min.js"
	var="jquerytablemin" />
<spring:url
	value="/resources/js/vendor/datatables-plugins/dataTables.bootstrap.min.js"
	var="tablebootstrapmin" />
<spring:url
	value="/resources/js/vendor/datatables-responsive/dataTables.responsive.js"
	var="tableresponsive" />

<link href="${bootstrap}" rel="stylesheet" />
<link href="${startmincss}" rel="stylesheet" />
<link href="${fontawesome}" rel="stylesheet" />
<link href="${pmotrack}" rel="stylesheet" />
<link href="${jqueryuicss}" rel="stylesheet" />
<link href="${validatejs}" rel="stylesheet" />

<script src="${jquerymin}"></script>
<script src="${bootstrapmin}"></script>
<script src="${metisMenumin}"></script>
<script src="${startmin}"></script>
<script src="${jquerytablemin}"></script>
<script src="${tablebootstrapmin}"></script>
<script src="${tableresponsive}"></script>
<script src="${jqueryuijs}"></script>
<script src="${reportsjs}"></script>
<script src="${country_state_cityjs}"></script>
<script src="${validatejs}"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" href="#"> <img src="${img}/logo.png"
					width="140">
				</a>
			</div>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<!-- Top Navigation: Left Menu -->

			<!-- Top Navigation: Right Menu -->
			<ul class="nav navbar-right navbar-top-links">
				<c:choose>
					<c:when
						test="${not empty sessionScope['scopedTarget.sessionObj'].getUserObj().getUserName()}">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
								${sessionScope['scopedTarget.sessionObj'].getUserObj().getUserName()}
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu dropdown-user">
								<li><a href="<%=request.getContextPath()%>/changePassword"><i
										class="fa fa-user fa-fw"></i> Change Password </a></li>

								<li class="divider"></li>
								<li><a href="<%=request.getContextPath()%>/logout"><i
										class="fa fa-sign-out fa-fw"></i> Logout </a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
								${sessionScope['scopedTarget.sessionObj'].getEmpObj().getEmployeeName()}
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu dropdown-user">
								<li><a
									href="<%=request.getContextPath()%>/changeEmpUserPassword"><i
										class="fa fa-user fa-fw"></i> Change Password </a></li>

								<li class="divider"></li>
								<li><a href="<%=request.getContextPath()%>/logout"><i
										class="fa fa-sign-out fa-fw"></i> Logout </a></li>
							</ul></li>
					</c:otherwise>
				</c:choose>

			</ul>
			<c:choose>
				<c:when
					test="${not empty sessionScope['scopedTarget.sessionObj'].getEmpObj().getEmployeeName()}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a
									href="<%=request.getContextPath()%>/managerdashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>

								<!-- Reporting Manager Login -->


								<%-- <li><a href="#"><i class="fa fa-list fa-fw"></i>
										Employee Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Employee/activeEmpUnderManager"><i
												class="glyphicon glyphicon-search"></i> Active Employee </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Employee/inActiveEmpUnderManager"><i
												class="glyphicon glyphicon-search"></i> InActive Employee </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Employee/createEmployee"><i
												class="fa fa-user-plus fa-fw"></i> Create Employee </a></li>
									
									</ul></li> --%>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Resources Allocations<span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Resource/searchResourceUnderManager"><i
												class="glyphicon glyphicon-search"></i> Resource Allocation
												List </a></li>
										<%-- <li><a
											href="<%=request.getContextPath()%>/Resource/allocateNewResourceManager"><i
												class="fa fa-user-plus fa-fw"></i> Allocating Resources </a></li> --%>

										<li><a
											href="<%=request.getContextPath()%>/Resource/massAllocations"><i
												class="fa fa-user-plus fa-fw"></i> Mass Allocations </a></li>

										<li><a
											href="<%=request.getContextPath()%>/Reports/getAllResorcesUnderManager"><i
												class="glyphicon glyphicon-equalizer"></i> Resources List </a></li>

									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i> Weekly
										Allocations <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Timesheet/createActualEfforts"><i
												class="glyphicon glyphicon-pencil"></i> Weekly Efforts </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Timesheet/getActualEffortsList"><i
												class="glyphicon glyphicon-pencil"></i> Weekly TimeSheets </a></li>
									</ul></li>
								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Reports <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Timesheet/managerTimesheetReport"><i
												class="glyphicon glyphicon-equalizer"></i> Weekly Efforts
												Report </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Reports/managerMonthlyReport"><i
												class="glyphicon glyphicon-equalizer"></i> Monthly Efforts
												Report </a></li>
									</ul></li>
							</ul>
						</div>
					</div>

				</c:when>

				<c:when
					test="${sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='a' || sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='A'}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a href="<%=request.getContextPath()%>/dashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>
								<li><a href="#"><i class="fa fa-list fa-fw"></i>Admin
										Users <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Users/searchUsers"><i
												class="glyphicon glyphicon-search"></i> Admin/PMO/Sales List
										</a></li>
										<li><a
											href="<%=request.getContextPath()%>/Users/createUsers"><i
												class="fa fa-user-plus fa-fw"></i> Create Admin/PMO/Sales </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Users/employeeLoginUsers"><i
												class="glyphicon glyphicon-search"></i> Reporting Manager
												List </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Users/createEmployeeLoging"><i
												class="fa fa-user-plus fa-fw"></i> Create Reporting Manager
										</a></li>

									</ul></li>


								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Category Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Category/searchCategory"><i
												class="glyphicon glyphicon-search"></i> Category List </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Category/createCategory"><i
												class="fa fa-user-plus fa-fw"></i> Create Category </a></li>
										<%-- <li><a
											href="<%=request.getContextPath()%>/Employee/categoryUpload"><i
												class="fa fa-user-plus fa-fw"></i> Category Upload </a></li> --%>

									</ul></li>

								<%-- <li><a href="#"><i class="fa fa-list fa-fw"></i>
										Holiday Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Holiday/searchHolidays"><i
												class="glyphicon glyphicon-search"></i> Search Holidays </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Holiday/createHolidays"><i
												class="fa fa-user-plus fa-fw"></i> Create Holiday </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Holiday/holidayBulkUpload"><i
												class="glyphicon glyphicon-open"></i> Upload Holiday
												Calender </a></li>

									</ul></li> --%>

								<%--	<li><a href="#"><i class="fa fa-list fa-fw"></i> Leave
										Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Leaves/searchLeaves"><i
												class="glyphicon glyphicon-search"></i> Search Leaves </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Leaves/createLeaves"><i
												class="fa fa-user-plus fa-fw"></i> Create Leaves </a></li>
										<li><a
									href="<%=request.getContextPath()%>/Holiday/holidayBulkUpload"><i
										class="glyphicon glyphicon-open"></i> Upload Holiday Calender
								</a></li>

									</ul></li> --%>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										SkillSet Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Category/searchSkillSet"><i
												class="glyphicon glyphicon-search"></i> SkillSet List </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Category/createSkillSet"><i
												class="fa fa-user-plus fa-fw"></i> Create SkillSet </a></li>
									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Employee Master <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Employee/activeEmployee"><i
												class="glyphicon glyphicon-search"></i> Active Employees </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Employee/inActiveEmp"><i
												class="glyphicon glyphicon-search"></i> InActive Employees </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Employee/createEmployee"><i
												class="fa fa-user-plus fa-fw"></i> Create Employee </a></li>
										<%-- <li><a
													href="<%=request.getContextPath()%>/Employee/employeeBulkUpload"><i
														class="glyphicon glyphicon-open"></i> Employee Bulk Upload
												</a></li> --%>
									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Projects <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Project/activeProject"><i
												class="glyphicon glyphicon-search"></i> Active Projects </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Project/closedProject"><i
												class="glyphicon glyphicon-search"></i> Closed Projects </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Project/createProject"><i
												class="fa fa-user-plus fa-fw"></i> Create Project </a></li>
									</ul></li>



								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Resources Allocations<span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Resource/searchResource"><i
												class="glyphicon glyphicon-search"></i> Resource Allocation
												List </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Resource/allocateNewResource"><i
												class="fa fa-user-plus fa-fw"></i> Resources Allocations </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Resource/allEmpMassAllocations"><i
												class="fa fa-user-plus fa-fw"></i> Mass Allocations </a></li>

										<li><a
											href="<%=request.getContextPath()%>/Resource/resourceBulkUpload"><i
												class="glyphicon glyphicon-open"></i> Resources Bulk Upload</a></li>
										<%-- <li><a
									href="<%=request.getContextPath()%>/Resource/resourceAction"><i
										class="glyphicon glyphicon-open"></i> Upload Changes</a></li> --%>
									</ul></li>
								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Reports <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/Reports/getAllEmpReport"><i
												class="glyphicon glyphicon-equalizer"></i> Weekly Resource
												Report </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Reports/getBenchEmployees"><i
												class="glyphicon glyphicon-equalizer"></i> Employee Bench
												Report </a></li>
										<li><a
											href="<%=request.getContextPath()%>/Reports/empsTotalBenchPeriod"><i
												class="glyphicon glyphicon-equalizer"></i> Total Bench
												Period Report </a></li>
										<%-- <li><a
									href="<%=request.getContextPath()%>/Reports/empBenchSearch"><i
										class="glyphicon glyphicon-equalizer"></i> Employee Bench Report</a></li>  --%>
										<li><a
											href="<%=request.getContextPath()%>/Reports/allEmpsTotalBenchPeriod"><i
												class="glyphicon glyphicon-equalizer"></i> Employee wise
												Bench Report</a></li>
										<li><a
											href="<%=request.getContextPath()%>/Reports/empTimesheetReport"><i
												class="glyphicon glyphicon-equalizer"></i> Weekly Efforts
												Report </a></li>

										<li><a
											href="<%=request.getContextPath()%>/Reports/monthlyReport"><i
												class="glyphicon glyphicon-equalizer"></i> Monthly Efforts
												Report </a></li>
									</ul></li>
							</ul>
						</div>
					</div>
				</c:when>

				<c:when
					test="${sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='p' || sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='P'}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a href="<%=request.getContextPath()%>/pmoDashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>

								<li><a
									href="<%=request.getContextPath()%>/Timesheet/timesheetsSubmitted"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Weekly Submitted TimeSheets </a></li>

								<li><a
									href="<%=request.getContextPath()%>/Timesheet/pmoMonthlyReport"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Monthly Submitted TimeSheets</a></li>

								<li><a
									href="<%=request.getContextPath()%>/Reports/getPmoResourcesList"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Resources Report </a></li>
							</ul>
						</div>
					</div>
				</c:when>



				<c:when
					test="${sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='ac' || sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='Ac'}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a href="<%=request.getContextPath()%>/pmoDashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>

								<li><a
									href="<%=request.getContextPath()%>/Reports/projectwiseTimeSheetReport"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Employee TimeSheet Report </a></li>
								<li><a
									href="<%=request.getContextPath()%>/Reports/getBenchEmployees"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Employee Bench Report </a></li>

								<%-- <li><a
									href="<%=request.getContextPath()%>/Timesheet/account"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Accounts </a></li> --%>
								<%-- <li><a
									href="<%=request.getContextPath()%>/Reports/getPmoResourcesList"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Resources List </a></li> --%>
							</ul>
						</div>
					</div>
				</c:when>


				<c:when
					test="${sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='s' || sessionScope['scopedTarget.sessionObj'].getUserObj().getRole()=='S'}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a href="<%=request.getContextPath()%>/salesDashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>
								<li><a
									href="<%=request.getContextPath()%>/Employee/salesSearch"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Bench Employee Skill Set Report</a></li>
								<li><a
									href="<%=request.getContextPath()%>/Employee/empUtilizationReport"
									class="active"><i class="glyphicon glyphicon-equalizer"></i>
										Employee Monthly Utilization Report </a></li>
							</ul>
						</div>
					</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</nav>
	</div>
</body>
</html>

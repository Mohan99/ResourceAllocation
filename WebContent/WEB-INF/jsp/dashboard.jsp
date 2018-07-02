<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
		<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Overview</b></h3>
				<div class="col-md-6"></div>
			</div>

			<p class="page-header"></p>
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<!-- <i class="fa fa-comments fa-5x"></i> -->
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].employeeCount}</div>
								<div><%-- ${sessionScope['scopedTarget.sessionObj'].getEmployeeCount()} --%>
									Employees</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Employee/activeEmployee">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-green">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<!-- <i class="fa fa-tasks fa-5x"></i> -->
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].resourceCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getResourceCount()} --%>
									Engaged Resources</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Resource/engagedResources">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-5">
                                    <!-- <i class="fa fa-shopping-cart fa-5x"></i> -->
                                </div>
                                <div class="col-xs-9 text-right">
                                   
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].benchCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getEmployeeCount() - sessionScope['scopedTarget.sessionObj'].getResourceCount()} --%>
									Bench </div>
							
                                </div>
                                
                            </div>
                        </div>
                        <a href="<%=request.getContextPath()%>/Reports/getBenchEmployees">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>

			<div class="col-lg-4 col-md-6">
				<div class="panel panel-yellow">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<!-- <i class="fa fa-shopping-cart fa-5x"></i> -->
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].projectsCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getProjectsCount()} --%>
									Total Projects</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Project/totalProjects">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			
			
			            <div class="col-lg-4 col-md-6">
				<div class="panel panel-red">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<!-- <i class="fa fa-comments fa-5x"></i> -->
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].activeProjectsCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getActiveProjectsCount()} --%>
									Active Projects</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Project/activeProject">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
                
                <div class="col-lg-4 col-md-6">
                   <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-5">
                                   <!--  <i class="fa fa-support fa-5x"></i> -->
                                </div>
                               <div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].closedProjectsCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getClosedProjectsCount()} --%>
									Completed Projects</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Project/closedProject">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
						
                        </a>
                    </div>
                </div>
			
			

		<%-- 	<div class="col-lg-3 col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-comments fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].usersCount}</div>
								<div>
									${sessionScope['scopedTarget.sessionObj'].getUsersCount()}
									Users</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Users/searchUsers">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div> --%>

			<!-- <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">20</div>
                                    <div>Projects Delivered </div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">13</div>
                                    <div>Support Tickets!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div> -->
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>


<jsp:include page="footer.jsp" />   
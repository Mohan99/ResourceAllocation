<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
		<div class="col-lg-12">
				<h3 class="page-header" style="color:blue">Overview</h3>
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
					<a href="<%=request.getContextPath()%>/Employee/pmoEmployeeListView">
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
					<a href="<%=request.getContextPath()%>/Resource/searchResource">
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
                                   
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].employeeCount -sessionScope['scopedTarget.sessionObj'].resourceCount}</div>
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
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />   
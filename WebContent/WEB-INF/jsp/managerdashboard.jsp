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
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].empCountByReporting}</div>
								<div><%-- ${sessionScope['scopedTarget.sessionObj'].getEmpCountByReporting()} --%>
									Employees </div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Employee/activeEmpUnderManager"> 
						<div class="panel-footer">
						 	<span class="pull-left">View Details</span>
						<span 
								class="pull-right"> <i class="fa fa-arrow-circle-right"></i> </span>
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
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].resCountByReporting}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getResCountByReporting()} --%>
									Engaged Resources</div>
							</div>
						</div>
					</div>
					 <a href="<%=request.getContextPath()%>/Resource/resourceListUnderManager"> 
						<div class="panel-footer">
							 <span class="pull-left">View Details</span> <span
								class="pull-right"> <i class="fa fa-arrow-circle-right"></i> </span>
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
                                   
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].empCountByReporting -sessionScope['scopedTarget.sessionObj'].resCountByReporting}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getEmpCountByReporting() - sessionScope['scopedTarget.sessionObj'].getResCountByReporting()} --%>
									Bench </div>
							
                                </div>
                            </div>
                        </div>
                        <a href="<%=request.getContextPath()%>/Resource/benchListUnderManager"> 
                            <div class="panel-footer">
                                 <span class="pull-left">View Details</span> 
                                <span class="pull-right"> <i class="fa fa-arrow-circle-right"></i> </span>
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
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].projCountByReporting}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getProjCountByReporting()} --%>
									Total Projects</div>
							</div>
						</div>
					</div>
					 <a href="<%=request.getContextPath()%>/Project/totalProjectsUnderManager"> 
						<div class="panel-footer">
							 <span class="pull-left">View Details</span>  <span
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
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].activeProjCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getActiveProjCount()} --%>
									Active Projects</div>
							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/Project/activeProjectUnderManager"> 
						<div class="panel-footer">
							 <span class="pull-left">View Details</span>  <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i> </span>
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
								<div class="huge">${sessionScope['scopedTarget.sessionObj'].closedProjCount}</div>
								<div>
									<%-- ${sessionScope['scopedTarget.sessionObj'].getClosedProjCount()} --%>
									Completed Projects</div>
							</div>
						</div>
					</div>
					 <a href="<%=request.getContextPath()%>/Project/completedProjectsUnderManager"> 
						<div class="panel-footer">
							 <span class="pull-left">View Details</span>
							  <span class="pull-right"> <i class="fa fa-arrow-circle-right"></i> </span>
							<div class="clearfix"></div>
						</div>
						
                        </a>
                    </div>
                </div> 
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>


<jsp:include page="footer.jsp" />   
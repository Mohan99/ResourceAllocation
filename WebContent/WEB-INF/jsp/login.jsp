<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
 <base href="${pageContext.request.contextPath}">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<title>Resource Allocation</title>

<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
<%-- <spring:url value="/resources/css/loginstyle.css" var="loginstyle" /> --%>
<spring:url value="/resources/css/startmin.css" var="startmin" />
<spring:url value="/resources/css/font-awesome.min.css" var="fontawesome" />
<spring:url value="/resources/js/jquery.min.js" var="jquerymin" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapmin" />
<spring:url value="/resources/js/metisMenu.min.js" var="metisMenumin" />
<spring:url value="/resources/js/startmin.js" var="startminjs" />
<%-- <!-- jQuery --> 
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script> 
<!-- Bootstrap Core JavaScript --> 
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> 
<!-- Metis Menu Plugin JavaScript --> 
<script src="<%=request.getContextPath()%>/js/metisMenu.min.js"></script> 
<!-- Custom Theme JavaScript --> 
<script src="<%=request.getContextPath()%>/js/startmin.js"></script> --%>
<spring:url value="/resources/img" var="img" />
<link href="${bootstrap}" rel="stylesheet" />
<%-- <link href="${loginstyle}" rel="stylesheet" /> --%>
<link href="${startmin}" rel="stylesheet" />
<link href="${fontawesome}" rel="stylesheet" />

<script src="${jquerymin}"></script>
<script src="${bootstrapmin}"></script>
<script src="${metisMenumin}"></script>
<script src="${startminjs}"></script>

   
</head>
 
<body class="bg"  onload="noBack();" 
	onpageshow="if (event.persisted) noBack();" onunload="">
<div id="wrapper"> 
  
  <!-- Navigation -->
  <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="navbar-header"> <a class="navbar-brand" href="#"> <img src="${img}/logo.png" width="140"> </a> </div>
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      
  </nav>
  
  <!-- Page Content -->
  <div id="" style="margin-top: 50px;">
    <div class="container-fluid">
      <div class="row">
        <div class="col-lg-12">
          <div class="col-md-4 col-md-offset-4">
            <h3 class="page-header">Employee Resource Allocation </h3>
           	<form:form class="form-horizontal"
						modelAttribute="LoginRequest" id="form" method="post"
						action="${pageContext.request.contextPath}/validate">
              <div class="form-group">
                <label for="exampleInputEmail1">Employee ID</label>
                <form:input type="text" class="form-control" path="email" id="email" placeholder="Email"/>
               
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                 <form:input type="password" class="form-control" path="password" id="password" placeholder="Password"/>          
              </div>
              <div class="checkbox">
                <label>
                  <input type="checkbox">
                  Keep me signed in </label>
              </div>
              <br>
              <!--<button type="submit" class="btn btn-default btn-primary">Submit</button>-->
              <button type="submit" class="btn-primary btn" > Login</button>
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
</div>
 <script type="text/javascript">
        window.history.forward();
        function noBack()
        {
            window.history.forward();
        }
</script>

</body>
</html>

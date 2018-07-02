<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" /> 


<div id="page-wrapper" class="bg">
    <div class="container-fluid">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><b>Change Password</b></h3>
          <div class="col-md-6">
            <h2><span style='color:green'>${Response.getStatusMessage()}</span></h2>
          </div>
        </div>
      </div>
      
      <!-- ... Your content goes here ... --> 
      
    </div>
  </div>
<jsp:include page="footer.jsp" />  
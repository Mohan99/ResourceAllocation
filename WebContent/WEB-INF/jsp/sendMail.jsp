<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" /> 


<div id="page-wrapper" class="bg">
    <div class="container-fluid">
    <div class="row" class="col-lg-12"  style="${Response.getStatusCode()!=null?'':'display:none'}">
                        <div class="" id="alert_display_boxParent">
                            <div id="alert_display_box" class="AlertClose">
                                <div>
                                <div  class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> <i class="fa fa-ban"></i>  
                                ${Response.getStatusMessage()}
                                </div>
                                 
                                </div>                           
                            </div>
                        </div>
                       
                    </div>
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><b>Send Mail</b></h3>
          <div class="col-md-6">
            <form:form class="form-horizontal" modelAttribute="SendMailRequest" method="post" id="form" action="${pageContext.request.contextPath}/Employee/sendEmail">
  
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">To Email</label>
    <div class="col-sm-7">
   
      <!-- <input type="text" id=""  placeholder="Employee Code" > -->
    <form:input class="form-control disabled" id="toEmail"  path="toEmail" />
    </div>
  </div>
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">cc Emails</label>
    <div class="col-sm-7">
    <form:input class="form-control disabled" placeholder=" emails separated with comma" id="ccEmails" path="ccEmails" />  
    </div>
  </div>  
  
 <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Subject</label>
    <div class="col-sm-7">
     <form:input class="form-control disabled" id="subject" path="subject" />  
    </div>
  </div>  
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Message</label>
    <div class="col-sm-7">
     <form:textarea class="form-control disabled" id="message" path="message" />  
    </div>
  </div>  
  <div class="form-group">
    <div class="col-sm-offset-4 col-sm-8" style="margin-top:10px;">
      <button type="button" class="btn btn-primary" onclick="verifyChangePassword()">Send Email</button>
    </div>
  </div>
</form:form>
          </div>
        </div>
      </div>
      
      <!-- ... Your content goes here ... --> 
      
    </div>
  </div>
<jsp:include page="footer.jsp" />  
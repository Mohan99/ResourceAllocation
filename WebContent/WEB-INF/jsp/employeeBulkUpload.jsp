<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" /> 

<div id="page-wrapper" class="bg">
    <div class="container-fluid">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header" style="color:blue"><b> Employee Bulk Upload</b></h3>
          <div class="col-md-6">
          
         <%--  <form method="POST" action="${pageContext.request.contextPath}/Employee/upload" enctype="multipart/form-data">
    <input type="file" name="file" /><br/>
    <input type="submit" value="Submit" />
</form> --%>
<form action="${pageContext.request.contextPath}/Employee/upload" method="post" enctype="multipart/form-data">  
 
<div class="form-group">
 <label for=" " class="col-sm-5 control-label">Select File: </label>
    <div class="col-sm-offset-4 col-sm-8" style="margin-top:10px;">
    <input type="file" class="form-control" name="file"/>
    </div>
  </div>
<div class="form-group">
    <div class="col-sm-offset-4 col-sm-8" style="margin-top:10px;">
      <button type="submit" class="btn btn-primary">Upload File</button>
    </div>
  </div>
</form>                 
          </div>
        </div>
      </div>
      
      <!-- ... Your content goes here ... --> 
      
    </div>
  </div>
<jsp:include page="footer.jsp" />  
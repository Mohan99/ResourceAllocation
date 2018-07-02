<%@page import="com.gcs.db.businessDao.Users"%>
<%@page import="com.gcs.dbDao.UsersDao"%>
<%@page import="com.gcs.dbDaoImpl.UsersDaoImpl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<script>
	function deleteUsers(userId) {
		var x = confirm("Are you sure you want to delete this Users  "
				);
		if (x)
			location.href = $('#deleteUsersUrl').val() + userId;
	}
</script>
<!-- <script>
 function deleteMultiple(userId){
    var result = confirm("Do you really want to delete records?" +userId);
    if(result){
    	 location.href=$('#deleteMultipleUrl').val()+userId;
    	   return true;
    	    }else{
    	        return false;
    	    } 
    	} 
    
 $(document).ready(function(){
    $('#check_all').on('click',function(){
        if(this.checked){
            $('.checkbox').each(function(){
                this.checked = true;
            });
        }else{
             $('.checkbox').each(function(){
                this.checked = false;
            });
        }
    });
    
    $('.checkbox').on('click',function(){
        if($('.checkbox:checked').length == $('.checkbox').length){
            $('#check_all').prop('checked',true);
        }else{
            $('#check_all').prop('checked',false);
        }
    }); 
}); 
</script> -->
<input type="hidden" id="deleteUsersUrl"
	value="<%=request.getContextPath()%>/Users/deleteUsers/" />
<%-- <input type="hidden" id="deleteMultipleUrl"
	value="<%=request.getContextPath()%>/Users/deleteMultiple/" /> --%>
<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b> Category List</b></h3>
				<div class="col-md-6">
					<!-- <form class="form-horizontal">
  <div class="form-group">
    <label for=" " class="col-sm-3 control-label">Employee ID</label>
    <div class="col-sm-6">
      <input type="text" class="form-control" id=" " placeholder="Employee ID">
    </div>
    <div class="col-sm-3 pull-left">
      <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  </div>
  <div class="form-group">
    
  </div>
</form>-->
				</div>
			</div>
		</div>
		<form action="deleteMultiple" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Users List</b></h5>
					</div>
					
					
		
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Users/createUsers"> <i
							class="fa fa-user-plus"> </i> Add User
						</a>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<table width="100%"
										class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
										id="dataTables-example" role="grid"
										aria-describedby="dataTables-example_info"
										style="width: 100%;">
										<thead>
											<tr role="row" class="bg-warning">
												
												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="User Name: activate to sort column descending"
													style="width: 171px;">S.No</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">User Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Login Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Login Password</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">isActive</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Project Name: activate to sort column ascending"
													style="width: 148px;">Email</th>

												<!--                           <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-label="Designation: activate to sort column ascending" style="width: 188px;">Is Active</th>                     
 -->
												<th style="width: 70px;">Edit</th>
												<th style="width: 80px;">Delete</th>
												<!-- <th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-label="Project Name: activate to sort column ascending" style="width: 148px;"> </th>
                          -->
												<!--  <th><input type="checkbox" name="check_all" id="check_all" value=""/></th>  -->
											</tr>
										</thead>
										<tbody>

											<c:forEach var="users" items="${Users}">


												<tr class="gradeA odd" role="row">
													<td><input type="checkbox" class="chkCheckBoxId"
														value="${users.userId}" name="userId" id="userId"></td>
													<td class="sorting_1">${users.userId}</td>
													<td>${users.userName}</td>
													<td>${users.loginName}</td>
													<td>${users.loginPassword}</td>
													<td>${users.isActive}</td>
													<td>${users.email}</td>
													<%--  <td class="center">${manager.isActive==1?true:false}</td> --%>
													<td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/Users/editUsers/${users.userId}">
															<i class="fa fa-edit"> </i>
													</a></td>

													<td class="center"><a class="btn-danger btn-sm"
														href="#" onclick="deleteUsers('${users.userId}')"> <i
															class="fa fa-trash-o fa-lg"> </i>
													</a></td>

													<!-- <td align="center"><input type="checkbox" name="selected_id[]" class="checkbox" value="yes" ></td> -->
												</tr>

												<!-- <script type="text/javascript">
function deleteConfirm(){
    var result = confirm("Are you sure to delete?");
    if(result){
        return true;
    }else{
        return false;
    }
}

$(document).ready(function(){
    $('#select_all').on('click',function(){
        if(this.checked){
            $('.checkbox').each(function(){
                this.checked = true;
            });
        }else{
             $('.checkbox').each(function(){
                this.checked = false;
            });
        }
    });
    
    $('.checkbox').on('click',function(){
        if($('.checkbox:checked').length == $('.checkbox').length){
            $('#select_all').prop('checked',true);
        }else{
            $('#select_all').prop('checked',false);
        }
    });
});
</script> -->
												<%-- <tr> <td><input type="submit" value="Delete" class="btn btn-danger" onclick="deleteMultiple('${users.userId}');"> </td></tr> --%>
											</c:forEach>
											<!-- <tr class="gradeA even" role="row">
                          <td class="sorting_1">Employee Name</td>
                          <td>GI1214</td>
                          <td>UI/UX Designer</td>
                          <td class="center">M-Vite</td>
                          <td class="center"><a class="btn-info btn-sm" href="Form-HR-Edit.html"> <i class="fa fa-edit"> </i> Edit</a></td>
                        </tr> -->

										</tbody>
									</table>

									<%-- <input type="submit" value="Delete" class="btn btn-danger"
										onclick="deleteMultiple('${users.userId}');"> --%>
									 <input type="submit" name="userId" value="Delete" class="btn btn-danger" onclick="return confirm ('Are You Sure?')" >
									<!-- <input type="submit" id="userId" name="userId" value="Delete" onclick="GetCheckedState ();"/> -->
								</div>
							</div>

						</div>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
<!--  <script type="text/javascript">
$(document).ready(function() {
		$('#checkBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkCheckBoxId').prop('checked', true);
			else
				$('.chkCheckBoxId').prop('checked', false);

		});
	});
</script>  -->
<!-- <script type="text/javascript">
        function GetCheckedState () {
            var input = document.getElementById ("userId");
            var isChecked = input.checked;
            isChecked = (isChecked)? "checked" : "not checked";
            alert ("The checkBox is " + isChecked);
        }
    </script> -->
<!-- <script>
   /* function deleteMultiple(userId){
	    var result = confirm("Do you really want to delete records?" +users);
	    if(result){
	    	//location.href=$('#deleteMultipleUrl').val()+userId;
	    	   return true;
	    	    }else{
	    	        return false;
	    	    }  */
  $(document).ready(function(){
	    $('#check_all').on('click',function(){
	        if(this.checked){
	            $('.checkbox').each(function(){
	                this.checked = true;
	                alert ("The checkBox is ");
	            });
	        }else{
	             $('.checkbox').each(function(){
	                this.checked = false;
	                alert ("The checkBox is " );
	            });
	        }
	    });
	    
	    $('.checkbox').on('click',function(){
	        if($('.checkbox:checked').length == $('.checkbox').length){
	            $('#check_all').prop('checked',true);
	            alert ("The checkBox is ");
	        }else{
	            $('#check_all').prop('checked',false);
	            alert ("The checkBox is ");
	        }
	    });
	}); 
	</script>   -->

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#checkBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkCheckBoxId').prop('checked', true);
			else
				$('.chkCheckBoxId').prop('checked', false);

		});
	});
</script>
<jsp:include page="footer.jsp" />

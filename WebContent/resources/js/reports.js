
$(function() {
	
	loadReportForm();
});
function loadReportForm(){
	var searchHidden=$('#searchHidden').val();

	if(searchHidden!="")
		$('#searchBy').val(searchHidden);
		
	searchHidden == "Manager"?hideManagerEmpDiv(true,false):searchHidden == "Employee"?hideManagerEmpDiv(false,true)
			:hideManagerEmpDiv(true,true);	
}

 function hideManagerEmpDiv(isEmp,isManager){
 isEmp?$("#empDiv").hide():$("#empDiv").show();
 isManager?$("#managerDiv").hide():$("#managerDiv").show();
 }

 function searchByChange(){	
	 var x = $("#searchBy").val();
	 x == "Manager"?hideManagerEmpDiv(true,false):x == "Employee"?hideManagerEmpDiv(false,true)
				:hideManagerEmpDiv(true,true);	
 }

 function StartDateAllowMonday(id){	
	
	 var dt=$('#'+id).val();
	 var d = new Date(dt);
	 if(d.getDay()==1){
		// var day=d.getDate()+6;
		 d.setDate(d.getDate() + 6);
			var endDate = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
		 setEndDate(endDate);
	 }else{
		 alert("Please select monday only");
		 $('#'+id).val("");
	 }		 	 
 }
 function setNullToSearchField(isEmp,isManger){
	 
	 if(isEmp) $('#empId').val("");
	 if(isManger) $('#managerId').val("");
	 
 }
 
 function validateReportSearch(){
	 var x=true;
	 var searchBy= $('#searchBy').val();
	 if(searchBy=="All"){
	  x=confirm("Are you sure do you want search by all employees");
	  setNullToSearchField(true,true);
	 }
	 else if(searchBy=="Manager"){
		var manager= $('#managerId').val();
		if(manager==""){
			alert("Plese select manager");
			x=false;
		}
		setNullToSearchField(true,false);
	 }else if(searchBy=="Employee"){		
			var emp= $('#empId').val();			
			if(emp==""){
				alert("Plese select Employee");
				x=false;
			}
			setNullToSearchField(false,true);
			}
	 	 
	 if(x){
	 var startDate= $('#startDate').val();
	 var endDate= $('#endDate').val();
	 if(startDate==""||endDate==""){
		 alert("Please enter start date and end date")
	 }
	 else if(dateDiffrence(startDate,endDate)){
			 alert("start date should be less than end date");
	 }
	 else		 	
		document.getElementById("form").submit();
	 
	 }
 }
 function dateDiffrence(d1,d2){
	 var dt1=new Date(d1);
	 var dt2=new Date(d2);
	 return dt1.getTime()>dt2.getTime();
 }

 function setEndDate(date){
	 $('#endDate').val(date);
 }
 function StartDateAllowSunDay(id){
	 var x=true;
	 var dt=$('#'+id).val();
	 var d = new Date(dt);
	 if(d.getDay()!=0){	 
		 alert("Please select end date sunday only");
		 $('#'+id).val("");
		 x=false;
	 }	
	 return x;
 }
 
function setLastWeekDates(){
	
	var d = new Date();
	// set to Monday of this week
	d.setDate(d.getDate() - (d.getDay() + 6) % 7);
	// set to previous Monday
	d.setDate(d.getDate() - 7);

	// create new date of day before
	var lastMonday = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
	//var day=d.getDate()+6;
	d.setDate(d.getDate()+6);
	var lastSunday = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
	
	$('#startDate').val(lastMonday);
	$('#endDate').val(lastSunday);
	
}

function verifyChangePassword(){
	var pwd1=$('#newPassword').val();
	var pwd2=$('#confirmPassword').val();
	if(pwd1==pwd2)
		$( "#form" ).submit();
	else
		alert("New password and Confirm password are not matched");			
}

function infoDisplayAlert(message) {
    showAlertBox();
    $('#alert_display_box').html('<div  class="alert alert-info alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> <i class="fa fa-info-circle"></i> ' + message + '</div>');
    $('html,body').scrollTop(0);
}
function warningDisplayAlert(message) {
    showAlertBox();
    $('#alert_display_box').html('<div  class="alert alert-warning alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> <i class="fa fa-warning"></i> ' + message + '</div>');
    $('html,body').scrollTop(0);  
}
function successDisplayAlert(message) {
    showAlertBox();
    $('#alert_display_box').html('<div  class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> <i class="fa fa-thumbs-up"></i> ' + message + '</div>');
    $('html,body').scrollTop(0);    
}
function dangerDisplayAlert(message) {
    showAlertBox();
    $('#alert_display_box').html('<div  class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> <i class="fa fa-ban"></i> ' + message + '</div>');
    $('html,body').scrollTop(0);
}

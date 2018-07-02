<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header" style="color:blue"><b>Weekly Efforts Report</b></h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5><b>Weekly Efforts Report</b></h5>
					</div>

					<div class="panel-body">
						<form:form class="form-inline" method="post"
							action="${pageContext.request.contextPath}/Reports/getAllEmpWeeklyTimeSheetDetails">
							<div class="form-group">
								<label for="startDate">Start Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="startdate" required="required"
										name="startdate" placeholder="Start Date" autocomplete="off">
								</div>
							</div>
							<div class="form-group">
								<label for="endDate">End Date </label>
								<div class="input-group">
									<input type="text" class="form-control" id="enddate" required="required"
										name="enddate" placeholder="End Date" autocomplete="off">
								</div>
							</div>

							<div class="input-group">
								<button type="submit" value="Fetch" class="form-control btn btn-primary" onclick="return dateValidation()">Fetch</button>

							</div>
							
							<div class="panel-heading">
						<h6><b>*** Consider Week as Five working Days(Monday-Friday) ***</b></h6>
					</div>
							

						</form:form>
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

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});

	function empsTotalBenchPeriod(){
		var startDate=document.getElementById('startdate').value;
		var endDate=document.getElementById('enddate').value;
		
		window.location.href="<%=request.getContextPath()%>/Reports/getTimeSheetDetails";
	};
</script>


<script type="text/javascript">

function DisableSaturday(date) {
	 
	  var day = date.getDay();
	 // If day == 1 then it is MOnday
	 if (day == 6) {
	 
	 return [false] ; 
	 
	 } else { 
	 
	 return [true] ;
	 }
	  
	}

$(function() {
	$('#startdate').datepicker({
		showOtherMonths : true,
		selectOtherMonths : true,
	//	beforeShowDay : DisableSaturday,
		beforeShowDay : $.datepicker.noWeekends,
		maxDate: new Date(),
           onSelect: function (date) {
               var date2 = $('#startdate').datepicker('getDate');
               $('#enddate').datepicker('option', 'minDate', date2);
               $('#enddate').datepicker('option', 'maxDate', new Date());
           }
	});

	$('#enddate').datepicker({
		showOtherMonths : true,
		selectOtherMonths : true,
		beforeShowDay : $.datepicker.noWeekends,
		maxDate: new Date(),
           minDate: $('#startdate').datepicker('getDate'),
           onClose: function () {
               var dt1 = $('#startdate').datepicker('getDate');
               var dt2 = $('#enddate').datepicker('getDate');
               //check to prevent a user from entering a date below date of dt1
               if (dt2 < dt1) {
                   $('#enddate').datepicker('setDate', $('#startdate').datepicker('option', 'minDate'));
               }
           }
	
	});
	
});

	function dateValidation() {
		 var strtDate;
		 var endDate;
		 strtDate=new Date(document.getElementById("startdate").value);
		 endDate=new Date(document.getElementById('enddate').value);
		 var timeDiff = Math.abs(strtDate.getTime() - endDate.getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24))+1;
			var weekDay=strtDate.getDay();
			if(diffDays!=5 || (weekDay!=1 && weekDay!=0)){
				alert("Please select proper week");
				return false;
			}
			else
				return true;;
	};
</script>
<jsp:include page="footer.jsp" />

var userCountryId;
var userStateId;
var userCityId;
var country_id;
function getState(contryId){
	//alert(contryId);
		$.ajax({
	        url: '<%=application.getContextPath()%>/Employee/getStatesList',
	        dataType: "json",
	        type: "GET",
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        data : {"country_Id" : contryId},
	        success: function(response){
	        	//alert("success")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	}
	        },
	        error: function(response) { 
	        	//alert("fail")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	//alert(response.responseText);
	        	}
	        }
	    });
		
	}
function getCity(stateId){
  // alert(stateId);
	$.ajax({
        url: '<%=application.getContextPath()%>/Employee/getCitiesList',
			dataType : "json",
			type : "GET",
			contentType : 'application/json',
			mimeType : 'application/json',
			data : {
				"state_Id" : stateId
			},
			success : function(response) {
				if(response.responseText!=null){
				$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if(response.responseText!=null){
				$("#cityid").append(response.responseText);
				//alert(response.responseText);
				}
			}
		});

	}
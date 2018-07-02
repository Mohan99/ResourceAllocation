var userCountryId;
var userStateId;
var userCityId;
function getCountry(){
	$.ajax({ url: "<%=application.getContextPath()%>/CountryAction.ajax",
        context: document.body,
		success : function(data) {
				var country = document
					.getElementById("countryid");
			$(country).empty();
			$(country).append("<option vale=''>Choose Country</option>");
			$
					.each(
							data,
							function(
									index) {
								
								if(userCountryId==data[index].countryId){
									$(
											country)
											.append(
													"<option selected value=" + data[index].countryId + ">"
															+ data[index].country
															+ "</option>");
										
								}else{
									$(
										country)
										.append(
												"<option  value=" + data[index].countryId + ">"
														+ data[index].country
														+ "</option>");
								}
									
							});
			}
});
}

function getState(cid){
	var state = document
	.getElementById("st");

	$.ajax({
		type : "POST",
		url : "<%=application.getContextPath()%>/StateAction.ajax",
												data : {
													"cId" : cid
												},
												success : function(data) {
					
													$(state).append("<option vale=''>Choose State</option>");
													
													$
															.each(
																	data,
																	function(
																			index) {
																		if(userStateId==data[index].stateID){
																			$(
																				state)
																				.append(
																						"<option selected value=" + data[index].stateID + ">"
																								+ data[index].state
																								+ "</option>");
																		}else{
																			$(
																					state)
																					.append(
																							"<option  value=" + data[index].stateID + ">"
																									+ data[index].state
																									+ "</option>");
																		}
																		});
												}
											});
	
	
}

function getCity(stId){
/* alert(stId);
 */	var city = document
	.getElementById("cityId");
	
	$.ajax({
		type : "POST",
		url : "<%=application.getContextPath()%>/CityAction.ajax",
												data : {
													"sId" : stId
												},
												success : function(data) {
													
													$(city).append("<option vale=''>Choose city</option>");
													
													$
													
													.each(
																	data,
																	function(
																			index) {
																		
																		if(data[index].cityId==userCityId){
																			$(city)
																			.append(
																					"<option selected  value=" + data[index].cityId + ">"
																							+ data[index].City
																					+ "</option>");
																		
																		}else{
																		$(city)
																				.append(
																						"<option  value=" + data[index].cityId + ">"
																								+ data[index].City
																						+ "</option>");
																		}
																		});
												}
											});

}

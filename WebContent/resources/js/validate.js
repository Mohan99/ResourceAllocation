function validateform() {
		var username = document.myform.userName.value;
		var loginname = document.myform.loginName.value;
		var password = document.myform.loginPassword.value;
		var x=document.myform.email.value;
		var atposition=x.indexOf("@");  
		var dotposition=x.lastIndexOf(".");  
		//var email = document.myform.email.value;
		// var mail=email.indexOf("@");
		//var dot=email.lastIndexOf(".");
		var letters = /[a-zA-Z ]$/; 
		/*var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
 */
		if (username == null || username == "" ) {
			alert("Error : Username can't be blank");
			document.getElementById("username").innerHTML="Username is required";
			return false;
		}else if(loginname== null || loginname==""){
			alert("Login Name can't be blank");
			document.getElementById("loginname").innerHTML="Loginname is required";
			return false;
		}else if (password==null || password==""){
			alert("Password can't be blank");
			document.getElementById("loginpassword").innerHTML="Password is required";
			return false;	
		}else if (password.length < 4) {
			alert("Password must be at least 4 characters long.");
			document.getElementById("loginpassword").innerHTML="LoginPassword must contain minimum 4 letters";
			return false;
		}else if(x==null || x==""){
			alert("EmailId can't be blank");
			document.getElementById("emailloc").innerHTML="Email Id is required";
			return false;
		}
		else if (atposition<1 || dotposition<atposition+2 || dotposition+2>x.length){  
			document.getElementById("emailloc").innerHTML=  
				" Please enter a valid E-mail address";  
				  return false;  
	 
		}
		/*else if(email== null || email==""){
			//alert("Email can't be blank");
			document.getElementById('email').innerHTML="*EmailID is required";
			return false;
		} else if(mail<1 || dot<mail+2 || dot+2>email.length){
			 alert("Please enter a valid e-mail address");
			 document.getElementById('email').innerHTML="*InValid Email Address";
			return false;
		}*/
 		/*else if(username.value.match(letters)){
				alert("Username should contain alphabet characters only");
				document.getElementById('username').innerHTML="*Username must contain only alphabets";
				return false; 
		}else if(!isNaN(username)){
			alert("Please enter only alphabets");
			return false;
		} 
		else if(username.value.match(letters)){
			alert("Username should contain alphabet characters only");
			document.getElementById('username').innerHTML="*Username must contain only alphabets";
			return false; 
		}*/
		/* var letters = /^[0-9a-zA-Z]+$/; 
		if(username.value.match(letters)) 
		{ 
		alert('Your registration is correct '); 
		document.myform.userName.focus(); 
		return true; 
		} 
		else 
		{ 
		alert('Please input alphanumeric characters only'); 
		return false; 
		}  */

		
		/* else
			alert("please inputs a-z");
		    return false; */
		    
		/* else {
			alert("You have entered invalid email address....!!");
			email.focus();
			return false;
		} */
		/* else if(mail<2 || dot<mail+2 || dot+2 < email.length){
			alert("Not a valid email address");
			return false; */
		//}
	
		}
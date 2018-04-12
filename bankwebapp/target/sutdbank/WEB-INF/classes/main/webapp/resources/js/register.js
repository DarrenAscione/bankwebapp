
	$(function(){
	    // validate mandatory
	    $('#registerBtn').click(function(){
	    	var fullName = $('#fullName').val().trim(),
	    		fin = $('#fin').val().trim(),
	    		dateOfBirth = $('#dateOfBirth').val().trim(),
	    		occupation = $('#occupation').val().trim(),
	    		address = $('#address').val().trim(),
	    		email = $('#email').val().trim(),
	    		username = $('#username').val().trim(),
	    		password = $('#password').val().trim(),
	    		hasError = false;
	    if (!fullName) {
	    		$('#input-group-fullName').addClass('has-error');
	    		hasError = true;
	    	} else {
	    		$('#input-group-fullName').removeClass('has-error');
	    	}
	    if (!fin) {
			$('#input-group-fin').addClass('has-error');
			hasError = true;
		} else {
			$('#input-group-fin').removeClass('has-error');
		}
	    if (!dateOfBirth) {
			$('#input-group-dateOfBirth').addClass('has-error');
			hasError = true;
		} else {
			$('#input-group-dateOfBirth').removeClass('has-error');
		}
	    if (!occupation) {
			$('#input-group-occupation').addClass('has-error');
			hasError = true;
		} else {
			$('#input-group-occupation').removeClass('has-error');
		}
	    if (!address) {
			$('#input-group-address').addClass('has-error');
			hasError = true;
		} else {
			$('#input-group-address').removeClass('has-error');
		}
	    if (!email || !validateEmail(email)) {
			$('#input-group-email').addClass('has-error');
			hasError = true;
		} else {
			$('#input-group-email').removeClass('has-error');
		}
	    	if (!username) {
	    		$('#input-group-username').addClass('has-error');
	    		hasError = true;
	    	} else {
	    		$('#input-group-username').removeClass('has-error');
	    	}
	    	if (!password) {
	    		$('#input-group-password').addClass('has-error');
	    		hasError = true;
	    	} else {
	    		$('#input-group-password').removeClass('has-error');
	    	}
	    	if (hasError) {
	    		$('#messageBox').html('<p class="text-danger">Validation error!</p>');
	    		$('#messageBox').removeClass('hidden');
	    		return false;
	    	} else {
	    		$('#messageBox').addClass('hidden');
	    		$('#registrationForm').submit();    		
	    	}
	    });
	});

function validateEmail(email) {
	  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
}
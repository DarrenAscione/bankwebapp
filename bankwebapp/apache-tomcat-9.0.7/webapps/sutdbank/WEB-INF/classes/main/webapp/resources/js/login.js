$(function(){
    // Handle login
    $('#loginButton').click(function(){
    	var username = $('#username').val().trim(),
    		password = $('#password').val().trim(),
    		hasError = false;
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
    		$('#messageBox').html('<p class="text-danger">Both username and password fields are mandatory.</p>');
    		$('#messageBox').removeClass('hidden');
    		return false;
    	} else {
    		$('#messageBox').addClass('hidden');
    		$('#loginForm').submit();    		
    	}
    });
});

function validateUsername(username) {
    var re = /^[a-z0-9]+$/i;
    return re.test(username);
}
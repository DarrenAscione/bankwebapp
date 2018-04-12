
$(function(){
    // validate mandatory
    $('#createTransBtn').click(function(){
        var amount = $('#amount').val().trim(),
            toAccountNum = $('#toAccountNum').val().trim(),
            transcode = $('#transcode').val().trim(),
            hasError = false;
    if (!amount || !validateAmount(amount)) {
        $('#input-group-amount').addClass('has-error');
        hasError = true;
    } else {
        $('#input-group-amount').removeClass('has-error');
    }

    if (!toAccountNum) {
        $('#input-group-toAccount').addClass('has-error');
        hasError = true;
    } else {
        $('#input-group-toAccount').removeClass('has-error');

    }
    if (!transcode)  {
        $('#input-group-transcode').addClass('has-error');
        hasError = true;
    } else {
        $('#input-group-transcode').removeClass('has-error');
    }
        if (hasError) {
            $('#messageBox').html('<p class="text-danger">Validation error! All Transaction Amount must be greater or equal to $10.00</p>');
            $('#messageBox').removeClass('hidden');
            return false;
        } else {
            $('#messageBox').addClass('hidden');
            $('#registrationForm').submit();
        }
    });
});

function validateAmount(amount) {
    return amount >= 10;
}
// workaround
// run this staff after page loaded!
function _validatorETH0OnLoad() {
    $('#frmNetEth').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        }
    });
}

function _isvalidETH0() {
    $('#frmNetEth').bootstrapValidator('validate');
    return $('#frmNetEth').data('bootstrapValidator').isValid();
}
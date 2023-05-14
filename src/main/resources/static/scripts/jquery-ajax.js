$(function() {
	$("#userid").keyup(validateUserId);
	disableSubmitBtn();
});

function validateUserId() {
	var url = "validate?id=" + $("#userid").val();
	$.ajax({
		url: url, success: processRequest, cache: false
	});
}

function processRequest(text) { 
	var xmlDoc = $.parseXML(text),
	    valid = $(xmlDoc).find("valid");
	if (valid == "false") {
		$("#userIdMessage").html("<div style=\"color:red\">Invalid User Id</div>");
		$("#submit_btn").prop("disabled", true);
	} else {
		$("#userIdMessage").html("<div style=\"color:green\">Valid User Id</div>");
		$("#submit_btn").prop("disabled", false);
	}
}

function disableSubmitBtn() {
	$("#submit_btn").prop("disabled", true);
}


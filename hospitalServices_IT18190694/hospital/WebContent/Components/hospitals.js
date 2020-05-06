$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "HospitalsAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onHospitalSaveComplete(response.responseText, status);
		}
	});
});

function onHospitalSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidIDSave").val("");
	$("#formHospital")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidIDSave").val(
					$(this).closest("tr").find('#hidIDUpdate').val());
			$("#hostID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#teleNo").val($(this).closest("tr").find('td:eq(1)').text());
			$("#hostName").val($(this).closest("tr").find('td:eq(2)').text());
			$("#location").val($(this).closest("tr").find('td:eq(3)').text());
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "HospitalsAPI",
		type : "DELETE",
		data : "ID=" + $(this).data("id"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});
function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// HostID=========================================================================
function validateHospitalForm() {
	// CODE
	if ($("#hostID").val().trim() == "") {
		return "Insert hostID.";
	}
	// TeleNo
	if ($("#teleNo").val().trim() == "") {
		return "Insert teleNo.";
	}
	// hostName-------------------------------
	if ($("#hostName").val().trim() == "") {
		return "Insert hostName.";
	}

	// location------------------------
	if ($("#location").val().trim() == "") {
		return "Insert location.";
	}
	return true;

}

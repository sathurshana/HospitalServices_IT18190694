<%@page import="model.Hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<head>
<meta charset="ISO-8859-1">
<title>Hospital Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/hospitals.js"></script>
</head>
<body background= 2.gif>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Hospital Management</h1>
				<form id="formHospital" name="formHospital">
					hostID: <input id="hostID" name="hostID" type="text"
						class="form-control form-control-sm"> <br>
					 teleNo: <input id="teleNo" name="teleNo" type="text"
						class="form-control form-control-sm"> <br>
				    hostName: <input id="hostName" name="hostName" type="text"
						class="form-control form-control-sm"> <br>
				     location: <input id="location" name="location" type="text"
						class="form-control form-control-sm"> <br>
					 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary">
					<input type="hidden"id="hidIDSave" name="hidIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
                <div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divHospitalsGrid">
					<%
						Hospital hostObj = new Hospital();
						out.print(hostObj.readHospitals());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
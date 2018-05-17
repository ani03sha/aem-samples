<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="cq.jquery" />
<html>
<head>
<meta charset="UTF-8">
<title>AEM Samples Test</title>
<style>
#signup .indent label.error {
	margin-left: 0;
}

#signup label.error {
	font-size: 0.8em;
	color: #F00;
	font-weight: bold;
	display: block;
	margin-left: 215px;
}

#signup  input.error, #signup select.error {
	background: #FFA9B8;
	border: 1px solid red;
}
</style>
<script>
	//Creates a GUID value using JavaScript - used for the unique value for the generated claim     
	function createUUID() {

		var s = [];
		var hexDigits = "0123456789abcdef";
		for (var i = 0; i < 36; i++) {
			s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		}
		s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
		s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
		s[8] = s[13] = s[18] = s[23] = "-";

		var uuid = s.join("");
		return uuid;
	}

	$(document).ready(
			function() {

				$('body').hide().fadeIn(5000);

				$('#submit').click(
						function() {
							var failure = function(err) {
								alert("Unable to retrive data " + err);
							};

							//Get the user-defined values
							var myFirst = $('#FirstName').val();
							var myLast = $('#LastName').val();
							var date = $('#DateId').val();
							var cat = $('#Cat_Id').val();
							var state = $('#State_Id').val();
							var details = $('#Explain').val();
							var city = $('#City').val();
							var address = $('#Address').val();
							var claimId = createUUID();

							//Use JQuery AJAX request to post data to a Sling Servlet
							$.ajax({
								type : 'POST',
								url : '/bin/aemsamples/handleclaim',
								data : 'id=' + claimId + '&firstName='
										+ myFirst + '&lastName=' + myLast
										+ '&address=' + address + '&cat=' + cat
										+ '&state=' + state + '&details='
										+ details + '&date=' + date + '&city='
										+ city,
								success : function(msg) {

									var json = jQuery.parseJSON(msg);
									var msgId = json.id;
									var lastName = json.lastname;
									var firstName = json.firstname;

									$('#ClaimNum').val(msgId);
									$('#json').val(
											"Filed by " + firstName + " "
													+ lastName);
								}
							});
						});

			}); // end ready
</script>
</head>

<title>Adobe CQ Sling Mobile Page</title>

<body>


	<h1>Adobe CQ Mobile Claim Sling Form</h1>

	</div>

	<form method="#">

		<table border="1" align="left">

			<tr>
				<td><label for="ClaimNum" id="ClaimNumLabel">A. Claim
						Number</label></td>
				<td><input id="ClaimNum" name="A1. Claim Number" readonly=true
					type="text" value=""></td>
			</tr>
			<tr>
				<td><label for="DateId" id="DateIncident">A.2. Date of
						Incident</label></td>
				<td><input id="DateId" name="A.2 Date of Incident" type="text"
					value=""></td>
			</tr>

			<tr>
				<td><label for="FirstName" id="FirstNameLabel">B2.
						First Name</label></td>
				<td><input id="FirstName" name="B1. First Name    " type="text"
					value=""></td>
			</tr>

			<tr>
				<td><label for="LastName" id="LastNameLabel"
					name="LastNameeLabel">C1. Last Name </label></td>
				<td><input id="LastName" name="C1. Last Name     " type="text"
					value=""></td>
			</tr>

			<tr>
				<td><label for="Cat_Id">D1. Category </label></td>
				<td><select id="Cat_Id" name="Category ">
						<option value="Home">Home Claim</option>
						<option value="Auto">Auto Claim</option>
						<option value="Boat">Boat Claim</option>
						<option value="Personal">Personnal Claim</option>
				</select></td>
			</tr>

			<tr>
				<td><label for="Address" id="AddressLabel" name="AddressLabel">E1.
						Address </label></td>
				<td><input id="Address" name="Address   " type="text" value="">
				</td>
			</tr>

			<tr>
				<td><label for="City" id="CityLabel" name="CityLabel">F1.
						City </label></td>
				<td><input id="City" name="City   " type="text" value="">
				</td>
			</tr>

			<tr>
				<td><label for="Explain" id="ExplainLabel" name="ExplainLabel">G1.
						Additional Details </label></td>
				<td><input id="Explain" name="Explain   " type="text" value="">
				</td>
			</tr>

			<tr>
				<td><label for="State_Id">H1. State </label></td>
				<td><select id="State_Id" name="State ">
						<option value="Alabama">Alabama</option>
						<option value="Alaska">Alaska</option>
						<option value="Arizona">Arizona</option>
						<option value="Arkansas">Arkansas</option>
						<option value="California">California</option>
						<option value="Colorado">Colorado</option>
						<option value="Connecticut">Connecticut</option>
						<option value="Delaware">Delaware</option>
						<option value="District of Columbia">District of Columbia</option>
						<option value="Florida">Florida</option>
						<option value="Georgia">Georgia</option>
						<option value="Hawaii">Hawaii</option>
						<option value="Idaho">Idaho</option>
						<option value="Illinois">Illinois</option>
						<option value="Indiana">Indiana</option>
						<option value="Iowa">Iowa</option>
						<option value="Kansas">Kansas</option>
						<option value="Kentucky">Kentucky</option>
						<option value="Louisiana">Louisiana</option>
						<option value="Maine">Maine</option>
						<option value="Maryland">Maryland</option>
						<option value="Massachusetts">Massachusetts</option>
						<option value="Michigan">Michigan</option>
						<option value="Minnesota">Minnesota</option>
						<option value="Mississippi">Mississippi</option>
						<option value="Missouri">Missouri</option>
						<option value="Montana">Montana</option>
						<option value="Nebraska">Nebraska</option>
						<option value="Nevada">Nevada</option>
						<option value="New Hampshire">New Hampshire</option>
						<option value="New Jersey">New Jersey</option>
						<option value="New Mexico">New Mexico</option>
						<option value="New York">New York</option>
						<option value="North Carolina">North Carolina</option>
						<option value="North Dakota">North Dakota</option>
						<option value="Ohio">Ohio</option>
						<option value="Oklahoma">Oklahoma</option>
						<option value="Oregon">Oregon</option>
						<option value="Pennsylvania">Pennsylvania</option>
						<option value="Rhode Island">Rhode Island</option>
						<option value="South Carolina">South Carolina</option>
						<option value="South Dakota">South Dakota</option>
						<option value="Tennessee">Tennessee</option>
						<option value="Texas">Texas</option>
						<option value="Utah">Utah</option>
						<option value="Vermont">Vermont</option>
						<option value="Virginia">Virginia</option>
						<option value="Washington">Washington</option>
						<option value="West Virginia">West Virginia</option>
						<option value="Wisconsin">Wisconsin</option>
						<option value="Wyoming">Wyoming</option>
				</select></td>
			</tr>

			<tr>
				<td></td>

				<td><textarea id="json" rows="4" cols="50">
</textarea></td>

			</tr>

			<tr>
				<td></td>
				<td><input type="button" value="Submit" name="submit"
					id="submit" value="Submit"></td>

			</tr>

		</table>

	</form>




</body>

</html>
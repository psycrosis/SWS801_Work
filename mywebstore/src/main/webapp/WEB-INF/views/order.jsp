<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Welcome</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1> ${greeting} hhhhhhhhhhhhhhhhhhhhhhhhhh ${theProduct}</h1>
				<p> ${tagline} </p>
			</div>
		</div>
	</section>
	
	<section>
		<div class="display">
			<div>
				<div style="width: 128px; height: 128px; background-color: Black; float: left"></div>
				<p>You are purchasing: ???</p>
			</div>
		</div>
		
		<div>
			<form action="/mywebstore/order/${theProduct}/1">
				<div>
					<p>How many are you purchasing?</p><input id="quantity" type="number" value=1 />
				</div>
				<div>
					<input type="submit" value="Complete Order" />
				</div>
			</form>
		</div>
	</section>
</body>
</html>

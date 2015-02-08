<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">




<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>




<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Product </title>

</head>



<body>
<section>
<div class="jumbotron">
<div class="container">
<h1>products!</h1>
<p>add Products<p/>

</div>
</div>
</section>

<section class= "container">
	<form:form modelAttribute="newProduct" class="form-horizontal">
	<fieldset>
	<legend>Add New PRODUCT</legend>
	
<!-- 	Pre Externiliaxation -->
<!-- 	<div class="form-group"> -->
<!-- 		<label class="control-label col-lg-2 col-lg-2" for="productId">Product ID</label> -->
<!-- 		<div class="col-lg-10"> -->
<%-- 			<form:input id="productId" path="productId" type="text" class="forms:input:large"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->


<!-- externilized -->

<div class="form-group">
 		<label class="control-label col-lg-2 col-lg-2" for="productId">
			<spring:message code="add.Product.form.productId.label" ></spring:message>
		</label> 
 		 		<div class="col-lg-10"> 
 			<form:input id="productId" path="productId" type="text" class="forms:input:large"/> 
 			</div> 
 		</div> 
	
	
	
	
	
	
	
	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="name">name</label>
		<div class="col-lg-10">
			<form:input id="name" path="name" type="text" class="forms:input:large"/>
			</div>
		</div>
	
	
	
	
	</fieldset>
</form:form>

</section>


</body>
</html>
<jsp:include page="header.jsp" />
<!-- essentially this .jsp file is just a webpage  -->
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!DOCTYPE html>
<head>
<meta charset="ISO-8859-1">
<title>PubHub Tag Management Page</title>
</head>
<header>

<div class="container">
	
	<c:choose>
	<c:when test="${not empty message }">
	  <p class="alert ${messageClass}">${message }</p>
	<%
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null);
	%>
	</c:when>
	</c:choose>
	
	<h1>PUBHUB <small>Manage Tags</small></h1>
	<hr class="book-primary">
	
	</br>
	<form action="ManageTags" method="post" class="form-horizontal">
	<div class="form-group">
		<h3 class="">Add New Tag:</h3>
		<label for="tagAddName" class="col-sm-4 control-label">Tag Name: </label>
		<div class="col-sm-5">
			<input type="text" name="tagAddName" class="form-control" placeholder="Tag Name"></input>
		</div>
	</br>
		<div class="col-sm-offset-4 col-sm-1">
			<input type="submit" value="Add Tag" name="addTagSubmit" class="btn btn-info">
		</div>
	</div>
	</form>
	
	</br>
	
	<form action="ManageTags" method="post" class="form-horizontal">
	<div class="form-group">
		<h3 class="">Remove Tag:</h3>
		<label class="col-sm-4 control-label">All Tags:</label>
		<div class="col-sm-5">
			<select class="form-control" name="tagNames" >
				<option selected="selected" value="null"> -- No Tag Selected -- </option>
				<c:forEach items="${tagNames}" var="tag">
        			<option value="${tag.tagName}">${tag.tagName}</option> <!-- makes the tag what is selected -->
    			</c:forEach>
			</select>
		</div>
	</br>
		<div class="col-sm-offset-4 col-sm-1">
			<input type="submit" value="Remove Tag" name="removeTagSubmit" class="btn btn-info">
		</div>
	</div>
	</form>
	
	</br>
	
	<form action="ManageTags" method="post" class="form-horizontal">
	<div class="form-group">
		<h3 class="">Apply Tag to Book:</h3>
		<label for="tagNames" class="col-sm-4 control-label">Tag: </label>
		<div class="col-sm-5">
			<select class="form-control" name="tagNames" >
				<option selected="selected"> -- No Tag Selected -- </option>
				<c:forEach items="${tagNames}" var="tag">
        			<option value="${tag.tagName}">${tag.tagName}</option> <!-- makes the tag what is selected -->
    			</c:forEach>
			</select>
		</div>
	</br>
		<label for="bookISBN" class="col-sm-4 control-label">Book ISBN 13#: </label>
		<div class="col-sm-5">
			<input type="text" name="bookISBN" class="form-control" placeholder="ISBN 13"></input>
		</div>
	</br>
		<div class="col-sm-offset-4 col-sm-1">
			<input type="submit" value="Apply Relationship" name="applyTagSubmit" class="btn btn-info">
		</div>
	</div>
	</form>
	
	</br>
	
	<form action="ManageTags" method="post" class="form-horizontal">
	<div class="form-group">
		<h3 class="">Remove Tag from Book:</h3>
		<label for="tagName" class="col-sm-4 control-label">Tag: </label>
		<div class="col-sm-5">
			<select class="form-control" name="tagNames" >
				<option selected="selected"> -- No Tag Selected -- </option>
				<c:forEach items="${tagNames}" var="tag">
        			<option value="${tag.tagName}">${tag.tagName}</option> <!-- makes the tag what is selected -->
    			</c:forEach>
			</select>
		</div>
	</br>
		<label for="bookISBN" class="col-sm-4 control-label">Book ISBN 13#: </label>
		<div class="col-sm-5">
			<input type="text" name="bookISBN" class="form-control" placeholder="ISBN 13"></input>
		</div>
	</br>
		<div class="col-sm-offset-4 col-sm-1">
			<input type="submit" value="Remove Relationship" name="removeFromBookSubmit" class="btn btn-info">
		</div>
	</div>
	</form>
	
	</div>
</header>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />
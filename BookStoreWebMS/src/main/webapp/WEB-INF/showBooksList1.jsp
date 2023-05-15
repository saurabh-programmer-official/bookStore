<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html><head>
<title>JlcBookStore</title>
<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<link href="mycss/bookstore.css" rel="stylesheet">
</head>
<body>
<div class="card">
<c:import url="myheader.jsp"/>
</div>
<div class="container">
<form action="/showSelectedBooks">
<table class="table table-striped table-bordered table-light myfont">
<tr>
<td>
<select name="author" class="form-control mytext">
<c:forEach var="authorName" items="${MyAuthorList}">
<option value="${authorName}">
<c:out value="${authorName }"></c:out>
</c:forEach>
</select>
</td>

<td>
<select name="category" class="form-control mytext">
<c:forEach var="categoryName" items="${MyCategoryList}">
<option value="${categoryName}">
<c:out value="${categoryName}"></c:out>
</c:forEach>
</select>
</td>
<td>
<input type="submit" class="btn btn-primary" value="Show Books">
</td>

</tr>
</table >
</form>
</div>
<div class="container">
<table class="table table-hover table-striped table-bordered table-light myfont">
<thead class="bg-info">
<tr>
<th>BookId</th>
<th>BookName</th>
<th>Publication</th>
<th>Author</th>
<th>Category</th>
<th><a href="/showMyCart" class="btn btn-danger">Show My Cart</a></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="MyBook" items="${MyBooksList}">
<tr>
<td>${MyBook.bookId}</td>
<td>${MyBook.bookName}</td>
<td>${MyBook.publication }</td>
<td>${MyBook.author }</td>
<td>${MyBook.category }</td>
<td><form action="addToCart">
<input type="submit" class="btn btn-success" value="Add To Cart">
<input type="hidden" name="bookId" value="${MyBook.bookId}"/>
</form>
<td>
<form action="showBookInfo">
<input type="submit" class="btn btn-primary" value="View More Information">
<input type="hidden" name="bookId" value="${MyBook.bookId}">
</form>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</body>
</html>

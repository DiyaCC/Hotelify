<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Rentals</title>
</head>
<body>
<h2>Search Rentals by Checkout Date</h2>
<form action="RentalsByDateServlet" method="get">
    <input type="hidden" name="employee_id" value="<%= request.getParameter("employee_id") %>" />
    <input type="hidden" name="hotel_id" value="<%= request.getParameter("hotel_id") %>" />
    <label for="checkout">Checkout Date:</label>
    <input type="date" name="checkout" id="checkout" />
    <button type="submit">Search</button>
</form>

<div id="results">
    <%-- Servlet will output rentals here --%>
</div>
</body>
</html>

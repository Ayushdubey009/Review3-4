<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
    <h3>User Profile</h3>
    <p>Username: ${username}</p>
    <p>Email: ${email}</p>
    <c:if test="${not empty username}">
        <p>Welcome, ${username}!</p>
    </c:if>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Medical Appointment</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/' />">Online Medical Appointment</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/' />">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/services' />">Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/appointment' />">Appointments</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/contact' />">Contact</a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value='/logout' />">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value='/login' />">Login</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

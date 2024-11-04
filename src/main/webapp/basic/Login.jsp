<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Login</title>
    <style>
        .container {
            width: 100%;
            max-width: 400px;
            padding: 20px;
        }
        .login-container {
        
        	font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 70vh;
            margin: 0;
            
        	
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555555;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .login-btn {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .login-btn:hover {
            background-color: #45a049;
        }
        .extra-links {
            text-align: center;
            margin-top: 15px;
        }
        .extra-links a {
            color: #4CAF50;
            text-decoration: none;
            margin: 0 10px;
            font-size: 14px;
        }
        .extra-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <%@ include file="home.jsp" %> <!-- Including the header from home.jsp -->
    
    <div class="container">
        <h2>Patient Login</h2>
        <div class="login-container">
            <form action="${pageContext.request.contextPath}/patientLogin" method="post">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="login-btn">Login</button>
                
                <div class="extra-links">
                    <a href="<c:url value='/register' />">Forgot Password?</a>
                    <a href="<c:url value='/register' />">Register</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

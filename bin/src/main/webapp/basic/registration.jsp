<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Registration</title>
    <style>
        body {
            
        }
        .container {
            width: 100%;
            max-width: 400px;
            padding: 20px;
        }
        .registration-container {
        
        	display: flex;
            justify-content: center;
            align-items: center;
            height: 70vh;
            margin: 0;
            background-color: #f0f2f5;
            font-family: Arial, sans-serif;
        	
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
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .register-btn {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .register-btn:hover {
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
        <h2>Register</h2>
        <div class="registration-container">
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="name">Full Name:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="register-btn">Register</button>
                
                <div class="extra-links">
                    <a href="/login">Already have an account? Login</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

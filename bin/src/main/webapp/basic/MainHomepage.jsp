<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Medical Booking</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-image: url("https://thumbs.dreamstime.com/b/doctor-stethoscope-hand-hospital-background-gown-94227568.jpg"); /* Replace with an actual URL */
            background-size: cover;
            background-position: center;
            color: #ffffff;
            display: flex;
            flex-direction: column;
            height: 100vh;
            overflow: hidden;
        }
        
        header {
            background: rgba(0, 0, 0, 0.7);
            padding: 20px;
            text-align: center;
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
        }

        h1 {
            margin: 0;
            font-size: 2.8em;
            letter-spacing: 1px;
        }

        nav {
            margin-top: 10px;
        }

        nav a {
            margin: 0 10px;
            text-decoration: none;
            color: #ffffff;
            font-weight: bold;
            padding: 8px 18px;
            border: 2px solid #ffffff;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
            text-transform: uppercase;
            font-size: 0.9em;
        }

        nav a:hover {
            background-color: rgba(255, 255, 255, 0.8);
            color: #333;
        }

        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            flex-grow: 1;
            padding-top: 100px;
            text-align: center;
            backdrop-filter: blur(5px);
        }

        .content h2 {
            font-size: 2em;
            margin-bottom: 0.5em;
        }

        .content p {
            font-size: 1.2em;
            margin-bottom: 1em;
            line-height: 1.5;
            max-width: 600px;
        }

        .btn-primary {
            padding: 15px 30px;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            color: #ffffff;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s;
            text-transform: uppercase;
            text-decoration: none;
            font-weight: bold;
        }

        .btn-primary:hover {
            background-color: #45a049;
        }

        footer {
            background: rgba(0, 0, 0, 0.7);
            padding: 15px 0;
            text-align: center;
            color: #ffffff;
            font-size: 0.9em;
            position: fixed;
            width: 100%;
            bottom: 0;
        }

        /* Responsive */
        @media (max-width: 768px) {
            h1 {
                font-size: 2em;
            }

            nav a {
                font-size: 0.8em;
                padding: 6px 12px;
            }

            .content h2 {
                font-size: 1.5em;
            }

            .btn-primary {
                padding: 12px 25px;
                font-size: 1em;
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to Online Medical Booking</h1>
        <nav>
            <a href="<c:url value='/services' />">Services</a>
            <a href="<c:url value='/login' />">Patient Login</a>
            <a href="<c:url value='/login' />">Doctor Login</a>
            <a href="<c:url value='/register' />">Register</a>
            <a href="<c:url value='/about' />">About Us</a>
        </nav>
    </header>
    <div class="content">
        <h2>Your Health, Our Priority</h2>
        <p>Experience a streamlined way to book appointments with our user-friendly online medical booking system.</p>
        <a class="btn-primary" href="<c:url value='/appointment' />">Book an Appointment</a>
    </div>
    <footer>
        &copy; 2024 Online Medical Booking. All Rights Reserved.
    </footer>
</body>
</html>

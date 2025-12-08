<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Online Medical Booking</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
        }
        header {
            background: #4CAF50;
            color: white;
            padding: 20px;
            text-align: center;
        }
        h1 {
            margin: 0;
        }
        .content {
            max-width: 800px;
            margin: 30px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .content h2 {
            color: #4CAF50;
        }
        .content p {
            line-height: 1.6;
        }
    </style>
</head>
<body>
	<%@include file="home.jsp" %>
    <header>
        <h1>About Online Medical Booking</h1>
    </header>
    <div class="content">
        <h2>Our Mission</h2>
        <p>Our mission is to provide a convenient and accessible platform for patients to book appointments with their healthcare providers, ensuring a seamless and efficient healthcare experience.</p>

        <h2>Who We Are</h2>
        <p>We are a team of passionate healthcare and technology professionals who are committed to making healthcare more accessible through online booking services. Our platform is designed with patients' needs in mind, helping you manage appointments, view medical history, and connect with specialists from the comfort of your home.</p>

        <h2>What We Offer</h2>
        <ul>
            <li>Easy online appointment booking</li>
            <li>Quick access to a variety of healthcare services</li>
            <li>Secure storage of personal and medical information</li>
            <li>Timely reminders for upcoming appointments</li>
            <li>24/7 support to help with all your booking needs</li>
        </ul>

        <h2>Contact Us</h2>
        <p>If you have any questions or need assistance, feel free to reach out to us:</p>
        <p>Email: manoj@gmail.com</p>
        <p>Phone: 2200030709</p>
    </div>
</body>
</html>

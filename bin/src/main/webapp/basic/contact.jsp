<%@ page import="lombok.EqualsAndHashCode.Include" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us - Online Medical Booking</title>
    <style>
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
            font-size: 2.5em;
            margin-bottom: 0.5em;
            color: #fff;
        }

        .contact-form {
            background: rgba(255, 255, 255, 0.9);
            padding: 40px;
            border-radius: 12px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.3);
            margin: 100px auto;
            color: #333;
        }

        .contact-form h2 {
            margin-top: 0;
            font-size: 1.8em;
            color: #333;
            text-align: center;
        }

        .contact-form label {
            display: block;
            margin-top: 20px;
            font-size: 1em;
            font-weight: bold;
            color: #333;
        }

        .contact-form input, .contact-form textarea {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 1em;
            background: #f9f9f9;
            transition: border-color 0.3s;
        }

        .contact-form input:focus, .contact-form textarea:focus {
            border-color: #4CAF50;
            outline: none;
        }

        .contact-form textarea {
            resize: vertical;
            height: 120px;
        }

        .contact-form button {
            margin-top: 25px;
            padding: 15px;
            background-color: #4CAF50;
            border: none;
            border-radius: 8px;
            color: #ffffff;
            font-size: 1.1em;
            cursor: pointer;
            width: 100%;
            font-weight: bold;
            text-transform: uppercase;
            transition: background-color 0.3s, transform 0.1s;
        }

        .contact-form button:hover {
            background-color: #45a049;
            transform: scale(1.03);
        }

        .contact-form button:active {
            transform: scale(0.98);
        }
    </style>
</head>
<body>
    <%@include file="home.jsp" %>
    <div class="contact-form">
        <h2>Get in Touch</h2>
        <form action="<c:url value='/sendContactMessage' />" method="post">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>

            <label for="subject">Subject</label>
            <input type="text" id="subject" name="subject" required>

            <label for="message">Message</label>
            <textarea id="message" name="message" required></textarea>

            <button type="submit">Send Message</button>
        </form>
    </div>
</body>
</html>

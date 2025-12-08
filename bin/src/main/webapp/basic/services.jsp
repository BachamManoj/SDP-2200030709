<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Our Services</title>
    <style>
        
        .container {
            max-width: 800px;
            margin: auto;
          
            
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .service {
            border-bottom: 1px solid #ccc;
            padding: 15px 0;
        }
        .service:last-child {
            border-bottom: none;
        }
        h2 {
            color: #4CAF50;
            margin: 0;
            font-size: 1.5em;
        }
        p {
            color: #555;
            margin: 5px 0 0;
        }
        .highlight {
            font-weight: bold;
            color: #4CAF50;
        }
        .contact-info {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <%@ include file="home.jsp" %> 
    <div class="container">
        <h1>Our Services</h1>
        
        <div class="service">
            <h2>General Consultation</h2>
            <p>Get professional medical advice for various health issues.</p>
            <p class="highlight">Available for all age groups.</p>
        </div>

        <div class="service">
            <h2>Specialized Treatment</h2>
            <p>We offer specialized treatments in <span class="highlight">cardiology, dermatology, and orthopedics</span>.</p>
            <p class="highlight">Access to expert specialists.</p>
        </div>

        <div class="service">
            <h2>Preventive Care</h2>
            <p>Regular health check-ups to catch any potential health issues early.</p>
            <p class="highlight">Vaccinations, screenings, and health assessments.</p>
        </div>

        <div class="service">
            <h2>Maternity Services</h2>
            <p>Comprehensive care for mothers-to-be throughout their pregnancy.</p>
            <p class="highlight">Prenatal and postnatal care, along with delivery services.</p>
        </div>

        <div class="service">
            <h2>Telemedicine</h2>
            <p>Access healthcare from the comfort of your home with our online consultation services.</p>
            <p class="highlight">24/7 availability for urgent consultations.</p>
        </div>

        <div class="contact-info">
            <h2>Contact Us</h2>
            <p>If you have any questions or would like to book an appointment, please contact us at:</p>
            <p>Email: <a href="mailto:info@healthcare.com">info@healthcare.com</a></p>
            <p>Phone: <a href="tel:+1234567890">+1 234 567 890</a></p>
        </div>
    </div>
</body>
</html>

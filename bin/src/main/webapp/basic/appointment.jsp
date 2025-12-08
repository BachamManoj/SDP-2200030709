<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Appointment</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<%@include file="home.jsp" %>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Book an Appointment</h2>
        <form action="<c:url value='/submitAppointment' />" method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name" required>
            </div>
            
            <div class="form-group">
                <label for="age">Age</label>
                <input type="number" class="form-control" id="age" name="age" placeholder="Enter your age" required>
            </div>
            
            <div class="form-group">
                <label for="issue">Type of Issue</label>
                <select class="form-control" id="issue" name="issue" onchange="filterDoctors()" required>
                    <option value="">Select issue type</option>
                    <option value="cardiology">Cardiology</option>
                    <option value="orthopedics">Orthopedics</option>
                    <option value="dermatology">Dermatology</option>
                    <option value="neurology">Neurology</option>
                    <!-- Add more options as needed -->
                </select>
            </div>
            
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" placeholder="Briefly describe your issue" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="timeslot">Select Time Slot</label>
                <select class="form-control" id="timeslot" name="timeslot" required>
                    <option value="9:00 AM">9:00 AM</option>
                    <option value="10:00 AM">10:00 AM</option>
                    <option value="11:00 AM">11:00 AM</option>
                    <option value="12:00 PM">12:00 PM</option>
                    <option value="1:00 PM">1:00 PM</option>
                    <option value="2:00 PM">2:00 PM</option>
                    <option value="3:00 PM">3:00 PM</option>
                    <option value="4:00 PM">4:00 PM</option>
                    <option value="5:00 PM">5:00 PM</option>
                    <option value="6:00 PM">6:00 PM</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="date">Select Date</label>
                <input type="date" class="form-control" id="date" name="date" required>
            </div>
            
            <div class="form-group">
                <label for="doctor">Select Doctor</label>
                <select class="form-control" id="doctor" name="doctor" required>
                    <option value="">Select doctor</option>
                    <!-- Doctors will be populated based on the selected issue -->
                </select>
            </div>
            
            <button type="submit" class="btn btn-primary">Submit Appointment</button>
        </form>
    </div>

    <!-- JavaScript to dynamically filter doctors based on selected issue -->
    <script>
        const doctors = {
            cardiology: ["Dr. Smith", "Dr. Brown"],
            orthopedics: ["Dr. Green", "Dr. Lee"],
            dermatology: ["Dr. White", "Dr. Black"],
            neurology: ["Dr. Grey", "Dr. Pink"]
            // Add more specializations and doctors as needed
        };

        function filterDoctors() {
            const issueType = document.getElementById("issue").value;
            const doctorSelect = document.getElementById("doctor");
            doctorSelect.innerHTML = '<option value="">Select doctor</option>';

            if (issueType && doctors[issueType]) {
                doctors[issueType].forEach(doctor => {
                    const option = document.createElement("option");
                    option.value = doctor;
                    option.textContent = doctor;
                    doctorSelect.appendChild(option);
                });
            }
        }
    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

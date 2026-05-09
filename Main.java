
<!DOCTYPE html>

<html lang="en">

<head>

  <meta charset="UTF-8">

  <title>Form Validation</title>

  <style>

    body {

      font-family: Arial;

      padding: 20px;

      background-color: #f2f7ff;

    }

    h2 {

      text-align: center;

    }

    form {

      width: 320px;

      margin: auto;

      background: #ffffff;

      padding: 20px;

      border-radius: 8px;

      box-shadow: 0 0 10px rgba(0,0,0,0.1);

    }

    input, button, textarea {

      margin: 8px 0;

      display: block;

      width: 100%;

      padding: 8px;

    }

    .error {

      color: red;

      font-size: 12px;

    }

    .gender-group, .course-group {

      display: flex;

      gap: 10px;

      flex-wrap: wrap;

      margin: 8px 0;

    }

  </style>

</head>

<body>

 

<h2>Form Registration</h2>

 

<form id="myForm" onsubmit="return validateForm()">

  Name:

  <input type="text" id="name">

  <div id="nameError" class="error"></div>

 

  Email:

  <input type="text" id="email">

  <div id="emailError" class="error"></div>

 

  Password:

  <input type="password" id="password">

  <div id="passwordError" class="error"></div>

 

  Phone Number:

  <input type="text" id="phone">

  <div id="phoneError" class="error"></div>

 

  Gender:

  <div class="gender-group">

    <label><input type="radio" name="gender" value="male"> Male</label>

    <label><input type="radio" name="gender" value="female"> Female</label>

    <label><input type="radio" name="gender" value="others"> Others</label>

  </div>

 

  B.Tech Courses:

  <div class="course-group">

    <label><input type="checkbox" name="course" value="CSE"> CSE</label>

    <label><input type="checkbox" name="course" value="ECE"> ECE</label>

    <label><input type="checkbox" name="course" value="MECH"> MECH</label>

  </div>

 

  Comments:

  <textarea rows="3" placeholder="Enter your message..."></textarea>

 

  <button type="submit">Submit</button>

</form>

 

<script>

function validateForm() {

  let isValid = true;

 

  let name = document.getElementById("name").value.trim();

  let email = document.getElementById("email").value.trim();

  let password = document.getElementById("password").value.trim();

 

  document.getElementById("nameError").innerText = "";

  document.getElementById("emailError").innerText = "";

  document.getElementById("passwordError").innerText = "";

 

  if (name.length < 3) {

    document.getElementById("nameError").innerText = "Name must be at least 3 characters";

    isValid = false;

  }

 

  if (email === "" || email.indexOf("@") < 1 || email.lastIndexOf(".") < email.indexOf("@") + 2 || email.lastIndexOf(".") + 2 >= email.length) {

    document.getElementById("emailError").innerText = "Enter valid email";

    isValid = false;

  }

 

  if (password.length < 6) {

    document.getElementById("passwordError").innerText = "Password must be at least 6 characters";

    isValid = false;

  }

 

  return isValid;

}

</script>

 

</body>

</html>
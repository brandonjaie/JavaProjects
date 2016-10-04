/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    var passCorrect = $('#passCorrect');
    var cPassCorrect = $('#cPassCorrect');
    var passIncorrect = $('#passIncorrect');
    var cPassIncorrect = $('#incorrect');
    var incorrectMessage = $('#incorrectMessage');
    var correctMessage = $('#correctMessage');


    passCorrect.hide();
    cPassCorrect.hide();
    passIncorrect.hide();
    cPassIncorrect.hide();
    incorrectMessage.hide();
    correctMessage.hide();

    $('.passClass').change(function (event) {
//        event.preventDefault();
        var password = $('#edit-new-password').val();
        var confirmPassword = $('#edit-repeat-password').val();


        if (password !== confirmPassword && password.length !== 0 && confirmPassword.length !== 0) {
            passCorrect.hide();
            cPassCorrect.hide();
            correctMessage.hide();
            passIncorrect.hide();
            cPassIncorrect.show();
            incorrectMessage.show();
            document.getElementById("edit-repeat-password").style.borderColor = "red";
            document.getElementById("edit-repeat-password").style.background = "#FFD9D9";
            $('#edit-user-profile-button').prop('disabled', true);

        } else if (password === confirmPassword && password.length !== 0 && confirmPassword.length !== 0) {

            passCorrect.show();
            cPassCorrect.show();
            passIncorrect.hide();
            cPassIncorrect.hide();
            incorrectMessage.hide();
            correctMessage.show();
            document.getElementById("edit-new-password").style.borderColor = "green";
            document.getElementById("edit-new-password").style.background = "#CEFFCE";
            document.getElementById("edit-repeat-password").style.borderColor = "green";
            document.getElementById("edit-repeat-password").style.background = "#CEFFCE";
            $('#edit-user-profile-button').prop('disabled', false);

        } else if (password === "" && confirmPassword.length !== 0) {
            passCorrect.hide();
            cPassCorrect.hide();
            cPassIncorrect.hide();
            passIncorrect.show();
            incorrectMessage.show();
            correctMessage.hide();
            document.getElementById("edit-new-password").style.borderColor = "red";
            document.getElementById("edit-new-password").style.background = "#FFD9D9";
            $('#edit-user-profile-button').prop('disabled', true);
        } else if (confirmPassword === "" && password.length !== 0){
            passCorrect.hide();
            cPassCorrect.hide();
            cPassIncorrect.show();
            passIncorrect.hide();
            incorrectMessage.show();
            correctMessage.hide();
            document.getElementById("edit-repeat-password").style.borderColor = "red";
            document.getElementById("edit-repeat-password").style.background = "#FFD9D9";
            $('#edit-user-profile-button').prop('disabled', true);
        } else if (password === "" && confirmPassword === "") {
            passCorrect.hide();
            cPassCorrect.hide();
            cPassIncorrect.show();
            passIncorrect.show();
            incorrectMessage.show();
            correctMessage.hide();
            document.getElementById("edit-new-password").style.borderColor = "red";
            document.getElementById("edit-new-password").style.background = "#FFD9D9";
            document.getElementById("edit-repeat-password").style.borderColor = "red";
            document.getElementById("edit-repeat-password").style.background = "#FFD9D9";
            $('#edit-user-profile-button').prop('disabled', true);
        }
    });
});


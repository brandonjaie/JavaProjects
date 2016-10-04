/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function () {

    loadUsers();

    $('#search-admin-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/users',
            data: JSON.stringify({
                userId: $('#search-admin-member-id').val(),
                lastName: $('#search-admin-member-lastname').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-admin-member-id').val('');
            $('#search-admin-member-lastname').val('');
            fillAdminTable(data, status);
        }).error(function (data, status) {
        });
    });
});


function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearAdminTable() {
    $('#adminRows').empty();
}

function loadUsers() {
    $.ajax({
        url: 'users'
    }).success(function (data, status) {
        fillAdminTable(data, status);
    });
}

function fillAdminTable(data, status) {

    clearAdminTable();

    var aTable = $('#adminRows');

    $.each(data, function (index, userUserProfile) {
        var Member = "Member";
        var Employee = "Employee";
        var Admin = "Admin";
        
        if (userUserProfile.authority.authority == "ROLE_ADMIN") {
            userUserProfile.authority.authority = Admin;
        } else if (userUserProfile.authority.authority == "ROLE_EMPLOYEE"){
            userUserProfile.authority.authority = Employee;
        } else if (userUserProfile.authority.authority == "ROLE_USER"){
            userUserProfile.authority.authority = Member;
            
        }
        aTable.append($('<tr>')
                .append($('<td>').text(userUserProfile.userId))
                .append($('<td>').text(userUserProfile.firstName + ' ' + userUserProfile.lastName))
                .append($('<td>').text(userUserProfile.authority.authority))
                .append($('<td>')
                        .append($('<a>')
                                .attr({'onclick': 'deleteUser(' + userUserProfile.userId + ')'}).text('Delete')
                                ) 
                        ) 
                .append($('<td>')
                        .append($('<a>')
                                .attr({
                                    'onclick': 'resetPassword(' + userUserProfile.userId + ')'}).text('Reset Password')
                                ) 
                        ) 
                ); 
    });
}
function resetPassword(id) {
    var answer = confirm("Do you really want to reset this user's password?");
    if (answer === true) {

        $.ajax({
            type: 'PUT',
            url: 'resetPassword/' + id
        }).success(function () {
            alert("Password Successfully Reset");
            window.location.reload(true);

        });
    }
}

function deleteUser(id) {
    var answer = confirm("Do you really want to delete Member Id " + id + " from Kobold Camp? Please ensure all equipment associated with Member Id " + id + " is returned or accounted for.");
    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'users/' + id
        }).success(function () {
            loadUsers();
        });
    }
}




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

    $('#search-todays-records').click(function (event) {

        event.preventDefault();
        loadRecords();
    });

    $('#search-associates-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/records',
            data: JSON.stringify({
                recordDate: $('#search-date').val(),
                employeeId: $('#search-employee-id').val(),
                memberId: $('#search-members-id').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-date').val('');
            $('#search-employee-id').val('');
            $('#search-members-id').val('');
            fillRecordTable(data, status);
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

function clearRecordTable() {
    $('#records').empty();
}

function loadUsers() {
    $.ajax({
        url: 'users'
    }).success(function (data, status) {
        fillAdminTable(data, status);
    });
}

function loadRecords() {
    $.ajax({
        url: 'assetRecordsCurrentDate'
    }).success(function (data, status) {
        fillRecordTable(data, status);
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
        } else if (userUserProfile.authority.authority == "ROLE_EMPLOYEE") {
            userUserProfile.authority.authority = Employee;
        } else if (userUserProfile.authority.authority == "ROLE_USER") {
            userUserProfile.authority.authority = Member;

        }
        aTable.append($('<tr>')
                .append($('<td>').text(userUserProfile.userId))
                .append($('<td>').text(userUserProfile.firstName + ' ' + userUserProfile.lastName))
                .append($('<td>').text(userUserProfile.authority.authority))
                .append($('<td style="text-align: center">')
                        .append($('<a>')
                                .attr({'onclick': 'deleteUser(' + userUserProfile.userId + ')'}).text('Delete')
                                )
                        )
                .append($('<td style="text-align: center">')
                        .append($('<a>')
                                .attr({
                                    'onclick': 'resetPassword(' + userUserProfile.userId + ')'}).text('Reset')
                                )
                        )
                );
    });
}

function fillRecordTable(data, status) {

    clearRecordTable();

    var aTable = $('#records');

    $.each(data, function (index, record) {
        var member = "";
        var asset = record.asset.brand + " -- " + record.asset.description;
        if (record.member != null) {
            member = record.member.firstName + " " + record.member.lastName;
        } else if (record.employee != null) {
            member = record.employee.firstName + " " + record.employee.lastName;
        }
        aTable.append($('<tr>')
                .append($('<td>').text(record.recordDate))
                .append($('<td style="text-align: center">').text(member))
                .append($('<td style="text-align: center">').text(record.status.status))
                .append($('<td style="text-align: center">').text(asset))
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
            alert("Member " + id + " successfully deleted.");
            loadUsers();
        });
    }
}




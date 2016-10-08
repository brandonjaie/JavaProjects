/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    var available = $('#userNameAvailable');
    var notAvailable = $('#userNameNotAvailable');
    available.hide();
    notAvailable.hide();

    $(':checkbox').on('change', function () {
        var check1 = $('#check1');
        var isAdmin = check1.prop('name');
        var check2 = $('#check2');
        var isEmployee = check2.prop('name');
        if (check1.is(':checked')) {
            $(':checkbox[name="' + isAdmin + '"]').not($(this)).prop('checked', false);
            $(':checkbox[name="' + isEmployee + '"]').not($(this)).prop('checked', false);
        }
    });

    if ($('#add-username').val().length != 0) {
        available.show();
        $('#add-member-button').prop('disabled', false);
        document.getElementById("add-username").style.borderColor = "green";
        document.getElementById("add-username").style.background = "#CEFFCE";
    } else {
        $('#add-member-button').prop('disabled', true);
    }

    $('#add-username').change(function (event) {

        $.ajax({
            type: 'POST',
            url: 'checkUserNameAvailability',
            data: JSON.stringify({
                userName: $('#add-username').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {

            if (data.available == true && $('#add-username').val().length != 0) {
                available.show();
                notAvailable.hide();
                $('#add-member-button').prop('disabled', false);
                document.getElementById("add-username").style.borderColor = "green";
                document.getElementById("add-username").style.background = "#CEFFCE";
            } else {
                available.hide();
                notAvailable.show();
                $('#add-member-button').prop('disabled', true);
                document.getElementById("add-username").style.borderColor = "red";
                document.getElementById("add-username").style.background = "#FFD9D9";
            }

        }).fail(function () {
            alert("Not able to verify username...");
            $('#add-member-button').prop('disabled', true);
        });

    });

    $('#search-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/members',
            data: JSON.stringify({
                userId: $('#search-userProfile-id').val(),
                lastName: $('#search-userProfile-name').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-userProfile-id').val('');
            $('#search-userProfile-name').val('');
            fillUserProfilesTable(data, status);
        }).error(function (data, status) {
        });
    });
});


function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearUserProfilesTable() {
    $('#userProfileRows').empty();
}


function fillUserProfilesTable(data, status) {

    clearUserProfilesTable();

    var aTable = $('#userProfileRows');

    $.each(data, function (index, userUserProfile) {
        aTable.append($('<tr>')
                .append($('<td>').text(userUserProfile.userId))
                .append($('<td>').text(userUserProfile.firstName + ' ' + userUserProfile.lastName))
                .append($('<td>').text(userUserProfile.email))
                .append($('<td>').text(userUserProfile.phone))
                );

    });
}
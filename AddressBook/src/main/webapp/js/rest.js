/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    loadAddresses();

    $('#add-button').click(function (event) {

        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: 'address',
            data: JSON.stringify({
                firstName: $('#add-first-name').val(),
                lastName: $('#add-last-name').val(),
                street: $('#add-street').val(),
                city: $('#add-city').val(),
                state: $('#add-state').val(),
                zip: $('#add-zip').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#add-first-name').val('');
            $('#add-last-name').val('');
            $('#add-street').val('');
            $('#add-city').val('');
            $('#add-state').val('');
            $('#add-zip').val('');
            loadAddresses();
            clearErrors();
        }).error(function (data, status) {
            var errorDiv = $('#validationErrors');
            clearErrors();
            $.each(data.responseJSON.fieldErrors,
                    function (index, validationError) {
                        errorDiv.append(validationError.message).append($('<br>'));
                    });
        });
    });



    $('#edit-button').click(function (event) {
        event.preventDefault();

        $.ajax({
            type: 'PUT',
            url: 'address/' + $('#edit-address-id').val(),
            data: JSON.stringify({
                firstName: $('#edit-first-name').val(),
                lastName: $('#edit-last-name').val(),
                street: $('#edit-street').val(),
                city: $('#edit-city').val(),
                state: $('#edit-state').val(),
                zip: $('#edit-zip').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'

        }).success(function (data, status) {
            hideEditForm();
            loadAddresses();
            clearErrors();
        }).error(function (data, status) {
            var errorDiv = $('#validationErrorsEdit');
            clearErrors();
            $.each(data.responseJSON.fieldErrors, function (index, validationError) {
                errorDiv.append(validationError.message).append($('<br>'));
            });
        });
    });

});
function loadAddresses() {

    clearAddressTable();

    var contentRows = $('#contentRows');

    $.ajax({
        type: 'GET',
        url: 'addresses'
    }).success(function (data, status) {
        $.each(data, function (index, address) {
            var name = address.firstName + ' ' + address.lastName;
            var city = address.city;
            var id = address.addressId;

            var row = '<tr>';
            row += '<td>' + name + '</td>';
            row += '<td>' + city + '</td>';
            row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
            row += '<td><a onclick="deleteAddress(' + id + ')">Delete</a></td>';
            row += '</tr>';
            contentRows.append(row);


        });
    });


}

function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearAddressTable() {

    $('#contentRows').empty();

}

function deleteAddress(addressId) {
    var answer = confirm("Do you really want to delete this address?");
    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'address/' + addressId
        }).success(function () {
            hideEditForm();
            loadAddresses();
        });
    }
}

function showEditForm(addressId) {

    $.ajax({
        type: 'GET',
        url: 'address/' + addressId
    }).success(function (address, status) {
        $('#edit-first-name').val(address.firstName);
        $('#edit-last-name').val(address.lastName);
        $('#edit-street').val(address.street);
        $('#edit-city').val(address.city);
        $('#edit-state').val(address.state);
        $('#edit-zip').val(address.zip);
        $('#edit-address-id').val(address.addressId);
        $('#editFormDiv').show();
        $('#addFormDiv').hide();
    });
}

function hideEditForm() {

    $('#edit-first-name').val('');
    $('#edit-last-name').val('');
    $('#edit-street').val('');
    $('#edit-city').val('');
    $('#edit-state').val('');
    $('#edit-zip').val('');
    $('#edit-address-id').val('');
    $('#addFormDiv').show();
    $('#editFormDiv').hide();
}
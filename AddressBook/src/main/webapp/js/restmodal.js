/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global address */

$(document).ready(function () {

    loadAddresses();

    $('#add-button').click(function (event) {

        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/AddressBook/address',
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
            $('#validationErrors').empty();
            loadAddresses();
            clearAddressTable();
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
        $.ajax(
                {
                    type: 'PUT',
                    url: 'address/' + $('#edit-address-id').val(),
                    data: JSON.stringify({
                        firstName: $('#edit-first-name').val(),
                        lastName: $('#edit-last-name').val(),
                        street: $('#edit-street').val(),
                        city: $('#edit-city').val(),
                        state: $('#edit-state').val(),
                        zip: $('#edit-zip').val()
                                //addressId: $('#edit-address-id').val()
                    }),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    'dataType': 'json'

                }).success(function (data, status) {
            $('#editModal').modal('hide');
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

    $('#search-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/addresses',
            data: JSON.stringify({
                firstName: $('#search-first-name').val(),
                lastName: $('#search-last-name').val(),
                street: $('#search-street').val(),
                city: $('#search-city').val(),
                state: $('#search-state').val(),
                zip: $('#search-zip').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-first-name').val('');
            $('#search-last-name').val('');
            $('#search-street').val('');
            $('#search-city').val('');
            $('#search-state').val('');
            $('#search-zip').val('');
            fillAddressTable(data, status);
        });
    });
});

function loadAddresses() {
    $.ajax({
        url: 'addresses'
    }).success(function (data, stats) {
        fillAddressTable(data, status);
    });
}

function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearAddressTable() {
    $('#addressRows').empty();
}

function deleteAddress(id) {
    var answer = confirm("Do you really want to delete this address?");
    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'address/' + id
        }).success(function () {
            loadAddresses();
        });
    }
}

$('#detailsModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);
    var addressId = element.data('address-id');
    var modal = $(this);
    $.ajax({
        url: 'address/' + addressId
    }).success(function (address) {
        modal.find('#address-id').text(address.addressId);
        modal.find('#address-firstName').text(address.firstName);
        modal.find('#address-lastName').text(address.lastName);
        modal.find('#address-street').text(address.street);
        modal.find('#address-city').text(address.city);
        modal.find('#address-state').text(address.state);
        modal.find('#address-zip').text(address.zip);
    });
});

$('#editModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);
    var addressId = element.data('address-id');
    var modal = $(this);
    $.ajax({
        url: 'address/' + addressId
    }).success(function (address) {

//use .text on  text display fields like <h3> or <span>
        modal.find('#edit-address-id').val(address.addressId);
        modal.find('#edit-header-address-id').text(address.addressId);
//use .val on input fields
        modal.find('#edit-first-name').val(address.firstName);
        modal.find('#edit-last-name').val(address.lastName);
        modal.find('#edit-street').val(address.street);
        modal.find('#edit-city').val(address.city);
        modal.find('#edit-state').val(address.state);
        modal.find('#edit-zip').val(address.zip);
    });
});

function fillAddressTable(data, status) {
    clearAddressTable();

    var cTable = $('#addressRows');

    $.each(data, function (index, address) {
        cTable.append($('<tr>')
                .append($('<td>').append($('<a>')
                        .attr({'data-address-id': address.addressId,
                            'data-toggle': 'modal',
                            'data-target': '#detailsModal'})
                        .text(address.firstName + ' ' + address.lastName)
                        ) //end of <a> append
                        ) //end of <td> append
                .append($('<td>').text(address.city))
                .append($('<td>').append($('<a>')
                        .attr({
                            'data-address-id': address.addressId,
                            'data-toggle': 'modal',
                            'data-target': '#editModal'
                        })
                        .text('Edit')))
                .append($('<td>').append($('<a>')
                        .attr({
                            'onclick': 'deleteAddress(' + address.addressId + ')'})
                        .text('Delete')))
                );
    });
}
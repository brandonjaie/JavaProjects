/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global contact */

$(document).ready(function () {

    loadContacts();

    $('#add-button').click(function (event) {

        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: 'contact',
            data: JSON.stringify({
                firstName: $('#add-first-name').val(),
                lastName: $('#add-last-name').val(),
                company: $('#add-company').val(),
                phone: $('#add-phone').val(),
                email: $('#add-email').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#add-first-name').val('');
            $('#add-last-name').val('');
            $('#add-company').val('');
            $('#add-phone').val('');
            $('#add-email').val('');
            loadContacts();
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
            url: 'contact/' + $('#edit-contact-id').val(),
            data: JSON.stringify({
                firstName: $('#edit-first-name').val(),
                lastName: $('#edit-last-name').val(),
                company: $('#edit-company').val(),
                phone: $('#edit-phone').val(),
                email: $('#edit-email').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'

        }).success(function (data, status) {
            $('#editModal').modal('hide');
            loadContacts();
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
            url: 'search/contacts',
            data: JSON.stringify({
                firstName: $('#search-first-name').val(),
                lastName: $('#search-last-name').val(),
                company: $('#search-company').val(),
                phone: $('#search-phone').val(),
                email: $('#search-email').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-first-name').val('');
            $('#search-last-name').val('');
            $('#search-company').val('');
            $('#search-phone').val('');
            $('#search-email').val('');
            fillContactTable(data, status);
        });
    });

});



function loadContacts() {
    $.ajax({
        url: 'contacts'
    }).success(function (data, status) {
        fillContactTable(data, status);

    });
}

function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearContactTable() {
    $('#contactRows').empty();


}

function deleteContact(id) {
    var answer = confirm("Do you really want to delete this contact?");

    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'contact/' + id
        }).success(function () {
            loadContacts();
        });
    }
}

$('#detailsModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);

    var contactId = element.data('contact-id');

    var modal = $(this);

    $.ajax({
        url: 'contact/' + contactId
    }).success(function (contact) {
        modal.find('#contact-id').text(contact.contactId);

        modal.find('#contact-firstName').text(contact.firstName);

        modal.find('#contact-lastName').text(contact.lastName);

        modal.find('#contact-company').text(contact.company);

        modal.find('#contact-phone').text(contact.phone);

        modal.find('#contact-email').text(contact.email);

    });

});

$('#editModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);

    var contactId = element.data('contact-id');

    var modal = $(this);

    $.ajax({
        url: 'contact/' + contactId
    }).success(function (contact) {
        //use .text on  text display fields like <h3> or <span>
        modal.find('#edit-contact-id').val(contact.contactId);
        modal.find('#edit-header-contact-id').text(contact.contactId);

//use .val on input fields
        modal.find('#edit-first-name').val(contact.firstName);

        modal.find('#edit-last-name').val(contact.lastName);

        modal.find('#edit-company').val(contact.company);

        modal.find('#edit-phone').val(contact.phone);

        modal.find('#edit-email').val(contact.email);

        //modal.find('#edit-contact-id').val(contact.contactId);
    });

});

function fillContactTable(data, status) {

    clearContactTable();

    var cTable = $('#contactRows');

    $.each(data, function (index, contact) {
        cTable.append($('<tr>')
                .append($('<td>').append($('<a>')
                        .attr({'data-contact-id': contact.contactId,
                            'data-toggle': 'modal',
                            'data-target': '#detailsModal'})
                        .text(contact.firstName + ' ' + contact.lastName)
                        ) //end of <a> append
                        ) //end of <td> append
                .append($('<td>').text(contact.company))
                .append($('<td>').append($('<a>')
                        .attr({
                            'data-contact-id': contact.contactId,
                            'data-toggle': 'modal',
                            'data-target': '#editModal'
                        })
                        .text('Edit')))
                .append($('<td>').append($('<a>')
                        .attr({
                            'onclick': 'deleteContact(' + contact.contactId + ')'})
                        .text('Delete')))
                );
    });

}




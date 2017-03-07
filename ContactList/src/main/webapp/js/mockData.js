/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Document ready function
$(document).ready(function () {
    loadContacts();
});
//==========
// FUNCTIONS
//==========
// Load contacts into the summary table
function loadContacts() {
    // Clear the previous list
    clearContactTable();
    // Grab the tbody element that will hold the new list of contacts
    var cTable = $('#contentRows');
    // Iterate through each of the JSON objects in the test contact data
    // and render to the summary table
    $.each(testContactData, function (index, contact) {
        cTable.append($('<tr>')
                .append($('<td>')
                        .append($('<a>')
                                .attr({
                                    'data-contact-id': contact.contactId,
                                    'data-toggle': 'modal',
                                    'data-target': '#detailsModal'
                                })
                                .text(contact.firstName + ' ' + contact.lastName)
                                ) // ends the <a> tag
                        ) // ends the <td> tag for the contact name
                .append($('<td>').text(contact.company))
                .append($('<td>')
                        .append($('<a>')
                                .attr({
                                    'data-contact-id': contact.contactId,
                                    'data-toggle': 'modal',
                                    'data-target': '#editModal'
                                })
                                .text('Edit')
                                ) // ends the <a> tag
                        ) // ends the <td> tag for Edit
                .append($('<td>').text('Delete'))
                );
    });
}

// Clear all content rows from the summary table
function clearContactTable() {
    $('#contentRows').empty();
}

// #1 - NEW CODE
// This code runs in response to the show.bs.modal event - it gets the correct
// contact data and renders it to the dialog
$('#detailsModal').on('show.bs.modal', function (event) {
    // Get the element that triggered this event - in our case it is a contact
    // name link in the summary table. This link has an attribute that contains
    // the contactId for the given contact. We'll use that to retrieve the
    // contact's details.
    var element = $(event.relatedTarget);
    // grab the contact id
    var contactId = element.data('contact-id');

    // PLACEHOLDER: Eventually we'll make an ajax call to the server to get the
    // details for this contact but for now we'll load the dummy
    // data
    var modal = $(this);
    modal.find('#contact-id').text(dummyDetailsContact.contactId);
    modal.find('#contact-firstName').text(dummyDetailsContact.firstName);
    modal.find('#contact-lastName').text(dummyDetailsContact.lastName);
    modal.find('#contact-company').text(dummyDetailsContact.company);
    modal.find('#contact-phone').text(dummyDetailsContact.phone);
    modal.find('#contact-email').text(dummyDetailsContact.email);

});

// This code runs in response to the show.bs.modal event - it gets the correct
// contact data and renders it to the dialog
$('#editModal').on('show.bs.modal', function (event) {
    // Get the element that triggered this event - in our case it is a contact
    // name link in the summary table. This link has an attribute that contains
    // the contactId for the given contact. We'll use that to retrieve the
    // contact's details.
    var element = $(event.relatedTarget);
    // Grab the contact id
    var contactId = element.data('contact-id');
    // PLACEHOLDER: Eventually we'll make an ajax call to the server to get the
    // details for this contact but for now we'll load the dummy
    // data
    var modal = $(this);
    modal.find('#contact-id').text(dummyEditContact.contactId);
    modal.find('#edit-first-name').val(dummyEditContact.firstName);
    modal.find('#edit-last-name').val(dummyEditContact.lastName);
    modal.find('#edit-company').val(dummyEditContact.company);
    modal.find('#edit-phone').val(dummyEditContact.phone);
    modal.find('#edit-email').val(dummyEditContact.email);
});

// #2: NEW CODE
var dummyDetailsContact =
        {
            contactId: 42,
            firstName: "Kent",
            lastName: "Hrbek",
            company: "3M",
            email: "kent@3m.com",
            phone: "444-6798"
        };

var dummyEditContact =
        {
            contactId: 52,
            firstName: "Kirby",
            lastName: "Puckett",
            company: "Sun",
            email: "kirby@sun.com",
            phone: "123-5599"
        };

// TEST DATA
var testContactData = [
    {
        contactId: 1,
        firstName: "Susan",
        lastName: "Williams",
        company: "IBM",
        email: "swilliams@ibm.com",
        phone: "555-1212"},
    {
        contactId: 2,
        firstName: "George",
        lastName: "Smith",
        company: "EMC",
        email: "smith@emc.com",
        phone: "555-1234"},
    {
        contactId: 3,
        firstName: "Chuck",
        lastName: "Knobloch",
        company: "3M",
        email: "chuck@3m.com",
        phone: "555-5656"}
];



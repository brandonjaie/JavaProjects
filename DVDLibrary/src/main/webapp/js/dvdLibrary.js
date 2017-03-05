/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global dvd */

$(document).ready(function () {

    loadDvds();

    $('#add-button').click(function (event) {

        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: 'dvd',
            data: JSON.stringify({
                title: $('#add-title').val(),
                releaseDate: $('#add-release-date').val(),
                mpaaRating: $('#add-mpaa-rating').val(),
                director: $('#add-director').val(),
                studio: $('#add-studio').val(),
                genre: {genreId: $('#add-genre').val()}
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, success) {
            $('#add-title').val('');
            $('#add-release-date').val('');
            $('#add-mpaa-rating').val('');
            $('#add-director').val('');
            $('#add-studio').val('');
            $('#add-genre').val('');
            loadDvds();
            clearDvdTable();
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
            url: 'dvd/' + $('#edit-dvd-id').val(),
            data: JSON.stringify({
                title: $('#edit-title').val(),
                releaseDate: $('#edit-release-date').val(),
                mpaaRating: $('#edit-mpaa-rating').val(),
                director: $('#edit-director').val(),
                studio: $('#edit-studio').val(),
                genre: {genreId: $('#edit-genre').val()}
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'

        }).success(function (data, status) {
            $('#editModal').modal('hide');
            //hideEditForm();
            loadDvds();
        }).error(function (data, status) {
            var errorDiv = $('#validationErrorsEdit');
            clearErrors();
            $.each(data.responseJSON.fieldErrors, function (index, validationError) {
                errorDiv.append(validationError.message).append($('<br>'));
            });
        });
    });
});

function loadDvds() {
    $.ajax({
        url: "dvds"
    }).success(function (data, status) {
        fillDvdTable(data, status);
    });
}

function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearDvdTable() {
    $('#dvdRows').empty();
}

function deleteDvd(id) {
    var answer = confirm("Do you really want to delete this dvd?");

    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'dvd/' + id
        }).success(function () {
            loadDvds();
        });
    }
}

$('#detailsModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);

    var dvdId = element.data('dvd-id');

    var modal = $(this);

    $.ajax({
        url: 'dvd/' + dvdId
    }).success(function (dvd) {

        modal.find('#dvd-id').text(dvd.dvdId);

        modal.find('#dvd-title').text(dvd.title);

        modal.find('#dvd-releaseDate').text(dvd.releaseDate);

        modal.find('#dvd-mpaaRating').text(dvd.mpaaRating);

        modal.find('#dvd-director').text(dvd.director);

        modal.find('#dvd-studio').text(dvd.studio);

        modal.find('#dvd-genre').text(dvd.genre.genreName);


    });

});

$('#editModal').on('show.bs.modal', function (event) {

    var element = $(event.relatedTarget);

    var dvdId = element.data('dvd-id');

    var modal = $(this);

    $.ajax({
        url: 'dvd/' + dvdId
    }).success(function (dvd) {
//use .text on  text display fields like <h3> or <span>
        modal.find('#edit-dvd-id').val(dvd.dvdId);
        modal.find('#edit-header-dvd-id').text(dvd.dvdId);

//use .val on input fields
        modal.find('#edit-title').val(dvd.title);

        modal.find('#edit-release-date').val(dvd.releaseDate);

        modal.find('#edit-mpaa-rating').val(dvd.mpaaRating);

        modal.find('#edit-director').val(dvd.director);

        modal.find('#edit-studio').val(dvd.studio);

        modal.find('#edit-genre').val(dvd.genre.genreId);

    });
});

function fillDvdTable(data, status) {

    clearDvdTable();

    var cTable = $('#dvdRows');

    $.each(data, function (index, dvd) {
        cTable.append($('<tr>')
                .append($('<td>').append($('<a>')
                        .attr({'data-dvd-id': dvd.dvdId,
                            'data-toggle': 'modal',
                            'data-target': '#detailsModal'})
                        .text(dvd.title)
                        ) //end of <a> append
                        ) //end of <td> append
                .append($('<td>').text(dvd.releaseDate))
                .append($('<td>').append($('<a>')
                        .attr({
                            'data-dvd-id': dvd.dvdId,
                            'data-toggle': 'modal',
                            'data-target': '#editModal'
                        })
                        .text('Edit')))
                .append($('<td>').append($('<a>')
                        .attr({
                            'onclick': 'deleteDvd(' + dvd.dvdId + ')'})
                        .text('Delete')))
                );
    });
}




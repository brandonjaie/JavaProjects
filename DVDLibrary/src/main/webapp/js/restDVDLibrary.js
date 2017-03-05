/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
            hideEditForm();
            loadDvds();
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

function loadDvds() {

    clearDvdTable();

    var contentRows = $('#contentRows');

    $.ajax({
        type: 'GET',
        url: 'dvds'
    }).success(function (data, status) {
        $.each(data, function (index, dvd) {
            var title = dvd.title;
            var releaseDate = dvd.releaseDate;
            var id = dvd.dvdId;

            var row = '<tr>';
            row += '<td>' + title + '</td>';
            row += '<td>' + releaseDate + '</td>';
            row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
            row += '<td><a onclick="deleteDvd(' + id + ')">Delete</a></td>';
            row += '</tr>';
            contentRows.append(row);


        });
    });


}

function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearDvdTable() {

    $('#contentRows').empty();

}

function deleteDvd(dvdId) {

    $.ajax({
        type: 'DELETE',
        url: 'dvd/' + dvdId
    }).success(function () {
        hideEditForm();
        loadDvds();
    });
}

function showEditForm(dvdId) {

    $.ajax({
        type: 'GET',
        url: 'dvd/' + dvdId
    }).success(function (dvd, status) {
        $('#edit-title').val(dvd.title);
        $('#edit-release-date').val(dvd.releaseDate);
        $('#edit-mpaa-rating').val(dvd.mpaaRating);
        $('#edit-director').val(dvd.director);
        $('#edit-studio').val(dvd.studio);
        $('#edit-genre').val(dvd.genre.genreId);
        $('#edit-dvd-id').val(dvd.dvdId);
        $('#editFormDiv').show();
        $('#addFormDiv').hide();
    });
}

function hideEditForm() {

    $('#edit-title').val('');
    $('#edit-release-date').val('');
    $('#edit-mpaa-rating').val('');
    $('#edit-director').val('');
    $('#edit-studio').val('');
    $('#edit-genre').val('');
    $('#edit-dvd-id').val('');
    $('#addFormDiv').show();
    $('#editFormDiv').hide();
}
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    loadDvds();

    $('#search-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/dvds',
            data: JSON.stringify({
                title: $('#search-title').val(),
                releaseDate: $('#search-release-date').val(),
                mpaaRating: $('#search-mpaa-rating').val(),
                director: $('#search-director').val(),
                studio: $('#search-studio').val(),
                genre: $('#search-genre').val()
//                note: $('#search-note').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-title').val('');
            $('#search-release-date').val('');
            $('#search-mpaa-rating').val('');
            $('#search-director').val('');
            $('#search-studio').val('');
            $('#search-genre').val('');
            fillSearchTable(data, status);
        });
    });
});

function loadDvds() {
    $.ajax({
        url: "dvds"
    }).success(function (data, status) {
        fillSearchTable(data, status);
    });
}

function clearSearchTable() {
    $('#searchRows').empty();
}

function fillSearchTable(data, status) {

    clearSearchTable();

    var cTable = $('#searchRows');

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
                .append($('<td>').text(dvd.mpaaRating))
                .append($('<td>').text(dvd.director))
                .append($('<td>').text(dvd.studio))
                .append($('<td>').text(dvd.genre.genreName))
                );
    });
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
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $('#add-equipment-button').click(function (event) {

        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: 'asset',
            data: JSON.stringify({
                category: {categoryId: $('#add-category').val()},
                brand: $('#add-brand').val(),
                description: $('#add-description').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#add-category').val('');
            $('#add-brand').val('');
            $('#add-description').val('');
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

    $('#search-button').click(function (event) {

        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'search/assets',
            data: JSON.stringify({
                asset: $('#search-rentals-asset-id').val(),
                category: $('#search-rentals-category').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-rentals-asset-id').val('');
            $('#search-rentals-category').val('');
            fillRentalsTable(data, status);
            clearErrors();
        }).error(function (data, status) {
        });
    });
});


function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearRentalsTable() {
    $('#rentalRows').empty();
}


function fillRentalsTable(data, status) {

    clearRentalsTable();

    var aTable = $('#rentalRows');

    $.each(data, function (index, asset) {
        aTable.append($('<tr>')
                .append($('<td>').text(asset.assetId))
                .append($('<td>').text(asset.category.name))
                .append($('<td>').text(asset.brand))
                .append($('<td>').text(asset.description))
                ); 

    });
}

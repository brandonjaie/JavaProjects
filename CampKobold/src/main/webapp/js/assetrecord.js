/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    loadRecords();
    $('#edit-asset-record-status').change(function (event) {
        clearErrors();
        $('#edit-asset-record-member').val('');
        $('#edit-asset-record-member').prop('disabled', true);
        var status = $('#edit-asset-record-status').val();
        var notAvailable = $('#assetNotAvailable');
        var duplicate = $('#duplicate');

        if (status != "") {
            duplicateStatus();
        }
        if (status == 2) {
            $('#edit-asset-record-member').prop('disabled', false);
            assetAvailability();
        } else {
            notAvailable.hide();
            $('#edit-button').prop('disabled', false);
            $('#edit-asset-record-member').prop('disabled', true);
        }
    });

//    $('#edit-asset-record-member').change(function (event) {
//        var memberEmpty = $('#memberEmpty');
//        var memberDoesNotExist = $('#memberDoesNotExist');
//        var member = $('#edit-asset-record-member').val();
//        var status = $('#edit-asset-record-status').val();
//        clearErrors();
//        if (member != "") {
//            memberEmpty.hide();
//            memberExists();
//        } else if (member == "") {
//            memberDoesNotExist.hide();
//            $('#edit-button').prop('disabled', false);
//        }
//    });

    $('#edit-button').click(function (event) {
        var notAvailable = $('#assetNotAvailable');
        var memberEmpty = $('#memberEmpty');
        var memberDoesNotExist = $('#memberDoesNotExist');
        var member = $('#edit-asset-record-member').val();
        var status = $('#edit-asset-record-status').val();
        notAvailable.hide();
        memberEmpty.hide();
        memberDoesNotExist.hide();
        clearErrors();
        event.preventDefault();


        if (member == "" && status == 2) {
            memberEmpty.show();
        }
        $.ajax(
                {
                    type: 'POST',
                    url: 'assetRecords',
                    data: JSON.stringify({
                        asset: {assetId: $('#edit-asset-record-asset-id').val()},
                        member: {userId: $('#edit-asset-record-member').val()},
                        employee: {userId: $('#edit-asset-record-employee').val()},
                        status: {statusId: $('#edit-asset-record-status').val()},
                        assetNote: $('#edit-asset-record-note').val()
                    }),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    'dataType': 'json'
                }).success(function (data, status) {
            clearErrors();
            notAvailable.hide();
            memberEmpty.hide();
            memberDoesNotExist.hide();
            $('#editStatusModal').modal('hide');
            loadRecords();
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
            url: 'search/assetRecords',
            data: JSON.stringify({
                asset: $('#search-asset-id').val(),
                category: $('#search-category').val(),
                description: $('#search-description').val(),
                status: $('#search-status').val(),
                lastName: $('#search-member-id').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function (data, status) {
            $('#search-asset-id').val('');
            $('#search-category').val('');
            $('#search-description').val('');
            $('#search-status').val('');
            $('#search-member-id').val('');
            fillRecordsTable(data, status);
        }).error(function (data, status) {
        });
    });

    $('#delete-asset-record-button').click(function (event) {

        event.preventDefault();
        var status = $('#edit-asset-record-status').val();
        var deleteError = $('#deleteError');
        if (status === '2') {
            alert("You cannot delete an asset that is 'CHECKED OUT'");
            $('#editStatusModal').modal('hide');
            loadRecords();
        } else if (status !== '2') {
            var answer = confirm("Do you really want to delete asset " + $('#edit-asset-record-asset-id').val() + " ?");
            if (answer === true) {
                $.ajax({
                    type: 'DELETE',
                    url: 'assetRecord/' + $('#edit-asset-record-asset-id').val()
                }).success(function () {
                    $('#editStatusModal').modal('hide');
                    alert("Asset successfully removed");
                    loadRecords();
                }).error(function (data, status) {
                    alert("error");
                });
            }
        }
    }
    );
});

function assetAvailability() {
    var notAvailable = $('#assetNotAvailable');
    notAvailable.hide();
    $.ajax({
        type: 'POST',
        url: 'checkAssetAvailability',
        data: JSON.stringify({
            asset: {assetId: $('#edit-asset-record-asset-id').val()},
            member: {userId: $('#edit-asset-record-member').val()},
            employee: {userId: $('#edit-asset-record-employee').val()},
            status: {statusId: $('#edit-asset-record-status').val()},
            assetNote: $('#edit-asset-record-note').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json'
    }).success(function (data, status) {

        if (data.available == false) {
            notAvailable.show();
            $('#edit-button').prop('disabled', true);
            $('#edit-asset-record-member').prop('disabled', true);
        } else {
            notAvailable.hide();
            $('#edit-button').prop('disabled', false);
            $('#edit-asset-record-member').prop('disabled', false);
        }
    }).error(function (data, status) {
        alert("Invalid input. Please try again");
    });
}

function memberExists() {
    var memberDoesNotExist = $('#memberDoesNotExist');
    memberDoesNotExist.hide();
    $.ajax({
        type: 'POST',
        url: 'checkMemberExists',
        data: JSON.stringify({
            asset: {assetId: $('#edit-asset-record-asset-id').val()},
            member: {userId: $('#edit-asset-record-member').val()},
            employee: {userId: $('#edit-asset-record-employee').val()},
            status: {statusId: $('#edit-asset-record-status').val()},
            assetNote: $('#edit-asset-record-note').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json'
    }).success(function (data, status) {
        if (data.available == false) {
            memberDoesNotExist.show();
            $('#edit-button').prop('disabled', true);
        } else {
            memberDoesNotExist.hide();
            $('#edit-button').prop('disabled', false);
        }
    }).error(function (data, status) {
        alert("Invalid input. Please try again");
    });
}

function duplicateStatus() {
    var duplicate = $('#duplicate');
    duplicate.hide();
    $.ajax({
        type: 'POST',
        url: 'checkDuplicateStatus',
        data: JSON.stringify({
            asset: {assetId: $('#edit-asset-record-asset-id').val()},
            member: {userId: $('#edit-asset-record-member').val()},
            employee: {userId: $('#edit-asset-record-employee').val()},
            status: {statusId: $('#edit-asset-record-status').val()},
            assetNote: $('#edit-asset-record-note').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json'
    }).success(function (data, status) {

        if (data.available == true) {
            duplicate.show();
            $('#edit-button').prop('disabled', true);
            $('#edit-asset-record-member').prop('disabled', true);
        } else {
            duplicate.hide();
            $('#edit-button').prop('disabled', false);
        }
    }).error(function (data, status) {
        alert("Invalid input. Please try again");
    });
}

function deleteAssetRecord(id) {
    var answer = confirm("Do you really want to delete record " + id + " ?");
    if (answer === true) {
        $.ajax({
            type: 'DELETE',
            url: 'assetRecord/' + id
        }).success(function () {
            $('#editStatusModal').modal('hide');
            alert("Asset successfully removed");
            loadRecords();
        });
    }
}

function loadRecords() {
    $.ajax({
        url: 'assetRecords'
    }).success(function (data, status) {
        fillRecordsTable(data, status);
    });
}


function fillRecordsTable(data, status) {

    clearAssetRecordsTable();

    var aTable = $('#assetRows');

    $.each(data, function (index, record) {
        var member = "";
        if (record.member != null) {
            member = record.member.firstName + " " + record.member.lastName;
        }
        aTable.append($('<tr>')
                .append($('<td>').text(record.asset.assetId))
                .append($('<td>').text(record.asset.category.name))
                .append($("<td class='hidden-xs'>").text(record.asset.brand))
                .append($("<td class='hidden-xs'>").text(record.asset.description))
                .append($('<td>').text(record.status.status))
                .append($('<td>').text(member))
                .append($('<td style="text-align: center">')
                        .append($('<a>')
                                .attr({'href': 'assets/' + record.asset.assetId}).attr({'target': '_self'}).text('View')
                                )
                        )
                .append($('<td style="text-align: center">')
                        .append($('<a>')
                                .attr({
                                    'data-asset-id': record.asset.assetId,
                                    'data-toggle': 'modal',
                                    'data-target': '#editStatusModal'
                                })
                                .text('Edit')
                                )
                        )
                );
    });
}

$('#editStatusModal').on('show.bs.modal', function (event) {
    $('#edit-button').prop('disabled', true);
    $('#edit-asset-record-member').prop('disabled', true);
    var duplicate = $('#duplicate');
    var notAvailable = $('#assetNotAvailable');
    var memberEmpty = $('#memberEmpty');
    var memberDoesNotExist = $('#memberDoesNotExist');
    var deleteError = $('#deleteError');
    $(this).find('form')[0].reset(); // resets modal
    var element = $(event.relatedTarget);
    var assetId = element.data('asset-id');
    var modal = $(this);
    
    notAvailable.hide();
    memberEmpty.hide();
    memberDoesNotExist.hide();
    duplicate.hide();
    deleteError.hide();

    $.ajax({
        url: 'assetRecord/' + assetId
    }).success(function (record) {
        clearErrors();
        notAvailable.hide();
        memberEmpty.hide();
        memberDoesNotExist.hide();
        modal.find('#edit-asset-record-asset-id').val(record.asset.assetId);
        modal.find('#edit-header-asset-record-asset-id').text(record.asset.assetId);
        modal.find('#edit-asset-record-member').val(record.member.userId);
        modal.find('#edit-asset-record-employee').val(record.employee.userId);
        modal.find('#edit-asset-record-status').val(record.status.statusId);
        modal.find('#edit-asset-record-note').val(record.assetNote);

    });
});


function clearErrors() {
    $('#validationErrors').empty();

    $('#validationErrorsEdit').empty();
}

function clearAssetRecordsTable() {
    $('#assetRows').empty();
}


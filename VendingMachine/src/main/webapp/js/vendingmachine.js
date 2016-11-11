/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('document').ready(function () {

    var money = 0;
    var deposit = 0;
    
    $("span.insertFunds").click(function () {
        money = parseFloat($(this).data("money-value"));
        deposit += money;
        $("#totalDeposit").val("$ " + deposit.toFixed(2));
//        saveDeposit();
    });

    $('#restock-button').click(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'stockInventory',
            data: JSON.stringify({
                inventory: $('#inventory').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json'
        }).success(function () {
            window.location.reload(true);
            alert("Inventory Restocked");
        }).error(function (data, status) {
            alert("Restock Not Available");
        });
    });
});

//function saveDeposit() {
//    var deposit = document.getElementById("totalDeposit").value;
//    if (deposit == "") {
//        return false;
//    }
//
//    localStorage.setItem("deposit", deposit);
////    location.reload();
////    return false;
//    return true;
//}






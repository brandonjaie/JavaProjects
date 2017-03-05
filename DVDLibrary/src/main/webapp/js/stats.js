// #1: document ready
$(document).ready(function () {
//    drawChart();
      drawChart2();
});
// #2: draw the bar chart
function drawChart() {
    // #3: dummy data - will be replaced with Ajax call
    var data = google.visualization.arrayToDataTable([
        ['Genre', '# DVDs'],
        ['Western', 10],
        ['Action', 11],
        ['Comedy', 6],
        ['Foreign', 18]
    ]);
    // #4: this sets up the size of the chart, the main title, and the axis titles
    var options = {
        title: 'DVDs By Genre',
        vAxis: {title: 'Title', titleTextStyle: {color: 'red'}},
        hAxis: {title: 'Num DVDs', titleTextStyle: {color: 'red'}},
        'width': 500,
        'height': 400
    };
    // #5: create a new BarChart object, handing it the div into which we want it to render
    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    // #6: tell the chart to draw itself
    chart.draw(data, options);
};

function drawChart2() {
    $.get('stats/chart').success(function (data) {
        // first, create the DataTable from the response data string.
        // our response string is generated on the server by converting the Java
        // Google DataTable object into a JSON string using the Google JsonRenderer object
        var dataTable = new google.visualization.DataTable(data); // set options for the chart - 
        //we're setting the main title, the verticle and horizontal 
        //axis labels and the height/width of the chart
                var options = {
                    title: 'DVDs By Genre',
                    vAxis: {title: 'Genre', titleTextStyle: {color: 'red'}},
                    hAxis: {title: 'Number of DVDs', titleTextStyle: {color: 'red'}}, 
                    'width': 500,
                    'height': 400
                }; 
                // create the chart (a BarChart here) - pass in the HTML element into which you want to render the chart - 
                // here it is the <div> called 'chartDiv'on the jQueryChart.jsp page
                var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        // finally, draw the chart
        chart.draw(dataTable, options);
    });
}


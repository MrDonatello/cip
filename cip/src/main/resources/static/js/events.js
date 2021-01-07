/*

    google.charts.load('current', {'packages':['timeline']});
    google.charts.setOnLoadCallback(drawChart);


function drawChart() {
    var container = document.getElementById('timeline');
    var chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();

    dataTable.addColumn({ type: 'string', id: ' Cip' });
    dataTable.addColumn({ type: 'string', id: 'Object' });
    dataTable.addColumn({ type: 'string', id: 'style', role: 'style' });
    dataTable.addColumn({ type: 'date', id: 'Start' });
    dataTable.addColumn({ type: 'date', id: 'End' });
    dataTable.addRows([
        ['CIP1', 'Щелочь', null, new Date(2020, 5, 5, 5, 5, 5), new Date(2020, 5, 5, 5, 10, 5) ],
        ['CIP1', 'Ополаскивание',null, new Date(2020, 5, 5, 5, 10, 35), new Date(2020, 5, 5, 5, 20, 5) ],
        ['CIP1', 'слив щелочи',null, new Date(2020, 5, 5, 5, 10, 5), new Date(2020, 5, 5, 5, 10, 35) ],
        ['CIP1', 'Танк32', '#cbb69d', new Date(2020, 5, 5, 5, 5, 5), new Date(2020, 5, 5, 5, 20, 5) ],


        ['CIP1', 'Щелочь', null, new Date(2020, 5, 5, 5, 21, 5 ),  new Date(2020, 5, 5, 5, 25, 5) ],


        ['CIP2', 'Щелочь', null,new Date(2020, 5, 5, 5, 10, 10),  new Date(2020, 5,5, 5,15,20) ],
        ['CIP2', 'Ополаскивание', null, new Date(2020, 5, 5, 5, 15, 20),  new Date(2020, 5,5, 5,20,20) ],
        ['CIP2', 'Л.П 21-30', '#obb695', new Date(2020, 5, 5, 5, 10, 10),  new Date(2020, 5,5, 5,20,20) ]

    ]);

    chart.draw(dataTable);
    google.visualization.events.addListener(chart, 'select', selectHandler(e));
}

function selectHandler(e) {
    alert('A table row was selected');

}/!*
function selectHandler() {
    var selectedItem = chart.getSelection()[0];
    if (selectedItem) {
        var value = data.getValue(selectedItem.row, 0);
        alert('The user selected ' + value);
    }
}
    *!/
*/

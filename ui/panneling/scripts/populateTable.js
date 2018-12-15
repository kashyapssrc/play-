var tableTemplate = function () {

    var httpRequest = new XMLHttpRequest();
    var entityId = event.target.id;
    httpRequest.open('GET', 'assets/' + entityId + '.json', true);
    httpRequest.send();
    httpRequest.onreadystatechange = function () {

        if(httpRequest.readyState == 4) {

            var obj = JSON.parse(httpRequest.responseText);
            constructTable(obj);
            formTemplate(obj);
            document.getElementById('table').rows[1].click();
        }
    }
}

var constructTable = function(obj) {

    var keys = Object.keys(obj[0]);
    var table = '<thead><tr>';
    for(i = 0; i < keys.length; i++) {
        table += '<th>' + keys[i] + '</th>'
    }

    table += '</tr></thead>'
    for(j = 0; j < obj.length; j++){

        var values = Object.values(obj[j]);
        table += '<tr onclick="parseRowDetails(this)">'

        for(i = 0; i < values.length; i++) {
            table += '<td>' 
                  + values[i] 
                  + '</td>'
        }
        table += '<td><button class="delete" type=button>delete</button></td></tr>'
    }
    document.getElementById('table').innerHTML = table;
}

var formTemplate = function (obj) {

    var keys = Object.keys(obj[0]);
    var values = Object.values(obj[0]);
    var body = '';
    for (i = 0; i < keys.length; i++) {

        body += '<label class="customize-label">'
             +  keys[i]
             +  ' : '
             +  '</label>'
             +  '<input class="textbox-prop" id="'
             +  keys[i]
             +  '" '
             +  'type="text" placeholder="'
             +  keys[i]
             +  '" '
             +  'tabindex="'
             +  i
             +  '"/>'
             +  '<button class="submit" type="button" onclick="operation()">'
             +  'save </button>' 
    }
    document.getElementById('form').innerHTML = body;
}


var parseRowDetails = function(row) {

    var table = document.getElementById('info-table');
    console.log(row);
    var header = table.rows[0];
    for(i = 0; i < row.cells.length - 1; i++) {

        var inputId = header.cells[i].innerHTML;
        console.log(inputId);
        document.getElementById(inputId).value = row.cells[i].innerHTML;
    }
    document.getElementById('id').disabled = true;
}

var operation = function() {

    var table = document.getElementById('table');
    if(document.getElementById('id').value == '') {

        var header = table.rows[0];
        var row = table.insertRow(table.rows.length);
        for(i = 0; i < header.cells.length; i++) {

            var inputId = header.cells[i].innerHTML;
            var inputData = document.getElementById(inputId).value;
            var cell = row.insertCell(i);
            cell.innerHTML = inputData;
        }
        var opCell = row.insertCell(row.cells.length);
        opCell.innerHTML = '<button class="delete" type="button">delete</button>'
    } else {

        var id = document.getElementById('id').value;
        console.log(id);
        var rows = table.rows;
        for(i = 1; i < rows.length; i++) {

            var entityId = rows[i].getElementsByTagName('td')[0].innerHTML;
            if(entityId == id) {

                var header = table.rows[0]
                for(j = 0; j < header.cells.length; j++) {

                    var inputId = header.cells[j].innerHTML;
                    var inputData = document.getElementById(inputId).value;
                    rows[i].cells[j].innerHTML = inputData;
                }
            }
        }
    }
}

var reset = function () {
    document.getElementById('form').reset();
}

document.getElementById('person').click();
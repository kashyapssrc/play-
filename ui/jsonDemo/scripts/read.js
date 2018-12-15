var personDetails = function () {
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", "assets/person.json", true);
    httpRequest.send();
    httpRequest.onreadystatechange = function () {

        if(httpRequest.readyState == 4 ) {

            var personJson = JSON.parse(httpRequest.responseText);
                document.getElementById("firstname").innerHTML = personJson[0].firstname;
                document.getElementById("lastname").innerHTML = personJson[0].lastname;
        }
    }
}

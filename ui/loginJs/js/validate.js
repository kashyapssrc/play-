var validate = function(){

    var userName = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();
    if(!userName || !password){
        alert("enter missing field(s)");
    } else {
        alert("logged in successfully");
        window.location = "../ofmc/index.html";
    }
}    

var i = 0;
var reset = function() {
    i = 0;
}
var increment = function() {
    document.getElementById('number').innerHTML = i;
    i++;
}
var interval = setInterval('increment()', 5000);



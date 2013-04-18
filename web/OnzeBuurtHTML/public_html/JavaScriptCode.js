var url = "http://localhost:8080/onzebuurt/resources";
/*var request = new XMLHttpRequest();
request.open("GET", url);

request.onload = function()
{
    if(request.status ===200){
        alert(request.responseText);
    }
}
request.send(null);*/
window.onload=function()
{
    
    var request = new XMLHttpRequest();
  
    request.onload = function(){
        if(request.status ===200){
            updateEvent(request.responseText);
        }
    };
      request.open("GET", url + "/event");
    request.send(null);  

};
function updateEvent(responseText){
        var eventDiv = document.getElementById("melding");
       // eventDiv.innerHTML = responseText;
        var events = JSON.parse(responseText);
        for (var i = 0; i < events.length; i++){
            var event = events[i];
            var div = document.createElement("div");
            div.setAttribute("class","eventItem");
            div.innerHTML = "Het eventId is " + event.meldingId + " en titel is " + event.titel + ". De omschrijving is " + event.omschrijving;
            eventDiv.appendChild(div);
        }
    }
   
   
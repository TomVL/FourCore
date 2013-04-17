


function showdatepicker() {
$( "#datepicker" ).datepicker();
};
var map;
function getLocation(){
if(navigator.geolocation) {
var markersArray = [];
function hasPosition(position) {
var point = new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
myOptions = {
zoom: 20,
center: point,
mapTypeId: google.maps.MapTypeId.HYBRID,
mapTypeControl:true,
zoomControl: true,
zoomControlOptions:{style:google.maps.ZoomControlStyle.SMALL},
      mapTypeControlOptions: {
      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU}

},
map = new google.maps.Map(document.getElementById("mapcanvas"), myOptions),
marker = new google.maps.Marker({
position: point,
icon: "images/youIcon.png",
map: map,
title: "U bent hier"
})
google.maps.event.addListener(map, 'click', function(event){
deleteOverlays();
document.getElementById("latFld").value = event.latLng.lat();
document.getElementById("lngFld").value = event.latLng.lng();
var markerEvent_position = event.latLng;
markerEvent = new google.maps.Marker({
map: map,
draggable: false,
title:"Mijn event",
icon:"images/smallIconEvent.png"
});
markersArray.push(markerEvent);
markerEvent.setPosition(markerEvent_position);
// INFOWINDOW SHIZZLE

	var contentstring ='<div id=\"insInfowindow\"><form class="contact_form" action="scripts/mailer.php" method="POST" name="contact_form"><ul><li><h2>Event toevoegen</h2><span class="required_notification">* Moet ingevuld zijn</span></p> </li><li><label for="titel">*Titel:</label><input type="text"  placeholder="Naam voor event" required /></li><li><label for="datum">*Datum:</label><input onclick="showdatepicker"id="datepicker" class="hasDatepicker" type="text" placeholder="MM-DD-YYYY" required /> </li><li><label for="locatie">*Locatie:</label><input id="locatie"type="text"  placeholder="Locatie" required /></li><li><label for="omschrijving">*Omschrijving:</label><textarea name="message" cols="40" rows="6" required ></textarea></li><p><img src="images/noImage.jpg"/></p><li><button class="submit" type="submit">Toevoegen</button></li></ul></div></form>';
	/*var infowindow = new google.maps.InfoWindow ({
		content: contentstring
})*/
  google.maps.event.addListener(markerEvent,'click',function(){
  infowindow = new google.maps.InfoWindow({
	  content: contentstring});
	  
	  infowindow.open(map,markerEvent); 
  });
})
//EINDE INFOWINDOW SHIZZLE

 
}
// Deletes all markers in the array by removing references to them
function deleteOverlays() {
if (markersArray) {
for (i in markersArray) {
markersArray[i].setMap(null);
}
markersArray.length = 0;
}
}
}
navigator.geolocation.getCurrentPosition(hasPosition);
}
window.onload = getLocation();
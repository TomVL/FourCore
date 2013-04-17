var map;
var geocoder;
function getLocation()
  {
  if (navigator.geolocation)
    {
    navigator.geolocation.getCurrentPosition(showPosition,showError);
    }
  else{x.innerHTML="Geolocation is niet ondersteund op deze browser.";}
  }

function showPosition(position)
  {
  lat=position.coords.latitude;
  lon=position.coords.longitude;
  latlon=new google.maps.LatLng(lat, lon);
  mapcanvas=document.getElementById('mapcanvas');

  var myOptions={
  center:latlon,zoom:20,
  mapTypeId:google.maps.MapTypeId.HYBRID,
  mapTypeControl:true,
  zoomControl: true,
  zoomControlOptions:{style:google.maps.ZoomControlStyle.SMALL},
      mapTypeControlOptions: {
      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
    }
  };
  
  
 map=new google.maps.Map(document.getElementById("mapcanvas"),myOptions);
 geocoder= new google.maps.Geocoder();
  var marker_0=new google.maps.Marker({
	  position:latlon,
	  map:map,
	  title:"Mijn event",
	  draggable:true,
	  icon:"images/smallSituatieIcon.png",
	  })
	  
	var contentstring = '<div id=\"insInfowindow\"><img src="images/noImage.jpg"/><h1>Titel Test</h1><p>Paragraaf test</p></div>';
	/*var infowindow = new google.maps.InfoWindow ({
		content: contentstring
})*/
  google.maps.event.addListener(marker_0,'click',function(){ if(typeof infowindow != 'undefined') infowindow.close();
  infowindow = new google.maps.InfoWindow({
	  content: contentstring});
	  
	  infowindow.open(map,marker_0); 
  });
  }
function showError(error)
  {
  switch(error.code) 
    {
    case error.PERMISSION_DENIED:
      x.innerHTML="User denied the request for Geolocation."
      break;
    case error.POSITION_UNAVAILABLE:
      x.innerHTML="Location information is unavailable."
      break;
    case error.TIMEOUT:
      x.innerHTML="The request to get user location timed out."
      break;
    case error.UNKNOWN_ERROR:
      x.innerHTML="An unknown error occurred."
      break;
    }	
	
}

window.onload = getLocation();
  

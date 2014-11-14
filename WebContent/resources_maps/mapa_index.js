var map;
var idInfoBoxAberto;
var infoBox = [];
var markers = [];

function initialize() {	
	
	var latitude = document.getElementById('formMaps:latitude').getAttribute("value");
	var longitude = document.getElementById('formMaps:longitude').getAttribute("value");
	
	var latlng = new google.maps.LatLng(latitude, longitude);
	
    var options = {
        zoom: 5,
		center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("mapa"), options);
}

initialize();

function abrirInfoBox(id, marker) {
	if (typeof(idInfoBoxAberto) == 'number' && typeof(infoBox[idInfoBoxAberto]) == 'object') {
		infoBox[idInfoBoxAberto].close();
	}

	infoBox[id].open(map, marker);
	idInfoBoxAberto = id;
}

function carregarPontos() {
	
	$.getJSON('ServletPontosMapa', function(pontos) {
		
		var latlngbounds = new google.maps.LatLngBounds();
		
		$.each(pontos, function(index, ponto) {
			
			var marker = new google.maps.Marker({
				position: new google.maps.LatLng(ponto.latitude, ponto.longitude),
				title: ponto.endereco,
				icon: 'resources_maps/marcador.png'
			});
			
			//marcador apartamento
			//marcador_casa casa
			
			if(ponto.tipo == "Casa"){
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(ponto.latitude, ponto.longitude),
					title: ponto.endereco,
					icon: 'resources_maps/marcador_casa.png'
				});
			}
			
			var myOptions = {
				content: "<a href=\"coments.xhtml?id="+ponto.id+"\">" + ponto.endereco + "</a>",
				pixelOffset: new google.maps.Size(-150, 0),
				infoBoxClearance: new google.maps.Size(1, 1)
        	};

			infoBox[ponto.id] = new InfoBox(myOptions);
			infoBox[ponto.id].marker = marker;
			
			infoBox[ponto.id].listener = google.maps.event.addListener(marker, 'click', function (e) {
				abrirInfoBox(ponto.id, marker);
			});
			
			markers.push(marker);
			
			latlngbounds.extend(marker.position);
			
		});
		
		var markerCluster = new MarkerClusterer(map, markers);
		
		map.fitBounds(latlngbounds);
		
	});
	
}

carregarPontos();
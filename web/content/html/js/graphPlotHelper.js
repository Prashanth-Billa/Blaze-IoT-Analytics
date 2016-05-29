function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
        function(m,key,value) {
            vars[key] = value;
        });
    return vars;
}

var sensorType = getUrlVars()['sensorType'];

if("seismic" == sensorType){
    Plotly.d3.csv('./../CSV/seismicIntensity.csv', function(err, rows){
        function unpack(rows, key) {
            return rows.map(function(row) { return row[key]; });
        }
        var cityName = unpack(rows, 'name'),
            cityPop = unpack(rows, 'val'),
            cityLat = unpack(rows, 'lat'),
            cityLon = unpack(rows, 'lon'),
            color = [,"rgb(255,65,54)","rgb(133,20,75)","rgb(255,133,27)","lightgrey"],
            citySize = [],
            hoverText = [],
            scale = 50000;

        for ( var i = 0 ; i < cityPop.length; i++) {
            var currentSize = cityPop[i] / scale;
            var currentText = cityName[i] + "<br>Population: " + cityPop[i];
            citySize.push(currentSize);
            hoverText.push(currentText);
        }

        var data = [{
            type: 'scattergeo',
            locationmode: 'USA-states',
            lat: cityLat,
            lon: cityLon,
            text: hoverText,
            hoverinfo: 'text',
            marker: {
                size: citySize,
                line: {
                    color: 'black',
                    width: 2
                },

            }
        }];

        var layout = {
            title: 'Seismic Sensor Plot',
            showlegend: false,
            geo: {
                scope: 'usa',
                projection: {
                    type: 'albers usa'
                },
                showland: true,
                landcolor: 'rgb(217, 217, 217)',
                subunitwidth: 1,
                countrywidth: 1,
                subunitcolor: 'rgb(255,255,255)',
                countrycolor: 'rgb(255,255,255)'
            },
        };

        Plotly.plot(myDiv, data, layout, {showLink: false});
    });
}else if("humidity" == sensorType){
    alert("humidity");
}else if("fall-detection" == sensorType){
    alert("fall-detection");
}else if("air-pollution" == sensorType){
    alert("air pollution");
}else if("traffic" == sensorType){
    alert("traffic");
}else if("cross-correlation" == sensorType){
    alert("cross-correlation");
}else{
    ;
}


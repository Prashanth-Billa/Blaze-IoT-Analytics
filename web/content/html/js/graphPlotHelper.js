function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
        function(m,key,value) {
            vars[key] = value;
        });
    return vars;
}

var sensorType = getUrlVars()['sensorType'];
var sensorAction = getUrlVars()['sensor-action-value'];
if("seismic" == sensorType){
    Plotly.d3.csv('./../CSV/seismicIntensity.csv', function(err, rows){
        function unpack(rows, key) {
            return rows.map(function(row) { return row[key]; });
        }
        var cityName = unpack(rows, 'name'),
            intensity = unpack(rows, 'val'),
            cityLat = unpack(rows, 'lat'),
            cityLon = unpack(rows, 'lon'),
            color = [,"rgb(255,65,54)","rgb(133,20,75)","rgb(255,133,27)","lightgrey"],
            citySize = [],
            hoverText = [],
            scale = 50000;

        for ( var i = 0 ; i < intensity.length; i++) {
            var currentSize = intensity[i] / scale;
            var currentText = cityName[i] + "<br>Intensity: " + intensity[i];
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

    if(sensorAction == 0){
        var margin = {top: 20, right: 20, bottom: 70, left: 40},
            width = 600 - margin.left - margin.right,
            height = 300 - margin.top - margin.bottom;
        var parseDate = d3.time.format("%Y-%m").parse;

        var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

        var y = d3.scale.linear().range([height, 0]);

        var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom")
            .tickFormat(d3.time.format("%Y-%m"));

        var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left")
            .ticks(10);

        var svg = d3.select("#tab-1").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform",
                "translate(" + margin.left + "," + margin.top + ")");

        d3.csv("./../CSV/airPollution_1.csv", function(error, data) {

            data.forEach(function(d) {
                d.date = parseDate(d.date);
                d.value = +d.value;
            });

            x.domain(data.map(function(d) { return d.date; }));
            y.domain([0, d3.max(data, function(d) { return d.value; })]);

            svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .selectAll("text")
                .style("text-anchor", "end")
                .attr("dx", "-.8em")
                .attr("dy", "-.55em")
                .attr("transform", "rotate(-90)" );

            svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Pollution Level");

            svg.selectAll("bar")
                .data(data)
                .enter().append("rect")
                .style("fill", "steelblue")
                .attr("x", function(d) { return x(d.date); })
                .attr("width", x.rangeBand())
                .attr("y", function(d) { return y(d.value); })
                .attr("height", function(d) { return height - y(d.value); });

        });
    }else if(sensorAction == 1){
        var width = 960,
            height = 500,
            radius = Math.min(width, height) / 2;
        var color = d3.scale.ordinal()
            .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);
        var arc = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
        var labelArc = d3.svg.arc()
            .outerRadius(radius - 40)
            .innerRadius(radius - 40);
        var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.level; });
        var svg = d3.select("body").append("svg")
            .attr("width", width)
            .attr("height", height)
            .append("g")
            .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
        d3.csv("./../CSV/airPollution_2.csv", type, function(error, data) {
            if (error) throw error;
            var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc");
            g.append("path")
                .attr("d", arc)
                .style("fill", function(d) { return color(d.data.pollutant); });
            g.append("text")
                .attr("transform", function(d) { return "translate(" + labelArc.centroid(d) + ")"; })
                .attr("dy", ".35em")
                .text(function(d) { return d.data.pollutant; });
        });
        function type(d) {
            d.level = +d.level;
            return d;
        }
    }

}else if("traffic" == sensorType){
    alert("traffic");
}else if("cross-correlation" == sensorType){
    if(sensorAction == 0){
        var margin = {top: 20, right: 20, bottom: 30, left: 40},
            width = 960 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

        var x0 = d3.scale.ordinal()
            .rangeRoundBands([0, width], .1);

        var x1 = d3.scale.ordinal();

        var y = d3.scale.linear()
            .range([height, 0]);

        var color = d3.scale.ordinal()
            .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);

        var xAxis = d3.svg.axis()
            .scale(x0)
            .orient("bottom");

        var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left")
            .tickFormat(d3.format(".2s"));

        var svg = d3.select("#tab-1").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        d3.csv("./../CSV/airPollution_traffic.csv", function(error, data) {
            if (error) throw error;

            var ageNames = d3.keys(data[0]).filter(function(key) { return key !== "State"; });

            data.forEach(function(d) {
                d.ages = ageNames.map(function(name) { return {name: name, value: +d[name]}; });
            });

            x0.domain(data.map(function(d) { return d.State; }));
            x1.domain(ageNames).rangeRoundBands([0, x0.rangeBand()]);
            y.domain([0, d3.max(data, function(d) { return d3.max(d.ages, function(d) { return d.value; }); })]);

            svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis);

            svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Population");

            var state = svg.selectAll(".state")
                .data(data)
                .enter().append("g")
                .attr("class", "state")
                .attr("transform", function(d) { return "translate(" + x0(d.State) + ",0)"; });

            state.selectAll("rect")
                .data(function(d) { return d.ages; })
                .enter().append("rect")
                .attr("width", x1.rangeBand())
                .attr("x", function(d) { return x1(d.name); })
                .attr("y", function(d) { return y(d.value); })
                .attr("height", function(d) { return height - y(d.value); })
                .style("fill", function(d) { return color(d.name); });

            var legend = svg.selectAll(".legend")
                .data(ageNames.slice().reverse())
                .enter().append("g")
                .attr("class", "legend")
                .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

            legend.append("rect")
                .attr("x", width - 18)
                .attr("width", 18)
                .attr("height", 18)
                .style("fill", color);

            legend.append("text")
                .attr("x", width - 24)
                .attr("y", 9)
                .attr("dy", ".35em")
                .style("text-anchor", "end")
                .text(function(d) { return d; });

        });
    }
}else{
    ;
}


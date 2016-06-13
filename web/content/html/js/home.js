$(document).ready(function() {
    $(".tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

function populateSensorActionsList(actions){

    document.getElementById('sensor-actions').innerHTML = "";

    var index = 0;

    for(ele in actions)
    {
        var opt = document.createElement("option");
        opt.value= index;
        opt.innerHTML = actions[index];
        document.getElementById("sensor-actions").appendChild(opt);
        index++;
    }
    
    document.getElementById("sensorActionFormId").lastElementChild.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.firstElementChild.appendChild(opt);
}

function showSensorActions(){
    var value = document.getElementById("select-sensor").value;

    if("fall-detection" == value){

        var actions = ["What are the falls happening in a given region", "What is the region with the major number of falls", "Which time region(3to4hours slot) are falls happening", "How is the severity of the falls spread"];
        populateSensorActionsList(actions);

    }else if("seismic" == value){

        var actions = ["Find high intensity regions", "Find most active regions", "What is the crossover between above three cases (top 20 cities)"];
        populateSensorActionsList(actions);

    }else if("humidity" == value){

        var actions = ["How many events with high priority value are happening", "What is the overall event rate of the humidity system"];
        populateSensorActionsList(actions);

    }else if("air-pollution" == value){

        var actions = ["Show Air Pollution Levels in 2006", "Show the overall levels of pollutants", "Find city with maximum of all polutants combined", "Find maximum emitted pollutant in all cities combined"];
        populateSensorActionsList(actions);

    }else if("traffic" == value){

        var actions = ["Find most congested cities", "Find congestion spread in day", "Find accident spread in day"];
        populateSensorActionsList(actions);

    }else if("cross-correlation" == value){

        var actions = ["Find Pollutant level of each pollutant and traffic congestion in each state", "Find most congested cities and relate with high intensity and large duration seismic activities"];
        populateSensorActionsList(actions);

    }else{
        document.getElementById('sensor-actions').innerHTML = "";
    }
}

function checkSensorSelected() {
    var value = document.getElementById("select-sensor").value;
    if(value == "empty"){
        alert("Please select a sensor!");
        return;
    }
}

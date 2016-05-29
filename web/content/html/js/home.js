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

        var actions = ["Which regions are active", "Which regions have large tremors", "Which regions have large duration for tremors", "What is the crossover between above three cases"];
        populateSensorActionsList(actions);

    }else if("humidity" == value){

        var actions = ["How many high priority class events are happening", "How many events with priority value = 10 are happening", "How frequently sprinkleron events are occuring", "What is the overall event rate of the humidity system"];
        populateSensorActionsList(actions);

    }else if("air-pollution" == value){

        var actions = ["a1", "a2", "a3", "a4"];
        populateSensorActionsList(actions);

    }else if("traffic" == value){

        var actions = ["b1", "b2", "b3", "b4"];
        populateSensorActionsList(actions);

    }else if("cross-correlation" == value){

        var actions = ["c1", "c2", "c3", "c4"];
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

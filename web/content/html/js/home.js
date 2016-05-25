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

function showSensorActions(){
    var value = document.getElementById("select-sensor").value;
    var sensorActionSelect = document.getElementById("sensor-actions");

    var index = 0;
    if("fall-detection" == value){

        document.getElementById('sensor-actions').innerHTML = "";
        var actions = ["What are the falls happening in a given region", "What is the region with the major number of falls", "Which time region(3to4hours slot) are falls happening", "How is the severity of the falls spread"];
        for(ele in actions)
        {
            var opt = document.createElement("option");
            opt.value= index;
            opt.innerHTML = actions[index];
            sensorActionSelect.appendChild(opt);
            index++;
        }
    }else if("seismic" == value){


        document.getElementById('sensor-actions').innerHTML = "";
        var actions = ["Which regions are active", "Which regions have large tremors", "Which regions have large duration for tremors", "What is the crossover between above three cases"];
        for(ele in actions)
        {
            var opt = document.createElement("option");
            opt.value= index;
            opt.innerHTML = actions[index];
            sensorActionSelect.appendChild(opt);
            index++;
        }
    }else if("humidity" == value){


        document.getElementById('sensor-actions').innerHTML = "";
        var actions = ["How many high priority class events are happening", "How many events with priority value = 10 are happening", "How frequently sprinkleron events are occuring", "What is the overall event rate of the humidity system"];
        for(ele in actions)
        {
            var opt = document.createElement("option");
            opt.value= index;
            opt.innerHTML = actions[index];
            sensorActionSelect.appendChild(opt);
            index++;
        }

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

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
        var actions = ["Action1_1", "Action2_1", "Action3_1"];
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
        var actions = ["Action2_1", "Action2_2", "Action2_3"];
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
        var actions = ["Action3_1", "Action3_2", "Action3_3"];
        for(ele in actions)
        {
            var opt = document.createElement("option");
            opt.value= index;
            opt.innerHTML = actions[index];
            sensorActionSelect.appendChild(opt);
            index++;
        }

    }
}

function checkSensorSelected() {
    var value = document.getElementById("select-sensor").value;
    if(value == "empty"){
        alert("Please select a sensor!");
        return;
    }
}
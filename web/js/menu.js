document.addEventListener('DOMContentLoaded', document.getElementById("kategorii").addEventListener("click"),show("dmenu"));

function show(name) {
    if (document.getElementById(name).style.transform == "scaleY(1)") {
        document.getElementById(name).style.transform = "scaleY(0)";
        console.log(document.getElementById(name).style.transform);
    } else {
        document.getElementById(name).style.transform = "scaleY(1)";
    }

}
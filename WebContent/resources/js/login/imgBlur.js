addOnLoad(makeBackgroundBlur("loginTable", 0.4));
addOnLoad(makeBackgroundBlur("homeDiv", 1));
addOnLoad(makeBackgroundBlur("tableDiv", 1));
addOnLoad(errorAlert("error"));
addOnLoad(loginBackgroundFull());

function makeBackgroundBlur(id, opacity) {
	var element = document.getElementById(id);
	if(element == null) return;
	var img = document.createElement("img");
	img.setAttribute("src", "/ballade/resources/img/blank.png");
	img.style.opacity = opacity;
	width = element.offsetWidth + 34;
	height = element.offsetHeight + 20;
	img.style.width = width + "px";
	img.style.height = height + "px";
	//img.style.position = "absolute";
	//img.style.top = "0px";
	//img.style.left = "0px";
	var parent = element.parentNode;
	var div = document.createElement("div");
	div.appendChild(img);
	parent.insertBefore(div, element);
	element.parentNode = div;
	div.appendChild(element);
	div.style.position = "relative";
	element.style.position = "absolute";
	element.style.top = "10px";
	element.style.left = "17px";
}

function errorAlert(id) {
	var error = document.getElementById(id);
	if(error == null) return;
	alert(error.firstChild.nodeValue);
}

function loginBackgroundFull() {
	var form = document.getElementById("loginForm");
	if(form != null) {
		var clientHeight = document.documentElement.clientHeight;
		var formHeight = form.offsetHeight;
		form.style.marginTop = (clientHeight - formHeight) / 2 + "px";
		var maxHeight = 0;
		if(formHeight < clientHeight) {
			maxHeight = clientHeight;
		} else {
			maxHeight = formHeight;
		}
		document.getElementsByTagName("body")[0].style.backgroundSize = "100% " + maxHeight +"px";
	}
}

function addOnLoad(func) {
	var oldOnLoad = window.onload;
	if((typeof oldOnLoad) != "function") {
		window.onload = func;
	} else {
		window.onload = function() {
			oldOnLoad();
			func();
		};
	}
}
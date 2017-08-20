addOnLoad(putLeft());

function putLeft() {
	var left = document.getElementById("left");
	var right = document.getElementById("right");
	var leftWidth = left.offsetWidth;
	var rightWidth = right.offsetWidth;
	if(leftWidth > (document.body.clientWidth - rightWidth - 40)) {
		right.style.width = leftWidth + "px";
		right.style.height = "150px";
		var homeDiv = document.getElementById("homeDiv");
		homeDiv.style.width = leftWidth + "px";
		homeDiv.style.height = document.getElementById("funcTable").offsetHeight + "100" + "px";
		var table = document.getElementById("funcTable");
		table.style.marginTop = "0px";
		left.style.position = "absolute";
		left.style.top = right.offsetHeight + 100 + "px";
		right.style.position = "absolute";
		right.style.left = left.offsetLeft + "px";
		left.style.left = left.offsetLeft + 20 + "px";
		var allHeight = left.offsetTop + left.offsetHeight + 17;
		document.getElementsByTagName("body")[0].style.backgroundSize = "135% " + allHeight + "px";
		document.getElementById("calendarForm").style.marginLeft = "0px";
	} else {
		containerCenter();
	}
}

function containerCenter() {
	var left = document.getElementById("left");
	left.style.float = "none";
	var right = document.getElementById("right");
	var container = document.getElementById("container");
	container.style.position = "relative";
	var leftWidth = left.offsetWidth;
	var rightWidth = right.offsetWidth;
	var spaceWidth = 17;
	var spaceHeight = 17;
	container.style.width = leftWidth + spaceWidth + rightWidth + 40 + "px";
	container.style.marginLeft = "auto";
	container.style.marginRight = "auto";
	left.style.position = "absolute";
	left.style.left = "0px";
	right.style.position = "absolute";
	right.style.left = leftWidth + spaceWidth + 40 + "px";
	var maxHeight = 0;
	var leftTop = getTop(left);
	var leftHeight = left.offsetHeight + leftTop;
	var rightHeight = right.offsetHeight + leftTop;
	if(leftHeight > rightHeight) {
		maxHeight = leftHeight;
	} else {
		maxHeight = rightHeight;
	}
	var clientHeight = document.documentElement.clientHeight;
	if(maxHeight < clientHeight) {
		maxHeight = clientHeight;
	} else {
		left.style.marginBottom = "80px";
		maxHeight += 100;
	}
	document.getElementsByTagName("body")[0].style.backgroundSize = "100% " + maxHeight +"px";
}

function getTop(element) {
	var top = 0;
	while(element != null) {
		top += element.offsetTop;
		element = element.offsetParent;
	}
	return top;
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
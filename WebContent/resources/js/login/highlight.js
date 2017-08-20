addOnLoad(highlight("scoreTr", "#ED0B35"))

function highlight(className, fontColor) {
	var trs = document.getElementsByClassName(className);
	for(var i = 0; i < trs.length; i ++) {
		var tr = trs[i];
		tr.onmouseover = function() {
			this.style.color = fontColor;
		}
		tr.onmouseout = function() {
			this.style.color = "black";
		}
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
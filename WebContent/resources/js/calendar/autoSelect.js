addOnLoad(autoGet());

function autoGet() {
	/*
	var select = document.getElementsByTagName("select")[0];
	select.oninput = function() {
		window.location.href = "/ballade/home/calendar?week=" + select.value;
	};
	*/
	var select = document.getElementsByTagName("select")[0];
	select.onchange = function() {
		window.location.href = "/ballade/home/calendar?week=" + select[select.selectedIndex].value;
	};
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
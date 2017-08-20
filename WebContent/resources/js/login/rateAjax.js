addOnLoad(post());

function post() {
	document.getElementById("submit").onclick = function() {
		var selects = document.getElementsByTagName("select");
		var inputs = document.getElementsByTagName("input");
		var texts = new Array();
		var j = 0;
		for(var i = 0; i < inputs.length; i ++) {
			if(inputs[i].getAttribute("type") == "text") texts[j ++] = inputs[i];
		}
		var textareas = document.getElementsByTagName("textarea");
		var postData = "";
		for(var i = 0; i < texts.length; i ++) {
			for(var q = 0; q < 7; q ++) {
				if(q < 5) {
					var select = selects[i * 5 + q];
					postData += select.getAttribute("name") + "=" + select.value + "&";
				}
				if(q == 5) {
					var text = texts[i];
					postData += text.getAttribute("name") + "=" + text.value + "&";
				}
				if(q == 6) {
					var words = textareas[i];
					if(i != texts.length - 1) {
						postData += words.getAttribute("name") + "=" + words.value + "&";
					} else {
						postData += words.getAttribute("name") + "=" + words.value;
					}
				}
			}
		}
		var tableDiv = document.getElementById("tableDiv");
		changeTable(tableDiv);
		var span = document.createElement("span");
		span.setAttribute("id", "title");
		span.innerHTML = "评教";
		tableDiv.appendChild(span);
		var table = document.createElement("table");
		tableDiv.appendChild(table);
		table.setAttribute("id", "finishTable");
		var tr = document.createElement("tr");
		table.appendChild(tr);
		var imgTd = document.createElement("td");
		var aTd = document.createElement("td");
		tr.appendChild(imgTd);
		tr.appendChild(aTd);
		var img = document.createElement("img");
		img.setAttribute("src", "/ballade/resources/img/wait.png");
		img.setAttribute("id", "okImg");
		imgTd.appendChild(img);
		var txt = document.createElement("span");
		txt.setAttribute("id", "txt");
		aTd.appendChild(txt);
		txt.innerHTML = "正在上传数据，请稍候";
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("GET", "/ballade/home/rate/send?" + postData, true);
		xmlhttp.send();
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var img = document.getElementById("okImg");
				addA(img);
				img.setAttribute("src", "/ballade/resources/img/ok.png");
				var txt = document.getElementById("txt");
				addA(txt);
				txt.innerHTML = "评教成功，点击查看结果";
			}
		}
	};
}

function addA(element) {
	var td = element.parentNode;
	td.removeChild(element);
	var a = document.createElement("a");
	a.appendChild(element);
	td.appendChild(a);
	a.setAttribute("href", "/ballade/home/rate");
	a.setAttribute("id", "return");
}

function changeTable(tableDiv) {
	var oldChildren = tableDiv.childNodes;
	for(var i = 0; i < oldChildren.length; i ++) {
		child = oldChildren[i];
		if(child.nodeType == 1) {
			child.style.display = "none";
			tableDiv.removeChild(child);
		}
	}
	var button = document.getElementsByClassName("calendarInfo")[0];
	button.parentNode.removeChild(button);
	var brothers = tableDiv.parentNode.childNodes;
	for(var i = 0; i < brothers.length; i ++) {
		var brother = brothers[i];
		if(brother.nodeName == "img" || brother.nodeName == "IMG") brother.style.height = "300px";
	}
	document.getElementsByTagName("body")[0].style.backgroundSize = "100% " + document.documentElement.clientHeight + "px";
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
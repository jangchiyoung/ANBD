/*<![CDATA[*/
function c_listOpen() {
	var category = document.getElementById("c_list");
		category.style.display = 'block';
	document.getElementById("open").setAttribute("onclick", "c_listCansle()");	
	document.getElementById("open").setAttribute("id", "cansle");	
}
function c_listCansle() {
	var category = document.getElementById("c_list");
		category.style.display = 'none';
	document.getElementById("cansle").setAttribute("onclick", "c_listOpen()");	
	document.getElementById("cansle").setAttribute("id", "open");	
}
/*]]>*/
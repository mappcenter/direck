 function showHideDiv(id){
  var obj = document.getElementById(id);
    if (obj.style.display=="none"){
      obj.style.display='block';
    } else if(obj.style.display=="block"){
      obj.style.display='none';
    }
}

 function showHideDiv1(id){
  var vi = document.getElementById("vi");
  var en = document.getElementById("en");
  if (id=="en") {
		vi.style.display="none";
		en.style.display="block"
  }	else {
        en.style.display="none";
		vi.style.display="block"
  }
}
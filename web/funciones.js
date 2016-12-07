var txt=" ";

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
   		if (this.readyState == 4 && this.status == 200) {

        myFunction(this);
   		 }
		};
		xhttp.open("GET", "grupos.xml", true);
		xhttp.send();
		function myFunction(xml) {

		var  y, i,yLen, xmlDoc;
    	xmlDoc = xml.responseXML;
		y = xmlDoc.getElementsByTagName("grupo");
    	yLen = y.length;
    	  var x = document.getElementById("grupo");
    	for (i = 0; i < yLen; i++) { 
        	
		   	var option = document.createElement("option");
		    option.text =y[i].childNodes[0].nodeValue;
		    x.add(option);
        }
       	}
      
function nuevoGrupo()
		{
                
			var estado=document.getElementById("boton").value;
			if(estado==0)
			{
			document.getElementById("grupo").disabled=true;
			document.getElementById("nuevo").style.display='block';
			document.getElementById("boton").value=1;
                        document.getElementById("bandera").value=1;
			}
			else if(estado==1)
			{
				document.getElementById("nuevo").style.display='none';
				document.getElementById("grupo").disabled=false;
				document.getElementById("boton").value=0;
                                document.getElementById("bandera").value=0;
                               
			}

		}
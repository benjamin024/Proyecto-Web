function archivo(evt) {
                  var files = evt.target.files; // FileList object
             
                  // Obtenemos la imagen del campo "file".
                  for (var i = 0, f; f = files[i]; i++) {
                    //Solo admitimos imágenes.
                    if (!f.type.match('image.*')) {
                        continue;
                    }
             
                    var reader = new FileReader();
             
                    reader.onload = (function(theFile) {
                        return function(e) {
                          // Insertamos la imagen
                         document.getElementById("list").innerHTML = ['<img class="thumb" src="', e.target.result,'" title="', escape(theFile.name), '"/>'].join('');
                        };
                    })(f);
             
                    reader.readAsDataURL(f);
                  }
              }
document.getElementById('files').addEventListener('change', archivo, false);

var cont = 1;

function agregaPregunta(){
  document.getElementById("menos").style.display = "block";
  cont++;
  document.getElementById("cuerpoTabla").innerHTML += "<tr id=fila"+cont+"><td>"+cont+"</td><td><input type=text class=form-control name=pregunta"+cont+" required/></td><td>\n\
<input type=text  class=form-control  name=correcta"+cont+" required/></td>\n\
<td><input type=text  class=form-control  name=respA"+cont+" required/></td>\n\
<td><input type=text  class=form-control  name=respB"+cont+" required/></td></tr>";
  if(cont >= 5)
    document.getElementById("mas").style.display = "none";
}

function eliminaPregunta(){
  document.getElementById("mas").style.display = "block";
  var padre = document.getElementById("fila"+cont).parentNode;
  padre.removeChild(document.getElementById("fila"+cont));
  cont--;
  if(cont == 1)
    document.getElementById("menos").style.display = "none";
}

function instrucciones(){
	var i = "Para diseñar tu circuito correctamente sigue las siguientes instrucciones:\n\bPara agregar un componente arrastra el dibujo correspondiente de la parte izquierda al lienzo\n\bPara cambiar el valor de un componente, da clic en él, y escribe el nuevo valor\n\bPara eliminar un componente da clic en él y presiona la tecla Suprimir\nUna vez terminado tu diseño da clic en el botón Aceptar para descargar tu imagen";
	alert(i);
}

function instruccionesA(){
	var i = "Para practicar con un circuito sigue las siguientes instrucciones:\n\bPara agregar un componente arrastra el dibujo correspondiente de la parte izquierda al lienzo\n\bPara cambiar el valor de un componente, da clic en él, y escribe el nuevo valor\n\bPara eliminar un componente da clic en él y presiona la tecla Suprimir\n\bSi deseas obtener valores de voltaje, arrastra las puntas roja y negra a los puntos donde deseas medir";
	alert(i);
}
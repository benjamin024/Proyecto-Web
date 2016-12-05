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
  document.getElementById("cuerpoTabla").innerHTML += "<tr id=fila"+cont+"><td>"+cont+"</td><td><input type=text class=form-control name=pregunta"+cont+" required/></td><td><input type=text  class=form-control  name=resp"+cont+" required/></td></tr>";
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
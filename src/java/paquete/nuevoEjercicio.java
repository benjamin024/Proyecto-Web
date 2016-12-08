package paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class nuevoEjercicio extends HttpServlet {
    
    private String getNombreValido(String nombre){
        nombre = nombre.replace(" ", "_");
        nombre = nombre.replace("á","a");
        nombre = nombre.replace("é","e");
        nombre = nombre.replace("í","i");
        nombre = nombre.replace("ó","o");
        nombre = nombre.replace("ú","u");
        nombre = nombre.replace("Á","a");
        nombre = nombre.replace("É","e");
        nombre = nombre.replace("Í","i");
        nombre = nombre.replace("Ó","o");
        nombre = nombre.replace("Ú","u");
        nombre = nombre.replace("à","a");
        nombre = nombre.replace("è","e");
        nombre = nombre.replace("ì","i");
        nombre = nombre.replace("ò","o");
        nombre = nombre.replace("ù","u");
        nombre = nombre.replace("À","a");
        nombre = nombre.replace("È","e");
        nombre = nombre.replace("Ì","i");
        nombre = nombre.replace("Ò","o");
        nombre = nombre.replace("Ù","u");
        nombre = nombre.replace("ñ","n");
        nombre = nombre.replace("Ñ","n");
        nombre = nombre.replace("@","");
        nombre = nombre.replace("~","");
        nombre = nombre.replace("ä","a");
        nombre = nombre.replace("ë","e");
        nombre = nombre.replace("ï","i");
        nombre = nombre.replace("ö","o");
        nombre = nombre.replace("ü","u");
        nombre = nombre.replace("Ä","a");
        nombre = nombre.replace("Ë","e");
        nombre = nombre.replace("Ï","i");
        nombre = nombre.replace("Ö","o");
        nombre = nombre.replace("Ü","u");
        return nombre;
    }
    
    private String getFecha(){
        Locale l = new Locale("es","MX");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),l);
        return cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
        
    }
    
    private int getID() throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(ruta + "xml\\ejercicios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();
        return hijosRaiz.size() + 1;
    }
    
    private String getNombre(String n){
        String[] nombre = n.split("\\\\");
        for(int i = 0; i < nombre.length; i++){
            System.out.println(nombre[i]);
        }
        return nombre[nombre.length-1];
    }
    
    String instrucciones;
    String[] preguntas = new String[5];
    String[] correctas = new String[5];
    String[] respuestasA = new String[5];
    String[] respuestasB = new String[5];
    String nombre;
    String ruta;
    String grupo;
    int j;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        grupo = (String) sesion.getAttribute("grupo");
        FileItemFactory file_factory = new DiskFileItemFactory();
        ServletFileUpload servlet_up = new ServletFileUpload(file_factory);
        List items = null;
        try {
            items = servlet_up.parseRequest(request);
        } catch (FileUploadException ex) {
            System.out.println(ex.toString());
        }
        ruta = request.getRealPath("/");
        j = 0;
        for(int i=0;i<items.size();i++){
            FileItem item = (FileItem) items.get(i);
            if (! item.isFormField()){
                nombre = getNombre(item.getName());
                nombre = getNombreValido(nombre);
                File archivo_server = new File(ruta+"imagenesEjercicios\\"+nombre);
                try {
                    item.write(archivo_server);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }else{
                String campo = item.getFieldName();
                System.out.println(campo + " - " + item.getString());
                if(campo.equals("instru"))
                    instrucciones = item.getString();
                else if(campo.contains("pregunta"))
                    preguntas[Integer.parseInt(campo.charAt(campo.length()-1)+"")-1] = item.getString();
                else if(campo.contains("correcta"))
                    correctas[Integer.parseInt(campo.charAt(campo.length()-1)+"")-1] = item.getString();
                else if(campo.contains("respA"))
                    respuestasA[Integer.parseInt(campo.charAt(campo.length()-1)+"")-1] = item.getString();
                else if(campo.contains("respB")){
                    respuestasB[Integer.parseInt(campo.charAt(campo.length()-1)+"")-1] = item.getString();
                    j++;
                }
            }
        }

        try {
            guardaXML(response);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void guardaXML(HttpServletResponse resp) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejercicios.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        Element ejercicio = new Element("ejercicio");
        Element imagen = new Element("imagen");
        Element instrucciones = new Element("instrucciones");
        Element fecha = new Element("fecha");
        Element[] pregunta = new Element[5];
        Element[] correcta=new Element[5];
        Element[] respA=new Element[5];
        Element[] respB=new Element[5];
        
        ejercicio.setAttribute("ID", getID() + "");
        ejercicio.setAttribute("grupo",grupo);
        fecha.setText(getFecha());
        ejercicio.addContent(fecha);
        instrucciones.setText(this.instrucciones);
        ejercicio.addContent(instrucciones);
        imagen.setText(nombre);
        ejercicio.addContent(imagen);
        System.out.println("esto es J"+j);
        for(int i = 0; i < j; i++){
            pregunta[i] = new Element("pregunta");
            pregunta[i].setAttribute("ID",Integer.toString(i+1));
            pregunta[i].setAttribute("texto",preguntas[i]);
            correcta[i] = new Element("respuesta");
            correcta[i].setAttribute("ID","1");
            correcta[i].setText(correctas[i]);
            
            respA[i] = new Element("respuesta");
            respA[i].setAttribute("ID","2");
            respA[i].setText(respuestasA[i]);
             respB[i] = new Element("respuesta");
            respB[i].setAttribute("ID","3");
            respB[i].setText(respuestasB[i]);
            
            
            
            pregunta[i].addContent(correcta[i]);
            pregunta[i].addContent(respA[i]);
            pregunta[i].addContent(respB[i]);
            //pregunta[i].setAttribute("respuesta",correctas[i]);
            //pregunta[i].setText(preguntas[i]);
            ejercicio.addContent(pregunta[i]);
            System.out.println("esto es I"+i);
        }
        rootNode.addContent(ejercicio);
        
        XMLOutputter xmlout = new XMLOutputter();
        xmlout.setFormat(Format.getPrettyFormat());
        try (FileOutputStream fileout = new FileOutputStream(ruta + "xml\\ejercicios.xml")) {
            xmlout.output(document, fileout);
            fileout.flush();
            fileout.close();
        }
        resp.sendRedirect("formEjercicio");
    }

}

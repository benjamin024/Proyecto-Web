package paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author benja
 */
public class calificacionEjercicio extends HttpServlet {
    private String ruta;   
    private int correctas = 0;
    
    private Element getEjercicio(String id, String grupo) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(ruta + "xml\\ejercicios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();
         for (Element ejercicio : hijosRaiz){
             if(ejercicio.getAttributeValue("grupo").equals(grupo) && ejercicio.getAttributeValue("ID").equals(id)){
                 return ejercicio;
             }
         }
        return new Element("ejercicio");
    }
    
    private int getNumPreguntas(Element ejercicio) {
        List hijos = ejercicio.getChildren();
        return hijos.size() - 3;
    }

    private Element getPregunta(Element ejercicio, int i) {
        List<Element> hijos = ejercicio.getChildren("pregunta");
        for(Element hijo: hijos){
            if(hijo.getAttributeValue("ID").equals(i+""))
                return hijo;
        }
        return new Element("pregunta");
    }
    
    private Element getRespuesta(Element pregunta, int i){
        List<Element> hijos = pregunta.getChildren("respuesta");
        for(Element hijo: hijos){
            if(hijo.getAttributeValue("ID").equals(i+""))
                return hijo;
        }
        return new Element("respuesta");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();
            PrintWriter out = response.getWriter();
            ruta = request.getRealPath("/");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<link rel='shortcut icon' href='utilities/images/logoescom-182x128-95.png' type='image/x-icon'>");
            out.println("<link rel='stylesheet' href='utilities/bootstrap/css/bootstrap.min.css'> <!-- Ayuda al diseño responsivo -->");
            out.println("<link rel='stylesheet' href='utilities/dropdown/css/style.css'> <!-- Aplica animaciones y estilos al hacer scroll -->");
            out.println("<link rel='stylesheet' href='utilities/theme/css/style.css'><!-- Estilo del tema -->");
            out.println("<link rel='stylesheet' href='utilities/mobirise/css/mbr-additional.css' type='text/css'> <!-- estilo del nav -->");
            out.println("<title>Ley de Ohm | Escuela Superior de Cómputo</title>  ");
            out.println("</head>");
            out.println("<body>");
            out.println("<section id='menu-0'>");
            out.println("<nav class='navbar navbar-dropdown navbar-fixed-top'>");
            out.println("<div class='container'>");
            out.println("<div class='mbr-table'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='navbar-brand'>");
            out.println("<a href='administrador' class='navbar-logo'><img src='utilities/images/logoescom-182x128-95.png'></a>");
            out.println("<a class='navbar-caption text-white' href='administrador'>Escuela Superior de Cómputo</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<button class='navbar-toggler pull-xs-right hidden-md-up' type='button' data-toggle='collapse' data-target='#exCollapsingNavbar'>");
            out.println("<div class='hamburger-icon'></div>");
            out.println("</button>");
            out.println("<ul class='nav-dropdown collapse pull-xs-right nav navbar-toggleable-sm' id='exCollapsingNavbar'>");
            out.println("<li class='nav-item'><a class='nav-link link' href='entrenar' aria-expanded='false'  style='color: #FFFFFF;'>¡A Entrenar!</a></li>");
            out.println("<li class='nav-item'><a class='nav-link link' href='misEjercicios' aria-expanded='false'  style='color: #FFFFFF;'>Mis Ejercicios</a></li>");
            ejercicios e = new ejercicios();
            int sinResolver = 0;
            try {
               sinResolver = e.getEjercicios(sesion.getAttribute("grupo").toString(), request.getRealPath("/"), sesion.getAttribute("user").toString());
            } catch (Exception ex) {
                Logger.getLogger(alumno.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<li class='nav-item'><a class='nav-link link' href='ejercicios' aria-expanded='false'  style='color: #FFFFFF;'>Ejercicios sin Resolver ("+ sinResolver+")</a></li>");
            out.println("<li class='nav-item'><a class='nav-link link' href='ModificarAlumno' aria-expanded='false'  style='color: #FFFFFF;'>Modifica tus Datos</a></li>");
            out.println("<li class='nav-item'><a class='nav-link link' href='logout' aria-expanded='false'  style='color: #FFFFFF;'>Cerrar Sesión</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");
            out.println("</section>");
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1' >");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='container'>");
            out.println("<div class='row'>");
            out.println("<div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
            String id = request.getParameter("id");
            Element ejercicio = null;
            try {
                ejercicio = getEjercicio(id, sesion.getAttribute("grupo").toString());
            } catch (Exception ex) {
                Logger.getLogger(formResuelveEjercicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<h1 class='mbr-section-title display-1'>Resultados del Ejercicio</h1>");
            out.println("<center><div style='width: 40%; height: 30%;'><img src='imagenesEjercicios/" + ejercicio.getChildText("imagen") + "' width=100% height=100% /></div></center><br/>");
            out.println("<table class='table'>");
            out.println("<thead><tr><th>Pregunta</th><th>Respuesta Seleccionada</th><th>Respuesta Correcta</th><th></th></tr></thead>");
            out.println("<tbody>");
            int numPreguntas = getNumPreguntas(ejercicio);
            for(int i = 1; i <= numPreguntas;  i++){
                try {
                    if(esCorrecta(id, sesion.getAttribute("grupo").toString(),sesion.getAttribute("user").toString(), i)){
                        Element pregunta = getPregunta(ejercicio, i);
                        Element respuesta = getRespuesta(pregunta, 1);
                        out.println("<tr style='color: #0B610B;'><td>" + pregunta.getAttributeValue("texto") + "</td><td colspan=2 >" + respuesta.getText() +"</td></tr>");
                    }else{
                        Element pregunta = getPregunta(ejercicio, i);
                        Element respuesta = getRespuesta(pregunta, getSeleccionada(id, sesion.getAttribute("grupo").toString(),sesion.getAttribute("user").toString(), i));
                        Element correcta = getRespuesta(pregunta,1);
                        out.println("<tr style='color: #FF0000;'><td>" + pregunta.getAttributeValue("texto") + "</td><td>" + respuesta.getText() +"</td><td>"+correcta.getText()+"</td></tr>");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(calificacionEjercicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                out.println("<tr><td colspan=2> Calificación: </td><td>" + getCalificacion(numPreguntas, id, sesion.getAttribute("user").toString(), sesion.getAttribute("grupo").toString()) + "</td></tr>");
            } catch (Exception ex) {
                Logger.getLogger(calificacionEjercicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            correctas = 0;
            out.println("</tbody>");
            out.println("</table>");
            out.println("<a href=misEjercicios ><input type=button value='Aceptar' class='btn btn-lg btn-black-outline btn-black'></a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</section>");
            out.println("<footer class='mbr-small-footer mbr-section mbr-section-nopadding' id='footer1-2' style='background-color: rgb(40, 40, 40); padding-top: 1%; padding-bottom: 1%;'>");
            out.println("<div class='container'>");
            out.println("<p class='text-xs-center' style='color: #FFFFFF;'>Escuela superior de Cómputo<br>Tecnologías para la Web</p>");
            out.println("</div>");
            out.println("</footer>");
            out.println("<script src='utilities/jquery/jquery-3.1.1.js'></script>");
            out.println("<script src='utilities/bootstrap/js/bootstrap.min.js'></script>");
            out.println("<script src='utilities/dropdown/js/script.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
    }
    
    private int getSeleccionada(String ejercicio, String grupo, String alumno, int pregunta) throws Exception{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejerciciosResueltos.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List<Element> hijos = rootNode.getChildren();
        for(Element hijo : hijos){
            if(hijo.getAttributeValue("IdEjercicio").equals(ejercicio) && hijo.getChildText("alumno").equals(alumno) && hijo.getChildText("grupo").equals(grupo)){
                List<Element> preguntas = hijo.getChildren("pregunta");
                for(Element p : preguntas){
                    if(p.getAttributeValue("ID").equals(pregunta + "")){
                        return Integer.parseInt(p.getText());
                    }
                }
            }
        }
        return 1;
    }

    private boolean esCorrecta(String ejercicio, String grupo, String alumno, int pregunta) throws Exception{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejerciciosResueltos.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List<Element> hijos = rootNode.getChildren();
        for(Element hijo : hijos){
            if(hijo.getAttributeValue("IdEjercicio").equals(ejercicio) && hijo.getChildText("alumno").equals(alumno) && hijo.getChildText("grupo").equals(grupo)){
                List<Element> preguntas = hijo.getChildren("pregunta");
                for(Element p : preguntas){
                    if(p.getAttributeValue("ID").equals(pregunta+"") && p.getText().equals("1")){
                        correctas++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private int getCalificacion(int numPreguntas, String ejercicio, String alumno, String grupo) throws Exception{
        int calificacion = (correctas * 10) / numPreguntas;
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejerciciosResueltos.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List<Element> hijos = rootNode.getChildren();
        for(Element hijo : hijos){
            if(hijo.getAttributeValue("IdEjercicio").equals(ejercicio) && hijo.getChildText("alumno").equals(alumno) && hijo.getChildText("grupo").equals(grupo)){
                Element calificacionE = new Element("calificacion");
                calificacionE.setText(calificacion + "");
                if(hijo.getChildText("calificacion") == null)
                    hijo.addContent(calificacionE);
            }
        }
        XMLOutputter xmlout = new XMLOutputter();
        xmlout.setFormat(Format.getPrettyFormat());
        try (FileOutputStream fileout = new FileOutputStream(ruta + "xml\\ejerciciosResueltos.xml")) {
            xmlout.output(document, fileout);
            fileout.flush();
            fileout.close();
        }
        return calificacion;
    }
}

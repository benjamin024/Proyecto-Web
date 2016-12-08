package paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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


public class misEjercicios extends HttpServlet {
    private String ruta;   
    private List<Element> ejercicios = new ArrayList<Element>();

    private void getEjercicios(String grupo, String ruta, String alumno) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(ruta + "xml\\ejercicios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();
         for (Element ejercicio : hijosRaiz){
             if(ejercicio.getAttributeValue("grupo").equals(grupo) && fueContestado(ruta, alumno, ejercicio.getAttributeValue("ID")) ){
                 ejercicios.add(ejercicio);
             }
         }
    }
    
    private boolean fueContestado(String ruta, String alumno, String ID) throws Exception {
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(ruta + "xml\\ejerciciosResueltos.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();
         for (Element ejercicio : hijosRaiz){
             if(ejercicio.getAttributeValue("IdEjercicio").equals(ID) && ejercicio.getChildText("alumno").equals(alumno))
                 return true;
         }
         return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession(true);
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
            out.println("<link rel='stylesheet' href='utilities/archivos/archivos.css' type='text/css'>");
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
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1' style='background-image: url(utilities/images/fondo2.jpg);'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='container'>");
            out.println("<div class='row'>");
            out.println("<div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
            out.println("<h1 class='mbr-section-title display-1'>Mis Ejercicios Resueltos</h1>");
            try {
                getEjercicios(sesion.getAttribute("grupo").toString(), request.getRealPath("/"), sesion.getAttribute("user").toString());
            } catch (Exception ex) {
                Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(ejercicios.size() > 0){
                for(Element ejercicio: ejercicios){
                    out.println("<div align=center >");
                    out.println("<div class='img'><a href=calificacionEjercicio?id="+ejercicio.getAttributeValue("ID")+" ><img src='imagenesEjercicios/" + ejercicio.getChildText("imagen") + "' width=300 height=200 /></a>");
                    try {
                        out.println("<div class='desc'>Calificación: " + getCalificacion(ejercicio.getAttributeValue("ID"), sesion.getAttribute("user").toString(), sesion.getAttribute("grupo").toString()) + "</div></div>");
                    } catch (Exception ex) {
                        Logger.getLogger(misEjercicios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    out.println("</div>");
                }
            }else
                out.println("<h5>No hay ejercicios</h5>");
            ejercicios.clear();
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

    private String getCalificacion(String ejercicio, String alumno, String grupo) throws Exception {
         SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejerciciosResueltos.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List<Element> hijos = rootNode.getChildren();
        for(Element hijo : hijos){
            if(hijo.getAttributeValue("IdEjercicio").equals(ejercicio) && hijo.getChildText("alumno").equals(alumno) && hijo.getChildText("grupo").equals(grupo)){
                return hijo.getChildText("calificacion");
            }
        }
        return "";
    }


}

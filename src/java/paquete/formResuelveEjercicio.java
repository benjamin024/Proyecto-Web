package paquete;

import java.io.FileInputStream;
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

public class formResuelveEjercicio extends HttpServlet {
    private String ruta;
    
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
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();
            PrintWriter out = response.getWriter();
            String id = request.getParameter("id");
            ruta = request.getRealPath("/");
            Element ejercicio = null;
            try {
                ejercicio = getEjercicio(id, sesion.getAttribute("grupo").toString());
            } catch (Exception ex) {
                Logger.getLogger(formResuelveEjercicio.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1' style='background-image: url(utilities/images/fondo2.jpg);'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='container'>");
            out.println("<div class='row'>");
            out.println("<div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
            out.println("<h1 class='mbr-section-title display-1'>Resolver Ejercicio</h1>");
            out.println("<center><div style='width: 40%; height: 30%;'><img src='imagenesEjercicios/" + ejercicio.getChildText("imagen") + "' width=100% height=100% /></div></center><br/>");
            out.println("<div align='left'>");
            out.println("<h6>" + ejercicio.getChildText("instrucciones") + "</h6><br>");
            out.println("<form action=resuelveEjercicio method=post >");
            out.println("<ol>");
            int numPreguntas = getNumPreguntas(ejercicio);
            for(int i = 1; i <= numPreguntas;  i++){
                Element pregunta = getPregunta(ejercicio, i);
                out.println("<li>" + pregunta.getAttributeValue("texto") + "<br>");
                int[] orden = getOrdenRespuestas();
                for(int j = 0; j < 3; j++){
                    Element respuesta = getRespuesta(pregunta, orden[j]);
                    String selected = "";
                    if(j == 0)
                       selected = "checked";
                    else
                        selected = "";
                    out.println("<input type=radio name=pregunta"+pregunta.getAttributeValue("ID")+" value="+respuesta.getAttributeValue("ID")+" " + selected +">" + respuesta.getText() + "<br>");
                }
                out.println("<br>");
                out.println("</li>");
            }
            out.println("<ol></div>");
            out.println("<input type=hidden value=" + id + " name=id />");
            out.println("<input type=hidden value=" + numPreguntas + " name=numPreguntas />");
            out.println("<input type=submit value='Aceptar' class='btn btn-lg btn-black-outline btn-black'>");
            out.println("</form>");
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

    private int[] getOrdenRespuestas(){
        int random = (int) (Math.random()*6+1);
        int[] orden = new int[3];
        switch(random){
            case 1:
                orden[0] = 1;
                orden[1] = 2;
                orden[2] = 3;
                break;
            case 2:
                orden[0] = 3;
                orden[1] = 1;
                orden[2] = 2;
                break;
            case 3:
                orden[0] = 2;
                orden[1] = 3;
                orden[2] = 1;
                break;
            case 4:
                orden[0] = 3;
                orden[1] = 2;
                orden[2] = 1;
                break;
            case 5:
                orden[0] = 2;
                orden[1] = 1;
                orden[2] = 3;
                break;
            case 6:
                orden[0] = 1;
                orden[1] = 3;
                orden[2] = 2;
                break;
        }
        return orden;
    }
    
    
    private Element getRespuesta(Element pregunta, int i){
        List<Element> hijos = pregunta.getChildren("respuesta");
        for(Element hijo: hijos){
            if(hijo.getAttributeValue("ID").equals(i+""))
                return hijo;
        }
        return new Element("respuesta");
    }
}

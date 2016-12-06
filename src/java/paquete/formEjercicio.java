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

public class formEjercicio extends HttpServlet {

    private String rutaXML;
    
    private int getSolicitudes(String grupo) throws Exception{
        int cont = 0;
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("tipo").equals("3") && hijo.getAttributeValue("activo").equals("0") && hijo.getChildText("grupo").equals(grupo))
                cont++;
        }
        return cont;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();
            PrintWriter out = response.getWriter();
            rutaXML = request.getRealPath("/") + "xml";
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
            out.println("<a href='profesor' class='navbar-logo'><img src='utilities/images/logoescom-182x128-95.png'></a>");
            out.println("<a class='navbar-caption text-white' href='profesor'>Escuela Superior de Cómputo</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<button class='navbar-toggler pull-xs-right hidden-md-up' type='button' data-toggle='collapse' data-target='#exCollapsingNavbar'>");
            out.println("<div class='hamburger-icon'></div>");
            out.println("</button>");
            out.println("<ul class='nav-dropdown collapse pull-xs-right nav navbar-toggleable-sm' id='exCollapsingNavbar'>");
            out.println("<li class='nav-item'><a class='nav-link link' href='designCircuito' aria-expanded='false'  style='color: #FFFFFF;'>Diseñar Circuito</a></li>");
            out.println("<li class='nav-item'><a class='nav-link link' href='formEjercicio' aria-expanded='false'  style='color: #FFFFFF;'>Nuevo Ejercicio</a></li>");
            out.println("<li class='nav-item'><a class='nav-link link' href='ejercicios' aria-expanded='false'  style='color: #FFFFFF;'>Mis Ejercicios</a></li>");
            try {
                out.println("<li class='nav-item'><a class='nav-link link' href='verSolicitudes' aria-expanded='false'  style='color: #FFFFFF;'>Solicitudes del grupo ("+getSolicitudes(sesion.getAttribute("grupo").toString())+")</a></li>");
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
            out.println("<li class='nav-item'><a class='nav-link link' href='logout' aria-expanded='false'  style='color: #FFFFFF;'>Cerrar Sesión</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");
            out.println("</section>");
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='container'>");
            out.println("<div class='row'>");
            out.println("<div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
            out.println("<h5 class='mbr-section-title'>Nuevo Ejercicio</h5>");
            out.println("<form action='nuevoEjercicio' method='post' enctype='multipart/form-data'>");
            out.println("Sube la imagen del circuito:<br>");
            out.println("<input class='btn btn-default btn-file' type='file' id='files' name='files[]' required/><br>");
            out.println("Si no tienes la imagen aún, puedes <a href='circuito'>Diseñar un circuito</a><br><br>");
            out.println("<output id='list'></output><br><br>");
            out.println("Instrucciones: <input type='text' name='instru' class=form-control required/><br>");
            out.println("<table class='table'>");
            out.println("<thead><tr><th>No.</th><th>Pregunta</th><th>Respuesta Correcta</th><th>Respuesta</th><th>Respuesta</th></tr></thead>");
            out.println("<tbody id=cuerpoTabla>");
            out.println("<tr><td>1</td><td><input type=text class=form-control name=pregunta1 required/></td>"
                    + "<td><input type=text  class=form-control  name=correcta required/></td>"
                    + "<td><input type=text  class=form-control  name=respa required/></td>"
                    + "<td><input type=text  class=form-control  name=respb required/></td></tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("<div><input type=button id=mas size=1 value=+ onclick=agregaPregunta(); /><input type=button id='menos' size=1 value=- style='display: none;' onclick=eliminaPregunta(); /><br></div>");
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
            out.println("<script src='utilities/archivos/archivos.js'></script>");
            out.println("</body>");
            out.println("</html>");
    }

}

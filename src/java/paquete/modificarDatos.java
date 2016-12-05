package paquete;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import java.util.Properties;

public class modificarDatos extends HttpServlet {
    
    private String rutaXML;
    
          
    
     private String[] obtenDatos(String user) throws Exception{
         
         String[ ] datos = new String[3];
         
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("id").equals(user)){
                datos[0]=hijo.getChildText("nombre");
                datos[1]=hijo.getChildText("grupo");
                datos[2]=hijo.getChildText("email");
                
            }
        }
        return datos;
    }

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
            String user = request.getParameter("user");
            String[ ] datos = new String[3];
        try {
            datos=obtenDatos(user);
        } catch (Exception ex) {
            Logger.getLogger(modificarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
            rutaXML = request.getRealPath("/") + "xml";
            //response.sendRedirect("eligeAlumno");
        
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
            out.println("<a href='indexProf.html' class='navbar-logo'><img src='utilities/images/logoescom-182x128-95.png'></a>");
            out.println("<a class='navbar-caption text-white' href='indexProf.html'>Escuela Superior de Cómputo</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<button class='navbar-toggler pull-xs-right hidden-md-up' type='button' data-toggle='collapse' data-target='#exCollapsingNavbar'>");
            out.println("<div class='hamburger-icon'></div>");
            out.println("</button>");
            out.println("<ul class='nav-dropdown collapse pull-xs-right nav navbar-toggleable-sm' id='exCollapsingNavbar'>");
         
            out.println("<li class='nav-item'><a class='nav-link link' href='logout' aria-expanded='false'  style='color: #FFFFFF;'>Cerrar Sesión</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");
            out.println("</section>");
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1' style='background-image: url(utilities/images/fondo.png);'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='container'>");
            out.println("<div class='row'>");
            out.println("<div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
            //
            out.println("<section class='mbr-section mbr-section-hero mbr-section-full mbr-parallax-background' id='header1-1' style='background-image: url(utilities/images/fondo.png);'>");
    out.println("<div class='mbr-table-cell'>");
       out.println(" <div class='container'>");
         out.println("   <div class='row'>");
              out.println("   <div class='mbr-section col-md-10 col-md-offset-1 text-xs-center'>");
                   out.println("  <h1 class='mbr-section-title display-1'>¡Actualiza los datos!</h1>");
                    out.println(" <p class='text-black'>Modifica los campos que sean necesarios:</p>");
                    out.println(" <form action='actualizar' method='post' class='form'>");
                       out.println("  <div class='col-md-8'>");
                            out.println(" <label for='nombre' class='text-black'>Nombre Completo:</label>");
                           out.println("  <input type='text' class='form-control' placeholder='Nombre Completo' id='nombre' name='nombre' value='"+datos[0]+"' required/>");
                       out.println("  </div>");
                       out.println("  <div class='col-md-offset-8'>");
                            out.println(" <label for='grupo' class='text-black'>Grupo:</label>");
                            out.println(" <input type='text' class='form-control' placeholder='Grupo' id='grupo' name='grupo' value='"+datos[1]+"' required/>");
                       out.println("  </div>");
                        out.println(" <div class='col-md-6'>");
                            out.println(" <label for='user' class='text-black'>Número de Boleta:</label>");
                            out.println(" <input type='text' class='form-control' placeholder='Número de Boleta' id='user' name='user' value='"+user+"' required/>");
                       out.println("  </div>");
                       out.println("  <div class='col-md-offset-6'>");
                            out.println("<label for='mail' class='text-black'>Correo Electrónico:</label>");
                           out.println("  <input class='form-control' type='mail' name='mail' id='mail' placeholder='Correo Electrónico' value='"+datos[2]+"' required/>");
                        out.println(" </div>");
                       out.println("  <div class='input-group-sm'>");
                            out.println(" <br><input type='submit' value='Actualizar' class='btn btn-black btn-lg btn-black-outline'/>");
                       out.println(" </div></form></div></div></div></div></section>");
            
            //
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
}
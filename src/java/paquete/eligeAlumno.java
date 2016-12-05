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

public class eligeAlumno extends HttpServlet {
    private String rutaXML;
    
    private int getNumSolicitudes() throws Exception{
        int cont = 0;
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
           if(hijo.getAttributeValue("tipo").equals("3") /*&& hijo.getAttributeValue("activo").equals("0") && hijo.getChildText("grupo").equals(grupo)*/)
                cont++;
        }
        return cont;
    }
    
    private String[][] getSolicitudes() throws Exception{
        String solicitudes[][] = new String[getNumSolicitudes()][3];
        int x = 0;
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("tipo").equals("3")){
                solicitudes[x][0] = hijo.getAttributeValue("id");
                solicitudes[x][1] = hijo.getChildText("nombre");
                solicitudes[x][2] = hijo.getChildText("email");
                x++;
            }
        }
        return solicitudes;
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
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
            out.println("<link rel='shortcut icon' href='utilities/images/logoescom-182x128-95.png' type='image/x-icon'>");
            out.println("<link rel='stylesheet' href='utilities/bootstrap/css/bootstrap.min.css'> <!-- Ayuda al diseño responsivo -->");
            out.println("<link rel='stylesheet' href='utilities/dropdown/css/style.css'> <!-- Aplica animaciones y estilos al hacer scroll -->");
            out.println("<link rel='stylesheet' href='utilities/theme/css/style.css'><!-- Estilo del tema -->");
            out.println("<link rel='stylesheet' href='utilities/mobirise/css/mbr-additional.css' type='text/css'> <!-- estilo del nav -->");
            
           //out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>");
           //out.println("<script src='http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js'></script>");
            out.println("<script type='text/javascript' src='utilities/JQDataTable/jquery.js'></script>");
            out.println("<script type='text/javascript' src='utilities/JQDataTable/jquery.dataTables.min.js'></script>");
            //out.println("<script src='utilities/jquery/jquery-3.1.1.js'></script>");
            out.println("<script src='utilities/bootstrap/js/bootstrap.min.js'></script>");
            out.println("<script src='utilities/dropdown/js/script.min.js'></script>");
            
            out.println("<title>Ley de Ohm | Escuela Superior de Cómputo</title>  ");
            out.println(" <script> $(function(){$('#datos').dataTable();})</script><!--Se implementa la funcion para el manejo de la tabla con jquery datatable-->");
            out.println("</head>");
            out.println("<body>");
            out.println("<section id='menu-0'>");
            out.println("<nav class='navbar navbar-dropdown navbar-fixed-top'>");
            out.println("<div class='container'>");
            out.println("<div class='mbr-table'>");
            out.println("<div class='mbr-table-cell'>");
            out.println("<div class='navbar-brand'>");
            out.println("<a href='indexProf.html' class='navbar-logo'><img src='utilities/images/logoescom-182x128-95.png'></a>");
            out.println("<a class='navbar-caption text-white' href='administrador'>Escuela Superior de Cómputo</a>");
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
            try {
               // if(getNumSolicitudes(sesion.getAttribute("grupo").toString()) == 0)
                   // out.println("<h4>Por el momento no hay nuevas solicitudes para inscribirse a este grupo</h4>");
                //else{
                    out.println("<h2>Solicitudes para inscribirse al grupo</h2>");
                    String[][] solicitudes = getSolicitudes();
                    out.println("<table class='table table-bordered table-hover' id='datos'>");
                    out.println("<thead><tr><th>Usuario</th><th>Nombre Completo</th><th>Correo Electrónico</th><th>Opcion 1</th><th>Opcion 2</th></tr></thead>");
                    out.println("<tbody>");
                    for(int i = 0; i < solicitudes.length; i++){
                        out.println("<tr>");
                        out.println("<td>" + solicitudes[i][0] + "</td>");
                        out.println("<td>" + solicitudes[i][1] + "</td>");
                        out.println("<td>" + solicitudes[i][2] + "</td>");
                        out.println("<td><a href='modificarDatos?user="+solicitudes[i][0]+"'><span class=\"glyphicon glyphicon-edit\"></span>Modificar</a></td>");
                        out.println("<td><a href='Borra2?user="+solicitudes[i][0]+"'><span class=\"glyphicon glyphicon-remove\"></span>Eliminar</a></td>");
                        out.println("</tr>");
                   }
                    out.println("</tbody>");
                    out.println("</table>");
                //}
            }catch(Exception ex) {
                Logger.getLogger(verSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            //out.println("<script src='utilities/jquery/jquery-3.1.1.js'></script>");
            out.println("<script src='utilities/bootstrap/js/bootstrap.min.js'></script>");
            out.println("<script src='utilities/dropdown/js/script.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
    }
}

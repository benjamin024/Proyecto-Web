/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;


import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
 *
 * @author geoge
 */
public class Borra extends HttpServlet {
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     try {
         String ruta = this.getServletContext().getRealPath("/");
         int tipoUsuario = 0;
         response.setContentType("text/html;charset=UTF-8");
         HttpSession sesion = request.getSession();
         
         SAXBuilder builder = new SAXBuilder();
         File xmlFile = new File(ruta + "xml\\usuarios.xml");
         Document document = (Document) builder.build(xmlFile);
         Element rootNode = document.getRootElement();
         List<Element> hijosRaiz = rootNode.getChildren();
        for(Element hijo: hijosRaiz){            
                tipoUsuario = Integer.parseInt(hijo.getAttributeValue("tipo"));                
            }
         
         
         
         String tipo = (String) sesion.getAttribute("tipo");
         PrintWriter out = response.getWriter();
         out.println(tipoUsuario);
         out.println("<html>"
                 + "<head>"
                 + "<meta charset=\"UTF-8\">"
                 + "<link rel=\"shortcut icon\" a href=\"utilities/images/logoescom-182x128-95.png\" type=\"image/x-icon\">"
                 + "<link rel=\"stylesheet\" type=\"text/css\" href=\"utilities/bootstrap/css/bootstrap.min.css\"/>"
                 +"<link rel=\"stylesheet\" type=\"text/css\" href=\"utilities/dropdown/css/style.css\"/>"
                 +"<link rel=\"stylesheet\" type=\"text/css\" href=\"utilities/theme/css/style.css\"/>"
                 +"<link rel=\"stylesheet\" href=\"utilities/mobirise/css/mbr-additional.css\" type=\"text/css\"/>"
                 +"<title>Ley de Ohm | Escuela Superior de Cómputo</title>"
                 + "</head>"
                 +"<body onload=\"mostrarMensaje1()\">\n" +
"<section id=\"menu-0\">\n" +
"    <nav class=\"navbar navbar-dropdown navbar-fixed-top\">\n" +
"        <div class=\"container\">\n" +
"            <div class=\"mbr-table\">\n" +
"                <div class=\"mbr-table-cell\">\n" +
"                    <div class=\"navbar-brand\">\n" +
"                        <a href=\"index.html\" class=\"navbar-logo\"><img src=\"utilities/images/logoescom-182x128-95.png\"></a>\n" +
"                        <a class=\"navbar-caption text-white\" href=\"../index.html\">Escuela Superior de Cómputo</a>\n" +
"                    </div>\n" +
"                </div>\n" +
"                <div class=\"mbr-table-cell\">\n" +
"                    <button class=\"navbar-toggler pull-xs-right hidden-md-up\" type=\"button\" data-toggle=\"collapse\" data-target=\"#exCollapsingNavbar\">\n" +
"                        <div class=\"hamburger-icon\"></div>\n" +
"                    </button>\n" +
"                </div>\n" +
"            </div>\n" +
"\n" +
"        </div>\n" +
"    </nav>\n" +
"</section>\n" +
"\n" +
"<section class=\"mbr-section mbr-section-hero mbr-section-full mbr-parallax-background\" id=\"header1-1\" style=\"background-image: url(utilities/images/fondo.png);\">\n" +
"    <div class=\"mbr-table-cell\">\n" +
"        <div class=\"container\">\n" +
"            <div class=\"row\">\n" +
"                <div class=\"mbr-section col-md-10 col-md-offset-1 text-xs-center\">\n" +
"                    <h1 class=\"mbr-section-title display-1\">Borrar</h1>\n" +
"                    <p class=\"text-black\">Tabla De Usuarios</p>\n" +
"                    \n");
                 if (tipoUsuario==1) 
                 {
                 out.println(
                        "<script type=\"text/javascript\">\n" +
"var xmlDoc=null;\n" +
"if (window.ActiveXObject)\n" +
"{// code for IE\n" +
"xmlDoc=new ActiveXObject(\"Microsoft.XMLDOM\");\n" +
"}\n" +
"else if (document.implementation.createDocument)\n" +
"{// code for Mozilla, Firefox, Opera, etc.\n" +
"xmlDoc=document.implementation.createDocument(\"\",\"\",null);\n" +
"}\n" +
"else\n" +
"{\n" +
"alert('Tu navegador no soporta esta funcion');\n" +
"}\n" +
"if (xmlDoc!=null)\n" +
"{\n" +
"xmlDoc.async=false;\n" +
"xmlDoc.load(\"xml/usuarios.xml\");\n" +
"var x=xmlDoc.getElementsByTagName(\"usuarios\");\n" +

"document.write(\"<table border='1' >\");\n" +
"document.write(\"<thead>\");\n" +
"document.write(\"<thead><tr><th>Tipo de Usuario</th><th>Nombre Completo</th><th>Correo Electrónico</th></tr></thead>\");\n" +
"document.write(\"</thead>\");\n" +
"\n" +
"document.write(\"<tfoot>\");\n" +
"document.write(\"<tr><th colspan='2'>Usuarios Registrados</th></tr>\");\n" +
"document.write(\"</tfoot>\");\n" +
"\n" +
"for (var i=0;i<x.length;i++)\n" +
"{\n" +
"document.write(\"<tr>\");\n" +
"document.write(\"<td>\");\n" +
"document.write(x[i].getElementsByTagName(\"usuario\")[0].getAttribute(\"tipo\"));\n" +
"document.write(\"</td>\");\n" +
"\n" +
"\n" +
"document.write(\"<td>\");\n" +
"document.write(x[i].getElementsByTagName(\"nombre\")[0].childNodes[0].nodeValue);\n" +
"document.write(\"</td>\");\n" +
"\n" +
"\n" +
"\n" +
"document.write(\"<td>\");\n" +
"document.write(x[i].getElementsByTagName(\"email\")[0].childNodes[0].nodeValue);\n" +
"document.write(\"</td>\");\n" +
"}\n" +
"document.write(\"</table>\");\n" +
"}\n" +
"</script>"
                         
                                 
                 
                 
                 
                 
                 );
                         
                         
                  out.println(        
"                </div>\n" +
"            </div>\n" +
        " Introduzca correo de Usuario: <input type='text' size='40' name'idborra' id='idborra'/>"+
"    <input type='button' value='Borrar' onclick=\"borrar(idborra)\"/>"+
"        </div>\n" +
"</div>\n" +
"</section>\n" +
"\n" +
"<footer class=\"mbr-small-footer mbr-section mbr-section-nopadding\" id=\"footer1-2\" style=\"background-color: rgb(40, 40, 40); padding-top: 1%; padding-bottom: 1%;\">   \n" +
"    <div class=\"container\">\n" +
"        <p class=\"text-xs-center\" style=\"color: #FFFFFF;\">Escuela superior de Cómputo<br>Tecnologías para la Web</p>\n" +
"    </div>\n" +
"</footer>\n");
        
out.println(
        "<script>"+
                    "function borrar(idborra) {//aqui la ejecutamos\n" +
"\n" +
"  idborra = document.getElementsByTagName(\"email\")[0].childNodes[0].nodeValue;\n" +
"\n" +
"  field.removeChild(document.getElementById(obj));\n" +
"\n" +
"}"
                    + "</script>"
        
);
        
        
           
        
        
    out.println(    
"  <script src=\"utilities/jquery/jquery-3.1.1.js\"></script>\n" +
"  <script src=\"utilities/bootstrap/js/bootstrap.min.js\"></script>\n" +
"  <script src=\"utilities/dropdown/js/script.min.js\"></script>\n" +
"<script type=\"text/javascript\" src=\"utilities/dropdown/js/ConsAdmin.js\"></script>\n"+        
"  </body>");
        
             
            /* out.println("<table id=\"info\" class=\"table table-hover\"></table>");
             
             //BORRAR DATOS
             out.println("<div class=\"modal fade\" id=\"myModalDelete\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\">\n" +
                     "  <div class=\"modal-dialog modal-sm\" role=\"document\">\n" +
                     "    <div class=\"modal-content\">\n" +
                     "<form action='ServBorra' method='post'>" +
                     "      <div class=\"modal-header\">\n" +
                     "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
                     "        <h4 class=\"modal-title\" id=\"myModalLabel\">Borrar</h4>\n" +
                     "      </div>\n" +
                     "      <div class=\"modal-body\">\n" +
                     "        Esta seguro que desea eliminar a este usuario." +
                     "      <input type='hidden' name='id' id='id'/>" +
                     "      </div>\n" +
                     "      <div class=\"modal-footer\">\n" +
                     "       <button type=\"submit\" class=\"btn btn-danger\">Borrar</button>\n" +
                     "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Cancelar</button>\n" +
                     "      </div>\n" +
                     "</form> "+
                     "    </div>\n" +
                     "  </div>\n" +
                     "</div>");
             
             
             out.println("<script type=\"text/javascript\" src=\"../js/ConsAdmin.js\"></script>");*/
         }
         
         out.println("</html>");
     } catch (JDOMException ex) {
         Logger.getLogger(Borra.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
}

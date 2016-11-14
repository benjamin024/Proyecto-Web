/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class altaAlumno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String ruta = this.getServletContext().getRealPath("/");
        String nombre = request.getParameter("nombre");
        String grupo = request.getParameter("grupo");
        String user = request.getParameter("user");
        String mail = request.getParameter("mail");
        
        PrintWriter out = response.getWriter();
        registrarDatos(out,ruta,nombre,grupo,user,mail,response);
    }
     protected String generaPassword(String nom, String grup) {
        String [] nombre = nom.split(" ");
        return nombre[0].charAt(0) + nombre[1] + nombre[2].charAt(0) + grup.charAt(grup.length() - 2) + grup.charAt(grup.length() - 1);
    }
    public void registrarDatos(PrintWriter pw,String ruta,String nom,String grup,String user,String email, HttpServletResponse resp) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\usuarios.xml");
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            
            Element usuario = new Element("usuario");
            Element nombre = new Element("nombre");
            Element grupo = new Element ("grupo");
            Element mail = new Element("mail");
            Element pass = new Element("password");
            
            nombre.setText(nom);
        grupo.setText(grup);
        mail.setText(email);
        pass.setText("123");
        usuario.setAttribute("id", user);
        usuario.setAttribute("activo", "0");
        usuario.setAttribute("tipo", "3");
        usuario.addContent(nombre);
        usuario.addContent(grupo);
        usuario.addContent(mail);
        usuario.addContent(pass);
        ;
            rootNode.addContent(usuario);
                        
            XMLOutputter xmlout = new XMLOutputter();
            xmlout.setFormat(Format.getPrettyFormat());
            try (FileOutputStream fileout = new FileOutputStream(ruta + "xml\\usuarios.xml")) {
                xmlout.output(document, fileout);
                fileout.flush();
                fileout.close();
            }
            resp.sendRedirect("index.html");
       } catch (IOException io) {
            pw.println(io.getMessage());
        } catch (JDOMException jdomex) {
            pw.println(jdomex.getMessage());
        }
    }
}

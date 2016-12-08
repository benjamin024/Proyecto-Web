/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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


public class registraAlumno extends HttpServlet {
    
    private String rutaXML;
    
    public boolean registroAlumno(String nom, String grup, String user, String email, String pass) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();
        Element usuario = new Element("usuario");
        Element nombre = new Element("nombre");
        Element grupo = new Element ("grupo");
        Element mail = new Element("email");
        Element passw = new Element("password");
        nombre.setText(nom);
        grupo.setText(grup);
        mail.setText(email);
        passw.setText(pass);
        usuario.setAttribute("id", user);
        usuario.setAttribute("activo", "0");
        usuario.setAttribute("tipo", "3");
        usuario.addContent(nombre);
        usuario.addContent(grupo);
        usuario.addContent(mail);
        usuario.addContent(passw);
        raiz.addContent(usuario);
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter(rutaXML + "/usuarios.xml"));
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String nombre = request.getParameter("nombre");
            String grupo = request.getParameter("grupo");
            String user = request.getParameter("user");
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            String confpass = request.getParameter("confpass");
            rutaXML = request.getRealPath("/") + "xml";
            try {
                boolean ok = registroAlumno(nombre, grupo, user, mail, pass);
                if(ok){
                    out.println("Usuario registrado");
                    response.sendRedirect("confirmacionA.html");
                }else{
                    out.println("ERROR");
                }
            } catch (Exception ex) {
                Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }

    private String generaPassword(String nom, String grup) {
        String [] nombre = nom.split(" ");
        return nombre[0].charAt(0) + nombre[1] + nombre[2].charAt(0) + grup.charAt(grup.length() - 2) + grup.charAt(grup.length() - 1);
    }
}

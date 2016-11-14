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


public class actualizar extends HttpServlet {
    
    private String rutaXML;
    
    public boolean modificar(String nom, String grup, String user, String email) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        
            
         
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("id").equals(user)){
                hijo.getChild("nombre").setText(nom);
                hijo.getChild("grupo").setText(grup);
                hijo.getChild("email").setText(email);
                
                
            }
        }
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
            rutaXML = request.getRealPath("/") + "xml";
            try {
                boolean ok = modificar(nombre, grupo, user, mail);
                if(ok){
                    out.println("Usuario actualizado");
                    response.sendRedirect("eligeAlumno");
                }else{
                    out.println("ERROR");
                }
            } catch (Exception ex) {
                Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
}
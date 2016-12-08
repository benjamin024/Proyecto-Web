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


public class ActualizarEjercicio extends HttpServlet {
    
    private String rutaXML;
    
    public boolean modificar(String pregunta, String[]respuestas) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/ejercicios.xml"));
        Element raiz = doc.getRootElement();  
        
            
         
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("id").equals(user)){
                hijo.getChild("nombre").setText(nom);
                hijo.getChild("grupo").setText(grup);
                hijo.getChild("email").setText(email);
                hijo.getChild("password").setText(pass);
                
                
            }
        }
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter(rutaXML + "/ejercicios.xml"));
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String [] respuestas = new String [4];
            String pregunta = request.getParameter("pregunta");
                   respuestas[0]=request.getParameter("respuesta1");
                   respuestas[1]=request.getParameter("respuesta2");
                   respuestas[2]=request.getParameter("respuesta3");
            rutaXML = request.getRealPath("/") + "xml";
            try {
                boolean ok = modificar(pregunta, respuestas);
                if(ok){
                    out.println("actualizado");
                    response.sendRedirect("profesor");
                }else{
                    out.println("ERROR");
                }
            } catch (Exception ex) {
                Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
}
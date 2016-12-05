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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author geoge
 */
public class Borra extends HttpServlet {
   
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String rutaXML = request.getRealPath("/") + "xml";
        
         PrintWriter out = response.getWriter();
         out.println("<h1>"+rutaXML+"</h1>");
        String user = request.getParameter("user");
        // 1. cargar el XML original
    SAXBuilder builder = new SAXBuilder(); 
        
     try {
       Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        
            
      
           List<Element> hijosRaiz = raiz.getChildren();     
        Element borrar=new Element("usuario");
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("id").equals(user)){
               //hijo.removeContent();
               //hijo.removeAttribute("id");
               borrar=hijo;
             
              
            }
           
        } 
         borrar.detach();
            XMLOutputter xmlout = new XMLOutputter();
            xmlout.setFormat(Format.getPrettyFormat());
            try (FileOutputStream fileout = new FileOutputStream(rutaXML + "/usuarios.xml")) {
                xmlout.output(doc, fileout);
                fileout.flush();
                fileout.close();
            }
            out.println("<h1>borrado</h1>");
            response.sendRedirect("eligeProfesor");
       } catch (IOException io) {
            out.println(io.getMessage());
           
    
        
        // 3. Exportar nuevamente el XML


        
     } catch (JDOMException ex) {
         Logger.getLogger(Borra2.class.getName()).log(Level.SEVERE, null, ex);
     } 
   }
}
       
        
           



        
        
        
        
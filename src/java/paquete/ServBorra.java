package paquete;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class ServBorra extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String ruta = this.getServletContext().getRealPath("/");
        String id = request.getParameter("id");
        int idI = Integer.parseInt(id);
        PrintWriter out = response.getWriter();
        modificaDatos(out, ruta, idI, response);
    }

    public void modificaDatos(PrintWriter pw, String ruta, int id, HttpServletResponse resp) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "Xml\\Datos.xml");
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List listUsuarios = rootNode.getChildren("usuarios");
            Element usuarios = (Element) listUsuarios.get(id);
            usuarios.detach();
            XMLOutputter xmlout = new XMLOutputter();
            xmlout.setFormat(Format.getPrettyFormat());
            try (FileOutputStream fileout = new FileOutputStream(ruta + "Xml\\Datos.xml")) {
                xmlout.output(document, fileout);
                fileout.flush();
                fileout.close();
            }
            resp.sendRedirect("Borra");
       } catch (IOException io) {
            pw.println(io.getMessage());
        } catch (JDOMException jdomex) {
            pw.println(jdomex.getMessage());
        }
    }
}

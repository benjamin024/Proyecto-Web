package paquete;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class resuelveEjercicio extends HttpServlet {
    String[] respuestas;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        int preguntas = Integer.parseInt(request.getParameter("numPreguntas"));
        respuestas = new String[preguntas];
        for(int i = 1; i <= preguntas; i++){
            respuestas[i-1] = request.getParameter("pregunta"+i);
        }
        try {
            guardaResultados(sesion.getAttribute("user").toString(),request.getParameter("id"), request.getRealPath("/"), sesion.getAttribute("grupo").toString());
        } catch (Exception ex) {
            Logger.getLogger(resuelveEjercicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("calificacionEjercicio?id=" + request.getParameter("id"));
    }

    private void guardaResultados(String user, String ejercicio, String ruta, String grupo) throws Exception{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "xml\\ejerciciosResueltos.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        Element resuelto = new Element("ejercicioResuelto");
        resuelto.setAttribute("IdEjercicio",ejercicio);
        Element alumno = new Element("alumno");
        alumno.setText(user);
        resuelto.addContent(alumno);
        Element grupoE = new Element("grupo");
        grupoE.setText(grupo);
        resuelto.addContent(grupoE);
        Element[] preguntas = new Element[this.respuestas.length];
        for(int i = 0; i < preguntas.length; i++){
            preguntas[i] = new Element("pregunta");
            preguntas[i].setAttribute("ID", (i+1)+"");
            preguntas[i].setText(respuestas[i]);
            resuelto.addContent(preguntas[i]);
        }
        rootNode.addContent(resuelto);
        XMLOutputter xmlout = new XMLOutputter();
        xmlout.setFormat(Format.getPrettyFormat());
        try (FileOutputStream fileout = new FileOutputStream(ruta + "xml\\ejerciciosResueltos.xml")) {
            xmlout.output(document, fileout);
            fileout.flush();
            fileout.close();
        }
    }
}

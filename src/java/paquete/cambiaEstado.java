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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class cambiaEstado extends HttpServlet {

    private String rutaXML;
    private String mail;
    private String contra;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            String user = request.getParameter("user");
            rutaXML = request.getRealPath("/") + "xml";
            cambiarEstado(user,"1");
            enviarMail(user);
            response.sendRedirect("verSolicitudes");
        } catch (Exception ex) {
            Logger.getLogger(cambiaEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiarEstado(String user, String edo) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
        Element raiz = doc.getRootElement();  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            if(hijo.getAttributeValue("id").equals(user)){
                hijo.setAttribute("activo", edo);
                mail = hijo.getChildText("email");
                contra = hijo.getChildText("password");
            }
        }
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter(rutaXML + "/usuarios.xml"));
    }

    private void enviarMail(String user) {
        final String miCorreo = "escometflix@gmail.com";
        final String pass = "ESCOM123";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication
                getPasswordAuthentication() {
                    return new PasswordAuthentication(miCorreo, pass);
                }
            });
        
        try {
           Message message = new MimeMessage(session);
           //Seteamos la dirección desde la cual enviaremos el mensaje
           message.setFrom(new InternetAddress(miCorreo));
           //Seteamos el destino de nuestro mensaje
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
           //Seteamos el asunto
           message.setSubject("Has sido dado de alta en el Sistema");
           //Y por ultimo el texto.
           message.setText("Recientemente has solicitado registrarte al sistema para reforzar tus conocimientos sobre Ley de Ohm. \n¡Tu profesor ha aceptado tu solicitud!\n\nTus datos de acceso son:\nUsuario: " + user + "\nContraseña: " + contra + "\n\n¡Saludos!");
           //Esta orden envía el mensaje
           Transport.send(message);
           //Con esta imprimimos en consola que el mensaje fue enviado
           System.out.println("Mensaje Enviado");
        }catch(MessagingException e){
            System.out.println("Hubo un error al enviar el mensaje.");
        }
    }

    

}

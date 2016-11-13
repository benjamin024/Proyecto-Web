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

public class login extends HttpServlet {
    
    private int tipoUsuario = -1;
    private int activo = -1;
    private String rutaXML;
    private String nombre;
    private String grupo;
    
    private boolean usuarioEncontrado(String usr, String pass) throws Exception{
        SAXBuilder builder = new SAXBuilder(); 
        Document doc = builder.build(new FileInputStream(rutaXML + "/usuarios.xml"));
                // Obtenemos la etiqueta raíz  
        Element raiz = doc.getRootElement();  
        // Recorremos los hijos de la etiqueta raíz  
        List<Element> hijosRaiz = raiz.getChildren();  
        for(Element hijo: hijosRaiz){  
            String usuario = hijo.getAttributeValue("id");
            String password = hijo.getChildText("password");
            if(usuario.equals(usr) && password.equals(pass)){
                tipoUsuario = Integer.parseInt(hijo.getAttributeValue("tipo"));
                if(tipoUsuario == 3)
                    activo  = Integer.parseInt(hijo.getAttributeValue("activo"));
                nombre = hijo.getChildText("nombre");
                if(tipoUsuario != 1)
                    grupo = hijo.getChildText("grupo");
                return true;
            }
        } 
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String usuario = request.getParameter("user");
            String pass = request.getParameter("pass");
            response.setContentType("text/html;charset=UTF-8");
            rutaXML = request.getRealPath("/") + "xml";
            PrintWriter out=response.getWriter();
            try {
                boolean ok = usuarioEncontrado(usuario, pass);
                if(ok){ //usuario y contraseña correctas
                    switch(tipoUsuario){
                        case 1:
                            out.println("Bienvenido " + usuario);
                            break;
                        case 2:
                            HttpSession sesion = request.getSession();
                            sesion.setAttribute("nombre", nombre);
                            sesion.setAttribute("user", usuario);
                            sesion.setAttribute("grupo", grupo);
                            response.sendRedirect("profesor");
                            break;
                        case 3:
                            if(activo == 1){
                                out.println("Bienvenido " + usuario);
                            }else{
                                out.println("Usuario Inactivo");
                            }
                    }
                }else{
                    out.println("<script>alert('Usuario o password incorrectos');</script>");
                }
            } catch (Exception ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    

}

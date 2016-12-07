package paquete;

    import java.io.IOException;
    import java.io.PrintWriter;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    public class path extends HttpServlet
    {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException 
            {
            response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter(); 
                
                String path=request.getRealPath("/");
                out.println(path);
    		}
    }


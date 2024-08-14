package upload_file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig
public class upload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
 private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        Part filePart = request.getPart("file");
        
       String fileName = filePart.getSubmittedFileName();
    
        File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        File file = new File(uploadDir, fileName);
        try (InputStream inputStream = filePart.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        response.setContentType("text/html");
        response.getWriter().println("<html><style type=\"text/css\"> a{\r\n"
        		+ " 		position: relative;\r\n"
        		+ " 		background:#444;\r\n"
        		+ " 		color:green;\r\n"
        		+ " 		text-decoration: none;\r\n"
        		+ " 		text-transform: uppercase;\r\n"
        		+ " 		font-size: 1.5em;\r\n"
        		+ " 		letter-spacing:0.1em;\r\n"
        		+ " 		padding: 10px 20px;\r\n"
        		+ " 		transition: 0.5s;\r\n"
        		+ " 	}\r\n"
        		
        		+ "body{\r\n"
        		+ " 		text-align: flex;\r\n"
        		+ " 		color: white;\r\n"
        		+ " 		justify-content:center;	 \r\n"
        		+ " 		margin-left:300px;\r\n"
        		+ " 		background: black;\r\n"
        		+ " 		min-height:100vh;\r\n"
        		+ " 		flex-direction:column;\r\n"
        		+ " 		gap:30px;\r\n"
        		+ "\r\n"
        		+ " 	}</style><body>");
        
        response.getWriter().println("<h2>File uploaded successfully: " + fileName + "</h2>");
        response.getWriter().println("<a href='upload.html'>Upload another file</a>");
        response.getWriter().println("</body></html>");
    }
}
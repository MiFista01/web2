/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Biography;
import entity.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import session.BiographyFacade;
import session.Order_userFacade;
import session.PictureFacade;
import session.UnitFacade;
import session.UserFacade;
import tools.Password_protector;
/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index"})
@MultipartConfig()
public class servlet_prime extends HttpServlet {
    @EJB
    UserFacade userFacade = new UserFacade();
    @EJB
    UnitFacade unitFacade = new UnitFacade();
    @EJB
    PictureFacade pictureFacade = new PictureFacade();
    @EJB
    Order_userFacade order_userFacade = new Order_userFacade();
    @EJB
    BiographyFacade biographyFacade = new BiographyFacade();
    
    String role = "0";
    Password_protector password_protector;
    String salt;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    public void init()
            throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        if(userFacade.count() == 0){
            User pers = new User();
            password_protector = new Password_protector();
            salt = password_protector.getSalt();
            System.out.println(salt);
            pers.setSalt(salt);
            int role = 2;
            String password = "12345";
            pers.setLogin("admin");
            pers.setName("Tatjana");
            pers.setSurname("Matskevits");
            pers.setPhone("XXXXXX");
            pers.setEmail("example@gmail.com");
            pers.setPassword(password_protector.getPassword_protector(password, salt));
            pers.setRole(role);
            userFacade.create(pers);
        }
        if(biographyFacade.count() == 0){
            Biography biography = new Biography();
            biographyFacade.create(biography);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path){
            case "/index":
                request.getRequestDispatcher("index.html").forward(request, response);
                break;
        }
    }

    
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
    }
    public void writeToFile(byte[] data, String fileName) throws IOException{
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(data);
        }
    }
    public byte[] resize(File icon) {
        try {
           BufferedImage originalImage = ImageIO.read(icon);
           byte[] imageInByte;
            try ( //To save with original ratio uncomment next line and comment the above.
            //originalImage= Scalr.resize(originalImage, 153, 128);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                String name = icon.getName();
                if ("jpg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "jpg", baos);
                }
                if ("png".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "png", baos);
                }
                if ("svg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "svg", baos);
                }
                if ("jpeg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "jpeg", baos);
                }
                baos.flush();
                imageInByte = baos.toByteArray();
            }
            return imageInByte;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean getParameterValues(String kinds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sitelab;

import static com.sun.webkit.graphics.RenderTheme.METER;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.security.util.Length;

/**
 *
 * @author Brandon
 */
@WebServlet(name = "interest-calculator", urlPatterns = {"/interest-calculator"})
public class InterestCalcServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("interestcalculator.jsp");
        rd.forward(request, response);
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
        Locale loc = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(loc);
        

        try {
            double principal = Integer.parseInt(request.getParameter("principal"));
            double interestRate = Integer.parseInt(request.getParameter("interest"));
            int years = Integer.parseInt(request.getParameter("years"));

            List<Interest> interests = new ArrayList<>();
            for (int i = 0; i < years; i++) {
                double interestForYear;
                Interest interest = new Interest();
                interest.setYear(i + 1);
                interest.setBeginningPrincipal(nf.format(principal));

                interestForYear = principal * (interestRate / 100);
                principal += interestForYear;

                interest.setInterestEarned(nf.format(interestForYear));
                interest.setEndingPrincipal(nf.format(principal));
                interests.add(interest);
            }
            request.setAttribute("interests", interests);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Input error. Please try again.");
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Interest Calculator";
    }// </editor-fold>

}

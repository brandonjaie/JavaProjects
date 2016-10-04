/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sitelab;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brandon
 */
@WebServlet(name = "flooring-calculator", urlPatterns = {"/flooring-calculator"})
public class FlooringCalcServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("flooringcalculator.jsp");
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
            double laborCostPerHour = 86.0;

            int width = Integer.parseInt(request.getParameter("width"));
            int length = Integer.parseInt(request.getParameter("length"));
            double cost = Double.parseDouble(request.getParameter("cost"));

            double materialCost = width * length * cost;

            int labor = (width * length) / 20;
            if ((width * length) % 20 != 0) {
                labor++;
            }

            double laborCost = labor * (laborCostPerHour / 4);
            
            request.setAttribute("width", width);
            request.setAttribute("length", length);
            request.setAttribute("cost", (nf.format(cost)));
            request.setAttribute("materialCost", (nf.format(materialCost)));
            request.setAttribute("laborCost", (nf.format(laborCost)));
            request.setAttribute("totalCost", (nf.format(laborCost + materialCost)));

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
        return "Flooring Calculator";
    }// </editor-fold>

}

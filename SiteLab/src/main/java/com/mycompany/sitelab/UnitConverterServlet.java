/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sitelab;

import java.io.IOException;
import java.text.DecimalFormat;
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
@WebServlet(name = "unit-converter", urlPatterns = {"/unit-converter"})
public class UnitConverterServlet extends HttpServlet {

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
        RequestDispatcher rd = request.getRequestDispatcher("unitconverter.jsp");
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

        double factor = 0;
        double result = 0;

        String userInput1 = "";
        String userInput2 = "";

        try {
            DecimalFormat df = new DecimalFormat("#.###");
            userInput1 = request.getParameter("userInput1");
            userInput2 = request.getParameter("userInput2");
            double value = Double.parseDouble(request.getParameter("value"));

            switch (userInput1) {
                case "Millimeters":
                    switch (userInput2) {
                        case "Centimeters":
                            result = value * 0.1;
                            break;
                        case "Inches":
                            result = value * 0.0393701;
                            break;
                        case "Feet":
                            result = value * 0.00328084;
                            break;
                        case "Yards":
                            result = value * 0.00109361;
                            break;
                        default:
                            result = value;
                            break;
                    }
                    break;
                case "Centimeters":
                    switch (userInput2) {
                        case "Millimeters":
                            result = value / 0.1;
                            break;
                        case "Inches":
                            result = value * 0.393701;
                            break;
                        case "Feet":
                            result = value * 0.0328084;
                            break;
                        case "Yards":
                            result = value * 0.0109361;
                            break;
                        default:
                            result = value;
                            break;
                    }
                    break;
                case "Inches":
                    switch (userInput2) {
                        case "Millimeters":
                            result = value / 0.039370;
                            break;
                        case "Centimeters":
                            result = value / 0.393701;
                            break;
                        case "Feet":
                            result = value * 0.08333333;
                            break;
                        case "Yards":
                            result = value * 0.0277778;
                            break;
                        default:
                            result = value;
                            break;
                    }
                    break;
                case "Feet":
                    switch (userInput2) {
                        case "Millimeters":
                            result = value * 0.00328084;
                            break;
                        case "Centimeters":
                            result = value / 0.0328084;
                            break;
                        case "Inches":
                            result = value / 0.08333333;
                            break;
                        case "Yards":
                            result = value * 0.333333;
                            break;
                        default:
                            result = value;
                            break;
                    }
                    break;
                case "Yards":
                    switch (userInput2) {
                        case "Millimeters":
                            result = value / 0.00109361;
                            break;
                        case "Centimeters":
                            result = value / 0.0109361;
                            break;
                        case "Inches":
                            result = value / 0.0277778;
                            break;
                        case "Feet":
                            result = value / 0.333333;
                            break;
                        default:
                            result = value;
                            break;
                    }

                    break;
                default:
                    break;
            }
            request.setAttribute("userInput1", userInput1);
            request.setAttribute("userInput2", userInput2);
            request.setAttribute("value", value);
            request.setAttribute("factor", factor);
            request.setAttribute("result", df.format(result));

        } catch (NumberFormatException | NullPointerException ex) {
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
        return "Unit Convertor";
    }// </editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactlistmvc;


import com.mycompany.contactlistmvc.dto.CompanyContactCount;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.render.JsonRenderer;
import com.mycompany.contactlistmvc.dao.ContactListDao;
import javax.inject.Inject;

/**
 *
 * @author Brandon
 */
@Controller
public class StatsController {
    
        private ContactListDao dao;

    @Inject
    public StatsController(ContactListDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String displayStatsPage() {
        return "stats";
    }
    
//Searching for Contacts
//Verb: GET
//URL: stats/chart
//RequestBody: None
//ResponseBody: Google DataTable JSON object containing Contact chart data 

    @RequestMapping(value = "/stats/chart", method = RequestMethod.GET)
// the @ResponseBody annotation tells Spring MVC that the value returned from
// this method is NOT the name of a View component. The value returned should
// be the body of the response to the caller
    @ResponseBody
    public String getDataTable() {
        try {
            // Get the contact counts for each company
            List<CompanyContactCount> counts = dao.getCompanyContactCounts();

            // Set up the table columns and descriptions
            DataTable t = new DataTable();
            t.addColumn(new ColumnDescription("Company_Name",
                    ValueType.TEXT,
                    "Company"));
            t.addColumn(new ColumnDescription("Number_Contacts",
                    ValueType.NUMBER,
                    "# Contacts"));
            // Convert each CompanyContactCount object into a table row
            for (CompanyContactCount currentCount : counts) {
                TableRow tr = new TableRow();
                tr.addCell(currentCount.getCompany());
                tr.addCell(currentCount.getNumContacts());
                t.addRow(tr);
            }
            // Use the JsonRenderer to convert the DataTable to a JSON string
            // the default Jackson converter doesn't convert the DataTable object
            // to JSON properly so we have to do it here.
            return JsonRenderer.renderDataTable(t, true, false, false).toString();
        } catch (Exception e) {
            // just return an error string if we encounter an issue
            return "Invalid Data";
        }
    }
}

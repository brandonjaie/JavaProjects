/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine;

import com.tsg.vendingmachine.dao.ItemDao;
import com.tsg.vendingmachine.dto.Change;
import com.tsg.vendingmachine.dto.Item;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Brandon
 */
@Controller
public class VendingMachineController {

    private Change change;
    private ItemDao iDao;

    @Inject
    public VendingMachineController(ItemDao iDao) {
        this.iDao = iDao;
    }

    @RequestMapping(value = {"/", "/vend"}, method = RequestMethod.GET)
    public String displayHomePage(Model model) {
        List<Item> items = iDao.getAllItems();
        model.addAttribute("items", items);
        return "vend";
    }

    @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
    public String displayErrorPage(Model model) {
        List<Item> items = iDao.getAllItems();
        model.addAttribute("items", items);
        return "error";
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String displayAdminPage(Model model) {

        List<Item> items = iDao.getAllItems();
        model.addAttribute("items", items);
        return "admin";
    }

    @RequestMapping(value = "/vendItem", method = RequestMethod.POST)
    public String vendItem(HttpServletRequest req, Model model) {

        Locale loc = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(loc);

        String totalDeposit = req.getParameter("totalDeposit");
        totalDeposit = (!(totalDeposit).equals("")) ? totalDeposit.substring(2) : "0.00";

        String selection = req.getParameter("itemId");

        if (!totalDeposit.equals("") && selection == null) {
            double coinReturn = Double.parseDouble(totalDeposit);
            req.setAttribute("coinReturnMsg", "Coin Return");
            change = new Change(coinReturn);

            req.setAttribute("quarters", change.getQuarters());
            req.setAttribute("dimes", change.getDimes());
            req.setAttribute("nickels", change.getNickels());

        } else {

            int itemId = Integer.parseInt(selection);

            if (itemId != 0) {

                Item item = iDao.getItemById(itemId);

//                totalDeposit = (!(totalDeposit).equals("")) ? totalDeposit.substring(2) : "0.00";
                double deposit = Double.parseDouble(totalDeposit);
                double coins = (deposit) - (item.getItemPrice());
                change = new Change(coins);

                model.addAttribute("item", item);
                req.setAttribute("itemPrice", nf.format(item.getItemPrice()));
                req.setAttribute("image", item.getImage());
                req.setAttribute("coins", nf.format(coins));
                req.setAttribute("deposit", nf.format(deposit));

                if (item.getInventory() > 0) {
                    if (deposit >= item.getItemPrice()) {
                        req.setAttribute("successMsg", item.getItemName());
                        iDao.vendItem(itemId);
                    } else {
                        double insertAmount = (deposit) - (item.getItemPrice());
                        double coinReturn = Double.parseDouble(totalDeposit);
                        req.setAttribute("coinReturnMsg", "Coin Return");
                        change = new Change(coinReturn);
                        req.setAttribute("quarters", change.getQuarters());
                        req.setAttribute("dimes", change.getDimes());
                        req.setAttribute("nickels", change.getNickels());
                        req.setAttribute("insertAmount", nf.format(insertAmount));
                        req.setAttribute("insertFundsMsg", "Insufficient Funds");
                    }
                } else {
                    req.setAttribute("soldOutMsg", "Sold Out");
                }
            }
            req.setAttribute("quarters", change.getQuarters());
            req.setAttribute("dimes", change.getDimes());
            req.setAttribute("nickels", change.getNickels());
        }

        List<Item> items = iDao.getAllItems();
        model.addAttribute("items", items);

        return "vend";
    }

    @RequestMapping(value = "/stockInventory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stockInventory() {
        iDao.stockInventory();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Status;
import java.util.List;

/**
 *
 * @author Brandon
 */
public interface StatusDao {
    
    public Status getStatusById(int statusId);

    public Status getStatusByName(String status);
    
}

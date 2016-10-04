/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Category;

/**
 *
 * @author Brandon
 */
public interface CategoryDao {
    
    public Category getCategoryById(int categoryId);
    
}

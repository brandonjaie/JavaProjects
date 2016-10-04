/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Authority;

/**
 *
 * @author Brandon
 */
public interface AuthorityDao {

    public Authority getHighestAuthorityByUserName(String userName);

    public Authority getAuthorityByName(String authority);

}

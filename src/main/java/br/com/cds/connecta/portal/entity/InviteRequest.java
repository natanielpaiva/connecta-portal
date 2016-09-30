/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author heloisa
 */
public class InviteRequest {
    
    @Id
    private Long id;
    
    @Column
    private String sender;
    
    @Column
    private Domain domain;
    
    @Column
    private String receiver;
    
    @Column
    private String hash;
    
}

package com.slashmobility.taxly.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends AppUser{
    
    private Date termsAcceptedDate;
    
   
	public Date getTermsAcceptedDate() {
		return termsAcceptedDate;
	}

	public void setTermsAcceptedDate(Date termsAcceptedDate) {
		this.termsAcceptedDate = termsAcceptedDate;
	}

    
}

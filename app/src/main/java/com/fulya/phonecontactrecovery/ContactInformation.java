package com.fulya.phonecontactrecovery;

/**
 * Created by fulya on 10.04.2016.
 */
public class ContactInformation {
    private String  contactName;
    private String  contactPhoneNumber;

    public ContactInformation(String contactName, String contactPhoneNumber) {
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactName() {
        return contactName;
    }
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

}

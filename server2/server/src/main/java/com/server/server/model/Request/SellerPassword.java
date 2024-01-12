package com.server.server.model.Request;

public class SellerPassword {
    
    private String id;
    private String oldPassword;
    private String newPassword;

    public String getId(){
        return this.id;
    }

    public String getOldPassword(){
        return this.oldPassword;
    }

    public String getNewPassword(){
        return this.newPassword;
    }
}

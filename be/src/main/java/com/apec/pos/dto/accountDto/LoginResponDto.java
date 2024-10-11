package com.apec.pos.dto.accountDto;

import com.apec.pos.entity.RoleEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class LoginResponDto {
    private Integer id;
    private Set<RoleEntity> role;
    private String token;
    private String sdt;
    private String accountName;
    private String imgUser;
    private String username;
    private String email;

    public LoginResponDto(Integer id, Set<RoleEntity> role, String token, String sdt, String accountName, String imgUser, String username, String email) {
        this.id = id;
        this.role = role;
        this.token = token;
        this.sdt = sdt;
        this.accountName = accountName;
        this.imgUser = imgUser;
        this.username = username;
        this.email = email;
    }

    public LoginResponDto(Integer id, Set<RoleEntity> role, String token, String sdt, String accountName,
                          String imgUser, String username) {
        super();
        this.id = id;
        this.role = role;
        this.token = token;
        this.sdt = sdt;
        this.accountName = accountName;
        this.imgUser = imgUser;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }



    public LoginResponDto(Set<RoleEntity> role, String token, String sdt, String accountName, String imgUser,
                          String username) {
        super();
        this.role = role;
        this.token = token;
        this.sdt = sdt;
        this.accountName = accountName;
        this.imgUser = imgUser;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginResponDto(Set<RoleEntity> role, String token, String sdt, String accountName, String imgUser) {
        super();
        this.role = role;
        this.token = token;
        this.sdt = sdt;
        this.accountName = accountName;
        this.imgUser = imgUser;
    }

    public Set<RoleEntity> getRole() {
        return role;
    }

    public void setRole(Set<RoleEntity> role) {
        this.role = role;
    }

    public String getsdt() {
        return sdt;
    }

    public void setsdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponDto(Set<RoleEntity> role, String token) {
        super();
        this.role = role;
        this.token = token;
    }

    public LoginResponDto() {
        super();
    }


}

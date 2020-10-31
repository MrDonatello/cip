package com.cip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.Cookie;
import java.util.UUID;

/*@Entity*/
public class Session {

   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private int userId;
    private Cookie cookie;


    public Session createSession(int userId) {
        UUID uuid = UUID.randomUUID();
        this.cookie = new Cookie("JAVASESSIONID", uuid.toString());
        this.cookie.setMaxAge(-1);
        this.userId = userId;
        return this;
    }

    public Session() {
    }

    public Cookie getCookie() {
        return cookie;
    }

    public int getUserId() {
        return userId;
    }
}

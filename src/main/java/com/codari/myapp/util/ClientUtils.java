package com.codari.myapp.util;

import javax.servlet.http.HttpServletRequest;

public class ClientUtils {
    public static String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR"); 
        
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
       
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }   
        return ip;
   }
}

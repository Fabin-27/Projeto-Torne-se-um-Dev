package com.web.Lourenco.SpringWeb.servico;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String valor, int segundos) throws IOException {
        // O primeiro parâmetro deve ser o nome do cookie, e o segundo parâmetro deve ser o valor.
        Cookie cookie = new Cookie(key, URLEncoder.encode(valor, "UTF-8"));
        cookie.setMaxAge(segundos);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // Definir o caminho para o cookie
        response.addCookie(cookie);
    }
    

    public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException{
        String valor = Optional.ofNullable(request.getCookies())
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(cookie -> key.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    
        if (valor != null) {
            valor = URLDecoder.decode(valor, "UTF-8");
        }
        return valor;
    }
    
    
    
}

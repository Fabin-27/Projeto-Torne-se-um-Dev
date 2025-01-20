package com.web.Lourenco.SpringWeb.Controllers;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.Lourenco.SpringWeb.models.Administrador;
import com.web.Lourenco.SpringWeb.repositorio.AdministradoresRepo;
import com.web.Lourenco.SpringWeb.servico.CookieService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private AdministradoresRepo repo;

    @GetMapping("/login")
    public String index() {
        return "login/index";
    }

    @PostMapping("/logar")
    public String logar(Model model, Administrador admParam, String lembrar, HttpServletResponse response) throws IOException {

        // Verificando se as credenciais estão corretas
        Administrador adm = this.repo.login(admParam.getEmail(), admParam.getSenha());
        
        if (adm != null) {
            int tempoLogado = (60 * 60); // 1 hora de cookie
            if (lembrar != null) tempoLogado = (60 * 60 * 24 * 365); // 1 ano de cookie

            // Verificando se o nome do usuário é válido
            String nomeUsuario = adm.getNome();
            if (nomeUsuario == null || nomeUsuario.isEmpty()) {
                nomeUsuario = "UsuarioAnonimo"; // Valor padrão caso o nome seja nulo ou vazio
            }

            // Codificando o nome do usuário para garantir que não haja caracteres inválidos
            String nomeCodificado = URLEncoder.encode(nomeUsuario, "UTF-8").replaceAll("\\+", " ");

            // Verificando se o ID do usuário é válido
            String usuarioId = String.valueOf(adm.getId());
            if (usuarioId == null || usuarioId.isEmpty()) {
                usuarioId = "0"; // Valor padrão caso o ID seja nulo ou vazio
            }

            // Definindo os cookies
            CookieService.setCookie(response, "usuarioId", usuarioId, tempoLogado);
            CookieService.setCookie(response, "nomeUsuario", nomeCodificado, tempoLogado);

            return "redirect:/"; // Redireciona para a página principal
        }

        // Caso o usuário ou senha estejam incorretos
        model.addAttribute("erro", "Usuário ou senha inválidos");
        return "login/index"; // Retorna para a tela de login
    }

    @GetMapping("/sair")
    public String sair(HttpServletResponse response) throws IOException {
        // Apaga os cookies ao sair
        CookieService.setCookie(response, "usuarioId", "", 0);
        CookieService.setCookie(response, "nomeUsuario", "", 0);

        return "redirect:/login"; // Redireciona para a tela de login
    }
}

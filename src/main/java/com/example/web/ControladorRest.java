package com.example.web;

import com.example.domain.Individuo;
import com.example.servicio.IndividuoServicio;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ControladorRest {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private IndividuoServicio individuoServicio;

    public ControladorRest(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) datasource);
    }

    @GetMapping("/")
    public String comienzo(Model model) {

        List<Individuo> individuos = individuoServicio.listaIndividuos();
        model.addAttribute("individuos", individuos);

        return "Formulario";
    }

    @GetMapping("/anexar")
    public String anexar(Individuo individuo) {
        return "Registrar";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("usuario") String usuario,
            @RequestParam("correo") String correo,
            @RequestParam("contrasena") String contrasena,
            Model model) {

        if (usuario.isEmpty() && correo.isEmpty() && contrasena.isEmpty()) {
            model.addAttribute("mensaje", "Debes llenar al menos un campo");
            return "Formulario";
        }


        // Verificar si el usuario está registrado en la base de datos
        boolean usuarioValido = verificarUsuario(usuario, correo, contrasena);

        if (usuarioValido) {
            // Usuario válido, continuar con la lógica de tu aplicación
            model.addAttribute("mensaje", "Usuario exitoso");
            return "Buscador";
        } else {
            // Usuario no válido, mostrar mensaje de error
            model.addAttribute("mensaje", "Usuario no válido");
            return "Formulario";
      
      }
    }

    @PostMapping("/salvar")
    public String salvar(Individuo individuo, Model model) {
        if (individuo.getUsuario().isEmpty() && individuo.getCorreo().isEmpty() && individuo.getContraseña().isEmpty()) {
            model.addAttribute("mensaje", "Debes llenar al menos un campo");
            return "Registrar";
        } if (individuo.getContrasena().length() < 8) {
            model.addAttribute("mensaje", "La contraseña debe tener al menos 8 caracteres");
            return "Registrar";
        }

        individuoServicio.salvar(individuo);
        return "Formulario";
    }

    @PostMapping("/buscar")
    public String buscar(Individuo individuo) {
        individuoServicio.buscar(individuo);
        return "Guardado";

    }

    private boolean verificarUsuario(String usuario, String correo, String contrasena) {
        String query = "SELECT COUNT(*) FROM usuario WHERE usuario = ? AND correo = ? AND contrasena = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, usuario, correo, contrasena);
        return count == 1;
    }

}

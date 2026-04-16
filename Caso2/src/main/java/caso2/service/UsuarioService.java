package caso2.service;

import caso2.domain.Usuario;
import caso2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    public List<Usuario> listarUsuarios() { 
        return usuarioRepository.findAll(); 
    }
    
    public Usuario guardar(Usuario usuario, boolean esNuevo) {
        Usuario guardado = usuarioRepository.save(usuario);
        
        if (esNuevo) {
            enviarCorreoBienvenida(guardado.getEmail(), guardado.getNombre());
        }
        return guardado;
    }

    public Usuario buscarPorId(Long id) { 
        return usuarioRepository.findById(id).orElse(null); 
    }
    
    public void eliminar(Long id) { 
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        }
    }

    public List<Usuario> buscarPorRol(String nombreRol) { 
        return usuarioRepository.findByRolNombre(nombreRol); 
    }

    private void enviarCorreoBienvenida(String emailDestino, String nombre) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDestino);
            message.setSubject("¡Bienvenido a la Plataforma de Eventos!");
            message.setText("Hola " + nombre + ",\n\nTu cuenta ha sido creada exitosamente y ya puedes acceder al sistema. ¡Bienvenido!");
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
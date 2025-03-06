package com.yanvelasco.user.model.service;

import com.yanvelasco.user.model.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO);
    ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(String email);
    ResponseEntity<Void> deletaUsuarioPorEmail(String email);
}
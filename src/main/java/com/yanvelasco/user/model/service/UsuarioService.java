package com.yanvelasco.user.model.service;

import com.yanvelasco.user.model.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    public ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO);
}

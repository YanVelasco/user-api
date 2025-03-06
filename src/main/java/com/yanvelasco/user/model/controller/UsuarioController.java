package com.yanvelasco.user.model.controller;

import com.yanvelasco.user.infra.security.JwtUtil;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.service.impl.UsuarioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl, AuthenticationManager authenticationManager,
                             JwtUtil jwtUtil) {
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServiceImpl.cadastrar(usuarioDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return usuarioServiceImpl.buscarUsuarioPorEmail(email);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioServiceImpl.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
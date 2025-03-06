package com.yanvelasco.user.model.service.impl;

import com.yanvelasco.user.exceptions.AlreadyExistsException;
import com.yanvelasco.user.exceptions.ResourceNotFoundException;
import com.yanvelasco.user.infra.security.JwtUtil;
import com.yanvelasco.user.mapper.UsuarioMapper;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.entity.Usuario;
import com.yanvelasco.user.model.repository.UsuarioRepository;
import com.yanvelasco.user.model.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
                              BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email já está cadastrado");
        }

        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioMapper.toUsuario(usuarioDTO);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toUsuarioDTO(usuario));
    }

    @Override
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.map(value -> ResponseEntity.ok(usuarioMapper.toUsuarioDTO(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<Void> deletaUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<UsuarioDTO> atualizarDadosDoUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email", "email", email)
        );

        if(usuarioDTO.getNome() != null){
            usuario.setNome(usuarioDTO.getNome());
        }
        if(usuarioDTO.getEmail() != null){
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if(usuarioDTO.getSenha() != null){
            usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioMapper.toUsuarioDTO(usuario));
    }

}
package com.yanvelasco.user.model.service.impl;

import com.yanvelasco.user.exceptions.AlreadyExistsException;
import com.yanvelasco.user.mapper.UsuarioMapper;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.entity.Usuario;
import com.yanvelasco.user.model.repository.EnderecoRepository;
import com.yanvelasco.user.model.repository.TelefoneRepository;
import com.yanvelasco.user.model.repository.UsuarioRepository;
import com.yanvelasco.user.model.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TelefoneRepository telefoneRepository, EnderecoRepository enderecoRepository, UsuarioMapper usuarioMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.telefoneRepository = telefoneRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email já está cadastrado");
        }

        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usario = usuarioMapper.toUsuario(usuarioDTO);
        usuarioRepository.save(usario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toUsuarioDTO(usario));
    }
}
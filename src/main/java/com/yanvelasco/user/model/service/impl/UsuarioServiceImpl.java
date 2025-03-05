package com.yanvelasco.user.model.service.impl;

import com.yanvelasco.user.mapper.UsuarioMapper;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.entity.Usuario;
import com.yanvelasco.user.model.repository.EnderecoRepository;
import com.yanvelasco.user.model.repository.TelefoneRepository;
import com.yanvelasco.user.model.repository.UsuarioRepository;
import com.yanvelasco.user.model.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TelefoneRepository telefoneRepository, EnderecoRepository enderecoRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.telefoneRepository = telefoneRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO) {
        Usuario usario = usuarioMapper.toUsuario(usuarioDTO);
        usuarioRepository.save(usario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toUsuarioDTO(usario));
    }
}

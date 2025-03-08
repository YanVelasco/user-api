package com.yanvelasco.user.model.service;

import com.yanvelasco.user.model.dto.EnderecoDTO;
import com.yanvelasco.user.model.dto.TelefoneDTO;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    ResponseEntity<UsuarioDTO> cadastrar(UsuarioDTO usuarioDTO);

    ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(String email);

    ResponseEntity<Void> deletaUsuarioPorEmail(String email);

    ResponseEntity<UsuarioDTO> atualizarDadosDoUsuario(String token, UsuarioDTO usuarioDTO);

    ResponseEntity<EnderecoDTO> atualizarEndereco(Long id, EnderecoDTO enderecoDTO);

    ResponseEntity<TelefoneDTO> atualizarTelefone(Long id, TelefoneDTO telefoneDTO);

    ResponseEntity<EnderecoDTO> cadastrarEndereco(String token, EnderecoDTO enderecoDTO);

    ResponseEntity<TelefoneDTO> cadastrarTelefone(String token, TelefoneDTO telefoneDTO);
}
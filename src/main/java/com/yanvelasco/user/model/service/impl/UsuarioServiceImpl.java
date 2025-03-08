package com.yanvelasco.user.model.service.impl;

import com.yanvelasco.user.exceptions.AlreadyExistsException;
import com.yanvelasco.user.exceptions.ResourceNotFoundException;
import com.yanvelasco.user.infra.security.JwtUtil;
import com.yanvelasco.user.mapper.UsuarioMapper;
import com.yanvelasco.user.model.dto.EnderecoDTO;
import com.yanvelasco.user.model.dto.TelefoneDTO;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.entity.Endereco;
import com.yanvelasco.user.model.entity.Telefone;
import com.yanvelasco.user.model.entity.Usuario;
import com.yanvelasco.user.model.repository.EnderecoRepository;
import com.yanvelasco.user.model.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
                              BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
                              EnderecoRepository enderecoRepository, TelefoneRepository telefoneRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
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

        if (usuarioDTO.getNome() != null) {
            usuario.setNome(usuarioDTO.getNome());
        }
        if (usuarioDTO.getEmail() != null) {
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getSenha() != null) {
            usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioMapper.toUsuarioDTO(usuario));
    }

    @Override
    public ResponseEntity<EnderecoDTO> atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
        var endereco = enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Endereço", "id", id.toString())
        );

        usuarioMapper.updateEndereco(enderecoDTO, endereco);
        enderecoRepository.save(endereco);

        return ResponseEntity.ok(usuarioMapper.toEnderecoDTO(endereco));
    }

    @Override
    public ResponseEntity<TelefoneDTO> atualizarTelefone(Long id, TelefoneDTO telefoneDTO) {
        var telefone = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Telefone", "id", id.toString())
        );

        if (telefoneDTO.getNumero() != null) {
            telefone.setNumero(telefoneDTO.getNumero());
        }
        if (telefoneDTO.getDdd() != null) {
            telefone.setDdd(telefoneDTO.getDdd());
        }

        telefoneRepository.save(telefone);

        usuarioMapper.upadateTelefone(telefoneDTO, telefone);

        return ResponseEntity.ok(telefoneDTO);

    }

    public ResponseEntity<EnderecoDTO> cadastrarEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email", "email", email)
        );

        Endereco endereco = usuarioMapper.toEndereco(enderecoDTO, usuario.getId());

        enderecoRepository.save(endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toEnderecoDTO(endereco));
    }

    public ResponseEntity<TelefoneDTO> cadastrarTelefone(String token, TelefoneDTO telefoneDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email", "email", email)
        );

        Telefone telefone = usuarioMapper.toTelefone(telefoneDTO, usuario.getId());

        telefoneRepository.save(telefone);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toTelefoneDTO(telefone));
    }
}
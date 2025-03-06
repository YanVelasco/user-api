package com.yanvelasco.user.mapper;

import com.yanvelasco.user.model.dto.EnderecoDTO;
import com.yanvelasco.user.model.dto.TelefoneDTO;
import com.yanvelasco.user.model.dto.UsuarioDTO;
import com.yanvelasco.user.model.entity.Endereco;
import com.yanvelasco.user.model.entity.Telefone;
import com.yanvelasco.user.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(toListEndereco(usuarioDTO.getEnderecoDTO()))
                .telefones(toListTelefone(usuarioDTO.getTelefoneDTO()))
                .build();
    }

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecoDTO(toListEnderecoDTO(usuario.getEnderecos()))
                .telefoneDTO(toListTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<Endereco> toListEndereco(List<EnderecoDTO> enderecoDTO) {
        return enderecoDTO.stream()
                .map(this::toEndereco)
                .toList();
    }

    public List<EnderecoDTO> toListEnderecoDTO(List<Endereco> endereco) {
        return endereco.stream()
                .map(this::toEnderecoDTO)
                .toList();
    }

    public EnderecoDTO toEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> toListTelefoneDTO(List<Telefone> telefone) {
        return telefone.stream()
                .map(this::toTelefoneDTO)
                .toList();
    }

    public TelefoneDTO toTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Endereco toEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .id(enderecoDTO.getId())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> toListTelefone(List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream()
                .map(this::toTelefone)
                .toList();
    }

    public Telefone toTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .id(telefoneDTO.getId())
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }


    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco) {
        if (enderecoDTO.getRua() != null) {
            endereco.setRua(enderecoDTO.getRua());
        }
        if (enderecoDTO.getNumero() != null) {
            endereco.setNumero(enderecoDTO.getNumero());
        }
        if (enderecoDTO.getComplemento() != null) {
            endereco.setComplemento(enderecoDTO.getComplemento());
        }
        if (enderecoDTO.getCidade() != null) {
            endereco.setCidade(enderecoDTO.getCidade());
        }
        if (enderecoDTO.getEstado() != null) {
            endereco.setEstado(enderecoDTO.getEstado());
        }
        if (enderecoDTO.getCep() != null) {
            endereco.setCep(enderecoDTO.getCep());
        }
        return endereco;
    }

    public Telefone upadateTelefone(TelefoneDTO telefoneDTO, Telefone telefone) {
        Telefone.builder()
                .id(telefoneDTO.getId())
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
        return telefone;
    }
}
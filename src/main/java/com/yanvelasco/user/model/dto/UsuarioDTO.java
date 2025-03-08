package com.yanvelasco.user.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO> enderecoDTO;
    private List<TelefoneDTO> telefoneDTO;
}

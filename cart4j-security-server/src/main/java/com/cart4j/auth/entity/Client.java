package com.cart4j.auth.entity;

import com.cart4j.model.security.dto.v1.ClientDto;
import com.cart4j.model.security.dto.v1.ResourceDto;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name="c4_client")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Client implements Serializable {
   private static final long serialVersionUID = -5850296378213921930L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientUniqueId;
    private String clientSecret;
    @OneToMany(mappedBy="client")
    private List<AccessToken> accessTokens;


    @OneToMany(mappedBy = "client")
    private List<RedirectUri> redirectUris;

    @ManyToMany
    @JoinTable(name = "c4_client_resource", joinColumns=@JoinColumn(name="client_id",
            referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"))
    private List<Resource> resources;

    @ManyToMany
    @JoinTable(name = "c4_client_scope", joinColumns=@JoinColumn(name="client_id",
            referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "scope_id", referencedColumnName = "id"))
    private List<Scope> scopes;

    @ManyToMany
    @JoinTable(name = "c4_client_role", joinColumns=@JoinColumn(name="client_id",
            referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    private String grantTypes;

    public ClientDto toDto() {
     List<ResourceDto> resourceDtoList = null;
     if (!CollectionUtils.isEmpty(resources)) {
       resourceDtoList = resources.stream().map(Resource::toDto).collect(Collectors.toList());
     }

     return ClientDto.builder()
               .clientUniqueId(clientUniqueId)
               .grantTypes(grantTypes)
               .resources(resourceDtoList)
               .id(id)
               .build();
    }
}

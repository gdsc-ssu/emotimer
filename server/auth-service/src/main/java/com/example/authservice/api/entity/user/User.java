package com.example.authservice.api.entity.user;

import com.example.authservice.oauth.ProviderType;
import com.example.authservice.oauth.RoleType;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Size(max = 512)
    @Column(name = "EMAIL", unique = true) // 이메일은 유일
    private String email;

    @JsonIgnore
    @NotNull
    @Size(max = 128)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Size(max = 100)
    @Column(name = "USERNAME")
    private String username;

//    @NotNull
//    @Column(name = "PROFILE_IMAGE_URL")
//    @Size(max = 512)
//    private String profileImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER_TYPE", length = 20)
    private ProviderType providerType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE", length = 20)
    private RoleType roleType;

    @NotNull
    @Column(name = "UPDATED_AT")
    @CreatedDate
    private LocalDateTime updatedAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public User(String email, String password, String username, ProviderType providerType, RoleType roleType){
        this.email = email;
        this.password = password;
        this.username = username;
        this.providerType = providerType;
        this.roleType = roleType;
    }

}

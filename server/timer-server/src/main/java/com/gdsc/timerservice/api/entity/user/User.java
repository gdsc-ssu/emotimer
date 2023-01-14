package com.gdsc.timerservice.api.entity.user;

import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

	@Id
	private String userId;

	@NotNull
	@Size(max = 512)
	private String email;

	@JsonIgnore
	@NotNull
	@Size(max = 128)
	private String password;


	@Size(max = 100)
	private String username;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProviderType providerType;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@NotNull
	@CreatedDate
	private LocalDateTime updatedAt;

	@NotNull
	@LastModifiedDate
	private LocalDateTime createdAt;

	private String imageUrl;

	@Builder
	public User(String email, String password, String username, ProviderType providerType, RoleType roleType) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.providerType = providerType;
		this.roleType = roleType;
	}

	public User(String userId) {
		this.userId = userId;
	}

	public User(User user) {
		this.username = user.getUsername();
		this.roleType = user.getRoleType();
		this.email = user.getEmail();
	}
}

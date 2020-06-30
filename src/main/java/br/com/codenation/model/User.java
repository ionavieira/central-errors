package br.com.codenation.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.codenation.model.interfaces.IModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends AbstractAuditingEntity implements IModel<UUID> {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotNull
	@Size(min = 3)
	private String name;

	@Email
	@NotNull
	private String email;

	@NotNull
	private String password;

	private String token;

	@Builder.Default
	private Boolean active = true;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Error> errors;

}

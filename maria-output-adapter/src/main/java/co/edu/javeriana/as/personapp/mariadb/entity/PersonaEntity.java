package co.edu.javeriana.as.personapp.mariadb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author aasanchez
 */
@Entity
@Table(name="persona", catalog = "persona_db", schema = "")
@NamedQueries({ @NamedQuery(name = "PersonaEntity.findAll", query = "SELECT p FROM PersonaEntity p"),
		@NamedQuery(name = "PersonaEntity.findByCc", query = "SELECT p FROM PersonaEntity p WHERE p.cc = :cc"),
		@NamedQuery(name = "PersonaEntity.findByNombre", query = "SELECT p FROM PersonaEntity p WHERE p.nombre = :nombre"),
		@NamedQuery(name = "PersonaEntity.findByApellido", query = "SELECT p FROM PersonaEntity p WHERE p.apellido = :apellido"),
		@NamedQuery(name = "PersonaEntity.findByGenero", query = "SELECT p FROM PersonaEntity p WHERE p.genero = :genero"),
		@NamedQuery(name = "PersonaEntity.findByEdad", query = "SELECT p FROM PersonaEntity p WHERE p.edad = :edad") })

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"telefonos", "estudios"})
public class PersonaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer cc;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String nombre;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String apellido;
	@Basic(optional = false)
	@Column(nullable = false)
	private Character genero;
	private Integer edad;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "duenio")
	private List<TelefonoEntity> telefonos;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.LAZY)
	private List<EstudiosEntity> estudios;
}

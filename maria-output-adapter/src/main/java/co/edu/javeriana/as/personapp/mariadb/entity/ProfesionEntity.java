package co.edu.javeriana.as.personapp.mariadb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author aasanchez
 */
@Entity
@Table(name="profesion", catalog = "persona_db", schema = "")
@NamedQueries({ @NamedQuery(name = "ProfesionEntity.findAll", query = "SELECT p FROM ProfesionEntity p"),
		@NamedQuery(name = "ProfesionEntity.findById", query = "SELECT p FROM ProfesionEntity p WHERE p.id = :id"),
		@NamedQuery(name = "ProfesionEntity.findByNom", query = "SELECT p FROM ProfesionEntity p WHERE p.nom = :nom") })

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "estudios")
public class ProfesionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 90)
	private String nom;
	@Lob
	@Column(length = 65535)
	private String des;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profesion")
	private List<EstudiosEntity> estudios;
}

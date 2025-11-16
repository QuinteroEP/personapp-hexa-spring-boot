package co.edu.javeriana.as.personapp.mariadb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="estudios", catalog = "persona_db", schema = "")
@NamedQueries({ @NamedQuery(name = "EstudiosEntity.findAll", query = "SELECT e FROM EstudiosEntity e"),
		@NamedQuery(name = "EstudiosEntity.findByIdProf", query = "SELECT e FROM EstudiosEntity e WHERE e.estudiosEntityPK.idProf = :idProf"),
		@NamedQuery(name = "EstudiosEntity.findByCcPer", query = "SELECT e FROM EstudiosEntity e WHERE e.estudiosEntityPK.ccPer = :ccPer"),
		@NamedQuery(name = "EstudiosEntity.findByFecha", query = "SELECT e FROM EstudiosEntity e WHERE e.fecha = :fecha"),
		@NamedQuery(name = "EstudiosEntity.findByUniver", query = "SELECT e FROM EstudiosEntity e WHERE e.univer = :univer") })

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"persona", "profesion"})
public class EstudiosEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected EstudiosEntityPK estudiosEntityPK;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@Column(length = 50)
	private String univer;
	@JoinColumn(name = "cc_per", referencedColumnName = "cc", nullable = false, insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private PersonaEntity persona;
	@JoinColumn(name = "id_prof", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private ProfesionEntity profesion;

	public EstudiosEntityPK getEstudiosPK() {
		return estudiosEntityPK;
	}

	public void setEstudiosPK(EstudiosEntityPK estudiosEntityPK) {
		this.estudiosEntityPK = estudiosEntityPK;
	}
}

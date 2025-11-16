package co.edu.javeriana.as.personapp.mariadb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name="telefono", catalog = "persona_db", schema = "")
@NamedQueries({ @NamedQuery(name = "TelefonoEntity.findAll", query = "SELECT t FROM TelefonoEntity t"),
		@NamedQuery(name = "TelefonoEntity.findByNum", query = "SELECT t FROM TelefonoEntity t WHERE t.num = :num"),
		@NamedQuery(name = "TelefonoEntity.findByOper", query = "SELECT t FROM TelefonoEntity t WHERE t.oper = :oper") })

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "duenio")
public class TelefonoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(nullable = false, length = 15)
	private String num;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String oper;
	@JoinColumn(name = "duenio", referencedColumnName = "cc", nullable = false)
	@ManyToOne(optional = false)
	private PersonaEntity duenio;
}

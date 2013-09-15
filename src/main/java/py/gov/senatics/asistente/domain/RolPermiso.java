package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ROL_PERMISO database table.
 * 
 */
@Entity
@Table(name = "ROL_PERMISO")
public class RolPermiso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ROL_PERMISO_ROLPERMISOID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_PERMISO_ROLPERMISOID_GENERATOR")
	@Column(name = "ROL_PERMISO_ID")
	private long rolPermisoId;

	// bi-directional many-to-one association to Permiso
	@ManyToOne
	@JoinColumn(name = "PERMISO_ID")
	private Permiso permiso;

	// bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name = "ROL_ID")
	private Rol rol;

	public RolPermiso() {

	}

	public long getRolPermisoId() {

		return this.rolPermisoId;
	}

	public void setRolPermisoId(long rolPermisoId) {

		this.rolPermisoId = rolPermisoId;
	}

	public Permiso getPermiso() {

		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {

		this.permiso = permiso;
	}

	public Rol getRol() {

		return this.rol;
	}

	public void setRol(Rol rol) {

		this.rol = rol;
	}

}

package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the PERMISO database table.
 * 
 */
@Entity
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERMISO_PERMISOID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERMISO_PERMISOID_GENERATOR")
	@Column(name="PERMISO_ID")
	private long permisoId;

	private String nombre;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="permiso")
	private Set<RolPermiso> rolPermisos;

    public Permiso() {
    }

	public long getPermisoId() {
		return this.permisoId;
	}

	public void setPermisoId(long permisoId) {
		this.permisoId = permisoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<RolPermiso> getRolPermisos() {
		return this.rolPermisos;
	}

	public void setRolPermisos(Set<RolPermiso> rolPermisos) {
		this.rolPermisos = rolPermisos;
	}
	
}
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
 * The persistent class for the ROL database table.
 * 
 */
@Entity
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROL_ROLID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_ROLID_GENERATOR")
	@Column(name="ROL_ID")
	private long rolId;

	private String nombre;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="rol")
	private Set<RolPermiso> rolPermisos;

	//bi-directional many-to-one association to UsuarioRol
	@OneToMany(mappedBy="rol")
	private Set<UsuarioRol> usuarioRols;

    public Rol() {
    }

	public Rol(String string) {
		setNombre(string);
	}

	public long getRolId() {
		return this.rolId;
	}

	public void setRolId(long rolId) {
		this.rolId = rolId;
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
	
	public Set<UsuarioRol> getUsuarioRols() {
		return this.usuarioRols;
	}

	public void setUsuarioRols(Set<UsuarioRol> usuarioRols) {
		this.usuarioRols = usuarioRols;
	}
	
}
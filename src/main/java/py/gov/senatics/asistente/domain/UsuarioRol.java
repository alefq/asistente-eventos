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
 * The persistent class for the USUARIO_ROL database table.
 * 
 */
@Entity
@Table(name="USUARIO_ROL")
public class UsuarioRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ROL_USUARIOROLID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ROL_USUARIOROLID_GENERATOR")
	@Column(name="USUARIO_ROL_ID")
	private long usuarioRolId;

	//bi-directional many-to-one association to Rol
    @ManyToOne
	@JoinColumn(name="ROL_ID")
	private Rol rol;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="USUARIO_ID")
	private Usuario usuario;

    public UsuarioRol() {
    }

	public long getUsuarioRolId() {
		return this.usuarioRolId;
	}

	public void setUsuarioRolId(long usuarioRolId) {
		this.usuarioRolId = usuarioRolId;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
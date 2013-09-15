package py.gov.senatics.asistente.persistence;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.gov.senatics.asistente.domain.Ciudad;

@PersistenceController
public class CiudadDAO extends JPACrud<Ciudad, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public List<Ciudad> filterCityByCountry(Long paisId) {

		Query q = em.createQuery("select c"
				+ " from Ciudad c inner join c.pais p" + " where p.paisId="
				+ paisId);
		return q.getResultList();
	}
}

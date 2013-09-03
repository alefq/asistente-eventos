package py.gov.setics.asistente.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import py.gov.senatics.asistente.domain.Usuario;
import py.gov.setics.asistente.business.UsuarioBC;

@WebServlet(name = "DownloadServlet", urlPatterns = "/download")
@MultipartConfig
public class DownloadServlet extends HttpServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private UsuarioBC usuarioBC;

	// size of byte buffer to send file
	private static final int BUFFER_SIZE = 4096;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * Este es el método invocado desde la vista para descargar el archivo
	 * 
	 * @return
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/* Obtenemos el ID para la descarga desde el parámetro del URL */
		long fileId = Long.parseLong(request.getParameter("fileId"));

		String fileName = getFilePathById(fileId);
		File fileToDownload = new File(fileName);
		if (fileToDownload.exists()) {
			if (fileToDownload.canRead()) {
				InputStream inputStream = new FileInputStream(fileName);
				int fileLength = inputStream.available();

				logger.info("A enviar archivo: " + fileName);

				logger.info("fileLength = " + fileLength);

				ServletContext context = getServletContext();

				// sets MIME type for the file download
				String mimeType = context.getMimeType(fileName);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				// set content properties and header attributes for the response
				response.setContentType(mimeType);
				response.setContentLength(fileLength);
				String headerKey = "Content-Disposition";
				String headerValue = String.format(
						"attachment; filename=\"%s\"",
						getNombreSugerido(fileId));
				response.setHeader(headerKey, headerValue);

				// writes the file to the client
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();
			} else {
				logger.error("No se tiene permisos para leer el archivo:"
						+ fileName);
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				fileToDownload = null;
			}
		} else {
			logger.error("No se encontró el archivo:" + fileName);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			fileToDownload = null;
		}

	}

	private String getNombreSugerido(Long value) {
		/*
		 * Implementar aquí la lógica que traiga la ruta sugerida para el nombre
		 * del archivo a descargar
		 */
		return "file.pdf";
	}

	private String getFilePathById(Long value) {
		/*
		 * Implementar aquí la lógica que traiga la ruta del archivo a
		 * descargar, se coloca sólo a modo de ejemplo cómo se recuperaría un
		 * dato de la base de datos por medio de un BusinessController
		 */
		Usuario usuario = usuarioBC.load(1l);
		logger.info("Prueba de recuperacion de la BD: " + usuario);
		return "/tmp/file.pdf";
	}
}

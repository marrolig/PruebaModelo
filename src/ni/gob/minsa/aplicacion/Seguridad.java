// -----------------------------------------------
// Seguridad.java
// -----------------------------------------------
package ni.gob.minsa.aplicacion;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ni.gob.minsa.ciportal.dto.InfoResultado;
import ni.gob.minsa.ciportal.servicios.PortalService;

/**
 * Servicios relacionados con la capa de seguridad del portal 
 * de aplicaciones y que implementa el llamado a los servicios EJB
 * <p>
 * @author Marlon Arróliga
 * @author <a href=mailto:marrolig@hotmail.com>marrolig@hotmail.com</a>
 * @version 1.2, &nbsp; 20/03/2012
 * @since jdk1.6.0_21
 */
public class Seguridad {

	public Seguridad() {
	}
	
	/**
	 * Verifica si el usuario se encuentra autorizado a acceder a la vista
	 * especificada mediante una ruta relativa del contexto de la aplicación.
	 * 
	 * @param pUsuarioId Identificador del usuario
	 * @param pVistaId   Ruta relativa del contexto de la aplicación (p.ej. /foo.xhtml)
	 * @param pSistema   Código del sistema
	 * @return <code>true</code> si el usuario se encuentra autorizado, <code>false</code> caso contrario
	 */
	public static boolean esUsuarioAutorizado(long pUsuarioId, String pVistaId, String pSistema) {
		
		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.esUsuarioAutorizado(pUsuarioId, pVistaId, pSistema);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * Verifica si las credenciales utilizadas, username y clave, son válidas.  Una credencial se considera
	 * válida si el usuario se encuentra activo y su cuenta de usuario se encuentra vigente.
	 * 
	 * @param pUsername username o nombre de fantasía del usuario
	 * @param pClave    clave de acceso del usuario
	 * @return Objeto InfoResultado que contiene encapsulado en la propiedad Objeto el valor Booleano que indica
	 * el resultado de la verificación de la credencial.  Si el usuario no existe o se produce un error
	 * en la verificación de la credencial, el valor Booleano será <code>false</code>.
	 */
	public static InfoResultado verificarCredenciales(String pUsername, String pClave) {
		
		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.verificarCredenciales(pUsername, pClave);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();
			
			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	oInfoResultado.setObjeto((Object) Boolean.FALSE);
        	return oInfoResultado;
		} 
	}
	
	/**
	 * Obtiene el nombre del usuario a partir del nombre de fantasía o username.
	 * Si el usuario no es encontrado o sucede un error en el servicio del portal, 
	 * retornará el username.
	 * 
	 * @param pUserName Nombre de fantasía o username del usuario
	 * @return Cadena de caracteres con el nombre del usuario
	 */
	public static String NombreUsuario(String pUserName) {
		
		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.obtenerNombreUsuario(pUserName);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();
			return pUserName;
		} 

	}

	/**
	 * Obtiene el nombre del sistema a partir del código.
	 * Si el usuario no es encontrado o sucede un error en el servicio del portal, 
	 * retornará una cadena vacía.
	 * 
	 * @param pCodigo Código del sistema
	 * @return Cadena de caracteres con el nombre del sistema.
	 */
	public static String NombreSistema(String pCodigo) {
		
		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.obtenerNombreSistema(pCodigo);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();
			return "";
		} 

	}

	public static InfoResultado guardarClave(String pUserName, String pClaveAnterior, String pClaveNueva) {

		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.cambiarClave(pUserName, pClaveAnterior, pClaveNueva);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();

			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	return oInfoResultado;
		} 

	}

	public static InfoResultado obtenerPinMd5(long pCodigoSanitario) {

		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.obtenerPinMedico(pCodigoSanitario);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();

			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	return oInfoResultado;
		} 

	}

	public static InfoResultado guardarPinMedico(long pCodigoSanitario,String pUserName) {

		InitialContext ctx;
		
		try {
			
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			return portalService.guardarPinMedico(pCodigoSanitario,pUserName);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();

			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	return oInfoResultado;
		} 

	}

	/**
	 * La solicitud de una aplicación, <code>pSistema</code>, para un usuario <code>pUserName</code>,
	 * implica el registro de los rastros de accesos (logs) de dicho a usuario al sistema y
	 * el retorno de la dirección URL para acceder al dicho recurso.<br>
	 * Este método crea una sesión en el servidor de datos y gestiona la cookie en el cliente.
	 * 
	 * @param pUserName       Username del usuario
	 * @param pSistema        Código del sistema
	 * @return InfoResultado  La propiedad Objeto contiene la cadena con la dirección URL del sistema
	 */
	public static InfoResultado solicitarAplicacion(String pUserName, String pSistema) {
		
		boolean iAutorizado=false;
		InitialContext ctx;
		
		try {
			InfoResultado oInfoResultado=new InfoResultado();
			ctx = new InitialContext();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			// primero verifica si el usuario es miembro del sistema
			Map<String,String> oSistemasAutorizados=portalService.listarSistemasAutorizados(pUserName);

			for (Map.Entry<String, String> oSistemaAutorizado : oSistemasAutorizados.entrySet()) {
				if (oSistemaAutorizado.getKey().equals(pSistema)) {
					iAutorizado=true;
					break;
				}
	        }
			
			if (!iAutorizado) {
	    		oInfoResultado.setOk(false);
	        	oInfoResultado.setMensaje("El usuario no posee autorización al sistema solicitado.");
	        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_ERROR);
	        	return oInfoResultado;
			}
			
			// obtiene el identificador de la sesión
			oInfoResultado=portalService.crearSesion(pUserName, pSistema);
			if (!oInfoResultado.isOk()) return oInfoResultado;
			
			String iSesionId=(String)oInfoResultado.getObjeto();
				
			FacesContext oContext = FacesContext.getCurrentInstance();
			Cookie oCookie = new Cookie("BDSESSIONID", iSesionId);
			oCookie.setPath("/");
			((HttpServletResponse)oContext.getExternalContext().getResponse()).addCookie(oCookie);

			// accede a la información del cliente
			HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();   

			String iServidorRemoto=httpServletRequest.getRemoteHost();
			String iUsuarioRemoto=httpServletRequest.getRemoteUser()==null?"DESCONOCIDO":httpServletRequest.getRemoteUser();

			return portalService.obtenerUrlSistema(pUserName, pSistema, iServidorRemoto, iUsuarioRemoto);

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();

			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	return oInfoResultado;
		} 
	}
	
	public static InfoResultado listarSistemasAutorizados(String pUserName) {
		
		InitialContext ctx;
		
		try {
			ctx = new InitialContext();
			InfoResultado oInfoResultado=new InfoResultado();
			PortalService portalService = (PortalService)ctx.lookup("ejb/Portal");
			oInfoResultado.setObjeto(portalService.listarSistemasAutorizados(pUserName));
			oInfoResultado.setOk(true);
			return oInfoResultado;

		} catch (NamingException e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Error: No se ha podido encontrar el servicio ejb/Portal");
			e.printStackTrace();

			InfoResultado oInfoResultado = new InfoResultado();
    		oInfoResultado.setOk(false);
        	oInfoResultado.setMensaje("Se ha producido un error en la aplicación.  Los servicios del portal no se encuentran disponibles.");
        	oInfoResultado.setGravedad(InfoResultado.SEVERITY_FATAL);
        	return null;
		} 
	}
	
	
	
}

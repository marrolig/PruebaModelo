package ni.gob.minsa.componente;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import ni.gob.minsa.aplicacion.Seguridad;
import ni.gob.minsa.ciportal.dto.InfoResultado;
import ni.gob.minsa.ciportal.dto.NodoArbol;
import ni.gob.minsa.ciportal.dto.NodoItem;
import ni.gob.minsa.ciportal.dto.NodoSubmenu;
import ni.gob.minsa.ciportal.servicios.PortalService;

@ManagedBean
@ViewScoped
public class MenuUtilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private NodoArbol oArbolMenu;
	private MenuModel menuModelo;
	private Boolean administrador;
	private String nombreUsuario;


	public MenuUtilBean() {
		
		InfoResultado oInfo=Seguridad.listarSistemasAutorizados("marrolig");
		@SuppressWarnings("unchecked")
		Map<String,String> oSistemas=(Map<String,String>)oInfo.getObjeto();
		System.out.println(oSistemas);
		
		InfoResultado oInfoPin=Seguridad.guardarPinMedico(8060,"marrolig");
		if (oInfoPin.isOk()) {
			System.out.println("---clave MD5 -----------------");
			System.out.println((String)oInfoPin.getObjeto());
		} else {
			System.out.println("---error en clave MD5 -----------------");
			System.out.println(oInfoPin.getMensaje());
		}
		
		
	}

	/**
	 * @param oMenuModel the oMenuModel to set
	 */
	public void setoArbolMenu(NodoArbol oArbolMenu) {
		this.oArbolMenu = oArbolMenu;
	}


	/**
	 * @return the oMenuModel
	 */
	public NodoArbol getoArbolMenu() {
		return oArbolMenu;
	}


	/**
	 * @param oMenuModel the oMenuModel to set
	 */
	public void setMenuModelo(MenuModel menuModelo) {
		this.menuModelo = menuModelo;
	}


	/**
	 * @return the oMenuModel
	 */
	public MenuModel getMenuModelo() {
		return menuModelo;
	}

	/**
	 * @param administrador the administrador to set
	 */
	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	/**
	 * @return the administrador
	 */
	public Boolean getAdministrador() {
		return administrador;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}


}


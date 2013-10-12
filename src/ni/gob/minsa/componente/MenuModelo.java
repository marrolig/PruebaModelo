// -----------------------------------------------
// MenuModelo.java
// -----------------------------------------------
package ni.gob.minsa.componente;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ni.gob.minsa.ciportal.dto.NodoArbol;
import ni.gob.minsa.ciportal.dto.NodoItem;
import ni.gob.minsa.ciportal.dto.NodoSubmenu;
import ni.gob.minsa.ciportal.servicios.PortalService;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 * Servicios relacionados con el componente MenuModel del Primefaces
 * y que permite obtener dicho modelo mediante un llamado a un EJB
 * que gestiona los servicios del portal de aplicaciones.
 * <p>
 * @author Marlon Arróliga
 * @author <a href=mailto:marrolig@hotmail.com>marrolig@hotmail.com</a>
 * @version 1.0, &nbsp; 07/02/2012
 * @since jdk1.6.0_21
 */
public class MenuModelo {
	
	private static InitialContext ctx;
	
	public MenuModelo() {
	}
	
	/**
	 * Obtiene el objeto MenuModel del Primefaces, haciendo un llamado al 
	 * componente EJB que gestiona los servicios del portal (ejb/Portal).<br>
	 * Retorna <code>null</code> si no se puede construir el model de menú,
	 * ya sea por denegación de acceso o por un error en la construcción del
	 * mismo, en cuyo caso verificar en el log del servidor de aplicaciones.
	 * 
	 * @param pUsuarioId  Identificador del usuario que solicita la autorización
	 * @param pSistema    Código del sistema
	 * @return            Objeto MenuModel de primefaces
	 */
	public static MenuModel obtenerMenuModelo(long pUsuarioId, String pSistema) {
		
		MenuModel oMenuModelo=new DefaultMenuModel();
		NodoArbol oArbolMenu;
		
		try {
				
			ctx = new InitialContext();
			PortalService br = (PortalService)ctx.lookup("ejb/Portal");

			oArbolMenu=br.obtenerArbolMenu(pUsuarioId, pSistema);
				
			for (NodoArbol oNodoMenu:oArbolMenu.hijos()) {
				if (oNodoMenu.getDatoNodo()!=null) {
					if (oNodoMenu.getTipoNodo()==0) {
						if (oNodoMenu.tieneHijos()) {
							NodoSubmenu oNodoSubmenu=(NodoSubmenu)oNodoMenu.getDatoNodo();
							Submenu pfSubMenu = new Submenu();
							pfSubMenu.setLabel(oNodoSubmenu.getNombre());
							poblarMenu(oNodoMenu.hijos(),pfSubMenu);
							oMenuModelo.addSubmenu(pfSubMenu);
						}
					}
					else {
						Submenu pfSubMenu = new Submenu();

						MenuItem pfMenuItem = new MenuItem();
						NodoItem oNodoItem=(NodoItem)oNodoMenu.getDatoNodo();
						pfMenuItem.setValue(oNodoItem.getNombre());
						if ((oNodoItem.getEstilo()!=null) && (!oNodoItem.getEstilo().isEmpty())) {
							pfMenuItem.setStyle(oNodoItem.getEstilo());
						}
						if ((oNodoItem.getUrl()!=null) && (!oNodoItem.getUrl().isEmpty())) {
							pfMenuItem.setUrl(oNodoItem.getUrl());
						}
						pfSubMenu.getFacets().put("label",pfMenuItem);
						oMenuModelo.addSubmenu(pfSubMenu);
					}
				}
			}
			return oMenuModelo;
			
		} catch (NamingException e) {
			System.out.println("--------------------------------------------------------------------");
			System.out.println("Error: EJB no pudo ser localizado, verificar que se encuentra activo");
			e.printStackTrace();
			return null;
		}
	}
	
	private static void poblarMenu(NodoArbol[] pNodosHijos, Submenu pSubMenu) {
		
		for(NodoArbol oNodoMenu:pNodosHijos){
			
			if (oNodoMenu.getDatoNodo()!=null) {
				if (oNodoMenu.getTipoNodo()==0) {
					Submenu pfSubMenu =new Submenu();
					NodoSubmenu oNodoSubMenu=(NodoSubmenu)oNodoMenu.getDatoNodo();
					pfSubMenu.setLabel(oNodoSubMenu.getNombre());
					if (oNodoMenu.tieneHijos()) {
						poblarMenu(oNodoMenu.hijos(),pfSubMenu);
					}
					pSubMenu.getChildren().add(pfSubMenu);
				}
				else {
					MenuItem pfMenuItem = new MenuItem();
					NodoItem oNodoItem=(NodoItem)oNodoMenu.getDatoNodo();
					pfMenuItem.setValue(oNodoItem.getNombre());
					if (oNodoItem.getEstilo()!=null && !oNodoItem.getEstilo().isEmpty()) {
						pfMenuItem.setStyle(oNodoItem.getEstilo());
					}
					if (oNodoItem.getUrl()!=null && !oNodoItem.getUrl().isEmpty()) {
						pfMenuItem.setUrl(oNodoItem.getUrl());
					}
					pSubMenu.getChildren().add(pfMenuItem);
				}
			}
		}
	}

}

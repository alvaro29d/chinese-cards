package com.ado.ui.repasar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import com.ado.domain.NivelEnum;
import com.ado.domain.Palabra;
import com.ado.service.NivelService;
import com.ado.ui.estudiar.EstudiarLayout;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Component
@Scope("prototype")
public class PruebaDeTiempoLayout extends Window {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PruebaDeTiempoLayout.class);

	private static final int INTENTOS_MINIMOS = 3;
	
	enum MODO {NORMAL,MUERTE_SUBITA}
	
	@Autowired
	private EstudiarLayout estudiarLayout;
	@Autowired
	private BarraTiempo barraTiempo;
	@Autowired
	private NivelService service;
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private VerticalLayout lyOpciones = new VerticalLayout();
	
	private Label lblCaracter = new Label();
	private Label lblPalabrasRestantes = new Label();
	private MultilineButton[] opciones = new MultilineButton[8];

	private List<Palabra> palabrasSesion = new ArrayList<Palabra>();
	private Set<Palabra> palabrasMuerteSubita = new LinkedHashSet<Palabra>();
	private int opcionCorrecta;
	private Set<Palabra> errores = new LinkedHashSet<Palabra>();
	private MODO modo;
	private int intentos;
	private ConfirmDialog.Listener ganasteListener;

	
	boolean attach;

	private String userId;
	private NivelEnum nivel;
	private int subNivel;
	
	public PruebaDeTiempoLayout() {
		
	}
	
	public void setDatos(List<Palabra> palabras, NivelEnum nivel, int subNivel, String userId, org.vaadin.dialogs.ConfirmDialog.Listener ganasteListener) {
		this.nivel = nivel;
		this.subNivel = subNivel;
		this.userId = userId;
		this.ganasteListener = ganasteListener;

		palabrasSesion.clear();
//		for(int i = 0 ; i < 8 ; i++){
//			palabrasSesion.add(palabras.get(i));
//		}
		palabrasSesion.addAll(palabras);
		initModoNormal();
		cargarEjercicio();
	}

	@Override
	public void attach() {
		super.attach();
		modo = MODO.NORMAL;
		if(!attach) {
			build();
			setListeners();
		}
	}
		
	private void build() {
		setContent(mainLayout);
		setClosable(false);
		setResizable(false);
		setWidth("800px");
		setModal(true);
		
		HorizontalLayout hlSpacer = new HorizontalLayout();
		hlSpacer.setHeight("10px");
		
		mainLayout.removeAllComponents();
		mainLayout.addComponent(initHeader());
		mainLayout.addComponent(initBarraTiempo());
		mainLayout.addComponent(initOpciones());
		mainLayout.addComponent(hlSpacer);
		mainLayout.setComponentAlignment(initBarraTiempo(), Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(lyOpciones, Alignment.MIDDLE_CENTER);
	}
	
	private com.vaadin.ui.Component initHeader() {
		GridLayout header = new GridLayout();
		header.setColumns(3);
		header.setWidth("780px");
		
		
		VerticalLayout left = new VerticalLayout();
		left.setWidth("260px");
		VerticalLayout center = new VerticalLayout();
		center.setWidth("260px");
		VerticalLayout right = new VerticalLayout();
		right.setWidth("260px");

		lblPalabrasRestantes.setWidthUndefined();
		lblPalabrasRestantes.setStyleName(ValoTheme.LABEL_H1);
		lblCaracter.setWidthUndefined();
		lblCaracter.setStyleName(ValoTheme.LABEL_H1);
		
		left.addComponent(lblPalabrasRestantes);
		center.addComponent(lblCaracter);
		header.addComponent(left);
		header.addComponent(center);
		header.addComponent(right);
		header.setColumnExpandRatio(2, 0f);
		left.setComponentAlignment(lblPalabrasRestantes, Alignment.MIDDLE_CENTER);
		center.setComponentAlignment(lblCaracter, Alignment.MIDDLE_CENTER);
		return header;
	}

	private BarraTiempo initBarraTiempo() {
		return barraTiempo;
	}

	private com.vaadin.ui.Component initOpciones() {
		lyOpciones.setSizeUndefined();
		lyOpciones.removeAllComponents();
		lyOpciones.setSpacing(true);
		for(int i = 0 ; i < opciones.length ; i++) {
			opciones[i] = new MultilineButton();
		}
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setWidth("800px");
		hl1.setSpacing(true);
		hl1.addComponents(opciones[0],opciones[1],opciones[2]);
		hl1.setComponentAlignment(opciones[0], Alignment.MIDDLE_CENTER);
		hl1.setComponentAlignment(opciones[1], Alignment.MIDDLE_CENTER);
		hl1.setComponentAlignment(opciones[2], Alignment.MIDDLE_CENTER);
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("800px");
		hl2.setSpacing(true);
		hl2.addComponents(opciones[3],opciones[4]);
		hl2.setComponentAlignment(opciones[4], Alignment.MIDDLE_CENTER);
		hl2.setComponentAlignment(opciones[3], Alignment.MIDDLE_CENTER);
		HorizontalLayout hl3 = new HorizontalLayout();
		hl3.setWidth("800px");
		hl3.setSpacing(true);
		hl3.addComponents(opciones[5],opciones[6],opciones[7]);
		lyOpciones.addComponents(hl1,hl2,hl3);
		hl3.setComponentAlignment(opciones[5], Alignment.MIDDLE_CENTER);
		hl3.setComponentAlignment(opciones[6], Alignment.MIDDLE_CENTER);
		hl3.setComponentAlignment(opciones[7], Alignment.MIDDLE_CENTER);
		return lyOpciones;
	}
	
	private void setListeners() {
		this.attach = true;
		for(MultilineButton opcion : opciones) {
			opcion.addLayoutClickListener(btnOpcionLayoutClickListener);
		}
	}

	@SuppressWarnings("serial")
	private LayoutClickListener btnOpcionLayoutClickListener = new LayoutClickListener() {
		@Override
		public void layoutClick(LayoutClickEvent event) {
			LOGGER.debug("Seleccion tarjeta");
			MultilineButton opcionSeleccionada = (MultilineButton)event.getSource();
			seleccionarOpcion(opcionSeleccionada);
		}
	};

	

	private void seleccionarOpcion(MultilineButton opcionSeleccionada) {
		intentos++;
		if(opciones[opcionCorrecta] == opcionSeleccionada) {
			if(modo == MODO.NORMAL && intentos >= INTENTOS_MINIMOS && (errores.size() ==  0 || intentos/errores.size() > 9)){
				initModoMuerteSubita();
			}
			if(palabrasMuerteSubita.size() == palabrasSesion.size()) {
				ganaste();
			} else {
				cargarEjercicio();
			}
		} else {
			if(modo == MODO.MUERTE_SUBITA) {
				gameOver();
			} else {
				errores.add(opciones[opcionCorrecta].getPalabra());
				opcionSeleccionada.removeLayoutClickListener(btnOpcionLayoutClickListener);
				opcionSeleccionada.setEnabled(false);
				deshabilitarOtro();
			}
		}
		
	}

	private void deshabilitarOtro() {
		int deshabilitar = RandomUtils.nextInt(0,8);
		for(int i = deshabilitar; i < deshabilitar + 8; i++) {
			if(opciones[i%8].isEnabled() && opcionCorrecta !=i%8){
				opciones[i%8].setEnabled(false);
				i = deshabilitar + 8;
			}
		}
	}
	
	private void cargarEjercicio() {
		LOGGER.debug("cargarEjercicio");
		Palabra palabraNueva = getPalabraNueva();
		Set<Palabra> setEjercicio = new LinkedHashSet<Palabra>();
		setEjercicio.add(palabraNueva);
		while(setEjercicio.size() < 8) {
			setEjercicio.add(palabrasSesion.get(RandomUtils.nextInt(0, palabrasSesion.size())));
		}
		
		LinkedList<Palabra> listaEjercicio = new LinkedList<Palabra>(setEjercicio);
		Collections.shuffle(listaEjercicio);
		
		Iterator<Palabra> iterator = listaEjercicio.iterator();
		for (int i = 0; i < 8; i++) {
			Palabra next = iterator.next();
			opciones[i].addLayoutClickListener(btnOpcionLayoutClickListener);
			opciones[i].setEnabled(true);
			opciones[i].setPalabra(next);
			if(palabraNueva == opciones[i].getPalabra()){
				lblCaracter.setValue(next.getCaracter());
				this.opcionCorrecta = i; 
			}
		}
		
		if(modo == MODO.NORMAL) {
			barraTiempo.setTime(1f);
			barraTiempo.setSpeed(barraTiempo.getSpeed() + 0.001f);
			
		} else {
			barraTiempo.addTime(0.4f);
			barraTiempo.setSpeed(barraTiempo.getSpeed() + 0.002f);
			lblPalabrasRestantes.setValue(String.valueOf(palabrasSesion.size() - palabrasMuerteSubita.size() + 1));
		}
		
	}

	private Palabra getPalabraNueva() {
		Palabra palabraNueva = new Palabra();
		if(modo == MODO.NORMAL){
			palabraNueva = palabrasSesion.get(RandomUtils.nextInt(0, palabrasSesion.size()));
		} else {
			int cantMuerteSubita = palabrasMuerteSubita.size();
			while(palabrasMuerteSubita.size() == cantMuerteSubita) {
				int indexPalabraNueva = RandomUtils.nextInt(0,palabrasSesion.size());
				palabraNueva = palabrasSesion.get(indexPalabraNueva);
				System.out.println(palabraNueva.getPingin());
				palabrasMuerteSubita.add(palabraNueva);
			}
			System.out.println("nro muerte subita: " + palabrasMuerteSubita.size() + " palabraNueva: " + palabraNueva.getPingin());
		}
		return palabraNueva;
	}
	
	private void ganaste() {
		service.saveAvance(userId, nivel, subNivel);
		ConfirmDialog.show(UI.getCurrent(), "Congratulations!", "You learn the level!","Save & Reload Page", "Only Save", ganasteListener);
		this.close();
	}
	
	public void gameOver() {
		errores.add(opciones[opcionCorrecta].getPalabra());
		if(((com.ado.ChineseApplication.VaadinUI)getParent())!= null) {
			((com.ado.ChineseApplication.VaadinUI)getParent()).addWindow(estudiarLayout);
			close();
			estudiarLayout.setDatos(new ArrayList<Palabra>(errores));
		}
	}
	
	private void initModoNormal() {
		System.out.println("initNormal");
		errores.clear();
		intentos = 0;
		this.modo = MODO.NORMAL;
		barraTiempo.refreshTime();
		lblPalabrasRestantes.setVisible(false);
		palabrasMuerteSubita.clear();
	}
	
	private void initModoMuerteSubita() {
		System.out.println("initMuerteSubita, errores: " + errores.size() + " intentos: "+ intentos);
		this.modo = MODO.MUERTE_SUBITA;
		barraTiempo.refreshTime();
		lblPalabrasRestantes.setVisible(true);
		lblPalabrasRestantes.setValue(String.valueOf(palabrasSesion.size()));
		palabrasMuerteSubita.clear();
	}
	

}

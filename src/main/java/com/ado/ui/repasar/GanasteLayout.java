package com.ado.ui.repasar;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Component
@Scope("prototype")
public class GanasteLayout extends Window {
	private static final long serialVersionUID = 1L;

	private VerticalLayout mainContent = new VerticalLayout(); 
	
	private Label lblGanaste = new Label();
	private Button btnAceptar = new Button("OK");

	private boolean isAttach;
	
	public GanasteLayout() {
		
	}
	
	@Override
	public void attach() {
		super.attach();
		build();
		if(!isAttach) {
			setListeners();
		}
	}


	private void build() {
		setCaption("Congratulations!");
		setModal(true);
		setClosable(false);
		setResizable(false);
		setContent(mainContent);
		setWidth("300px");
		mainContent.setMargin(new MarginInfo(true, true, true, true));
		
		mainContent.removeAllComponents();
		lblGanaste.setValue("You learn the level!");
		lblGanaste.setStyleName(ValoTheme.LABEL_COLORED);
		lblGanaste.addStyleName(ValoTheme.LABEL_H2);
		mainContent.addComponent(lblGanaste);
		mainContent.addComponent(btnAceptar);
		mainContent.setComponentAlignment(btnAceptar, Alignment.BOTTOM_RIGHT);
	}
	
	@SuppressWarnings("serial")
	private void setListeners() {
		this.isAttach = true;

		btnAceptar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
	}
	
}

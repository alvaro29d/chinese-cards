package com.ado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ado.ui.repasar.NivelesLayout;
import com.ado.ui.repasar.RepasarLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringBootApplication
public class ChineseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChineseApplication.class, args);
	}

	@Theme("valo")
	@SpringUI(path = "")
	public static class VaadinUI extends UI {
		private static final long serialVersionUID = 1L;
		
		private VerticalLayout mainLayout = new VerticalLayout();
		private VerticalLayout content = new VerticalLayout();
		

		@Autowired
		private RepasarLayout repasarLayout;

		@Autowired
		private NivelesLayout nivelesLayout;
		
		@Override
		protected void init(VaadinRequest request) {
			setContent(mainLayout);
			setSizeFull();

			MenuBar menu = new MenuBar();
			menu.setWidth("800px");
			menu.addItem("Repasar", btnRepasarCommand);
			menu.addItem("Revisar", btnRevisarCommand);
//			mainLayout.addComponent(menu);
			mainLayout.addComponent(content);
//			mainLayout.setComponentAlignment(menu, Alignment.TOP_CENTER);
			mainLayout.setComponentAlignment(content, Alignment.TOP_CENTER);
			content.setSizeUndefined();
			content.addComponent(nivelesLayout);
		}

		@SuppressWarnings("serial")
		private Command btnRepasarCommand = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				content.removeAllComponents();
				content.addComponent(repasarLayout);
//				content.setComponentAlignment(repasarLayout, Alignment.TOP_CENTER);
			}
		};

		@SuppressWarnings("serial")
		private Command btnRevisarCommand = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				content.removeAllComponents();
//				content.addComponent(repasarLayout);
				content.addComponent(nivelesLayout);
			}
		};

	}

}

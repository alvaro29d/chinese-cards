package com.ado.ui.repasar;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
public class BarraTiempo extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	
	private ProgressBar barraTiempo = new ProgressBar();

	private float speed = 0.1f;
	
	public BarraTiempo() {
		
	}
	
	public void refreshTime() {
		barraTiempo.setValue(1.0f);
	}
	
	public void addTime(float tiempo) {
		barraTiempo.setValue(barraTiempo.getValue() + tiempo);
	}
	
	public void setModoMuerteSubita() {
		
	}
	
	public void setSpeed(long speed){
		
	}
	
	@Override
	public void attach() {
		super.attach();
		build();
	}
	
	private void build() {
		removeAllComponents();
		setSizeFull();
		setSpacing(true);
		setHeight("38px");
		barraTiempo.setWidth("600px");
		barraTiempo.setValue(1.0f);
		addComponent(barraTiempo);
		setComponentAlignment(barraTiempo, Alignment.TOP_CENTER);
		
		UI.getCurrent().setPollInterval(500);
		launchProgressUpdater(UI.getCurrent());
	}
	
	
	private void launchProgressUpdater(UI ui) {
		new Thread() {
			@Override
			public void run() {
				while (barraTiempo.getValue() > 0 - speed) {
					try {
						Thread.sleep(1000);
					} catch (final InterruptedException e) {
						throw new RuntimeException("Unexpected interruption", e);
					}
					barraTiempo.setValue(barraTiempo.getValue() - speed);
				}
				((PruebaDeTiempoLayout)BarraTiempo.this.getParent().getParent()).gameOver();
			}
		}.start();
	
    }
	
	

}

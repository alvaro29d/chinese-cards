package com.ado.ui.repasar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(BarraTiempo.class);
	
	private ProgressBar barraTiempo = new ProgressBar();
	private Thread thread;
	private float speed = 0.05f;

	public BarraTiempo() {
		
	}
	
	public void refreshTime() {
		setValueSync(1f);
		speed = 0.05f;
	}
	
	public void addTime(float tiempo) {
		if(barraTiempo.getValue()+ tiempo > 1.0) {
			setValueSync(1f);
		} else {
			barraTiempo.setValue(barraTiempo.getValue() + tiempo);
		}
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
		setValueSync(1.0f);
		
		addComponent(barraTiempo);
		setComponentAlignment(barraTiempo, Alignment.TOP_CENTER);
		
		UI.getCurrent().setPollInterval(500);
		launchProgressUpdater(UI.getCurrent());
	}
	
	public void setValueSync(Float value){
		this.getParent().getParent().getUI().getSession().getLockInstance().lock();
		try {
		   barraTiempo.setValue(value);
		}catch(Exception e) {
			LOGGER.error("Error al modificar la barra de tiempo",e);
		} finally {
			this.getParent().getParent().getUI().getSession().getLockInstance().unlock();
		}
		
		
	}
	
	private void launchProgressUpdater(UI ui) {
		thread = new Thread() {
			@Override
			public void run() {
				while (barraTiempo.getValue() > 0 - speed) {
					try {
						Thread.sleep(1000);
					} catch (final InterruptedException e) {
						throw new RuntimeException("Unexpected interruption", e);
					}
					if(!BarraTiempo.this.isAttached()) {
						break;
					}
					setValueSync(barraTiempo.getValue() - speed);
				}
				if(BarraTiempo.this.isAttached()) {
					((PruebaDeTiempoLayout)BarraTiempo.this.getParent().getParent()).gameOver();
				}
			}
		};
		thread.start();
    }
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	} 
	
	public void setTime(float value) {
		setValueSync(value);
	}

}

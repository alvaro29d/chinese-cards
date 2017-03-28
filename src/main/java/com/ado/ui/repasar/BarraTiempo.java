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
	private Thread thread;
	private float speed = 0.05f;

	public BarraTiempo() {
		
	}
	
	public void refreshTime() {
		barraTiempo.setValue(1f);
		speed = 0.05f;
	}
	
	public void addTime(float tiempo) {
		if(barraTiempo.getValue()+ tiempo > 1.0) {
			barraTiempo.setValue(1f);
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
		barraTiempo.setValue(1.0f);
		
		addComponent(barraTiempo);
		setComponentAlignment(barraTiempo, Alignment.TOP_CENTER);
		
		UI.getCurrent().setPollInterval(500);
		launchProgressUpdater(UI.getCurrent());
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
					barraTiempo.setValue(barraTiempo.getValue() - speed);
				}
				if(BarraTiempo.this.isAttached()) {
//					refreshTime();
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
		barraTiempo.setValue(value);
	}

}

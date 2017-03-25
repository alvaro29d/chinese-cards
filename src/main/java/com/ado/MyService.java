package com.ado;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MyService {

	public String sayHi() {
		return "hi";
	}

}

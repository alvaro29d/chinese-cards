package com.ado;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ado.domain.NivelEnum;

public class ByteTest {

	@Test
	public void test() {
		
		
		
		byte[] list = new byte[10];
		for(byte b : list){
			System.out.println(b);
		}
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK1.getNroPalabras()/30]));
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK2.getNroPalabras()/30]));
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK3.getNroPalabras()/30]));
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK4.getNroPalabras()/30]));
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK5.getNroPalabras()/30]));
		System.out.println(Base64.getEncoder().encodeToString(new byte[NivelEnum.HSK6.getNroPalabras()/30]));
		Assert.assertEquals(true, true);
	}
	
}

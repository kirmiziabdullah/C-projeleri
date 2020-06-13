package com.deneme.soap.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.spring.guides.gs_producing_web_service.Car;

@Component
public class CarRepository {
	private static final List<Car> carList = new ArrayList<>();

	@PostConstruct
	public void initData() {//dosyadan okuma işlemi yapmak için kullandığımız fonksiyondur.findCar() fonksiyonu içerisinde kullanıyoruz
		carList.clear();
		Car car = new Car();
		try {
			String fileName = "filename.txt";
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				String part1 = parts[0]; // 004
				String part2 = parts[1];
				String part3 = parts[2];

				car = new Car();
				car.setMarka(part1);
				car.setModel(part2);
				car.setSinif(part3);
				carList.add(car);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Car> findCar(String marka, String model, String sinif, String searchKey) {//arama fonksiyonu;marka,model veya sınıfa göre arama yapılabilir.
		this.initData();
		List<Car> filteredList= carList;
		
		if(searchKey != null) {//searchKey parametresi diğer parametrelerden önceliklidir!
			for (Iterator<Car> iterator = filteredList.iterator(); iterator.hasNext(); ) {
			    Car c= iterator.next();
			    String query = c.getMarka() + c.getModel() + c.getSinif();
			    
			    if(!query.toLowerCase().contains(searchKey.toLowerCase())) {
			        iterator.remove();
			    }
			}			
		} else {
			if(marka != null) {
				for (Iterator<Car> iterator = filteredList.iterator(); iterator.hasNext(); ) {
				    Car c= iterator.next();
				    if(!c.getMarka().equalsIgnoreCase(marka)) {
				        iterator.remove();
				    }
				}
			}
			
			if(model != null) {
				for (Iterator<Car> iterator = filteredList.iterator(); iterator.hasNext(); ) {
				    Car c= iterator.next();
				    if(!c.getModel().equalsIgnoreCase(model)) {
				        iterator.remove();
				    }
				}
			}
			
			if(sinif != null) {
				for (Iterator<Car> iterator = filteredList.iterator(); iterator.hasNext(); ) {
				    Car c= iterator.next();
				    if(!c.getSinif().equalsIgnoreCase(sinif)) {
				        iterator.remove();
				    }
				}
			}
		}
		
		return filteredList;
	}
}

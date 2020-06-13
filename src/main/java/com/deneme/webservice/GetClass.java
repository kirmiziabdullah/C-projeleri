package com.deneme.webservice;

import java.io.BufferedReader;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deneme.pojo.Cars;

import io.spring.guides.gs_producing_web_service.Car;

@RestController
public class GetClass {

	@RequestMapping(value = "/carlist", method = RequestMethod.GET)//Get isteğini localhost:8080/carlist urlinden alıyoruz-soap da aynı
	public List<Cars> getCarList(String marka, String model, String sinif, String searchKey) {
		List<Cars> carList = new ArrayList<>();//br.close() komutuna kadar txt dosyasından okuma yaparak objeleri listeye alıyoruz 
		Cars car = new Cars();
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

				car = new Cars();
				car.setMarka(part1);
				car.setModel(part2);
				car.setSinif(part3);
				carList.add(car);
			}
			br.close();
			
			if(searchKey != null) {//aranan kelimenin ait olmadığı objeler listeden düşer
				for (Iterator<Cars> iterator = carList.iterator(); iterator.hasNext(); ) {
				    Cars c= iterator.next();
				    String query = c.getMarka() + c.getModel() + c.getSinif();
				    
				    if(!query.toLowerCase().contains(searchKey.toLowerCase())) {
				        iterator.remove();
				    }
				}			
			} else {
				//kullanıcı marka headerı girdiyse ve değeri bu objenin markası değils obje listeden silinir.
				if(marka != null) {
					for (Iterator<Cars> iterator = carList.iterator(); iterator.hasNext(); ) {
					    Cars c= iterator.next();
					    if(!c.getMarka().equalsIgnoreCase(marka)) {
					        iterator.remove();
					    }
					}
				}
				//kullanıcı model headerı girdiyse ve değeri bu objenin modeli değilse obje listeden silinir.
				if(model != null) {
					for (Iterator<Cars> iterator = carList.iterator(); iterator.hasNext(); ) {
					    Cars c= iterator.next();
					    if(!c.getModel().equalsIgnoreCase(model)) {
					        iterator.remove();
					    }
					}
				}
				//kullanıcı sinif headerı girdiyse ve değeri bu objenin sinif değilse listeden silinir.
				if(sinif != null) {
					for (Iterator<Cars> iterator = carList.iterator(); iterator.hasNext(); ) {
					    Cars c= iterator.next();
					    if(!c.getSinif().equalsIgnoreCase(sinif)) {
					        iterator.remove();
					    }
					}
				}
			}		
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return carList;
	}
}

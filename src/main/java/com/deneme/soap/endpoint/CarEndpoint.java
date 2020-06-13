package com.deneme.soap.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.deneme.soap.repository.CarRepository;

import io.spring.guides.gs_producing_web_service.Car;
import io.spring.guides.gs_producing_web_service.GetCarRequest;
import io.spring.guides.gs_producing_web_service.GetCarResponse;

@Endpoint
public class CarEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";//gerekli bilgilere bu kaynaktan erişilebilir

	private CarRepository carRepository;

	@Autowired
	public CarEndpoint(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarRequest")
	@ResponsePayload
	public GetCarResponse getCar(@RequestPayload GetCarRequest request) {
		GetCarResponse response = new GetCarResponse();
		List<Car> carList = carRepository.findCar(request.getMarka(), request.getModel(), request.getSinif(), request.getSearchKey());
		for (Car car : carList) {
			response.getCar().add(car);		
		}

		return response;
	}
}

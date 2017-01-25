package fr.istic.sir.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Device;
import domain.Heater;
import domain.Home;

@Path("/hello")
public class SampleWebService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello, how are you?";
	}

	@GET
	@Path("/home")
	@Produces(MediaType.APPLICATION_JSON)
	public Home getHome() {
		Home h = new Home();
		h.setSize(1000);
		Heater h1 = new Heater();
		h1.setName("heat1");
		List<Device> devices = new ArrayList<Device>();
		devices.add(h1);
		h.setDevices(devices);
		return h;
	}
}

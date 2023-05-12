package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Event;

public interface EventService {

	void saveEvent(Event event);
	void deleteEvent(int idEvent);
	List<Event> getAllEvent();
	Event getEventByIdEvent(int idEvent);
}

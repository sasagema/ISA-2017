package com.example.isa2017.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.CinemaToCinemaDTO;
import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Movie;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.repository.CinemaRepository;
import com.example.isa2017.service.CinemaService;
import com.example.isa2017.service.MovieService;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaController {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private CinemaToCinemaDTO toCinemaDTO;
	
	/*
	 *          za neregistrovane korisnike
	 * */
	
	@RequestMapping(value="getCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<CinemaDTO>> getCinemas() {
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(toCinemaDTO.convert(cinemas), HttpStatus.OK);
	}
	
	/*
	 *          za TCadmine
	 * */
	
	@RequestMapping(value="getTCadminCinemas", method = RequestMethod.GET)
	public ResponseEntity<List<Cinema>> getTCadminCinemas(HttpServletRequest request) {
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		List<Cinema> cinemas = cinemaService.findAll();
		 
		return new ResponseEntity<>(cinemas, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "editCinema/{cinemaId}", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Cinema> editMovie(HttpServletRequest request, @RequestBody Cinema cinema, @PathVariable Long cinemaId){
		
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged==null)
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		
		
		cinema.setId(cinemaId);
		cinema.setMovies(cinemaService.findOne(cinemaId).getMovies());
		Cinema editedCinema = cinemaService.save(cinema);
		
	 return new ResponseEntity<>(editedCinema, HttpStatus.OK);
	}
	
	@RequestMapping(value = "rateCinema/{cinemaId}", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<Cinema> rateCinema(@PathVariable Long cinemaId, @RequestBody Cinema rating){
		
		Cinema cinema = cinemaService.findOne(cinemaId);
		
		cinema.getRatingList().add(rating.getRating());
		
		System.out.println("integers in the rating list: ");
		for(int i : cinema.getRatingList()){
			System.out.println(i + ", ");
		}
		
		cinema.setAvgRating(cinema.calculateAverage(cinema.getRatingList()));
		
		cinemaService.save(cinema);
		
		return new ResponseEntity<>(cinema, HttpStatus.OK);
	}
	
}

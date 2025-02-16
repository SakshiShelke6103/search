package com.example.demo.CONTROLLER;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotel Controller", description = "APIs for managing hotels") // Swagger tag
public class Controller {

    private List<Hotel> hotels = new ArrayList<>();

    @GetMapping
    @Operation(summary = "Get all hotels", description = "Fetches the list of all available hotels")
    public List<Hotel> getAllHotels() {
        return hotels;
    }

    @GetMapping("/search")
    @Operation(summary = "Search hotels by location", description = "Finds hotels based on the provided location")
    public List<Hotel> searchHotelsByLocation(@RequestParam String location) {
        return hotels.stream()
                .filter(hotel -> hotel.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

   // @PostMapping
   // @Operation(summary = "Add a new hotel", description = "Creates and adds a new hotel")
    @PostMapping("/bulk-add")
    public String addMultipleHotels(@RequestBody List<Hotel> newHotels) {
        for (Hotel hotel : newHotels) {
            int newId = hotels.size() + 1; // Auto-increment ID
            hotel.setId(newId);
            hotels.add(hotel);
        }
        return newHotels.size() + " hotels added successfully!";
    }

   

    @DeleteMapping
    @Operation(summary = "Delete a hotel", description = "Deletes a hotel based on its ID")
    public String deleteHotel(@RequestParam int id) {
        boolean removed = hotels.removeIf(hotel -> hotel.getId() == id);
        return removed ? "Hotel deleted successfully!" : "Hotel not found!";
    }

    // Inner class for Hotel
    static class Hotel {
        private int id;
        private String name;
        private String location;
        private String description;
        private List<String> amenities;

        public Hotel() {}

        public Hotel(int id, String name, String location, String description, List<String> amenities) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.description = description;
            this.amenities = amenities;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public List<String> getAmenities() { return amenities; }
        public void setAmenities(List<String> amenities) { this.amenities = amenities; }

        @Override
        public String toString() {
            return "Hotel{id=" + id + ", name='" + name + "', location='" + location + "', description='" + description + "', amenities=" + amenities + "}";
        }
    }
}

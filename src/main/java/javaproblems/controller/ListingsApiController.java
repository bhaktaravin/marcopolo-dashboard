package javaproblems.controller;

import javaproblems.model.ListingsAndReviews;
import javaproblems.repository.ListingsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ListingsApiController {

    private final ListingsRepository repository;

    public ListingsApiController(ListingsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listings")
    public Map<String, Object> getListings(@RequestParam(defaultValue = "200") int limit) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ListingsAndReviews> listings = repository.findAll(PageRequest.of(0, limit)).getContent();

            List<Map<String, Object>> markers = listings.stream()
                    .filter(l -> l.getAddress() != null
                            && l.getAddress().getLocation() != null
                            && l.getAddress().getLocation().getCoordinates() != null
                            && l.getAddress().getLocation().getCoordinates().size() == 2)
                    .map(l -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("id", l.getId());
                        m.put("name", l.getName());
                        m.put("summary", l.getSummary() != null ? l.getSummary().substring(0, Math.min(150, l.getSummary().length())) : "");
                        m.put("propertyType", l.getPropertyType());
                        m.put("roomType", l.getRoomType());
                        m.put("price", l.getPrice());
                        m.put("accommodates", l.getAccommodates());
                        m.put("bedrooms", l.getBedrooms());
                        m.put("beds", l.getBeds());
                        m.put("numberOfReviews", l.getNumberOfReviews());
                        m.put("rating", l.getReviewScores() != null ? l.getReviewScores().getReviewScoresRating() : null);
                        m.put("country", l.getAddress().getCountry());
                        m.put("market", l.getAddress().getMarket());
                        m.put("lng", l.getAddress().getLocation().getCoordinates().get(0));
                        m.put("lat", l.getAddress().getLocation().getCoordinates().get(1));
                        m.put("image", l.getImages() != null ? l.getImages().getPictureUrl() : null);
                        return m;
                    })
                    .toList();

            response.put("success", true);
            response.put("count", markers.size());
            response.put("listings", markers);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            response.put("listings", List.of());
        }
        return response;
    }
}

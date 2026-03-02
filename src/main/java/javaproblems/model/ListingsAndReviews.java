
package javaproblems.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Document(collection = "listingsAndReviews")
public class ListingsAndReviews {

    @Id
    private String id;

    private String name;
    private String summary;

    @Field("property_type")
    private String propertyType;

    @Field("room_type")
    private String roomType;

    @Field("bed_type")
    private String bedType;

    private Object price;

    private Integer accommodates;
    private Integer bedrooms;
    private Integer beds;

    @Field("number_of_reviews")
    private Integer numberOfReviews;

    @Field("review_scores")
    private ReviewScores reviewScores;

    private Address address;

    private List<String> amenities;

    private Images images;

    @Getter
    @Setter
    public static class Address {
        private String street;
        private String suburb;
        private String market;
        private String country;

        @Field("country_code")
        private String countryCode;

        private Location location;
    }

    @Getter
    @Setter
    public static class Location {
        private String type;
        private List<Double> coordinates; // [longitude, latitude]
    }

    @Getter
    @Setter
    public static class ReviewScores {
        @Field("review_scores_rating")
        private Integer reviewScoresRating;
    }

    @Getter
    @Setter
    public static class Images {
        @Field("picture_url")
        private String pictureUrl;
    }
}

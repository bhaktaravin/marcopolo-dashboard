package javaproblems.repository;

import javaproblems.model.ListingsAndReviews;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingsRepository extends MongoRepository<ListingsAndReviews, String> {
}

Scalable Tracking Number Generator API
Overview
This project is a RESTful API built with Spring Boot and Java 17 that generates unique tracking numbers for parcels. It is designed to be scalable, efficient, and capable of handling high concurrency.
________________________________________
Features
•	Generates unique tracking numbers matching the pattern ^[A-Z0-9]{1,16}$
•	Accepts multiple query parameters to provide context for the tracking number
•	Ensures no duplicate tracking numbers are created
•	Handles concurrent requests efficiently
•	Returns JSON response with tracking number and creation timestamp
________________________________________
Technologies Used
•	Java 17
•	Spring Boot 3.x (Spring Web, Spring Data JPA)
•	MySQL 8.x
•	Maven for dependency management
•	Lombok (optional)
•	SecureRandom for secure random number generation
________________________________________
API Endpoint
[GET /next-tracking-number](http://localhost:8081/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-10T15:20:00%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics)


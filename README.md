Secure Document Portal API (Spring Boot Backend)
This repository contains the complete backend source code for the Shree Jenu Associates secure client portal. It is a robust, stateless RESTful API built with Java and Spring Boot, designed to handle user authentication, role-based authorization, and highly secure, scalable file storage.

The frontend React application that consumes this API is in a separate repository.

Key Features
Secure Authentication: Complete user registration and login system.

Stateless JWT Security: Implements Spring Security 6 with JSON Web Tokens (JWT) for stateless, secure API authentication.

Role-Based Access Control (RBAC): Differentiates between user roles (ROLE_USER and ROLE_ADMIN), with API endpoints protected based on the user's authority.   

Scalable File Storage: Integrates with AWS S3 for file storage, ensuring high availability and scalability.

Secure Uploads (S3 Pre-signed URLs): Implements the S3 Pre-signed URL pattern. This is a modern, highly secure architecture where the client uploads files directly to S3, bypassing the server. This offloads all bandwidth from the API, keeping it fast and lightweight.   

Data Persistence: Uses Spring Data JPA (Hibernate) to manage all data in a PostgreSQL database.

Flexible Metadata: Leverages PostgreSQL's native jsonb data type to store flexible, semi-structured metadata for uploaded documents.   

Core Architectural Concepts
This API is built on two core modern-backend concepts:

1. Stateless Authentication (Spring Security + JWT)
To support a decoupled frontend and ensure the API is scalable, security is handled statelessly.

Login: The client (React) sends an email and password to the public /api/auth/login endpoint.   

Token Generation: The AuthService uses Spring Security's AuthenticationManager to validate the credentials. If successful, the JwtService generates a signed, self-contained JSON Web Token (JWT).   

Token Storage: The client receives this token and stores it (e.g., in localStorage).

Secure Requests: For all future requests to protected endpoints (like /api/documents/initiate-upload), the client adds the token to the Authorization: Bearer <token> header.   

Token Validation: A custom JwtAuthenticationFilter intercepts every request. It validates the token's signature, and if valid, it populates the SecurityContextHolder. This tells Spring Security who the user is for that single request.   

Authorization: The SecurityConfig rules (e.g., .anyRequest().authenticated()) then check the SecurityContext and grant or deny access.   

2. Secure File Uploads (S3 Pre-signed URLs)
To handle file uploads without overloading the server, this API does not proxy files. Instead, it uses the highly scalable "pre-signed URL" pattern.

Request URL: The authenticated React client (with its JWT) sends a small JSON request to POST /api/documents/initiate-upload containing the fileName and fileType.

Authorize & Log: The DocumentController validates the user's JWT. If valid, it creates a new Document entity and saves the metadata (filename, owner) to the PostgreSQL database.

Generate URL: The S3Service uses the AWS SDK to generate a special, temporary, one-time-use URL (a pre-signed URL) that grants PUT access to a specific key in the S3 bucket.

Respond: The backend sends this unique uploadUrl back to the client.

Direct Client Upload: The React client receives this URL and makes a PUT request directly to AWS S3, with the file's binary data as the request body. The large file never touches the server, keeping the API fast and scalable.

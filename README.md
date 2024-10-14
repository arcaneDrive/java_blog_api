# java_blog_api
A simple blog API built with Spring Boot. This API allows users to create, read, update, and delete blog posts, manage user authentication, and more.

## Features:
- User authentication (JWT-based)
- CRUD operations for blog posts
- Pagination and sorting support
- Role-based access control (Admin/User)


## Getting Started

### Prerequisites
Make sure you have the following installed:
- Java 11 or higher
- Maven
- PostgreSQL (or any database you're using)

### Installation

1. Clone the repository:

   ```in your terminal```

   git clone https://github.com/kweyaanton/java_blog_api.git


2. Navigate to the project directory:

    cd java_blog_api

3. Install dependencies and package the application:

    mvn clean install

4. Set up your database:

    ```
    Open src/main/resources/application.properties
    Update the database configuration with your own credentials 
    
    ```


    spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update


5. Run the application

    mvn spring-boot:run


6. Access the api at 


### 3. **Add API Documentation**

```markdown
## API Endpoints

### Authentication
- **POST** `/auth/login` - Login and retrieve a JWT token.
  ```json
  {
    "username": "example",
    "password": "password123"
  }


<h1>Blog Posts</h1>

GET /api/posts - Retrieve a list of blog posts.
POST /api/posts - Create a new blog post (Requires ADMIN role).
GET /api/posts/{id} - Retrieve a specific post by ID.
PUT /api/posts/{id} - Update a post (Requires ADMIN role).
DELETE /api/posts/{id} - Delete a post (Requires ADMIN role).

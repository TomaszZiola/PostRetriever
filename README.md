# Post Export Service
This application provides a RESTful API endpoint /post/export to retrieve and export posts in a ZIP file format from  https://jsonplaceholder.typicode.com.

# How it Works
When a GET request is made to /post/export/{somenumber}, where {somenumber} specifies the number of posts for which comments should be fetched:

The PostController delegates the request to the PostService to retrieve the posts.
The PostService communicates with the PlaceholderClient (https://jsonplaceholder.typicode.com) to fetch posts as PostDto objects.
For each post, the service retrieves related comments.
The comments are grouped by the domain part of the email addresses (i.e., the part after the "@" symbol) of the users who posted them.
Each comments are saved into separate JSON files, named <domain_name>.json, where the comments are grouped by the email domain.
All of these JSON files are then packaged into a single ZIP file for easy retrieval.


## To build and run the application locally, use the following commands

```bash
./gradlew build
./gradlew bootRun
```

Make a GET request to http://localhost:8080/post/export/{somenumber} to download ZIP file where somenumber is a number meaning how much posts we
want to retrieve comments for

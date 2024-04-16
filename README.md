# Post Export Service
This application provides a RESTful API endpoint /post/export to retrieve and export posts in a ZIP file format from a placeholder service.

# How it Works
When a GET request is made to /post/export, the PostController delegates the request to the PostService to retrieve posts. The PostService communicates with the PlaceholderClient (https://jsonplaceholder.typicode.com) to fetch posts as PostDto objects. Each PostDto is placed in a separate file named <post_id>.json, and then everything is packaged into a ZIP file for easy retrieval of the entire set.

To build and run the application locally, use the following commands

./gradlew build
./gradlew bootRun

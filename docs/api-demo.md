API Demo

1. POST - Create a Book (201 Created)

Command:
http POST :9001/books isbn=1234567891 title="Northern Lights" author="Lyra Silverstar" price:=9.90

Result:
HTTP/1.1 201

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 9.9,
    "title": "Northern Lights"
}

2. GET - Retrieve a Book (200 OK)

Command:
http :9001/books/1234567891

Result:
HTTP/1.1 200

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 9.9,
    "title": "Northern Lights"
}

3. GET - List All Books (200 OK)

Command:
http :9001/books

Result:
HTTP/1.1 200

[
    {
        "author": "Lyra Silverstar",
        "isbn": "1234567891",
        "price": 9.9,
        "title": "Northern Lights"
    }
]

4. POST - Invalid Book (400 Bad Request)

Command:
http POST :9001/books isbn=123ABC456Z title="" author="Jon Snow" price:=9.90

Result:
HTTP/1.1 400

{
    "isbn": "The ISBN format must be valid.",
    "title": "The book title must be defined."
}

5. POST - Duplicate ISBN (422 Unprocessable Entity)

Command:
http POST :9001/books isbn=1234567891 title="Northern Lights" author="Lyra Silverstar" price:=9.90

Result:
HTTP/1.1 422

A book with ISBN 1234567891 already exists.

6. GET - Book Not Found (404 Not Found)

Command:
http :9001/books/9999999999

Result:
HTTP/1.1 404

The book with ISBN 9999999999 was not found.

7. PUT - Update Existing Book (200 OK)

Command:
http PUT :9001/books/1234567891 isbn=1234567891 title="Updated Northern Lights" author="Lyra Silverstar" price:=19.99

Result:
HTTP/1.1 200

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 19.99,
    "title": "Updated Northern Lights"
}

8. DELETE - Remove Book (204 No Content)

Command:
http DELETE :9001/books/1234567891

Result:
HTTP/1.1 204
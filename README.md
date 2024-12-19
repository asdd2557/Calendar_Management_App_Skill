
# API Specification

## Board API

### Create Board
- **Method**: `POST`
- **Endpoint**: `/boards`
- **Request Body**:
  ```json
  {
    "title": "string",
    "contents": "string",
    "email": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "title": "string",
    "contents": "string",
    "email": "string",
    "createdDate": "string",
    "updatedDate": "string"
  }
  ```
- **Status**: `201 Created`

### Get All Boards
- **Method**: `GET`
- **Endpoint**: `/boards`
- **Response**:
  ```json
  [
    {
      "id": "number",
      "title": "string",
      "contents": "string",
      "email": "string",
      "createdDate": "string",
      "updatedDate": "string"
    }
  ]
  ```
- **Status**: `200 OK`

### Get Board by ID
- **Method**: `GET`
- **Endpoint**: `/boards/{id}`
- **Response**:
  ```json
  {
    "id": "number",
    "title": "string",
    "contents": "string",
    "email": "string",
    "createdDate": "string",
    "updatedDate": "string",
    "age": "number"
  }
  ```
- **Status**: `200 OK`

### Update Board by ID
- **Method**: `PATCH`
- **Endpoint**: `/boards/{id}`
- **Request Body**:
  ```json
  {
    "title": "string",
    "contents": "string"
  }
  ```
- **Response**:
  ```json
  {
    "title": "string",
    "contents": "string"
  }
  ```
- **Status**: `200 OK`

### Delete Board
- **Method**: `DELETE`
- **Endpoint**: `/boards/{id}`
- **Status**: `200 OK`

## Member API

### Sign Up
- **Method**: `POST`
- **Endpoint**: `/members/signup`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "email": "string"
  }
  ```
- **Status**: `201 Created`

### Get Member by ID
- **Method**: `GET`
- **Endpoint**: `/members/{id}`
- **Response**:
  ```json
  {
    "id": "number",
    "email": "string"
  }
  ```
- **Status**: `200 OK`

### Update Password
- **Method**: `PATCH`
- **Endpoint**: `/members/{id}`
- **Request Body**:
  ```json
  {
    "oldPassword": "string",
    "newPassword": "string"
  }
  ```
- **Status**: `200 OK`

## User API

### Login
- **Method**: `POST`
- **Endpoint**: `/login`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "email": "string"
  }
  ```
- **Status**: `200 OK`

### Logout
- **Method**: `POST`
- **Endpoint**: `/logout`
- **Response**:
  ```json
  {
    "message": "Logged out successfully"
  }
  ```
- **Status**: `200 OK`

## Home API

### Home
- **Method**: `GET`
- **Endpoint**: `/home`
- **Cookie**:
  - Name: `userId`
- **Response**: Renders the `home` page if logged in, otherwise redirects to `login`.
- **Status**: `200 OK` (logged in) or redirect.

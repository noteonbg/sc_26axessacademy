# Rectangle PA Calculator Code Explanation & Workflow Guide

This document explains how the **Rectangle PA Calculator** full-stack code works step-by-step, including the request/response flow, validation logic, custom exception handling, and simple Axios integration.

---

## 🔄 End-to-End Execution Flow

```
+------------------+         POST /api/rectangle/calculate          +--------------------+
|  React Frontend  | ---------------------------------------------> | Spring Boot Backend|
|  (App.js)        |   Body: { "length": 5, "breadth": 4 }          | (Controller)       |
+------------------+                                                +--------------------+
         ^                                                                     |
         |                   HTTP 200 OK                                       v
         +--------------------------------------------------------- +--------------------+
                             Body: { "perimeter": 18, "area": 20 }  | Service Layer      |
                                                                    +--------------------+

------------------------------------------------------------------------------------------

+------------------+         POST /api/rectangle/calculate          +--------------------+
|  React Frontend  | ---------------------------------------------> | Spring Boot Backend|
|  (App.js)        |   Body: { "length": 0, "breadth": 0 }          | (Controller)       |
+------------------+                                                +--------------------+
         ^                                                                     |
         |                   HTTP 400 Bad Request                              v
         |                   Body: { "status": 400,                 +--------------------+
         +------------------         "message": "Length & Breadth   | Throws             |
         (Error caught by            cannot both be zero!" }        | ZeroDimensions     |
          axios try..catch)                                         | Exception          |
                                                                    +--------------------+
```

---

## 🍃 Backend Code Structure & How It Works

### 1. `RectangleRequestDto.java` (Input DTO)
- Defines the input object passed from React containing `Double length` and `Double breadth`.
- Annotations `@NotNull` and `@Min(0)` prevent null or negative numbers.

### 2. `PAResponseDto.java` (Output DTO)
- Defines the response object returned to React containing `double perimeter` and `double area`.

### 3. `ZeroDimensionsException.java` (Custom Exception)
- Extends `RuntimeException`.
- Thrown when `length == 0 && breadth == 0`.

### 4. `RectangleServiceImpl.java` (Service Logic)
- **Calculation Formulae**:
  - `area = length * breadth`
  - `perimeter = 2 * (length + breadth)`
- **Exception Rule**: Checks if `length == 0 && breadth == 0` and throws `ZeroDimensionsException`.

### 5. `GlobalExceptionHandler.java` (@RestControllerAdvice)
- Intercepts `ZeroDimensionsException` and returns a structured `ErrorResponseDto` with HTTP status `400 BAD REQUEST`.

### 6. `RectangleController.java` (REST Endpoint)
- Exposes `POST /api/rectangle/calculate`.
- Receives `@Valid @RequestBody RectangleRequestDto` and returns `ResponseEntity<PAResponseDto>` with `HttpStatus.OK` (200).

---

## ⚛️ Frontend Code Structure & How It Works

### `App.js` (Simple Axios Call - No Interceptors)
- Manages `length`, `breadth`, `result`, and `error` state.
- **Axios Execution**:
  ```javascript
  try {
    const response = await axios.post(
      'http://localhost:8080/api/rectangle/calculate',
      { length: parseFloat(length), breadth: parseFloat(breadth) }
    );
    setResult(response.data); // Renders area and perimeter
  } catch (err) {
    setError(err.response.data.message); // Renders red error banner
  }
  ```
- Directly updates React state with returned PA object or catches backend exception message.

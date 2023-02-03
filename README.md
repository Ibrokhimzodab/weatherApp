# WeatherApp
Weather app

This is test project

All endpoints excepting "/login", "/register" should be send with auth header Bearer token

Refresh token should be send to "/refreshToken" with header "refreshToken"

Common endpoints:

    POST /login (login)

    POST /refreshToken (refreshing access token by refresh token)

Endpoints for Root user:
    
    GET /users (gettinng all users on system)

    GET /users/{userId} (getting details with subscribtins by id)

    PUT /users/{userId} (changing users info)

    GET /cities (getting all cities)

    GET /cities/{cityId} (getting city by id)

    POST /cities (adding new city)

    PUT /cities/{cityId} (edit city info)

    PATCH /cities/{cityId}/disable (disbling city for subscription)

    POST /cities/{cityId}/weather (adding new weather)

    PUT /cities/{cityId}/weather (editing weather)

Endpoints for user:

    POST /register (register user, root user will be added on runtime)

    GET /user/cities (getting all active cities)

    POST /user/{userId}/subscribe/{cityId} (subscribe to city weather)

    POST /user/{userId}/unsubcribe/{cityId} (unsubscribe to city weather)

    GET /user/{userId}/wetather (get all weather from subscribed cities)


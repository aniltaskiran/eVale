## Register Customer
Register Customer to database.

`PUT /registration`

### Headers
- **accessToken** - Identifier for current session

### Parameters
- **phone** 		 - Phone Number for current Customer
- **carModel**   - Car Model for current Customer
- **carModelID** - Car Model ID for current Customer

[Car Model ID List](idlist.com)

### Response
- **result** - True or False

### Errors
- **ErrorCode1** - Caused by missing parameters
- **ErrorCode2** - cant save to server
- **ErrorCode3** - Server exploded

### Example Request
`POST /registration`

```javascript
{
	"phone":"00905342014656",
	"carModel":"lambo",
	"carModelID":"0"
}
```

### Example Response
`200 OK`

```javascript
{
	result: "true"
}
```

## Get Valet Info From Phone


`PUT /GetValetInfoFromPhone`


### Parameters
- **phone** 		 - Phone Number for current valet

### Response
- **result** - True or False

### Errors
- **400** - Bad Request
- **401** - Unauthorized
- **404** - Not found

### Example Request
`POST /registration`

```javascript
{
	"phone":"2128834401"
}

```

### Example Response
`200 OK`

```javascript
{
	"result": true,
	"Valet": {
		"isAuthorized": true,
		"isAdmin": true,
		"phone": "2128834401",
		"firstName": "Anıl",
		"surname": "Taşkıran",
		"venueId": "35"
	}
}
```

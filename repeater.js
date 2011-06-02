var express = require('express');
var app = express.createServer();

//taking care of get
app.get('/', function(req, res) {
	var date = new Date();
	if(req.param('type') == 'json') {
		res.contentType('application/json');
		var msg = {time: date};
		res.send(msg);
	}
	else {
    	res.send('time: ' + date + '\r\n');
	}
});

//taking care of post
app.use(express.bodyParser());
app.post('/', function(req, res){
  res.send(req.body);
});

app.listen(3000);


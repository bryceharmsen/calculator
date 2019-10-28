const http = require('http');

http.createServer((req, res) => {
	const {headers, method, url} = req;
	let body = [];
	req.on('error', (err) => {
		console.error(err);
		res.statusCode = 400;
		res.end();
	}).on('data, (chunk) => {
		body.push(chunk);
	}).on('end', () => {
		body = Buffer.concat(body).toString();
		//TODO figure out what goes here
		res.on('error', (err) => {
			console.error(err);
		});
		res.statusCode = 200;
		res.setHeader('Content-Type', 'application/json');
		const resBody = {headers, method, url, body};
		res.write(JSON.stringify(resBody));
		res.end();
	});
}).listen(443);

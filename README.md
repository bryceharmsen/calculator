# calculator
Calculator software for Advanced Software Engineering class.

Author: Bryce Harmsen
Date: 2019-11-4
Created based on an express-generated project.

------------------------------------------------------------------
Install:

To use this software, node.js, npm (as well as some packages managed by npm) must be installed. Installations of node.js and npm can be found online. This project's dependencies can be viewed in /nodecalculator/package.json.
To install the latest version of the listed dependencies from command-line, follow the following command template:
	npm install -g <dependency name>

The -g flag will install the dependency globally.
------------------------------------------------------------------

------------------------------------------------------------------
How to run in the command-line and use:

While inside /calculator/nodecalculator/, call the following:
	npm start app.js

Or, simply:
	npm start

This will run the calculator. The calculator is currently configured to listen on port 3000, but can be configured otherwise if needed. To access the calculator, open a browser on the installed machine and go to http://localhost:3000.
------------------------------------------------------------------

------------------------------------------------------------------
Features and technologies:
This software uses npm, express, ejs and jquery to provide a 
locally hosted calculator software. The current feature is an infix
 calculator providing +, -, \*, ^, and () operations.
------------------------------------------------------------------

------------------------------------------------------------------
Using the API:
For external use of the calculator module, the main functionality of the calculator is directed through the middleware call run(req, res, next) within /calculator/nodecalculator/rendering/calculator.js. For a non-middleware function that accepts a stringified list of operands and operations separated by spaces, use the call calculate(expression) in the same module. Calling calculate(expression) will effectively by-pass the middleware handling.
------------------------------------------------------------------
class Calculator {
    static async run(req, res, next) {
        try {
            let expression = req.query.expression;
            let calc = new Calculator();
            res.locals.result = await calc.evaluate(expression);
            next();
        } catch (error) {
            console.error(error);
        }
    }

    async evaluate(expression) {
        //split values/operators into array
        //run infix calculator
        return expression;
    }
}

module.exports = Calculator;
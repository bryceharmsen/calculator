class Calculator {
    static async run(req, res, next) {
        try {
            let expression = req.query.expression;
            let calc = new Calculator();
            let result = await calc.calculate(expression);
            res.locals.result = await calc.filterNonNumericResults(result);
            next();
        } catch (error) {
            console.error(error);
        }
    }

    async calculate(expression) {
        //split values/operators into array
        //run infix calculator
        let terms = expression.split(' ');
        let result = await this.calculateInFix(terms);
        return result;
    }

    async filterNonNumericResults(result) {
        if (result === Infinity) {
            result = "Infinity";
        } else if (result === Number.NEGATIVE_INFINITY) {
            result = "-Infinity";
        } else if (result === undefined) {
            result = "Illegal expression";
        }
        
        return result;
    }

    async calculateInFix(terms) {
        let numbers = [];
        let operators = [];
        let orderOfPrecedence = await this.getOrderOfPrecedence();
        for (let i = 0; i < terms.length; i++) {
            let term = terms[i];
            await this.processNext(term, numbers, operators, orderOfPrecedence);
        }

        if (numbers.length > 1) {
            while(operators.length > 0) {
                let operator = operators.pop();
                if (operator.includes('('))
                    return false;
                let result = await this.evaluate(numbers.pop(), numbers.pop(), operator);
                numbers.push(result);
            }
        }

        return numbers.pop();
    }

    async processNext(term, numbers, operators, orderOfPrecedence) {
        let num = parseFloat(term);
        if (''.includes(term))
            return false;
        if (!isNaN(num)) {
            numbers.push(num);
        } else {
            await this.processOperator(term, numbers, operators, orderOfPrecedence);
        }

        console.log('next term: \n', numbers, '\n', operators);
    }

    async processOperator(term, numbers, operators, orderOfPrecedence) {
        let prevOperator = operators[operators.length - 1];
        while(operators.length > 0 &&
                orderOfPrecedence.get(term) <=
                orderOfPrecedence.get(prevOperator = operators[operators.length - 1])
                    && !prevOperator.includes('(')) {
            console.log('popping: \n', numbers, '\n', operators);
            let result = await this.evaluate(numbers.pop(), numbers.pop(), operators.pop());
            numbers.push(result);
        }
        if (prevOperator && prevOperator.includes('(') && term.includes(')')) {
            operators.pop();
        }
        if (!term.includes(')')) {
            operators.push(term);
        }
    }

    async evaluate(bOperand, aOperand, operator) {
        let result = 0;
        switch(operator) {
            case '^':
                result = Math.pow(aOperand, bOperand);
                break;
            case '*':
                result = aOperand * bOperand;
                break;
            case '/':
                result = aOperand / bOperand;
                break;
            case '+':
                result = aOperand + bOperand;
                break;
            case '-':
                result = aOperand - bOperand;
                break;
            default:
                break;
        }

        return result;
    }

    async getOrderOfPrecedence() {
        let precedences = new Map();
        precedences.set(')', 0);
        precedences.set('+', 2);
        precedences.set('-', 2);
        precedences.set('/', 3);
        precedences.set('*', 3);
        precedences.set('^', 5);
        precedences.set('(', 6);
        return precedences;
    }
}

module.exports = Calculator;
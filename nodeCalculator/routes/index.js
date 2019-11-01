var express = require('express');
var router = express.Router();
var calculator = require('../rendering/calculator');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('calculator', {});
});

router.get('/calculate',
  calculator.run, 
  async (req, res, next) => {
    console.log(res.locals);
    res.json({
      result: res.locals.result
    });
  }
);

module.exports = router;

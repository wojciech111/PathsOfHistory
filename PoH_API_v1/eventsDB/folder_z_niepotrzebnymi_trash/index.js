var express = require('express');
var router = express.Router();

var db = require('../queries');


router.get('/api/puppies', db.getAllPuppies);

function getAllPuppies(req, res, next) {};

module.exports = router;
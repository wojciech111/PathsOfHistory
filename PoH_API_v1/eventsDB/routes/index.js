var express = require('express');
var router = express.Router();
var db = require('../queries');

/* GET home page. */
//router.get('/', function(req, res, next) {
// res.render('index', { title: 'Express' });
//});

router.get('/api/puppies', db.getAllPuppies);
//router.get('/api/events', db.getEvents);

router.get('/', function (req, res) {

    res.render('index', {title: 'node-postgres-promises'}); // load the single view file (angular will handle the page changes on the front-end)
});

module.exports = router;

var express = require('express');
var router = express.Router();

var db = require('../queries');


router.get('/api/puppies', db.getAllPuppies);
router.get('/api/events/', db.getEvents);
router.get('/api/events/:startDate/:endDate', db.getEvents);
router.get('/api/events/:startDate/:endDate/:startXcord/:endXcord', db.getEvents);
router.get('/api/events/:startDate/:endDate/:startXcord/:endXcord/:startYcord/:endYcord', db.getEvents);
/*router.post('/api/puppies', db.createPuppy);
router.put('/api/puppies/:id', db.updatePuppy);
router.delete('/api/puppies/:id', db.removePuppy);*/


module.exports = router;

var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

var pgp = require('pg-promise')(options);
var connectionString = 'postgres://postgres:php789@localhost:5432/eventsdb';
var db = pgp(connectionString);

function getAllPuppies(req, res, next) {
  db.any('select * from pups')
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL puppies'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}
//var pupID = parseInt(req.params.id);
//  db.one('select * from pups where id = $1', pupID)
function getEvents(req, res, next) {
  console.log("GOT API CALL");
  console.log(req.params.startDate);
  var startDate = new Date(req.params.startDate).toISOString().split('T')[0];
  var endDate = new Date(req.params.endDate).toISOString().split('T')[0];
  console.log("startDate",startDate, "endDate", endDate);
  var startXcord = parseFloat(req.params.startXcord);
  var endXcord = parseFloat(req.params.endXcord);
  console.log("startXcord",startXcord, "endXcord", endXcord);
  let queryXwhere='';
  if(startXcord <= endXcord){
    queryXwhere=`and coordinate_x >= ${startXcord} and coordinate_x <= ${endXcord}`;
  } else {
    queryXwhere=`and ((coordinate_x >= ${startXcord} and coordinate_x <= 180) or
      (coordinate_x >= -180 and coordinate_x <= ${endXcord}))`;
    console.log("inverted X");
  }
  var startYcord = parseFloat(req.params.startYcord);
  var endYcord = parseFloat(req.params.endYcord);
  console.log("startYcord",startYcord, "endYcord", endYcord);


  db.any(`select *  from events where
  date_of_event >= '${startDate}'
  and date_of_event <=  '${endDate}'
  ${queryXwhere}
  and coordinate_y >= ${startYcord}
  and coordinate_y <= ${endYcord}
  order by popularitysum desc
  limit 100`)//
    .then(function (data) {
      res.send(data)
      /*res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL events'
        });*/
    })
    .catch(function (err) {
      return next(err);
    });
}

module.exports = {
  getAllPuppies: getAllPuppies,
  getEvents: getEvents,
  /*createPuppy: createPuppy,
  updatePuppy: updatePuppy,
  removePuppy: removePuppy*/
};

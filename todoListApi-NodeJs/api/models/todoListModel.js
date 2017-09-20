'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;



var TaskSchema = new Schema({
  name: {
      type: String,
      required : 'Enter task name'
},
Created_date:{
  type: Date,
  default : Date.now
},
status: {
  type :[{
    type : String,
    enum : ['pending','ongoing','completed']
}],
default : 'pending'
}
});

module.exports = mongoose.model('Tasks',TaskSchema);


const express = require('express')
const consola = require('consola')
const cors = require('cors')
const { Nuxt, Builder } = require('nuxt')
const app = express()
const host = process.env.HOST || '127.0.0.1'
const port = process.env.PORT || 3000
const bodyParser = require('body-parser');

const email = require('emailjs')

app.set('port', port);
app.use(cors());
// Import and Set Nuxt.js options
let config = require('../nuxt.config.js')
config.dev = !(process.env.NODE_ENV === 'production')


app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true }));

function setSMTP() {
  return email.server.connect({
    user: "daeproject2019@gmail.com",
    password: "DAEproject",
    host: "smtp.gmail.com",
    port: 465,
    ssl: true
  });
}

async function start() {
  // Init Nuxt.js
  const nuxt = new Nuxt(config)

  // Build only in dev mode
  if (config.dev) {
    const builder = new Builder(nuxt)
    await builder.build()
  }


  app.post('/email', (req, res) => {
    var emailInstance = setSMTP();
    var emailClient = req.body.emailClient;
    var action = req.body.action;
    var subject = "";
    var text = "";
    var nameProdConf = req.body.nameProdConf;
    var username = req.body.username;
    var type = req.body.type;

    if(action == "Update"){
      var subject = "Product Configuration updated";
      var text = "Hello " +username +". Your "+type+" " + nameProdConf + " was updated";
    }
    if(action == "Created"){
      var subject = "Product Configuration created";
      var text = "Hello " +username +". Your "+type+" "  + nameProdConf + " was created";
    }
    if(action == "Remove"){
      var subject = "Product Configuration removed";
      var text = "Hello " +username +".  Your "+type+" "  + nameProdConf + " was removed";
    }


    var message	= {
      text:	text, 
      from:	"daeproject2019@gmail.com", 
      to:	 emailClient,
      subject: subject
   };
   
    emailInstance.send(message, () => {
      res.json({ msg: 'email sent' })
    })
  });

  // Give nuxt middleware to express
  app.use(nuxt.render)

  

  // Listen the server
  app.listen(port, host)
  consola.ready({
    message: `Server listening on http://${host}:${port}`,
    badge: true
  })
}
start()

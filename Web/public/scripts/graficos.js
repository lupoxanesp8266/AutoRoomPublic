function sensors(){
    // Initialize Firebase
      var firebaseConfig = {
  apiKey: "AIzaSyCbVmq1z2VcytfOA9jYtZY5IVmhbVZEvnc",
  authDomain: "myautoroom.firebaseapp.com",
  databaseURL: "https://myautoroom.firebaseio.com",
  projectId: "myautoroom",
  storageBucket: "myautoroom.appspot.com",
  messagingSenderId: "1098255606133",
  appId: "1:1098255606133:web:4efffd08613d72fa"
};
        // Initialize Firebase
        firebase.initializeApp(firebaseConfig);

var tempExt = document.getElementById('tempExt');
var tempInt = document.getElementById('tempInt');
var mov = document.getElementById('mov');

var dbRef = firebase.database();

var tempExterior = dbRef.ref('sensores').child('temp_ext');
var tempInterior = dbRef.ref('sensores').child('temp_int');
var movimiento = dbRef.ref('sensores').child('movimiento');


movimiento.on('value', function(snap){
    mov.innerHTML = snap.val()
});
tempInterior.on('value',function(snap){
    tempInt.innerText = snap.val()
});
tempExterior.on('value',function(snap){
    tempExt.innerText = snap.val()
});
    
}


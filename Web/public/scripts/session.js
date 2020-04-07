firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
      var user = firebase.auth().currentUser;
      var name;

if (user != null) {
  name = user.email;
}

      var dbRef = firebase.database();
    // User is signed in.
      document.getElementById("descarga").style.display="inline-block";
      document.getElementById("in").style.display="none";//Boton iniciar sesion
      document.getElementById("ilum").style.display="none";//Div iluminacion
      document.getElementById("out").style.display="inline-block";//Boton de cerrar sesion
      document.getElementById("iluminacion").style.display="inline-block";//Boton de la iluminacion.
      document.getElementById("sensors").style.display="inline-block";//Boton de la temperatura
      document.getElementById("comfort").style.display="inline-block";//Boton del comfort
      document.getElementById("leds").style.display="inline-block";//Boton de los leds
      document.getElementById("circle1").style.display="none";//Div del circulo
      document.getElementById("sensores").style.display="inline-block";//Div de los sensores
      document.getElementById("seccion").style.display="none";//Section de inicio de sesión
      document.getElementById("seccion1").style.display="block";//Seccion que contiene los botones
      document.getElementById("h").innerHTML = name;//H1 para la bienvenida
      document.getElementById("titulo").innerHTML = "Welcome";//Titulo de la pagina
      document.getElementById("Cleds").style.display="none";
      document.getElementById("estilos").href = "styles/style1.css";//Cambio de estilo de la pagina
      sensors();//Leer los sensores
  } else {
    // No user is signed in.
      document.getElementById("in").style.display="block";
      document.getElementById("descarga").style.display="none";
      document.getElementById("out").style.display="none";
      document.getElementById("aire").style.display="none";
      document.getElementById("Cleds").style.display="none";
      document.getElementById("iluminacion").style.display="none";//Boton iniciar sesion
      document.getElementById("ilum").style.display="none";
      document.getElementById("leds").style.display="none";
      document.getElementById("seccion").style.display="inline-block";
      document.getElementById("h").innerHTML = "Iniciar Sesión";
      document.getElementById("sensores").style.display="none";
      document.getElementById("circle1").style.display="block";
      document.getElementById("estilos").href = "styles/estilo.css";
      document.getElementById("titulo").innerHTML = "Iniciar sesión";
      document.getElementById("seccion1").style.display="none";
  }
});

function comfort(){
    document.getElementById("aire").style.display="inline-block";//Contenido de los leds
    document.getElementById("sensores").style.display="none";//Contenido de los sensores
    document.getElementById("Cleds").style.display="none";//Contenido de los sensores
    document.getElementById("ilum").style.display="none";//Contenido de la iluminacion
    
    var estado = document.getElementById('status');
    var consig = document.getElementById('consigna');
    
    var dbRef = firebase.database();
    
    var st = dbRef.ref('comfort').child('modo');
    var cons = dbRef.ref('comfort').child('consigna');
    
    st.on('value',function(snap){
        estado.innerHTML = snap.val();
    });
    
    cons.on('value',function(snap){
        consig.innerHTML = snap.val() + " ºC";
    });
}

function control_leds(){
    document.getElementById("Cleds").style.display="inline-block";//Contenido de los leds
    document.getElementById("aire").style.display="none";//Contenido de los leds
    document.getElementById("sensores").style.display="none";//Contenido de los sensores
    document.getElementById("ilum").style.display="none";//Contenido de la iluminacion
    
    var verde = document.getElementById('green');
    var rojo = document.getElementById('red');
    var blanco_l = document.getElementById('whiteL');
    var blanco_r = document.getElementById('whiteR');
    var cama = document.getElementById('bed');

    var dbRef = firebase.database();
    
    var green = dbRef.ref('leds').child('verdes');
    var red = dbRef.ref('leds').child('rojos');
    var whiteL = dbRef.ref('leds').child('blanco_l');
    var whiteR = dbRef.ref('leds').child('blanco_r');
    var bed = dbRef.ref('leds').child('blanco_cama');
    
    bed.on('value',function(snap){
        cama.innerHTML = snap.val() + " %";
    });
    
    green.on('value',function(snap){
        verde.innerHTML = snap.val() + " %";
    });
    
    red.on('value',function(snap){
        rojo.innerHTML = snap.val() + " %";
    });
    
    whiteL.on('value',function(snap){
        blanco_l.innerHTML = snap.val() + " %";
    });
    
    whiteR.on('value',function(snap){
        blanco_r.innerHTML = snap.val() + " %";
    });
}

function ilum(){
    document.getElementById("Cleds").style.display="none";//Contenido de los leds
    document.getElementById("ilum").style.display="inline-block";//Boton iniciar sesion
    document.getElementById("sensores").style.display="none";//Boton iniciar sesion
    document.getElementById("aire").style.display="none";//Contenido de los leds
    
    var bed = document.getElementById('cama');
    var table = document.getElementById('mesa');
    var up = document.getElementById('general');
    
    var autobed = document.getElementById('camaAuto');
    var autotable = document.getElementById('mesaAuto');
    var autoup = document.getElementById('upAuto');

    var dbRef = firebase.database();
    
    var estadoCama = dbRef.ref('iluminacion').child('luces').child('cama');
    var estadoMesa = dbRef.ref('iluminacion').child('luces').child('mesa');
    var estadoGeneral = dbRef.ref('iluminacion').child('luces').child('general');
    
    var estadoAutoCama = dbRef.ref('iluminacion').child('auto').child('cama');
    var estadoAutoMesa = dbRef.ref('iluminacion').child('auto').child('mesa');
    var estadoAutoGeneral = dbRef.ref('iluminacion').child('auto').child('general');
    
    estadoAutoCama.on('value',function(snap){
        if(snap.val()){
            autobed.innerHTML = "Automático";
        }else{
            autobed.innerHTML = "Manual";
        }

    });
    
    estadoAutoMesa.on('value',function(snap){
        if(snap.val()){
            autotable.innerHTML = "Automático";
        }else{
            autotable.innerHTML = "Manual";
        }

    });
    
    estadoAutoGeneral.on('value',function(snap){
        if(snap.val()){
            autoup.innerHTML = "Automático";
        }else{
            autoup.innerHTML = "Manual";
        }

    });
    
    estadoCama.on('value',function(snap){
        if(snap.val()){
            bed.setAttribute('checked',true);
        }else{
            bed.removeAttribute('checked');
        }

    });
    
    estadoMesa.on('value',function(snap){
        if(snap.val()){
            table.setAttribute('checked',true);
        }else{
            table.removeAttribute('checked');
        }

    });
    estadoGeneral.on('value',function(snap){
        if(snap.val()){
            up.setAttribute('checked',true);
        }else{
            up.removeAttribute('checked');
        }
    });
    
}

function reg(){
    
    var email = document.getElementById('usr').value;
    var password = document.getElementById('pss').value;
    
      if (email.length < 4) {
        alert('Please enter an email address.');
        return;
      }
      if (password.length < 4) {
        alert('Please enter a password.');
        return;
      }
    firebase.auth().createUserWithEmailAndPassword(email, password).catch(function(error) {
  // Handle Errors here.
  var errorCode = error.code;
  var errorMessage = error.message;
  // ...
});


}

function login(){
    var user = document.getElementById("usr").value;
    var psw = document.getElementById("pss").value;
    
    firebase.auth().signInWithEmailAndPassword(user, psw).catch(function(error) {
  // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;
        
    window.alert(errorMessage);
        
});
    
}

function logout(){
    firebase.auth().signOut().then(function() {
  // Sign-out successful.
    }).catch(function(error) {
  // An error happened.
    });

}

function sensors(){
    
    document.getElementById("Cleds").style.display="none";//Contenido de los leds
    document.getElementById("ilum").style.display="none";//Boton iniciar sesion
    document.getElementById("sensores").style.display="inline-block";//Boton iniciar sesion
    document.getElementById("aire").style.display="none";//Contenido de los leds

    var tempExt = document.getElementById('tempExt');
    var tempInt = document.getElementById('tempInt');
    var mov = document.getElementById('mov');
    
    var dbRef = firebase.database();
    
    var tempExterior = dbRef.ref('sensores').child('temp_ext');
    var tempInterior = dbRef.ref('sensores').child('temp_int');
    var movimiento = dbRef.ref('sensores').child('movimiento');
    
    
    movimiento.on('value', function(snap){
        mov.innerHTML = snap.val();
    });
    tempInterior.on('value',function(snap){
        tempInt.innerText = snap.val();
    });
    tempExterior.on('value',function(snap){
        tempExt.innerText = snap.val();
    });
}
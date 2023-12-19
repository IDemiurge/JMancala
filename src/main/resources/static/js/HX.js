    console.log("HX LOADED "); // Debugging line
var elements = document.getElementsByClassName('HX');

var id = sessionStorage.getItem('tabId');

           for (var i = 0; i < elements.length; i++) {
           console.log("HX ADD ", id); // Debugging line

               elements[i].setAttribute('hx-vals', JSON.stringify({ tabId: id }));
           }
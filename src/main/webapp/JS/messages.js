/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    var suggestions = [
        "Sto andando a fare la spesa, manca qualcosa?", 
        "Lista modificata. Guarda cosa ho aggiunto", 
        "Spesa fatta. Ti puoi rilassare",
        "Ho convidiso la lista con un'altra persona",
        "Puoi andare a fare la spesa?"
    ];

    for(var i = 0; i < suggestions.length; i++){
        $("#suggestions-btns").append('<li><a href="#">'+suggestions[i]+'</a></li>');
    }
});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    var suggestions = [{value:"Sto andando a fare la spesa, manca qualcosa?",data:"a"}];//, "Lista modificata. Guarda cosa ho aggiunto", "Spesa fatta. Ti puoi rilassare"];

    $("#btn-input").autocomplete({lookup:suggestions});
});
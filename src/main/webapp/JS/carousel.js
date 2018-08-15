/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


  jQuery(document).ready(function($) {
 
        $('#myCarousel').carousel({
                interval: 50
        });
 
        $('#carousel-text').html($('#slide-content-0').html());
 
 
});